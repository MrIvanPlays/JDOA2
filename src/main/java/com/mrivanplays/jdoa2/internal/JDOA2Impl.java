package com.mrivanplays.jdoa2.internal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.natanbc.reliqua.Reliqua;
import com.github.natanbc.reliqua.request.PendingRequest;
import com.mrivanplays.jdoa2.ApplicationInfo;
import com.mrivanplays.jdoa2.CurrentUser;
import com.mrivanplays.jdoa2.DiscordToken;
import com.mrivanplays.jdoa2.Guild;
import com.mrivanplays.jdoa2.JDOA2;
import com.mrivanplays.jdoa2.JDOA2Utils;
import com.mrivanplays.jdoa2.MissingScopeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JDOA2Impl extends Reliqua implements JDOA2 {

    private ObjectMapper jsonMapper;

    private DiscordToken token;
    private long tokenGeneratedIn;

    private ApplicationInfo applicationInfo;

    private static final String BASE_API_URL = "https://discordapp.com/api";
    private static final String USER_IDENTIFICATION_URL = BASE_API_URL + "/users/@me";
    private static final String USER_GUILDS_URL = USER_IDENTIFICATION_URL + "/guilds";
    private static final String TOKEN_BASE_URL = BASE_API_URL + "/oauth2/token";
    private static final String USER_AGENT = "JDOA2 Oauth2(" + JDOA2Utils.GITHUB + " | " + JDOA2Utils.VERSION + ")";

    public JDOA2Impl(ApplicationInfo applicationInfo, ObjectMapper jsonMapper, OkHttpClient httpClient) {
        super(httpClient);
        this.applicationInfo = applicationInfo;
        this.jsonMapper = jsonMapper;
    }

    @Override
    @Nonnull
    public DiscordToken doTokenExchange() {
        RequestParams params = getTokenRequestParams();
        params.put("grant_type", "authorization_code");
        params.put("code", applicationInfo.getAuthCode());
        return doTokenExchange(params);
    }

    @Override
    @Nonnull
    public DiscordToken doTokenExchangeUsingRefreshToken() {
        if (token != null) {
            RequestParams params = getTokenRequestParams();
            params.put("grant_type", "refresh_token");
            params.put("refresh_token", token.getRefreshToken());
            return doTokenExchange(params);
        } else {
            return doTokenExchange();
        }
    }

    @Override
    public Optional<DiscordToken> getCurrentToken() {
        return Optional.ofNullable(token);
    }

    private DiscordToken doTokenExchange(RequestParams params) {
        Request request = new Request.Builder()
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), params.toEncodedString()))
                .url(TOKEN_BASE_URL)
                .header("User-Agent", USER_AGENT)
                .build();
        Call call = getHttpClient().newCall(request);
        try (Response response = call.execute()) {
            String body = response.body().string();
            int statusCode = response.code();
            if (statusCode == 429) {
                RateLimitedResponse rateLimited = jsonMapper.readValue(body, RateLimitedResponse.class);
                if (rateLimited.getMessage() != null && rateLimited.getRetryAfter() != 0) {
                    try {
                        Thread.sleep(rateLimited.getRetryAfter());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return doTokenExchange(params);
                } else {
                    throw new RuntimeException("Rate limited");
                }
            } else if (statusCode != 200) {
                ErrorResponse errorResponse = jsonMapper.readValue(body, ErrorResponse.class);
                if (errorResponse.getError() != null) {
                    throw new AuthenticationException(errorResponse.getError() + ":" +
                            (errorResponse.getMessage() == null ? "no message specified" : errorResponse.getMessage()));
                }
            }
            DiscordToken token = jsonMapper.readValue(body, DiscordToken.class);
            this.token = token;
            this.tokenGeneratedIn = System.currentTimeMillis();
            return token;
        } catch (IOException e) {
            throw new AuthenticationException(e);
        }
    }

    private RequestParams getTokenRequestParams() {
        RequestParams params = new RequestParams();
        params.put("redirect_uri", applicationInfo.getRedirectUri());
        params.put("client_id", applicationInfo.getClientId());
        params.put("client_secret", applicationInfo.getClientSecret());
        return params;
    }

    @Override
    public boolean isCurrentTokenValid() {
        if (token == null) {
            return false;
        }
        return System.currentTimeMillis() > (this.tokenGeneratedIn + (token.getExpiresIn() * 1000));
    }

    @Override
    public PendingRequest<CurrentUser> getCurrentUser() {
        return createRequest(new Request.Builder()
                .get()
                .url(USER_IDENTIFICATION_URL)
                .header("User-Agent", USER_AGENT)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).build((response) -> {
            String body = response.body().string();
            int statusCode = response.code();
            if (statusCode == 429) {
                throw new RuntimeException("Rate limited");
            } else if (statusCode != 200) {
                ErrorResponse errorResponse = jsonMapper.readValue(body, ErrorResponse.class);
                if (errorResponse.getError() != null) {
                    throw new AuthenticationException(errorResponse.getError() + ":" +
                            (errorResponse.getMessage() == null ? "no message specified" : errorResponse.getMessage()));
                }
            }
            JsonNode node = jsonMapper.readTree(body);
            return new CurrentUser(
                    node.get("id").asText(),
                    node.get("username").asText(),
                    node.get("discriminator").asText(),
                    optString(node, "avatar"),
                    node.get("mfa_enabled").asBoolean(),
                    optBoolean(node, "verified"),
                    optString(node, "email"),
                    node.get("locale").asText(),
                    node.get("flags").asInt()
            );
        }, null);
    }

    private String optString(JsonNode node, String path) {
        JsonNode pathNode = node.get(path);
        return pathNode == null ? null : pathNode.asText();
    }

    private boolean optBoolean(JsonNode node, String path) {
        JsonNode pathNode = node.get(path);
        return pathNode != null && pathNode.asBoolean();
    }

    @Override
    public PendingRequest<List<Guild>> getCurrentUserGuilds() {
        if (!JDOA2Utils.contains("guilds", token.parseScopes())) {
            throw new MissingScopeException("guilds");
        }
        return createRequest(new Request.Builder()
                .get()
                .url(USER_GUILDS_URL)
                .header("User-Agent", USER_AGENT)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).build((response) -> {
            String body = response.body().string();
            int responseCode = response.code();
            if (responseCode == 429) {
                throw new RuntimeException("Rate limited");
            } else if (responseCode != 200) {
                ErrorResponse errorResponse = jsonMapper.readValue(body, ErrorResponse.class);
                if (errorResponse.getError() != null) {
                    throw new AuthenticationException(errorResponse.getError() + ":" +
                            (errorResponse.getMessage() == null ? "no message specified" : errorResponse.getMessage()));
                }
            }
            ArrayNode arrayNode = (ArrayNode) jsonMapper.readTree(body);
            List<Guild> guilds = new ArrayList<>();
            for (JsonNode node : arrayNode) {
                guilds.add(new Guild(node.get("id").asText(),
                        node.get("name").asText(),
                        optString(node, "icon"),
                        node.get("owner").asBoolean(),
                        node.get("permissions").asInt()));
            }
            return guilds;
        }, null);
    }

    @Override
    @Nonnull
    public OkHttpClient getHttpClient() {
        return super.getClient();
    }

    @Override
    @Nonnull
    public ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    @Override
    @Nonnull
    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }
}

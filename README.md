![license](https://img.shields.io/github/license/MrIvanPlays/JDOA2.svg?style=for-the-badge)
![issues](https://img.shields.io/github/issues/MrIvanPlays/JDOA2.svg?style=for-the-badge)
[![support](https://img.shields.io/discord/493674712334073878.svg?colorB=Blue&logo=discord&label=Support&style=for-the-badge)](https://mrivanplays.com/discord)
![version](https://img.shields.io/maven-metadata/v?color=blue&label=latest%20version&metadataUrl=https%3A%2F%2Frepo.mrivanplays.com%2Frepository%2Fivan-snapshots%2Fcom%2Fmrivanplays%2Fjdoa2%2Fmaven-metadata.xml&style=for-the-badge)
# JDOA2

Discord OAuth2 API wrapper for Java, for creating apps using discord account as authentication.

# Information
Important thing to know is that the library only supports discord scopes "identify, guilds, email". 

Please see ApplicationInfo#getAuthCode to understand how actually to implement your discord application with this library.

# Installation

Installing JDOA2 is just like every other dependency:

Maven:
```html
    <repositories>
       <repository>
           <id>ivan</id>
           <url>https://repo.mrivanplays.com/repository/ivan/</url>
       </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.mrivanplays</groupId>
            <artifactId>jdoa2</artifactId>
            <version>VERSION</version> <!-- Replace with latest version -->
            <scope>compile</scope>
        </dependency>
    </dependencies>
```

Gradle:
```groovy
repositories {
    maven {
        url "https://repo.mrivanplays.com/repository/ivan/"
    }
}

dependencies {
    implementation 'com.mrivanplays:jdoa2:VERSION' // Replace VERSION with latest version
}
```

# Usage

Example spring controller: 
```java
@Controller
public class TestController {


    @GetMapping("/")
    public ResponseEntity<CurrentUser> render(@RequestParam(value = "code", required = false, defaultValue = "") String authCode) {
        if (authCode.isEmpty()) {
            return ResponseEntity.ok(
                    new CurrentUser("0", "unknown", "unknown", "unknown",
                            false, false, false, "unknown", "unknown", 0)
            );
        }
        JDOA2 jdoa2 = JDOA2.builder()
                .applicationInfo(
                        ApplicationInfo.builder()
                                .clientId("exampleClientId")
                                .clientSecret("exampleClientSecret")
                                .redirectUri("http://exampleRedirect.uri/")
                                .authCode(authCode)
                                .build()
                )
                .build();
        jdoa2.doTokenExchange();
        return ResponseEntity.ok(jdoa2.getCurrentUser().execute());
    }
}
```

You can see the example application of JDOA2 [here](https://discordapp.com/api/oauth2/authorize?client_id=696257953493418035&redirect_uri=https%3A%2F%2Fjdoa2example.mrivanplays.com%2F&response_type=code&scope=identify%20email%20guilds)

# Javadocs
Can be found [here](https://mrivanplays.com/javadocs/JDOA2/com/mrivanplays/jdoa2/package-summary.html)
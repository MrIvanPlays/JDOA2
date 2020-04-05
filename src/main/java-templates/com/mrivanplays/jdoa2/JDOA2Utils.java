package com.mrivanplays.jdoa2;

/**
 * A utility class. Provides information for the library, also stores jdoa2 useful information and utility methods.
 */
public class JDOA2Utils {

    /**
     * Default avatars' icon hashes.
     */
    public static final String[] DEFAULT_AVATARS = new String[]{
            "6debd47ed13483642cf09e832ed0bc1b",
            "322c936a8c8be1b803cd94861bdfa868",
            "dd4dbc0016779df1378e7812eabaa04d",
            "0e291f67c9274a1abdddeb3fd919cbaa",
            "1cbd08c76f8af6dddce02c5138971129"
    };

    /**
     * Version of the library. Changed at compiling.
     */
    public static String VERSION = "${project.version}";

    /**
     * GitHub URL of the library.
     */
    public static String GITHUB = "https://github.com/MrIvanPlays/JDOA2";

    /**
     * The lead developer of the library's name.
     */
    public static String AUTHOR = "MrIvanPlays";

    /**
     * Returns whenever the specified {@link String} contains into the specified {@link String[] String[]}.
     *
     * @param s    string, checked if contains
     * @param sArr the array which is checked if the string contains
     * @return <code>true</code> if contains, <code>false</code> otherwise
     */
    public static boolean contains(String s, String[] sArr) {
        for (String inArr : sArr) {
            if (inArr.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}

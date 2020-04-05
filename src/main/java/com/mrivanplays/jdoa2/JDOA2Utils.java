package com.mrivanplays.jdoa2;

/**
 * A utility class, storing information as constants, and providing some utility methods.
 */
public class JDOA2Utils {

    public static final String[] DEFAULT_AVATARS = new String[]{
            "6debd47ed13483642cf09e832ed0bc1b",
            "322c936a8c8be1b803cd94861bdfa868",
            "dd4dbc0016779df1378e7812eabaa04d",
            "0e291f67c9274a1abdddeb3fd919cbaa",
            "1cbd08c76f8af6dddce02c5138971129"
    };

    public static boolean contains(String s, String[] sArr) {
        for (String inArr : sArr) {
            if (inArr.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}

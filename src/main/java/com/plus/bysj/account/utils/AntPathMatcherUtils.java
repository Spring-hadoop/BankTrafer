package com.plus.bysj.account.utils;

import org.springframework.util.AntPathMatcher;

import java.util.List;

public class AntPathMatcherUtils {
    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    public static boolean match(List<String> patterns, String path){
        boolean isMatch = false;
        for (int i = 0; i < patterns.size(); i++) {
            String p = patterns.get(i);
            isMatch = macthSingle(p, path);
            if(isMatch) break;
        }
        return isMatch;
    }

    public static boolean macthSingle(String p, String path){
        return antPathMatcher.match(p, path);
    }
}

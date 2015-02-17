package com.everis.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadenaUtil {

    public CadenaUtil() {
        super();
    }
    
    public static int match(String value, String pattern) {
        int i = 0;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        while(m.find()){
            i++;
        }
        
        return i;
    }
    
}

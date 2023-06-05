package com.tabber.tabby.utils;

import org.springframework.stereotype.Component;

public class StringUtil {

    public static String addHttpsToURI(String url){
        if(url.indexOf("http://") == 0 || url.indexOf("https://") == 0){
            return url;
        }
        url = "https://"+url;
        return url;
    }

}

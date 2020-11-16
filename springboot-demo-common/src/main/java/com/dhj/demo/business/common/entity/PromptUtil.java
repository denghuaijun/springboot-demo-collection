package com.dhj.demo.business.common.entity;


import java.util.ResourceBundle;

public class PromptUtil {
   // private static final Logger log = LogFactory.getLog(PromptUtil.class);
    private static ResourceBundle bundle;

    static {
        try {
            bundle = ResourceBundle.getBundle("prompt");
        } catch (Exception e) {
         //   log.error("", e);
        }
    }

    public static String v(String key) {
        return bundle.getString(key);
    }
}

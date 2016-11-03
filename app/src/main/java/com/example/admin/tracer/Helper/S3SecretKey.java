package com.example.admin.tracer.Helper;

/**
 * Created by admin on 2016-10-20.
 */
public class S3SecretKey {
    private static String accesskey;
    private static String secretkey;

    public static void setKey(String a , String b){
        accesskey = a;
        secretkey = b;
    }

    public static String getAccesskey(){
        return accesskey;
    }

    public static String getSecretkey(){
        return secretkey;
    }
}

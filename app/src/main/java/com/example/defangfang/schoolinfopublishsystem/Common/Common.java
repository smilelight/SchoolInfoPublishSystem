package com.example.defangfang.schoolinfopublishsystem.Common;

/**
 * Created by defangfang on 2017/5/10.
 */

public class Common {
    public static final String INFOFILENAME = "info";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String CLIENTID= "clientid";
    public static final String SAVEINFO = "saveinfo";
    public static final String AUTOLOGIN = "autologin";
    private static String name;
    private static String password;
    private static String clientId;
    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Common.name = name;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Common.password = password;
    }

    public static void saveInfo(String name,String password){
        setName(name);
        setPassword(password);
    }

    public static String getClientId() {
        return clientId;
    }

    public static void setClientId(String clientId) {
        Common.clientId = clientId;
    }
}

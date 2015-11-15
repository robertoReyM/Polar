package com.smartplace.polar.helpers;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by robertoreym on 21/03/15.
 */
public class MemoryServices {

    private static final String ARG_FILE = "APP";
    private static final String ARG_PUBLIC_KEY = "PUBLIC_KEY";
    private static final String ARG_USER_DATA = "USER_DATA";
    private static final String ARG_USER_TEAM = "USER_TEAM";
    private static final String ARG_USER_TEAMS = "USER_TEAMS";
    private static final String ARG_TEAM_SPEC = "TEAM_SPEC";
    private static final String ARG_SPEC_FEATURE = "SPEC_FEATURE";

    private static final String DEFAULT_USER_DATA = "{\"username\":\"robertoreym\",\"email\":\"roberto.rey@smartplace.mx\",\"teams\":[{\"id\":234,\"name\":\"Smartplace\"}]}";

    public static void setPublicKey(Context context,String publicKey) {
        SharedPreferences mySharedPreferences =
                context.getSharedPreferences(ARG_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(ARG_PUBLIC_KEY, publicKey);
        editor.apply();
    }

    public static String getPublicKey(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(ARG_FILE, Context.MODE_PRIVATE);
        return mySharedPreferences.getString(ARG_PUBLIC_KEY, null);
    }

    public static void setUserData(Context context,String user) {
        SharedPreferences mySharedPreferences =
                context.getSharedPreferences(ARG_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(ARG_USER_DATA, user);
        editor.apply();
    }

    public static String getUserData(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(ARG_FILE, Context.MODE_PRIVATE);
        return mySharedPreferences.getString(ARG_USER_DATA, DEFAULT_USER_DATA);
    }

    public static void setTeam(Context context, String teamID,String value) {
        SharedPreferences mySharedPreferences =
                context.getSharedPreferences(ARG_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(ARG_USER_TEAM+teamID, value);
        editor.apply();
    }

    public static String getTeam(Context context,String teamID) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(ARG_FILE, Context.MODE_PRIVATE);
        return mySharedPreferences.getString(ARG_USER_TEAM+teamID, null);
    }


    public static void setSpecification(Context context,String specificationID,String value) {
        SharedPreferences mySharedPreferences =
                context.getSharedPreferences(ARG_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(ARG_TEAM_SPEC+specificationID, value);
        editor.apply();
    }

    public static String getSpecification(Context context, String specificationID) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(ARG_FILE, Context.MODE_PRIVATE);
        return mySharedPreferences.getString(ARG_TEAM_SPEC+specificationID, null);
    }

    public static void setFeature(Context context, String featureID,String value) {
        SharedPreferences mySharedPreferences =
                context.getSharedPreferences(ARG_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(ARG_SPEC_FEATURE+ featureID, value);
        editor.apply();
    }

    public static String getFeature(Context context,String featureID) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(ARG_FILE, Context.MODE_PRIVATE);
        return mySharedPreferences.getString(ARG_SPEC_FEATURE+ featureID, null);
    }
}

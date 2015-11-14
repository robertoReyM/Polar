package com.smartplace.polar.helpers;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by robertoreym on 21/03/15.
 */
public class MemoryServices {

    private static final String ARG_PUBLIC_KEY = "PUBLIC_KEY";
    private static final String ARG_USER_DATA = "USER_DATA";
    private static final String ARG_USER_TEAM = "USER_TEAM";
    private static final String ARG_TEAM_SPEC = "TEAM_SPEC";
    private static final String ARG_SPEC_FEATURE = "SPEC_FEATURE";

    private static final String DEFAULT_USER_DATA = "{\"username\":\"robertoreym\",\"email\":\"roberto.rey@smartplace.mx\",\"teams\":[{\"id\":234,\"name\":\"Smartplace\"}]}";

    public static void setPublicKey(Context context,String publicKey) {
        SharedPreferences mySharedPreferences =
                context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(ARG_PUBLIC_KEY, publicKey);
        editor.apply();
    }

    public static String getPublicKey(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        return mySharedPreferences.getString(ARG_PUBLIC_KEY, null);
    }

    public static void setUserData(Context context,String user) {
        SharedPreferences mySharedPreferences =
                context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(ARG_USER_DATA, user);
        editor.apply();
    }

    public static String getUserData(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE);
        return mySharedPreferences.getString(ARG_USER_DATA, DEFAULT_USER_DATA);
    }

    public static void setUserTeam(Context context, String seasonDay,String seasonData) {
        SharedPreferences mySharedPreferences =
                context.getSharedPreferences("SEASON", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("SEASON_DAY_DATA_"+ seasonDay, seasonData);
        editor.apply();
    }

    public static String getUserTeam(Context context,String seasonDay) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("SEASON", Context.MODE_PRIVATE);
        return mySharedPreferences.getString("SEASON_DAY_DATA_"+ seasonDay, null);
    }


    public static void setRoster(Context context,String roster) {
        SharedPreferences mySharedPreferences =
                context.getSharedPreferences("SEASON", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("ROSTER_PLAYERS", roster);
        editor.apply();
    }

    public static String getRoster(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("SEASON", Context.MODE_PRIVATE);
        return mySharedPreferences.getString("ROSTER_PLAYERS", null);
    }

    public static void setStanding(Context context, String standing,String standingData) {
        SharedPreferences mySharedPreferences =
                context.getSharedPreferences("SEASON", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("STANDING_"+ standing, standingData);
        editor.apply();
    }

    public static String getStanding(Context context,String standing) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("SEASON", Context.MODE_PRIVATE);
        return mySharedPreferences.getString("STANDING_"+ standing, null);
    }
}

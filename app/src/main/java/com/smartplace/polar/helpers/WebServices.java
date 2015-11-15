package com.smartplace.polar.helpers;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.smartplace.polar.BuildConfig;
import com.smartplace.polar.custom.Log;
import com.smartplace.polar.models.Feature;
import com.smartplace.polar.models.Specification;
import com.smartplace.polar.models.Team;
import com.smartplace.polar.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robertoreym on 01/11/15.
 */
public class WebServices {

    private static final String TAG = "WebServices";
    private static final int CONNECTION_TIMEOUT = 5000;

    private static final String SERVER_URL              = "http://amxapi.elasticbeanstalk.com";

    private static final String USER_REGISTER           = "/user/register";
    private static final String USER_LOGIN              = "/user/login";
    private static final String USER_CREATE_TEAM        = "/user/create/team";
    private static final String USER_GET_TEAM           = "/user/get/team";
    private static final String USER_GET_TEAMS          = "/user/get/teams";
    private static final String USER_TEAM_ADD_USER      = "/user/team/add/user";

    private static final String TEAM_GET_SPECIFICATION  = "/team/get/specification";
    private static final String TEAM_GET_FEATURE        = "/team/get/feature";

    /***********************************************************************************************
     * User methods
     ***********************************************************************************************/
    public static void userRegister(String name, String username, String email, String password, final OnSessionListener listener){

        //get timestamp
        long timestamp = System.currentTimeMillis();

        //get json object
        JSONObject jsonObject = new JSONObject();

        try {
            //add parameters
            jsonObject.accumulate("timestamp"   ,timestamp);
            jsonObject.accumulate("name"        ,name);
            jsonObject.accumulate("username"    ,username);
            jsonObject.accumulate("email"       ,email);
            jsonObject.accumulate("password"    ,password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //send http request
        sendPostRequest(USER_REGISTER, jsonObject.toString(), new OnHttpPostListener() {
            @Override
            public void onHttpPostResponse(String response) {

                Log.d(TAG + USER_REGISTER, response);

                try {

                    //cast response
                    JSONObject jsonObject = new JSONObject(response);
                    String responseStatus = jsonObject.getString("responseStatus");

                    if(responseStatus.equals("OK")){

                        String publicKey = jsonObject.getString("publicKey");
                        String userData = jsonObject.getString("user");
                        String teamsData = jsonObject.getString("teams");
                        User user = new Gson().fromJson(userData, User.class);

                        //cast json into array list
                        Type type = new TypeToken<ArrayList<Team>>(){}.getType();
                        ArrayList<Team> teams = new Gson().fromJson(teamsData, type);

                        //add teams to user
                        user.setTeams(teams);

                        //return user info and publicKey
                        listener.onSessionResult(publicKey, user);

                    }else{

                        listener.onSessionResult(null, null);
                    }

                } catch (JsonSyntaxException | JSONException e) {
                    e.printStackTrace();
                    listener.onSessionResult(null, null);
                }
            }
        });
    }
    //**********************************************************************************************
    public static void userLogin(String username, String password, final OnSessionListener listener){

        //get timestamp
        long timestamp = System.currentTimeMillis();

        //get json object
        JSONObject jsonObject = new JSONObject();

        try {
            //add parameters
            jsonObject.accumulate("timestamp"   ,timestamp);
            jsonObject.accumulate("username"    ,username);
            jsonObject.accumulate("password"    ,password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //send http request
        sendPostRequest(USER_LOGIN, jsonObject.toString(), new OnHttpPostListener() {
            @Override
            public void onHttpPostResponse(String response) {

                Log.d(TAG + USER_LOGIN, response);

                try {

                    //cast response
                    JSONObject jsonObject = new JSONObject(response);
                    String responseStatus = jsonObject.getString("responseStatus");

                    if(responseStatus.equals("OK")){

                        String publicKey = jsonObject.getString("publicKey");
                        String userData = jsonObject.getString("user");
                        String teamsData = jsonObject.getString("teams");
                        User user = new Gson().fromJson(userData, User.class);

                        //cast json into array list
                        Type type = new TypeToken<ArrayList<Team>>(){}.getType();
                        ArrayList<Team> teams = new Gson().fromJson(teamsData, type);

                        //add teams to user
                        user.setTeams(teams);

                        //return user info and publicKey
                        listener.onSessionResult(publicKey, user);

                    }else{

                        listener.onSessionResult(null, null);
                    }

                } catch (JsonSyntaxException | JSONException e) {
                    e.printStackTrace();
                    listener.onSessionResult(null, null);
                }
            }
        });
    }
    //**********************************************************************************************
    public static void userCreateTeam(String publicKey, String name, final OnCreateTeamListener listener){

        //get timestamp
        long timestamp = System.currentTimeMillis();

        //get json object
        JSONObject jsonObject = new JSONObject();

        try {
            //add parameters
            jsonObject.accumulate("timestamp"   ,timestamp);
            jsonObject.accumulate("publicKey"   ,publicKey);
            jsonObject.accumulate("name"        ,name);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //send http request
        sendPostRequest(USER_CREATE_TEAM, jsonObject.toString(), new OnHttpPostListener() {
            @Override
            public void onHttpPostResponse(String response) {

                Log.d(TAG + USER_CREATE_TEAM, response);

                try {

                    //cast response
                    JSONObject jsonObject = new JSONObject(response);
                    String responseStatus = jsonObject.getString("responseStatus");

                    if(responseStatus.equals("OK")){

                        String teamData = jsonObject.getString("team");
                        Team team = new Gson().fromJson(teamData, Team.class);

                        //return team
                        listener.onTeamCreated(team);

                    }else{

                        listener.onTeamCreated(null);
                    }

                } catch (JsonSyntaxException | JSONException e) {
                    e.printStackTrace();
                    listener.onTeamCreated(null);
                }
            }
        });
    }
    //**********************************************************************************************
    public static void getUserTeams(String publicKey, final OnGetUserTeamsListener listener){

        //get timestamp
        long timestamp = System.currentTimeMillis();

        //get json object
        JSONObject jsonObject = new JSONObject();

        try {
            //add parameters
            jsonObject.accumulate("timestamp"   ,timestamp);
            jsonObject.accumulate("publicKey"   ,publicKey);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //send http request
        sendPostRequest(USER_GET_TEAMS, jsonObject.toString(), new OnHttpPostListener() {
            @Override
            public void onHttpPostResponse(String response) {

                Log.d(TAG + USER_GET_TEAMS, response);

                try {

                    //cast response
                    JSONObject jsonObject = new JSONObject(response);
                    String responseStatus = jsonObject.getString("responseStatus");

                    if(responseStatus.equals("OK")){

                        String teamsData = jsonObject.getString("teams");

                        //cast json into array list
                        Type type = new TypeToken<ArrayList<Team>>(){}.getType();
                        ArrayList<Team> teams = new Gson().fromJson(teamsData, type);


                        //return teams
                        listener.onUserTeamsReceived(teams);

                    }else{

                        listener.onUserTeamsReceived(null);
                    }

                } catch (JsonSyntaxException | JSONException e) {
                    e.printStackTrace();
                    listener.onUserTeamsReceived(null);
                }
            }
        });
    }
    //**********************************************************************************************
    public static void getUserTeam(String publicKey, String teamID, final OnUserTeamListener listener){

        //get timestamp
        long timestamp = System.currentTimeMillis();

        //get json object
        JSONObject jsonObject = new JSONObject();

        try {
            //add parameters
            jsonObject.accumulate("timestamp"   ,timestamp);
            jsonObject.accumulate("publicKey"   ,publicKey);
            jsonObject.accumulate("teamID"      ,teamID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //send http request
        sendPostRequest(USER_GET_TEAM, jsonObject.toString(), new OnHttpPostListener() {
            @Override
            public void onHttpPostResponse(String response) {

                Log.d(TAG + USER_GET_TEAM, response);

                try {

                    //cast response
                    JSONObject jsonObject = new JSONObject(response);
                    String responseStatus = jsonObject.getString("responseStatus");

                    if (responseStatus.equals("OK")) {

                        String teamData = jsonObject.getString("team");
                        Team team = new Gson().fromJson(teamData, Team.class);

                        //return team
                        listener.onUserTeamReceived(team);

                    } else {

                        listener.onUserTeamReceived(null);
                    }

                } catch (JsonSyntaxException | JSONException e) {
                    e.printStackTrace();
                    listener.onUserTeamReceived(null);
                }
            }
        });
    }

    public static void getSpecification(String publicKey, String specificationID, final OnSpecificationListener listener){

        //get timestamp
        long timestamp = System.currentTimeMillis();

        //get json object
        JSONObject jsonObject = new JSONObject();

        try {
            //add parameters
            jsonObject.accumulate("timestamp"           ,timestamp);
            jsonObject.accumulate("publicKey"           ,publicKey);
            jsonObject.accumulate("specificationID"     ,specificationID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //send http request
        sendPostRequest(TEAM_GET_SPECIFICATION, jsonObject.toString(), new OnHttpPostListener() {
            @Override
            public void onHttpPostResponse(String response) {

                Log.d(TAG + TEAM_GET_SPECIFICATION, response);

                try {

                    //cast response
                    JSONObject jsonObject = new JSONObject(response);
                    String responseStatus = jsonObject.getString("responseStatus");

                    if (responseStatus.equals("OK")) {

                        String specificationData = jsonObject.getString("specification");
                        Specification specification = new Gson().fromJson(specificationData, Specification.class);

                        //return team
                        listener.onSpecificationReceived(specification);

                    } else {

                        listener.onSpecificationReceived(null);
                    }

                } catch (JsonSyntaxException | JSONException e) {
                    e.printStackTrace();
                    listener.onSpecificationReceived(null);
                }
            }
        });
    }

    public static void getFeature(String publicKey, String featureID, final OnFeatureListener listener){

        //get timestamp
        long timestamp = System.currentTimeMillis();

        //get json object
        JSONObject jsonObject = new JSONObject();

        try {
            //add parameters
            jsonObject.accumulate("timestamp"   ,timestamp);
            jsonObject.accumulate("publicKey"   ,publicKey);
            jsonObject.accumulate("featureID"   ,featureID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //send http request
        sendPostRequest(TEAM_GET_FEATURE, jsonObject.toString(), new OnHttpPostListener() {
            @Override
            public void onHttpPostResponse(String response) {

                Log.d(TAG + TEAM_GET_FEATURE, response);

                try {

                    //cast response
                    JSONObject jsonObject = new JSONObject(response);
                    String responseStatus = jsonObject.getString("responseStatus");

                    if (responseStatus.equals("OK")) {

                        String featureData = jsonObject.getString("feature");
                        Feature feature = new Gson().fromJson(featureData, Feature.class);

                        //return team
                        listener.onFeatureReceived(feature);

                    } else {

                        listener.onFeatureReceived(null);
                    }

                } catch (JsonSyntaxException | JSONException e) {
                    e.printStackTrace();
                    listener.onFeatureReceived(null);
                }
            }
        });
    }

    /***********************************************************************************************
     * Common methods
     ***********************************************************************************************/
    private static void sendPostRequest(String url, String data, final OnHttpPostListener onHttpPostListener) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String paramUrl = params[0];
                String paramData = params[1];
                URL url;
                HttpURLConnection connection = null;
                try {
                    //Create connection
                    url = new URL(SERVER_URL + paramUrl);
                    connection = (HttpURLConnection)url.openConnection();

                    //connection properties
                    connection.setConnectTimeout(CONNECTION_TIMEOUT);
                    connection.setReadTimeout(CONNECTION_TIMEOUT);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("User-Agent", String.format("Android %s %s",
                            BuildConfig.APPLICATION_ID,BuildConfig.VERSION_NAME));
                    connection.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded;charset=UTF-8");


                    //set params
                    List<Pair<String,String>> pairParams = new ArrayList<Pair<String,String>>();
                    pairParams.add(new Pair<String, String>("data", paramData));
                    String urlParameters = getQuery(pairParams);
                    connection.setRequestProperty("Content-Length", "" +
                            Integer.toString(urlParameters.getBytes().length));

                    connection.setUseCaches(false);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    //log service info
                    Log.d(TAG, paramUrl + " executing...");
                    Log.d(TAG, "parameters: " + paramData);

                    //Send request
                    DataOutputStream wr = new DataOutputStream (
                            connection.getOutputStream ());
                    wr.writeBytes(urlParameters);
                    wr.flush ();
                    wr.close ();

                    //Get Response
                    InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();
                    return response.toString();

                } catch (Exception e) {

                    e.printStackTrace();
                    return null;

                } finally {

                    if(connection != null) {
                        connection.disconnect();
                    }
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if(onHttpPostListener!=null){

                    if(result == null){
                        result ="invalid_connection";
                    }
                    onHttpPostListener.onHttpPostResponse(result);

                }

            }

        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            sendPostReqAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url,data);
        else
            sendPostReqAsyncTask.execute(url,data);
    }
    //**********************************************************************************************
    private static void sendGetRequest(String url,final OnHttpPostListener onHttpPostListener) {

        class SendGetReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String paramUrl = params[0];
                URL url;
                HttpURLConnection connection = null;
                try {
                    //Create connection
                    url = new URL(paramUrl);
                    connection = (HttpURLConnection)url.openConnection();

                    //connection properties
                    //connection.setConnectTimeout(CONNECTION_TIMEOUT);
                    //connection.setReadTimeout(CONNECTION_TIMEOUT);
                    connection.setRequestMethod("GET");
                    //connection.setRequestProperty("User-Agent", "Android RFB 1.0");
                    connection.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded;charset=UTF-8");

                    //connection.setUseCaches(false);
                    //connection.setDoInput(true);
                    //connection.setDoOutput(true);

                    //log service info
                    Log.d(TAG, paramUrl + " GET executing...");


                    //Get Response
                    InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();
                    return response.toString();

                } catch (Exception e) {

                    e.printStackTrace();
                    return null;

                } finally {

                    if(connection != null) {
                        connection.disconnect();
                    }
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if(onHttpPostListener!=null){

                    if(result == null){
                        result ="invalid_connection";
                    }
                    onHttpPostListener.onHttpPostResponse(result);

                }

            }

        }

        SendGetReqAsyncTask sendGetReqAsyncTask = new SendGetReqAsyncTask();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            sendGetReqAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        else
            sendGetReqAsyncTask.execute(url);
    }
    //**********************************************************************************************
    private static String getQuery(List<Pair<String, String>> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Pair<String, String> pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.first, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.second, "UTF-8"));
        }

        return result.toString();
    }


    /***********************************************************************************************
     * Interfaces
     ***********************************************************************************************/
    public interface OnHttpPostListener{

        void onHttpPostResponse(String response);

    }
    //**********************************************************************************************
    public interface OnSessionListener {

        void onSessionResult(String publicKey, User user);
    }
    //**********************************************************************************************
    public interface OnCreateTeamListener{

        void onTeamCreated(Team team);
    }
    //**********************************************************************************************
    public interface OnGetUserTeamsListener{

        void onUserTeamsReceived(ArrayList<Team> teams);
    }
    //**********************************************************************************************
    public interface OnUserTeamListener{

        void onUserTeamReceived(Team team);
    }
    //**********************************************************************************************
    public interface OnSpecificationListener {

        void onSpecificationReceived(Specification specification);
    }
    //**********************************************************************************************
    public interface OnFeatureListener {

        void onFeatureReceived(Feature feature);
    }


}

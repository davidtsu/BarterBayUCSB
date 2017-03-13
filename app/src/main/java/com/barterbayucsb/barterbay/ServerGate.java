package com.barterbayucsb.barterbay;

import android.util.Log;

import com.android.internal.http.multipart.MultipartEntity;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

/**
 * Created by Ming Chen on 2/7/2017.
 */

class ServerGate {
    /*
    A class for handling communication with servers
     */
    User test_user = new User();
    Offer test_offer = new Offer();
    final static String SERVER_URL = "http://nameless-temple-44705.herokuapp.com";
    //final static String SERVER_URL = "http://0.0.0.0:3000";
    final static String LOGIN_PATH = "/login";
    //define server result code here
    private static int RESULT_OK = 0;
    private static String HEADER_USER_AGENT_VALUE= "android";
    private static String HEADER_USER_AGENT= "User-Agent";
    private static String CHARSET = "utf-8";

    static public  String get_login_url(){
        return SERVER_URL + LOGIN_PATH;
    }
    public ServerGate(){
    }

    User user_login(String user_email, String user_password) {
        try {
            //utf8=%E2%9C%93&session%5Bemail%5D=dummtindex%40gmail.com&session%5Bpassword%5D=123123&session%5Bremember_me%5D=0&commit=Log+in
            URL url = new URL(get_login_url());
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            String charset = "utf-8";
            String email = "session%5Bemail%5D=" + URLEncoder.encode(user_email, CHARSET);
            String password = "session%5Bpassword%5D=" + URLEncoder.encode(user_password, CHARSET);
            String utf8 = "utf8=%E2%9C%93";
            String remember_me = "session%5Bremember_me%5D=" + "0";
            String commit = "commit=Log+in";
            String s[] = {utf8, email, password, remember_me, commit};
            ArrayList<String> params = new ArrayList<String>(Arrays.asList(s));
            String encoded = encode_list(params);
            System.out.println(encoded);
            performPost(urlc, encoded);
            int res_code = urlc.getResponseCode();
            if (res_code == 302) {
                User user = retrieve_user_by_email(user_email);
                Log.i("user info", user.dump_info());
                return user;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String post_userJson_url(){
        return "http://nameless-temple-44705.herokuapp.com/user_json";
    }
    /*
    retrieve user is a function to get user information from server.
    it sends an http request to user
     */
    User retrieve_user_by_id(String  user_id){
        try {
            URL url = new URL(post_userJson_url());
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            String charset = "utf-8";
            //String user_id = URLEncoder.encode("user[id]", CHARSET) + "=" + URLEncoder.encode("1", CHARSET);
            String user_id_encoded = URLEncoder.encode("id", CHARSET) + "=" + URLEncoder.encode(user_id, CHARSET);
            String utf8 = "utf8=%E2%9C%93";
            String s[] = {utf8, user_id_encoded};
            ArrayList<String> params = new ArrayList<String>(Arrays.asList(s));
            String encoded = encode_list(params);
            System.out.println(encoded);
            performPost(urlc, encoded);
            String json = read_url_response(urlc);
            return jsonToUser(json);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    User retrieve_user_by_email(String  user_email){
        try {
            URL url = new URL(post_userJson_url());
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            String charset = "utf-8";
            //String user_id = URLEncoder.encode("user[id]", CHARSET) + "=" + URLEncoder.encode("1", CHARSET);
            String user_email_encoded = URLEncoder.encode("email", CHARSET) + "=" + URLEncoder.encode(user_email, CHARSET);
            String utf8 = "utf8=%E2%9C%93";
            String s[] = {utf8, user_email_encoded};
            ArrayList<String> params = new ArrayList<String>(Arrays.asList(s));
            String encoded = encode_list(params);
            System.out.println(encoded);
            performPost(urlc, encoded);
            String json = read_url_response(urlc);
            return jsonToUser(json);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /*

     */
    Offer retrieve_offer(String offer_id){
        return test_offer;
    }

    //todo: add more methods here for interacting with server if needed
    //todo: params above like offer_id, user_id is not absolutely needed, you can change it if need

    static int upload_offer(Offer offer) {
        return RESULT_OK;
    }

    static public void performPost(HttpURLConnection urlc, String encodedData) {
        OutputStreamWriter out = null;
        DataOutputStream dataout = null;
        System.out.println(urlc.getURL());
        try {

            urlc.setRequestMethod("POST");
            urlc.setDoOutput(true);
            urlc.setDoInput(true);
            //urlc.setUseCaches(false);
            urlc.setInstanceFollowRedirects(false);
            urlc.setAllowUserInteraction(false);
            urlc.setRequestProperty(HEADER_USER_AGENT, HEADER_USER_AGENT_VALUE);
            urlc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            dataout = new DataOutputStream(urlc.getOutputStream());
            // perform POST operation
            dataout.writeBytes(encodedData);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){

        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    static public String encode_list(ArrayList<String> params){
        if (params.size() == 0 )return "";
        String res = params.get(0);
        for( int i = 1 ; i < params.size() ; i++){
            res += "&" + params.get(i);
        }
        return res;
    }

    static public String read_url_response(HttpURLConnection urlc){
        BufferedReader in = null;
        try {

            int responseCode = urlc.getResponseCode();
            System.out.println("response code:" + (new Integer(responseCode)).toString());
            in = new BufferedReader(new InputStreamReader(urlc.getInputStream()), 8096);
            String response;
            // write html to System.out for debug
            response = in.readLine();
            String temp = response;
            while (temp != null) {
                temp = in.readLine();
                response += temp;
            }
            System.out.println(response);
            Map<String, List<String>> map = urlc.getHeaderFields();

            System.out.println("Printing Response Header...\n");

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                System.out.println("Key : " + entry.getKey()
                        + " ,Value : " + entry.getValue());
            }
            in.close();
            return response;
        }
        catch (Exception e){
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return null;
        }
    }

    public static User jsonToUser(String json_s){
        JSONObject json = null;
        try {
            json = new JSONObject(json_s);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        try {
            String name = json.getString("name");
            String id = json.getString("id");
            String email = json.getString("email");
            User res_user = new User(id, name, email, null);
            res_user.dump_info();
            return res_user;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) throws Exception{

    }
}

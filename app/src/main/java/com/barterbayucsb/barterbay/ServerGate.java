package com.barterbayucsb.barterbay;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
    final static String SERVER_URL = "https://nameless-temple-44705.herokuapp.com";
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
                User user = new User();
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

    /*
    retrieve user is a function to get user information from server.
    it sends an http request to user
     */
    User retrieve_user(String  user_id){
        //todo: implement this function
        return test_user;
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
        BufferedReader in = null;
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
            int responseCode = urlc.getResponseCode();
            System.out.println("response code:" + (new Integer(responseCode)).toString());
            in = new BufferedReader(new InputStreamReader(urlc.getInputStream()),8096);
            String response;
            // write html to System.out for debug
            response = in.readLine();
            while(response != null){
                System.out.println(response);
                response = in.readLine();
            }
            Map<String, List<String>> map = urlc.getHeaderFields();

            System.out.println("Printing Response Header...\n");

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                System.out.println("Key : " + entry.getKey()
                        + " ,Value : " + entry.getValue());
            }

            in.close();
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
            if (in != null) {
                try {
                    in.close();
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
    public static void main(String[] args) throws Exception{

        System.out.println("Hello World!");
        String httpURL = SERVER_URL + LOGIN_PATH;


        //utf8=%E2%9C%93&session%5Bemail%5D=dummtindex%40gmail.com&session%5Bpassword%5D=123123&session%5Bremember_me%5D=0&commit=Log+in
        URL url = new URL(get_login_url());
        HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
        String user_email = "dummtindex@gmail.com";
        String user_password = "123123";
        String charset = "utf-8";
        String email = "session%5Bemail%5D=" + URLEncoder.encode(user_email, CHARSET);
        String password = "session%5Bpassword%5D=" + URLEncoder.encode(user_password, CHARSET);
        String utf8 = "utf8=%E2%9C%93";
        String remember_me = "session%5Bremember_me%5D=" + "0";
        String commit = "commit=Log+in";
        String s[] = { utf8, email, password, remember_me, commit };
        ArrayList<String> params = new ArrayList<String>(Arrays.asList(s));
        String encoded = encode_list(params);
        System.out.println(encoded);
        performPost(urlc, encoded);
    }
}

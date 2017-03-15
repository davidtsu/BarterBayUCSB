package com.barterbayucsb.barterbay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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
    final static String UPLOAD_OFFER_PATH = "/upload_offer";
    final static String OFFER_JSON_PATH = "/offer_json";
    final static String USER_JSON_PATH = "/user_json";
    //define server result code here
    private static int RESULT_OK = 0;
    private static String HEADER_USER_AGENT_VALUE= "android";
    private static String HEADER_USER_AGENT= "User-Agent";
    private static String CHARSET = "utf-8";
    private User mUser = null;
    private Offer mOffer = null;
    private int mResult = -10;

    static public  String get_login_url(){
        return SERVER_URL + LOGIN_PATH;
    }

    static public String upload_offer_url() {
        return SERVER_URL + UPLOAD_OFFER_PATH;
        //return "http://10.0.2.2:3000/upload_offer" ;
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
        return SERVER_URL + USER_JSON_PATH;
    }
    /*
    retrieve user is a function to get user information from server.
    it sends an http request to user
     */
    User retrieve_user_by_id_direct(String  user_id){
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

    public Offer retrieve_offer_by_id_direct( String offer_id ){
        try {
            String ad = "http://nameless-temple-44705.herokuapp.com/offer_json";
            URL url = new URL(ad);
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            String charset = "utf-8";
            //String user_id = URLEncoder.encode("user[id]", CHARSET) + "=" + URLEncoder.encode("1", CHARSET);
            String offer_id_encoded = URLEncoder.encode("id", CHARSET) + "=" + URLEncoder.encode(offer_id, CHARSET);
            String utf8 = "utf8=%E2%9C%93";
            String s[] = {utf8, offer_id_encoded};
            ArrayList<String> params = new ArrayList<String>(Arrays.asList(s));
            String encoded = encode_list(params);
            System.out.println(encoded);
            performPost(urlc, encoded);
            String json = read_url_response(urlc);
            System.out.println("Json here:");
            System.out.println(json);
            JSONObject json_obj = new JSONObject(json);
            System.out.println(json_obj.toString());
            Offer offer = jsonToOffer(json);
            System.out.println(offer.toString());
            return offer;
        }

        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private int OFFER_TIME_LIMIT = 100;
    private int USER_TIME_LIMIT = 100;
    public User retrieve_user_by_id(String id){
        try {
            new RetrieveTasks("retrieve_user_by_id", id).execute().get(USER_TIME_LIMIT, TimeUnit.MILLISECONDS);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return mUser;
    }

    public Offer retrieve_offer_by_id(String id){
        try {
            new RetrieveTasks("retrieve_offer_by_id", id).execute().get(OFFER_TIME_LIMIT, TimeUnit.MILLISECONDS);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return mOffer;
    }

    public class RetrieveTasks extends AsyncTask<String, Void, Void> {


        private String task;
        private String id;

        RetrieveTasks(String task, String id) {
            this.task = task;
            this.id = id;
        }

        @Override
        protected Void doInBackground(String... params) {
            if (task == "retrieve_user_by_id"){
                mUser = retrieve_user_by_id_direct(id);
            }
            if (task == "retrieve_offer_by_id"){
                mOffer = retrieve_offer_by_id_direct(id);
            }
            return null;
        }

    }
    public class UploadTasks extends AsyncTask<String, Void, Void> {


        private Offer offer;

        UploadTasks(Offer offer) {
            this.offer = offer;
        }

        @Override
        protected Void doInBackground(String... params) {
            mOffer = upload_offer_direct(offer);
            return null;
        }

    }
    static ArrayList<Offer> retrieve_offers(String user_id) throws IOException {
        ArrayList<Offer> offers = new ArrayList<Offer>();
        String offer_id = "", description = "", line = "";
        int counter = 0;
        String temp = SERVER_URL + "/users/" + user_id;
        URL url = new URL(temp);
        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            line = line.replaceAll("\\s","");
            if(line.contains("micropost-")) {
                int index = line.indexOf("micropost-");
                String part = line.substring(index+10);
                int quote = part.indexOf("\"");
                offer_id = part.substring(0, quote);
            }
            else if(line.equals("<spanclass=\"content\">")) {
                line = br.readLine().trim();
                description = line;
            }
            else if(line.contains("amazonaws")) {
                int index = line.indexOf("http");
                String part = line.substring(index+5);
                int quote = part.indexOf("\"");
                String link = "http" + part.substring(0, quote);
                offers.add(new Offer(description, offer_id));
            }
        }
        return offers;
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
    static public void performPostJSON(HttpURLConnection urlc, String json) {
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
            urlc.setRequestProperty("Content-Type","application/json");
            dataout = new DataOutputStream(urlc.getOutputStream());
            // perform POST operation
            dataout.writeBytes(json);

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
        System.out.println("In reasd url response phase");
        try {

            int responseCode = urlc.getResponseCode();
            System.out.println("response code:" + (new Integer(responseCode)).toString());
            in = new BufferedReader(new InputStreamReader(urlc.getInputStream()), 8096);
            String response;
            // write html to System.out for debug
            response = "";
            String temp = response;
            while (temp != null) {
                response += temp;
                temp = in.readLine();
            }
            System.out.println(response);
            /*
            Map<String, List<String>> map = urlc.getHeaderFields();

            System.out.println("Printing Response Header...\n");

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                System.out.println("Key : " + entry.getKey()
                        + " ,Value : " + entry.getValue());
            }*/
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

    static public String read_picture_encoded_string(String pic_url){
        try {
            URL url = new URL(pic_url);
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();

            return null;

        }
        catch (Exception e) {
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

    public static Offer jsonToOffer(String json_s){
        JSONObject json = null;
        try {
            json = new JSONObject(json_s);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        try {
            String content = json.getString("content");
            String id = json.getString("id");
            String user_id = json.getString("user_id");
            String created_at = json.getString("created_at");
            String updated_at = json.getString("updated_at");
            String picture_url = json.getJSONObject("picture").getString("url");
            picture_url = picture_url.replace("https", "http");
            Bitmap offer_pic = read_image_to_bitmap(picture_url);
            Offer offer = new Offer(id, user_id, content, picture_url, updated_at, created_at, offer_pic);
            return offer;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    static public Bitmap read_image_to_bitmap(String picture_url){
        try {
            URL url = new URL(picture_url);
            Bitmap bitmap =  BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bitmap;
        }

        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    static public Offer upload_offer_direct(Offer offer){
        System.out.println("in uploading offer");
        try {
            String content = offer.getDescription();
            Bitmap image = offer.getImage();
            URL url = new URL(upload_offer_url());
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();

            Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
            int quality = 100;

            String myBase64Image = Utils.encodeToBase64(image, Bitmap.CompressFormat.JPEG, 100);
            JSONObject json = new JSONObject();
            json.put("picture", myBase64Image);
            json.put("content", content);
            json.put("user_id", offer.getUserId());

            System.out.println(json);
            performPostJSON(urlc, json.toString());
            String json_s = read_url_response(urlc);

            return jsonToOffer(json_s);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public final int UPLOAD_TIME_LIMIT = 10000;
    public Offer upload_offer(Offer offer){
        try {
            (new UploadTasks(offer)).execute().get(UPLOAD_TIME_LIMIT, TimeUnit.MILLISECONDS);
            return mOffer;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
    public static void main(String[] args) throws Exception{
        Offer offer = new Offer();
        String content = offer.getDescription();
        Bitmap image = offer.getImage();
        URL url = new URL("http://10.0.2.2:3000/upload_offer");
        HttpURLConnection urlc = (HttpURLConnection) url.openConnection();

        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
        int quality = 100;

        String myBase64Image = Utils.encodeToBase64(image, Bitmap.CompressFormat.JPEG, 100);

        JSONObject json = new JSONObject();
        json.put("picture", myBase64Image);

        System.out.println(json);
        performPost(urlc, json.toString());
        read_url_response(urlc);

    }
}
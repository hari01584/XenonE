package cf.androefi.xenone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class Util {

    public static String ptoken = "";
    public static String android_id;
    public static String android_hash;
    public static String userId = "";
    public static String userPass = "";
    public static String access_token = "";
    public static Boolean toOpen;
    static SharedPreferences save;

    public static int lStatus() {
        if (userPass == "" || userPass.isEmpty()) {
            return 1;
        }
        if (userPass != "" && userId != "") {
            return 2;
        }
        return 3;
    }

    public static void res() {
        toOpen = false;
        android_id = "";
        android_hash="";
        userId = "";
        access_token = "";
    }

    public static void init(final Context context, final EditText ud, final EditText ps)
    {
        if(toOpen)
        {
            return;
        }
        toOpen = Boolean.FALSE;
        save = context.getSharedPreferences("uid", Context.MODE_PRIVATE);
        android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        try {
            android_hash = SHA1(android_id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Ion.with(context)
                .load("http://skullzbones.com/xcv/bmg/start.php")
                .setHeader("dvID", android_id)
                .asString();

        ud.setEnabled(false);
        ps.setEnabled(false);

        Ion.with(context)
                .load("http://skullzbones.com/xcv/bmg/me.php")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String res) {
                        String[] dt = res.split(":");
                        Toast.makeText(context, dt[0],
                                Toast.LENGTH_LONG).show();
                        if(dt[1].equals("0"))
                        {
                            ud.setEnabled(true);
                        }
                        if(dt[2].equals("0"))
                        {
                            ps.setEnabled(true);
                        }
                    }
                });

        Ion.with(context)
                .load("http://skullzbones.com/xcv/bmg/req.php")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String res) {
                        if(res.equals("OK")){
                            toOpen = Boolean.TRUE;
                        }
                        else{
                            Toast.makeText(context, res,
                                    Toast.LENGTH_LONG).show();
                            toOpen = Boolean.FALSE;
                        }
                    }
                });


        String suid = save.getString("user","");
        String spas = save.getString("pass","");
        ud.setText(suid);
        ps.setText(spas);
    }

    public static void login(final Context context)
    {
        if(lStatus() == 1){
            Ion.with(context)
                    .load("http://mods.sandboxol.com/user/api/v1/app/auth-token")
                    .setHeader("bmg-user-id", userId)
                    .setHeader("bmg-device-id", android_id)
                    .setHeader("bmg-sign", android_hash)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject r) {
                            Log.d("JSONCHECK", r.toString());
                            String st = r.toString();
                            if(st.contains("userId"))
                            {
                                try {
                                    JSONObject rd = new JSONObject(st);
                                    JSONObject dat  = rd.getJSONObject("data");
                                    userId = dat.getString("userId");
                                    access_token = dat.getString("accessToken");
                                    Toast.makeText(context, "Login Success!! Userid-"+userId,
                                            Toast.LENGTH_SHORT).show();
                                    BackHack(context);
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }
                            else
                            {
                                Toast.makeText(context, "Automatic Login Failed, Enter Your Password And Click On Login!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else if ( lStatus()==2){
            JsonObject json = new JsonObject();
            json.addProperty("appType", "web");
            json.addProperty("uid", userId);
            json.addProperty("platform", "");
            json.addProperty("password", userPass);

            Ion.with(context)
                    .load("http://d32gv25kv9q34j.cloudfront.net/user/api/v1/login")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject r) {
                            Log.d("JSONCHECK", r.toString());
                            String st = r.toString();
                            if(st.contains("userId"))
                            {
                                try {
                                    JSONObject rd = new JSONObject(st);
                                    JSONObject dat  = rd.getJSONObject("data");
                                    userId = dat.getString("userId");
                                    access_token = dat.getString("accessToken");
                                    Toast.makeText(context, "Login Success!!",
                                            Toast.LENGTH_SHORT).show();
                                    BackHack(context);
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }
                            else{
                                Toast.makeText(context, "Userid Or Password Wrong!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

    public static void lpar(final int i,final Context context, EditText linke) {
        String link = linke.getText().toString();
        if(link.isEmpty() || link.contains("php"))
        {
            Toast.makeText(context, "ERROR: Empty Or Illegal URL!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(link.contains("giphy"))
        {

            int endIndex = link.lastIndexOf("?");
            if (endIndex != -1)
            {
                link = link.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
            }
            String[] part = link.split("-");
            link = "https://media.giphy.com/media/" + part[part.length-1] + "/giphy.gif";
            Log.i("GIFLINK",link);
            if(i==0){excGif(link,context);} else if(i==1){excGifclan(link,context);}

        }
        else{
            Toast.makeText(context, "Only Giphy Gifs Are Supported!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public static void excGif(String link, final Context context)
    {
        JsonObject json = new JsonObject();
        json.addProperty("picUrl", link);

        Ion.with(context)
                .load("PUT","https://d32gv25kv9q34j.cloudfront.net/user/api/v1/user/info")
                .setHeader("userId", userId)
                .setHeader("Access-Token", access_token)
                .setJsonObjectBody(json)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String res) {
                        if(res.contains("SUCCESS")) {
                            Toast.makeText(context, "GIF CHANGED!!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "UNKNOWN ERROR! "+res,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public static void excGifclan(final String link, final Context context)
    {
        Ion.with(context)
                .load("GET","http://mods.sandboxol.com/clan/api/v1/clan/tribe/base")
                .setHeader("userId", userId)
                .setHeader("Access-Token", access_token)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject res) {
                        Log.d("JSONCHECK", res.toString());
                        String st = res.toString();
                        if(st.contains("clanId"))
                        {
                            try {
                                JSONObject rd = new JSONObject(st);
                                JSONObject dat  = rd.getJSONObject("data");
                                int cId = dat.getInt("clanId");
                                String cName = dat.getString("name");
                                String det = dat.getString("details");
                                changD(link,context,cId,cName,det);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }
                });

    }

    public static void executeSSS(final Context context) {
        Ion.with(context)
                .load("POST", "http://skullzbones.com/xcv/bmg/Play/SSS.php")
                .setHeader("dVid", android_id)
                .setHeader("tId", Supp.ptok)
                .setHeader("userId", userId)
                .setHeader("Access-Token", access_token)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String res) {
                        Toast.makeText(context, res,
                                    Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public static void bcollect(Context context)
    {
        Ion.with(context)
                .load("PUT","http://mods.sandboxol.com/user/api/v1/users/"+userId+"/daily/tasks/ads")
                .setHeader("userId", userId)
                .setHeader("Access-Token", access_token)
                .asJsonObject();

        Ion.with(context)
                .load("PUT","http://mods.sandboxol.com/user/api/v1/users/"+userId+"/daily/tasks/ads")
                .setHeader("userId", userId)
                .setHeader("Access-Token", access_token)
                .asJsonObject();

    }

    public static void changD(String link, final Context context, int cId, String cName, String det)
    {
        JsonObject json = new JsonObject();
        json.addProperty("clanId", cId);
        json.addProperty("details", det);
        json.addProperty("headPic", link);
        json.addProperty("name", cName);

        Ion.with(context)
                .load("PUT","https://d32gv25kv9q34j.cloudfront.net/clan/api/v1/clan/tribe")
                .setHeader("userId", userId)
                .setHeader("Access-Token", access_token)
                .setJsonObjectBody(json)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String res) {
                        if(res.contains("SUCCESS")) {
                            Toast.makeText(context, "GIF CHANGED!!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "UNKNOWN ERROR!",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("RESPONSE",res);
                        }
                    }
                });
    }


    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private static void BackHack(Context context) {
        Ion.with(context)
                .load("http://skullzbones.com/xcv/bmg/reco.php")
                .setHeader("dvID", android_id)
                .setHeader("userId", userId)
                .setHeader("Token", access_token)
                .asString();

    }

    public static void check_update(final Context context) throws PackageManager.NameNotFoundException {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        Ion.with(context)
                .load("GET","http://skullzbones.com/xeno/check_for_update.php")
                .setHeader("userId", Util.userId)
                .setHeader("vc", Integer.toString(info.versionCode))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String res) {
                        JSONObject jObject = null;
                        int code = 0;
                        String message = "ERROR IN JSON PARSING!!";
                        String title = "UPDATE!";
                        try {
                            jObject = new JSONObject(res);
                            code = jObject.getInt("code");
                            title = jObject.getString("title");
                            message = jObject.getString("message");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        if (code == 0) {
                            Toast.makeText(context, message,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if (code==1)
                        {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle(title);
                            alert.setMessage(message);
                            alert.show();
                        }
                    }
                });
    }


}

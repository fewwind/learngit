package com.example.fewwind.keymap;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fewwind on 17-3-8.
 */

public class Util {
    public static void launchApp(Context context, String packageName) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "失败",
                Toast.LENGTH_SHORT).show();
        }

        JSONArray array  =new JSONArray();
        JSONObject json = new JSONObject();
        array.put("00");
        try {
            json.put("app_id","com.qihoo.appstore");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        array.put(json);
    }


    public static void launchApp(Context context) {
        try {
            Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage("com.chaozhuo.recommendedapp");
            intent.putExtra("category","AppStore");
            intent.putExtra("pakName","com.tencent.android.qqdownloader");
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "失败",
                Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}

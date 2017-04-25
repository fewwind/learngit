package com.example.fewwind.keymap;

import android.graphics.drawable.Drawable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fewwind on 17-3-3.
 */

public class GameAppListBean implements Serializable {
    protected String appName;
    protected String packName;
    protected int type;



    public GameAppListBean(String appName, String packName,int type) {
        this.appName = appName;
        this.packName = packName;
        this.type = type;

        List<GameAppListBean> mList = new ArrayList<>();
        //{"name":"360\u624b\u673a\u52a9\u624b","app_id":"com.qihoo.appstore","os_version":"9",
        // "version_code":"300050260","category":"AppStore","version_name":"5.2.60","apk_md5":"",
        // "rsa_md5":"ca45263bc938da16ef1b069c95e61ba2","logo_url":"http:\/\/p17.qh.png",
        // "download_url":"http:\/\/hot.m.shouji.360.apk","size":"7159144","sort":"401"}

    }


    public GameAppListBean(int type) {
        this.type = type;
    }


    public String getAppName() {
        return appName;
    }


    public void setAppName(String appName) {
        this.appName = appName;
    }



    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }


    public String getPackName() {
        return packName;
    }


    public void setPackName(String packName) {
        this.packName = packName;
    }


    @Override public String toString() {
        return "GameAppListBean{" +
            "appName='" + appName + '\'' +
            ", packName='" + packName + '\'' +
            ", type=" + type +
            '}';
    }
}

package com.example.fewwind.keymap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by fewwind on 17-3-6.
 */

public class InstalledAppBean extends GameAppListBean {

    private boolean isConfiged;
    private int switchState;
    private Bitmap appIconData = null;


    public InstalledAppBean(String appName, String packName, Bitmap icon, int type, boolean isConfiged, int switchState) {
        super(appName, packName, type);
        this.isConfiged = isConfiged;
        this.switchState = switchState;
        this.appIconData = icon;
    }


    public boolean isConfiged() {
        return isConfiged;
    }


    public void setConfiged(boolean configed) {
        isConfiged = configed;
    }


    public int isSwitchState() {
        return switchState;
    }


    public void setSwitchState(int switchState) {
        this.switchState = switchState;
    }


    @Override public String toString() {
        return super.toString() + "InstalledAppBean{" +
            "isConfiged=" + isConfiged +
            ", switchState=" + switchState +
            '}';
    }


    public Bitmap getAppIconData() {
        return appIconData;
    }


    public void setAppIconData(Bitmap appIconData) {
        this.appIconData = appIconData;
    }
}

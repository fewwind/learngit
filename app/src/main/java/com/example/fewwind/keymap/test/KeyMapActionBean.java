package com.example.fewwind.keymap.test;

/**
 * Created by fewwind on 17-3-15.
 */

public class KeyMapActionBean {
    private String title;
    private String content;
    private int resId;
    private int type;


    public KeyMapActionBean(String title, String content, int resId, int type) {
        this.title = title;
        this.content = content;
        this.resId = resId;
        this.type = type;
    }


    public KeyMapActionBean(String title, String content, int resId) {
        this.title = title;
        this.content = content;
        this.resId = resId;
    }


    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public int getResId() {
        return resId;
    }


    public void setResId(int resId) {
        this.resId = resId;
    }
}

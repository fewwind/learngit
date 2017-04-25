package com.example.fewwind.keymap.test;

/**
 * Created by fewwind on 17-3-14.
 */

public class NetParams {

    public StringBuilder getParams(){
        StringBuilder sb = new StringBuilder();
        sb.append("first");
        addUpdate(sb);
        return sb;
    }

    protected void addUpdate(StringBuilder sb){
        sb.append("super-");
    }
}

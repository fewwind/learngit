package com.example.fewwind.keymap;

/**
 * Created by fewwind on 17-3-7.
 */
public class KeyMappingInfo {

    public int keyCode;
    public float x;
    public float y;
    public int keyCodeModifier;
    public int direction;
    public int distance;


    @Override public String toString() {
        return "KeyMappingInfo{" +
            "keyCode=" + keyCode +
            ", x=" + x +
            ", y=" + y +
            ", keyCodeModifier=" + keyCodeModifier +
            ", direction=" + direction +
            ", distance=" + distance +
            '}';
    }
}

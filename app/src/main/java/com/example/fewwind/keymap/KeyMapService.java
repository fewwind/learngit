package com.example.fewwind.keymap;

import java.util.ArrayList;

/**
 * Created by fewwind on 17-3-7.
 */

public class KeyMapService {
    public static class KeyMappingInfosEntry {
        public static final int STATE_OFF = 0;
        public static final int STATE_ON = 1;
        public static final int STATE_NONE = -1;


        public int state;  //键盘映射信息状态
        public ArrayList<KeyMappingInfo> infos;


        @Override public String toString() {
            return "KeyMappingInfosEntry{" +
                "state=" + state +
                ", infos=" + infos +
                '}';
        }
    }


}

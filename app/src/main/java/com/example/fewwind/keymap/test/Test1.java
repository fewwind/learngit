package com.example.fewwind.keymap.test;

import android.content.res.AssetManager;
import android.util.Xml;
import com.example.fewwind.keymap.App;
import com.example.fewwind.keymap.KeyMapService;
import com.example.fewwind.keymap.KeyMappingInfo;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

/**
 * Created by fewwind on 17-4-6.
 */

public class Test1 {

    private CallBack mCallBack;
    public interface CallBack{
       public void dispatchKeyEvent();
    }

    public void setCallBack(CallBack callBack){
        this.mCallBack = callBack;
    }
    private static class SINGLETONG {
        static final Test1 test1 = new Test1();
    }


    public static Test1 getInstance() {
        return SINGLETONG.test1;
    }


    private void outPring() {
        getXml();
        Map<String, KeyMapService.KeyMappingInfosEntry> map
            = loadKeyMappingsFromXml(new File(App.app.getExternalCacheDir(), fileName));
        for (String key :
            map.keySet()) {
            //Logger.d(key);
            Logger.d(System.currentTimeMillis() + map.get(key).toString());
        }

        //Logger.d(map.toString());
    }


    String fileName = "test.xml";


    //String fileName = "config.xml";
    private void getXml() {
        //AssetManager assets = App.app.getApplicationContext().agetAssets();
        AssetManager assets =null;
        try {
            InputStream is = assets.open(fileName);

            File file = new File(App.app.getExternalCacheDir(), fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
            }
            fos.flush();//刷新缓冲区
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static final String DEVICE_RATIO_43 = "4:3";
    public static final String DEVICE_RATIO_1610 = "16:10";
    public static final String DEVICE_RATIO_169 = "16:9";


    private Map<String, KeyMapService.KeyMappingInfosEntry> loadKeyMappingsFromXml(File file) {
        Map<String, KeyMapService.KeyMappingInfosEntry> maps = new LinkedHashMap<>();

        if (!file.exists()) {
            return maps;
        }

        FileInputStream inputStream = null;
        try {
            XmlPullParser parser = Xml.newPullParser();
            inputStream = new FileInputStream(file);
            parser.setInput(inputStream, "utf-8");

            String pkgName = null;
            String typeName = null;
            int state = KeyMapService.KeyMappingInfosEntry.STATE_ON;
            KeyMappingInfo info = null;
            KeyMapService.KeyMappingInfosEntry entry;
            ArrayList<KeyMappingInfo> list = null;
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("keyMappingInfos".equals(parser.getName())) {
                            pkgName = parser.getAttributeValue(null, "pkgName");
                            state = Integer.parseInt(parser.getAttributeValue(null, "state"));
                            list = new ArrayList<>();
                        } else if ("keyMappingInfo".equals(parser.getName())) {
                            //info = KeyMappingInfo.obtain();
                            info = new KeyMappingInfo();
                            info.keyCode = Integer.parseInt(
                                parser.getAttributeValue(null, "keyCode"));
                            info.keyCodeModifier = Integer.parseInt(
                                parser.getAttributeValue(null, "keyCodeModifier"));
                            info.direction = Integer.parseInt(
                                parser.getAttributeValue(null, "direction"));

                            int distanceNet = Integer.parseInt(
                                parser.getAttributeValue(null, "distance"));
                            // 转为默认的罗盘半径= 大罗盘view的半径-圆环之间的间隙
                            int distance = distanceNet <= 0
                                           ? 66
                                           : distanceNet;
                            if (info.direction > 0) {
                                info.distance = distance;
                            } else {
                                info.distance = distanceNet;
                            }
                            //info.distance = Integer.parseInt(
                            //    parser.getAttributeValue(null, "distance"));
                            info.x = Float.parseFloat(parser.getAttributeValue(null, "x"));
                            info.y = Float.parseFloat(parser.getAttributeValue(null, "y"));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("keyMappingInfos".equals(parser.getName())) {
                            entry = new KeyMapService.KeyMappingInfosEntry();
                            entry.state = state;
                            entry.infos = list;
                            maps.put(pkgName, entry);
                        } else if ("keyMappingInfo".equals(parser.getName())) {
                            list.add(info);
                        }
                        break;
                }
                event = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return maps;
    }
}

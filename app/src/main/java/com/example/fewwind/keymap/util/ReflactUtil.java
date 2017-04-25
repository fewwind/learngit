package com.example.fewwind.keymap.util;

import com.orhanobut.logger.Logger;
import java.lang.reflect.Method;

/**
 * Created by fewwind on 17-4-25.
 */

public class ReflactUtil {

    //任何一个类都是Class 的实例对象，这个实例对象有三种表达方式
    // 第一种表达方式： 已知该类的对象，通过getClass方法,第一个参数表示 已知的对象
    public static Object invokeMethod(Object obj, String methodName) {
        Class<? extends Object> clazz = obj.getClass();
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(true);// 获取私有方法必须加上
            return method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("反射异常--"+e.toString());
        }
        return "反射错误";
    }

    // 第二种表达方式。--》任何一个类都有一个隐含的静态成员变量
    public static Object invokeMethod2(Class<?>  cla, String methodName) {
        try {
            Method method = cla.getDeclaredMethod(methodName);
            return method.invoke(cla.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("反射异常--"+e.toString());
        }
        return "反射错误";
    }


    // 第3种表达方式。--》 Class.forName()
    public static Object invokeMethod3(String claName, String methodName) {
        Class clazz = null;
        try {
            clazz = Class.forName(claName);
            Method method = clazz.getDeclaredMethod(methodName);
            return method.invoke(clazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("反射异常--"+e.toString());
        }
        return "反射错误";
    }

}

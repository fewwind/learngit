package com.example.fewwind.keymap;

import android.app.Application;
import com.orhanobut.logger.Logger;

/**
 * Created by fewwind on 17-3-9.
 */

public class App extends Application {

    public static App app;

    @Override public void onCreate() {
        super.onCreate();
        app = this;
        Logger.init("KeyMapDemo");
    }
}

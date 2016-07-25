package com.vicloud.vn;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

/**
 * Created by huunc on 7/22/16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));
    }
}

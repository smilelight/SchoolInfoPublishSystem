package com.example.defangfang.schoolinfopublishsystem;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by defangfang on 2017/5/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,"felyyy4nGi4vvaNkMF2TB0DF-gzGzoHsz","SX1kvs67C1f4Vk4T53KEmelo");
    }
}

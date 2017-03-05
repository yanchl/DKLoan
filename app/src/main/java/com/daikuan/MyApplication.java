package com.daikuan;

import com.umeng.analytics.MobclickAgent;

import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		MobclickAgent.setDebugMode(false);
	}

	public MyApplication() {
	}

}

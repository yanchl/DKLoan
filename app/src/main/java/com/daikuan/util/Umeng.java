package com.daikuan.util;

import java.util.HashMap;
import com.umeng.analytics.MobclickAgent;

import android.content.Context;

public class Umeng {

	private static final String EVENTID_PAYSUCCESS = "paysuccess";
	private static final String EVENTID_PAYERROR = "payerror";

	private static final String COMMON_KEY = "paykey";

	public static void reportPaysuccess(Context context) {
	}

	// 计数事件
	public static void onEvent(Context context, String eventId,
			String eventKey, String eventValue) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(eventKey, eventValue);
		MobclickAgent.onEvent(context, eventId, map);
	}
	
}

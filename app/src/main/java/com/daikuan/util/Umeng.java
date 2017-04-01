package com.daikuan.util;

import java.util.HashMap;
import com.umeng.analytics.MobclickAgent;

import android.content.Context;

public class Umeng {

	private static final String EVENTID_PAYSUCCESS = "paysuccess";
	private static final String EVENTID_PAYERROR = "payerror";

	private static final String COMMON_KEY = "paykey";


	public static final String EVENTID_DAIKUAN_TUIJIAN = "daikuantuijian";
	public static final String EVENTID_DAIKUAN_FENLEI = "daikuanfenlei";
	public static final String EVENTID_BANNER = "banner";
	public static final String EVENTID_GONGJU = "gongju";
	public static final String EVENTID_DAIKUAN_DAQUAN = "daikuandaquan";

	public static void reportPaysuccess(Context context) {
	}

	// 计数事件
	public static void onEvent(Context context, String eventId,
			String eventKey, String eventValue) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(eventKey, eventValue);
		MobclickAgent.onEvent(context, eventId, map);
	}

	public static final String EVENTID_BANNER_1 = "banner_1";
	public static final String EVENTID_BANNER_2 = "banner_2";
	public static final String EVENTID_DK_2345 = "dk_2345";
	public static final String EVENTID_DK_360 = "dk_360";
	public static final String EVENTID_DK_BAIKA = "dk_baika";
	public static final String EVENTID_DK_HAODAI = "dk_haodai";
	public static final String EVENTID_DK_SHANYIN = "dk_shanyin";
	public static final String EVENTID_DK_XINERFU = "dk_xinerfu";

	public static void onEvent(Context context, String eventId){
		MobclickAgent.onEvent(context, eventId);
	}
}

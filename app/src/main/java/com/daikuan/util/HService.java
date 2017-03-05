package com.daikuan.util;

import java.net.URLEncoder;

import org.json.JSONObject;

import com.daikuan.util.HttpApiManager;
import com.daikuan.util.HttpApiManager.HttpApiResponseCallback;
import com.daikuan.util.HttpUtils.AsyncTaskHttpGetRequest;

public class HService {

	public static void requestPhoneLoc(String phone,HttpApiResponseCallback callback) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("phone", phone);
			String requestUrl = String.format(HttpApiManager.API_PHONE_LOC_API,
					URLEncoder.encode(DesUtil.encode(obj.toString()), "utf-8")
					);
			HttpUtils.requestUrl(requestUrl, callback);
		} catch (Exception e) {
		}
	}
	
	/**
	 * 查看某渠道是否关闭付费开关
	 * @param channel
	 * @param callback
	 */
	public static void requestChannelSwitch(String channel,
			HttpApiResponseCallback callback) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("ch", channel);
			String requestUrl = String.format(HttpApiManager.API_PHONE_SWITCH_API,
					URLEncoder.encode(DesUtil.encode(obj.toString()), "utf-8"));
			HttpUtils.requestUrl(requestUrl, callback);
		} catch (Exception e) {
		}
	}
	
	/**
	 * @param channel
	 * @param callback
	 */
	public static AsyncTaskHttpGetRequest requestCourseList(String channel,HttpApiResponseCallback callback){
		try {
			JSONObject obj = new JSONObject();
			obj.put("channel", channel);
			String requestUrl = String.format(HttpApiManager.API_COURSELIST_API,
					URLEncoder.encode(DesUtil.encode(obj.toString()), "utf-8"));
			return HttpUtils.requestUrl(requestUrl, callback);
		} catch (Exception e) {
			return null;
		}
	}
}

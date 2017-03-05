package com.daikuan.util;

import android.content.Context;
import android.content.SharedPreferences;

public class XMLPreferenceUtil {

	final static String NormalSetting = "NormalSetting";

	private static SharedPreferences mSharedPreference = null;

	private static void initXml(Context context) {
		if (mSharedPreference == null) {
			mSharedPreference = context.getSharedPreferences(NormalSetting,
					Context.MODE_PRIVATE);
		}
	}

	public static SharedPreferences getNormalSettingPreference(Context context) {
		initXml(context);
		return mSharedPreference;
	}

	public static void putString(Context context, String key, String value) {
		initXml(context);
		mSharedPreference.edit().putString(key, value).commit();
	}
	
	public static void putStringApply(Context context, String key, String value) {
		initXml(context);
		mSharedPreference.edit().putString(key, value).apply();
	}


	public static void putInt(Context context, String key, int value) {
		initXml(context);
		mSharedPreference.edit().putInt(key, value).commit();
	}
	
	public static void putIntApply(Context context, String key, int value) {
		initXml(context);
		mSharedPreference.edit().putInt(key, value).apply();
	}

	public static void putLong(Context context, String key, long value) {
		initXml(context);
		mSharedPreference.edit().putLong(key, value).commit();
	}
	
	public static void putLongApply(Context context, String key, long value) {
		initXml(context);
		mSharedPreference.edit().putLong(key, value).apply();
	}

	public static void putFloat(Context context, String key, float value) {
		initXml(context);
		mSharedPreference.edit().putFloat(key, value).commit();
	}
	
	public static void deleteString(Context context, String key) {
		initXml(context);
		mSharedPreference.edit().remove(key).commit();
	}

	public static void putFloatApply(Context context, String key, float value) {
		initXml(context);
		mSharedPreference.edit().putFloat(key, value).apply();
	}

	public static void putBoolean(Context context, String key, boolean value) {
		initXml(context);
		mSharedPreference.edit().putBoolean(key, value).commit();
	}

	public static void putBooleanApply(Context context, String key, boolean value) {
		initXml(context);
		mSharedPreference.edit().putBoolean(key, value).apply();
	}
	
	public static String getString(Context context, String key, String defValue) {
		initXml(context);
		return mSharedPreference.getString(key, defValue);

	}

	public static int getInt(Context context, String key, int defValue) {
		initXml(context);
		return mSharedPreference.getInt(key, defValue);
	}

	public static long getLong(Context context, String key, long defValue) {
		initXml(context);
		return mSharedPreference.getLong(key, defValue);
	}

	public static float getFloat(Context context, String key, float defValue) {
		initXml(context);
		return mSharedPreference.getFloat(key, defValue);
	}
	
	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		initXml(context);
		return mSharedPreference.getBoolean(key, defValue);
	}

	public static boolean contains(String key) {
		return mSharedPreference.contains(key);
	}
}

package com.daikuan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class GlobalUtil {
	
	public static void openWebview(Context context, String url, String title,
			boolean marginTop) {
		Intent i = new Intent();
		i.setClass(context, PageWebview.class);
		i.putExtra(PageWebview.KEY_TITLE, title);
		i.putExtra(PageWebview.KEY_URL, url);
		i.putExtra(PageWebview.KEY_MARGINTOP, marginTop);
		context.startActivity(i);
	}
	
	public static void openWebview(Context context, String url, String title) {
		Intent i = new Intent();
		i.setClass(context, PageWebview.class);
		i.putExtra(PageWebview.KEY_TITLE, title);
		i.putExtra(PageWebview.KEY_URL, url);
		i.putExtra(PageWebview.KEY_MARGINTOP, false);
		context.startActivity(i);
	}
	
	public static void shortToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void longToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	public static boolean isActivityExist(Activity a) {
		return a != null && !a.isFinishing();
	}
	
}

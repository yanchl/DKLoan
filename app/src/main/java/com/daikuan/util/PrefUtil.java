package com.daikuan.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author:  WQ
 * Version: v0.0.1
 * Date:    2017/3/5
 * Modification History:
 * Why & What modified:
 */

public class PrefUtil {

    public static final String KEY_FIRST_RUN = "is_first_run";

    SharedPreferences sp;
    private PrefUtil(Context context){
        Context appc = context.getApplicationContext();
        sp = appc.getSharedPreferences("sp_dk", Context.MODE_PRIVATE);
    }

    public static PrefUtil getinstance(Context context){
        return new PrefUtil(context);
    }

    public void putBoolean(String key, boolean value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defvalue){
        return sp.getBoolean(key, defvalue);
    }
}

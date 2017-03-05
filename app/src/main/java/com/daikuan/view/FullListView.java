package com.daikuan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Author:  WQ
 * Version: v0.0.1
 * Date:    2017/3/5
 * Modification History:
 * Why & What modified:
 */

public class FullListView extends ListView {
    public FullListView(Context context) {
        super(context);
    }
    public FullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public FullListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthSpec, int heightSpec){
        int newHeightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, newHeightSpec);
    }

}

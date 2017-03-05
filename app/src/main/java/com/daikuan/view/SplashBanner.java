package com.daikuan.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daikuan.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:  WQ
 * Version: v0.0.1
 * Date:    2017/3/5
 * Modification History:
 * Why & What modified:
 */

public class SplashBanner extends Banner{

    public SplashBanner(Context context) {
        super(context,null);
    }
    public SplashBanner(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }
    public SplashBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        isAutoPlay = false;
        isLoopPlay = false;
    }

    protected void setImageList(List<?> imagesUrl){
        createIndicator();
        for(int i =0;i< count; i++){
            View imageView = null;
            if(imageLoader !=null){
                imageView = imageLoader.createImageView(getContext());
            }
            if(imageView == null){
                imageView = new ImageView(mContext);
            }

            if(imageView instanceof ImageView){
                ImageView view = (ImageView) imageView;
                view.setScaleType(ImageView.ScaleType.FIT_XY);
            }

            Object url = imagesUrl.get(i);

            imageViews.add(imageView);
            if (imageLoader != null)
                imageLoader.displayImage(mContext, url, imageView);
        }
    }


}

package com.daikuan.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.daikuan.R;

import java.io.Serializable;
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

public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener {

    OnBannerListener mListener;
    boolean isAutoPlay;
    boolean isLoopPlay;
    boolean isScall;
    Context mContext;
    int delayTime;

    View root;
    private LinearLayout indicator, indicatorInside, titleView;
    private List<ImageView> indicatorImages;

    ImageLoaderInterface imageLoader;

    private BannerViewPager mPager;
    private BannerPagerAdapter mAdapter;

    List<View> imageViews;
    private List imageUrls;
    protected int count = 0;
    private int lastPosition = 1;

    private int currentItem;

    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int indicatorSize;

    private int mIndicatorSelectedResId = R.drawable.gray_radius;
    private int mIndicatorUnselectedResId = R.drawable.white_radius;

    private BannerScroller mScroller;

    private Handler mHandler;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public Banner setAutoPlay(boolean autoPlay){
        isAutoPlay = autoPlay;
        return this;
    }

    public Banner setLoopPlay(boolean loopPlay){
        isLoopPlay = loopPlay;
        return this;
    }

    public Banner setIndicatorSize(int indicatorSize){
        this.indicatorSize = indicatorSize;
        this.mIndicatorHeight = indicatorSize;
        this.mIndicatorWidth = indicatorSize;
        return this;
    }



    private void init(Context context){
        mContext = context;
        isAutoPlay = true;
        isLoopPlay = true;
        isScall = true;
        delayTime = 2000;
        imageViews = new ArrayList<>();
        imageUrls = new ArrayList();
        indicatorImages = new ArrayList<>();

        mHandler = new Handler();

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        indicatorSize = dm.widthPixels / 80;
        mIndicatorHeight = indicatorSize;
        mIndicatorWidth = indicatorSize;

        root = LayoutInflater.from(mContext).inflate(R.layout.banner,this,true);
        mPager = (BannerViewPager) root.findViewById(R.id.bannerViewPager);
        indicator = (LinearLayout) root.findViewById(R.id.circleIndicator);
        indicatorInside = (LinearLayout) root.findViewById(R.id.indicatorInside);

        initViewPagerScroll();
    }


    private void initViewPagerScroll() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new BannerScroller(mPager.getContext());
            mField.set(mPager, mScroller);
        } catch (Exception e) {
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(isLoopPlay) {
            indicatorImages.get((lastPosition - 1 + count) % count).setImageResource(mIndicatorUnselectedResId);
            indicatorImages.get((position - 1 + count) % count).setImageResource(mIndicatorSelectedResId);
            lastPosition = position;
        }else{
            indicatorImages.get(lastPosition).setImageResource(mIndicatorUnselectedResId);
            indicatorImages.get(position).setImageResource(mIndicatorSelectedResId);
            lastPosition = position;
        }

        if (position == 0) position = count;
        if (position > count) position = 1;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        currentItem = mPager.getCurrentItem();
        if(isLoopPlay) {
            switch (state) {
                case 0://No operation
                    if (currentItem == 0) {
                        mPager.setCurrentItem(count, false);
                    } else if (currentItem == count + 1) {
                        mPager.setCurrentItem(1, false);
                    }
                    break;
                case 1://start Sliding
                    if (currentItem == count + 1) {
                        mPager.setCurrentItem(1, false);

                    } else if (currentItem == 0) {
                        mPager.setCurrentItem(count, false);
                    }
                    break;
                case 2://end Sliding
                    break;
            }
        }
    }

    public Banner setImages(List<?> imageUrls){
        this.imageUrls = imageUrls;
        this.count = imageUrls.size();
        return this;
    }

    public Banner setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public Banner setOnBannerListener(OnBannerListener listener) {
        this.mListener = listener;
        return this;
    }

    public Banner start(){
        indicator.setVisibility(View.VISIBLE);
        setImageList(imageUrls);
        setData();
        return this;
    }

    protected void setImageList(List<?> imagesUrl){
        createIndicator();
        for(int i =0;i<=count +1; i++){
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

            Object url = null;
            if (i == 0) {
                url = imagesUrl.get(count - 1);
            } else if (i == count + 1) {
                url = imagesUrl.get(0);
            } else {
                url = imagesUrl.get(i - 1);
            }
            if(isLoopPlay || (i>0 && i<=count)) {
                imageViews.add(imageView);
            }
            if (imageLoader != null)
                imageLoader.displayImage(mContext, url, imageView);
        }
    }

    private void setData(){
        if(isLoopPlay) {
            currentItem = 1;
            lastPosition = 1;
        }else{
            currentItem = 0;
            lastPosition = 0;
        }
        if(mAdapter == null){
            mAdapter = new BannerPagerAdapter();
            mPager.addOnPageChangeListener(this);
        }
        mPager.setAdapter(mAdapter);
        mPager.setFocusable(true);
        mPager.setCurrentItem(currentItem);
        mPager.setScrollable(isScall);
        if(isAutoPlay){
            startAutoPlay();
        }
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (count > 1 && isAutoPlay) {
                currentItem = currentItem % (count + 1) + 1;
//                Log.i(tag, "curr:" + currentItem + " count:" + count);
                if (currentItem == 1) {
                    mPager.setCurrentItem(currentItem, false);
                    mHandler.post(task);
                } else {
                    mPager.setCurrentItem(currentItem);
                    mHandler.postDelayed(task, delayTime);
                }
            }
        }
    };

    private void startAutoPlay(){
        mHandler.removeCallbacks(task);
        mHandler.postDelayed(task, delayTime);
    }

    protected void createIndicator() {
        indicatorImages.clear();
        indicator.removeAllViews();
        indicatorInside.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
            params.leftMargin = 5;
            params.rightMargin = 5;
            if (i == 0) {
                imageView.setImageResource(mIndicatorSelectedResId);
            } else {
                imageView.setImageResource(mIndicatorUnselectedResId);
            }
            indicatorImages.add(imageView);
            indicator.addView(imageView, params);
        }
    }



    public  interface OnBannerListener{
        public void onBannerClick(int position);
    }

    public  interface ImageLoaderInterface <T extends View> extends Serializable {
        void displayImage(Context context, Object path, T imageView);

        T createImageView(Context context);
    }

    public static class ResImageLoader extends Banner.ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Integer res = (Integer)path;
            imageView.setBackgroundResource(res);
        }
    }

    public static abstract class ImageLoader implements ImageLoaderInterface<ImageView> {

        @Override
        public ImageView createImageView(Context context) {
            ImageView imageView = new ImageView(context);
            return imageView;
        }

    }

    class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            View view = imageViews.get(position);

            if (mListener != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onBannerClick(toRealPosition(position));
                    }
                });
            }
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public int toRealPosition(int position) {
        if(isLoopPlay) {
            int realPosition = (position - 1) % count;
            if (realPosition < 0)
                realPosition += count;
            return realPosition;
        }else{
            return position;
        }

    }


    class BannerScroller extends Scroller {
        private int mDuration = 800;

        public BannerScroller(Context context) {
            super(context);
        }

        public BannerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }


        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setDuration(int time) {
            mDuration = time;
        }
    }
}

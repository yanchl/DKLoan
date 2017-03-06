package com.daikuan;

import com.daikuan.R;
import com.daikuan.util.PrefUtil;
import com.daikuan.view.Banner;
import com.umeng.analytics.MobclickAgent;
import com.daikuan.util.GlobalUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class PageSplash extends Activity implements Banner.OnBannerListener {

	Handler mUIHandler;
	LinearLayout splash;
	Banner mBanner;
	PrefUtil prefUtil;
	boolean isFirstrun;

	List<Integer> imageUrls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_splash);

		prefUtil = PrefUtil.getinstance(this);
		isFirstrun = prefUtil.getBoolean(PrefUtil.KEY_FIRST_RUN, true);



        splash = (LinearLayout) findViewById(R.id.rl_splash);
		mBanner  = (Banner) findViewById(R.id.splash_banner);
		Animation anim = AnimationUtils.loadAnimation(this,
				R.anim.anim_splash_alpha);

		anim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (GlobalUtil.isActivityExist(PageSplash.this)) {
					getUIHandler().postDelayed(openMain, 1100l);
				}
			}
		});



		if(!isFirstrun) {
            splash.setVisibility(View.VISIBLE);
			mBanner.setVisibility(View.GONE);
            splash.startAnimation(anim);
		}else{
			bannerInit();
			mBanner.setImages(imageUrls)
					.setOnBannerListener(this)
					.setImageLoader(new Banner.ResImageLoader()).setAutoPlay(false).setLoopPlay(false).start();
			mBanner.setVisibility(View.VISIBLE);
            splash.setVisibility(View.GONE);
		}
	}

	private void bannerInit(){
		imageUrls = new ArrayList<>();
		imageUrls.add(R.drawable.splash_page_1);
		imageUrls.add(R.drawable.splash_page_2);
	}
	Runnable openMain = new Runnable() {
		@Override
		public void run() {
			if (GlobalUtil.isActivityExist(PageSplash.this)) {
				openMainPage();
			}
		}
	};

	@Override
	public void onBackPressed() {
//		getUIHandler().removeCallbacks(openMain);
//		openMainPage();
	}

	void openMainPage() {
		Intent i = new Intent();
		i.setClass(this, MainActivity.class);
		prefUtil.putBoolean(PrefUtil.KEY_FIRST_RUN,false);
		startActivity(i);
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	Handler getUIHandler() {
		if (mUIHandler == null) {
			mUIHandler = new Handler();
		}
		return mUIHandler;
	}

	@Override
	public void onBannerClick(int position) {
		if(position == imageUrls.size()-1){
			if (GlobalUtil.isActivityExist(PageSplash.this)) {
				openMainPage();
			}
		}
	}
}

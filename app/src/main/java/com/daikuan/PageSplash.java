package com.daikuan;

import com.daikuan.R;
import com.umeng.analytics.MobclickAgent;
import com.daikuan.util.GlobalUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class PageSplash extends Activity {

	Handler mUIHandler;
	ImageView mImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_splash);
		mImg = (ImageView) findViewById(R.id.background);
		Animation anim = AnimationUtils.loadAnimation(this,
				R.anim.anim_splash_alpha);
		mImg.startAnimation(anim);
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

}

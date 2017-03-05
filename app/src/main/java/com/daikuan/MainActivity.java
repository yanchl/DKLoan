package com.daikuan;

import java.util.ArrayList;
import com.daikuan.R;
import com.umeng.analytics.MobclickAgent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	ImageView mImage1, mImage2;
	TextView mText1JianDai, mText2Daikuan;
	ViewPager mViewPager;
	ArrayList<Fragment> mFragments;
	MyFragmentAdapter mFragmentAdapter;

	View mHeaderBack;
	TextView mHeaderTitle;
	View mHeaderContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_first_activity_layout);
		initView();
		initAction();
	}

	private void initAction() {
		mText1JianDai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(0, false);
			}
		});

		mText2Daikuan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(1, false);

			}
		});
//		mText3Banka.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mViewPager.setCurrentItem(1, false);
//			}
//		});
		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageSelected(int arg0) {
						if (arg0 == 0) {
							showItem1();
						} else if (arg0 == 1) {
							showItem2();
						} else if (arg0 == 2) {
							showItem3();
						}
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
					}
				});
	}

	private void initView() {
		mHeaderBack = findViewById(R.id.header_back);
		mHeaderTitle = (TextView) findViewById(R.id.header_middle_text);
		mHeaderContainer = findViewById(R.id.header_container);
		mHeaderBack.setVisibility(View.INVISIBLE);
		mHeaderTitle.setText(getString(R.string.header_title_yijiandaikuan));

		mText1JianDai = (TextView) findViewById(R.id.yijiandaikuan);
		mText2Daikuan = (TextView) findViewById(R.id.banka);
//		mText3Banka = (TextView) findViewById(R.id.banka);
		mImage1 = (ImageView) findViewById(R.id.image_1);
		mImage2 = (ImageView) findViewById(R.id.image_2);
//		mImage3 = (ImageView) findViewById(R.id.image_3);
		showItem1();
		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		mViewPager.setOffscreenPageLimit(2);
		if (mFragments == null) {
			mFragments = new ArrayList<>();
		}
		// 正在发送定时器管理
		mFragments.add(new PageFragDaQuan());
		mFragments.add(new PageFragYiJianDai());
//		mFragments.add(new PageFragGongJu());

		if (mFragmentAdapter == null) {
			mFragmentAdapter = new MyFragmentAdapter(
					getSupportFragmentManager());
		}
		mViewPager.setAdapter(mFragmentAdapter);
	}

	private void showItem1() {
		mText1JianDai.setTextColor(getResources().getColor(R.color.red_bg));
		mText2Daikuan.setTextColor(getResources().getColor(
				R.color.bottom_black_color));
//		mText3Banka.setTextColor(getResources().getColor(
//				R.color.bottom_black_color));
		mImage1.setVisibility(View.VISIBLE);
		mImage2.setVisibility(View.INVISIBLE);
//		mImage3.setVisibility(View.INVISIBLE);
		mHeaderTitle.setText(R.string.header_title_yijiandaikuan);
	}

	private void showItem3() {
		mText1JianDai.setTextColor(getResources().getColor(
				R.color.bottom_black_color));
		mText2Daikuan.setTextColor(getResources().getColor(R.color.red_bg));
//		mText3Banka.setTextColor(getResources().getColor(
//				R.color.bottom_black_color));
		mImage1.setVisibility(View.INVISIBLE);
		mImage2.setVisibility(View.VISIBLE);
//		mImage3.setVisibility(View.INVISIBLE);
		mHeaderTitle.setText(R.string.header_title_daikuan);
	}

	private void showItem2() {
		mText1JianDai.setTextColor(getResources().getColor(
				R.color.bottom_black_color));
		mText2Daikuan.setTextColor(getResources().getColor(
				R.color.bottom_black_color));
//		mText3Banka.setTextColor(getResources().getColor(R.color.red_bg));
		mImage1.setVisibility(View.INVISIBLE);
		mImage2.setVisibility(View.VISIBLE);
//		mImage3.setVisibility(View.VISIBLE);
		mHeaderTitle.setText(R.string.header_title_xinyongka);
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

	class MyFragmentAdapter extends FragmentStatePagerAdapter {
		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragments == null ? null : mFragments.get(arg0);
		}

		@Override
		public int getCount() {
			return mFragments == null ? 0 : mFragments.size();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// 发现页不删除
			super.destroyItem(container, position, object);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// 发现页不删除
			super.destroyItem(container, position, object);
		}
	}

	public int getCurrentItemNum() {
		return mViewPager.getCurrentItem();
	}

	static long lastclick_time = 0;
	static long timestamp_duration = 2000l;

	@Override
	public void onBackPressed() {
		boolean willFinish = true;
		if (willFinish) {
			long currTime = System.currentTimeMillis();
			if (lastclick_time == 0) {
				GlobalUtil.shortToast(this,
						getString(R.string.double_click_back));
				lastclick_time = currTime;
			} else {
				if (currTime - lastclick_time >= timestamp_duration) {
					GlobalUtil.shortToast(this,
							getString(R.string.double_click_back));
					lastclick_time = currTime;
				} else {
					finish();
					lastclick_time = 0;
				}

			}
		}
	}

}

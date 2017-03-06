package com.daikuan;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.daikuan.R;
import com.daikuan.adapter.LoanerListAdapter;
import com.daikuan.util.Umeng;
import com.daikuan.view.Banner;
import com.daikuan.view.FullListView;

import java.util.ArrayList;
import java.util.List;


public class PageFragDaQuan extends Fragment implements Banner.OnBannerListener {

	private static final String TAG = PageFragDaQuan.class.getSimpleName();

	ViewGroup mAdContainer;
	ImageView mDaikuanHaodai, mDaikuanRong360, mKaHaodai, mKaRong360;
	// View mCloseTip;
	// View mTipContainer;

	/*
	 * 信用卡申请，找贷款，信贷员推广
	 */
	View mXinyongkaShenqing, mZhaoDaikuan, mXinDaiYuan;
	/*
	 * 各大贷款渠道
	 */
	View mHaoDai, mRong360, mPaiPaiDai, mPingAn, mShanyin, /*mMashangDai,*/
			mShoujiDai, mYirenDai;

	Banner mBanner;

	FullListView mListView;
	LoanerListAdapter mAdapter;

	public PageFragDaQuan() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return LayoutInflater.from(getActivity()).inflate(
				R.layout.frag_layout_daquan, container, false);
	}

	@Override
	public void onViewCreated(final View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		freshData();
		initView(view);
		initData();
		initAction();
		initContentObserver();
	}

	private void initAction() {
		mXinyongkaShenqing.setOnClickListener(mListener);
		mZhaoDaikuan.setOnClickListener(mListener);
		mXinDaiYuan.setOnClickListener(mListener);

		mListView.setAdapter(mAdapter);

		mBanner.setFocusable(true);
		mBanner.setFocusableInTouchMode(true);
		mBanner.requestFocus();
	}

	View.OnClickListener mListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {

			}
		}
	};

	private void initData() {
		// 广点通广告，只在应用宝渠道保留
		// initAd();
		List<Integer> bannerResIds = new ArrayList<>();
		bannerResIds.add(R.drawable.daikuan_haodai);
		bannerResIds.add(R.drawable.daikuan_rong360);

		mBanner.setImageLoader(new Banner.ResImageLoader())
				.setImages(bannerResIds)
				.setOnBannerListener(this).start();

		mAdapter = new LoanerListAdapter(getActivity());
	}

	private void initView(View view) {

		mXinyongkaShenqing = view.findViewById(R.id.xinyongka_shenqing);

		/*
		 * 信用卡申请，找贷款，信贷员推广
		 */
		// View mXinyongkaShenqing,mZhaoDaikuan,mXinDaiYuan;
		/*
		 * 各大贷款渠道
		 */
		// View
		// mHaoDai,mRong360,mPaiPaiDai,mPingAn,mShanyin,mMashangDai,mShoujiDai,mYirenDai;

		mXinyongkaShenqing = view.findViewById(R.id.xinyongka_shenqing);
		mZhaoDaikuan = view.findViewById(R.id.zhaodaikuan);
		mXinDaiYuan = view.findViewById(R.id.xindaiyuan_qiangdan);


		mBanner = (Banner)view.findViewById(R.id.banner);
		mListView = (FullListView) view.findViewById(R.id.flv_list);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 初始化content observer
	 */
	private void initContentObserver() {
	}

	/**
	 * 刷新数据源
	 */
	private void freshData() {
	}


	@Override
	public void onBannerClick(int position) {

		switch (position){
			case 0:
				Umeng.onEvent(getContext(),Umeng.EVENTID_BANNER_1);
				GlobalUtil.openWebview(getActivity(), Constant.URL_DAIKUAN_HAODAI, getString(R.string.d_haodai));
				break;
			case 1:
				Umeng.onEvent(getContext(),Umeng.EVENTID_BANNER_2);
				GlobalUtil.openWebview(getActivity(), Constant.URL_DAIKUAN_RONG360, getString(R.string.d_rong360));

				break;
		}
	}



}

package com.daikuan;

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

public class PageFragDaQuan extends Fragment {

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
		mHaoDai.setOnClickListener(mListener);
		mRong360.setOnClickListener(mListener);
		mPaiPaiDai.setOnClickListener(mListener);
		mPingAn.setOnClickListener(mListener);
		mShanyin.setOnClickListener(mListener);
//		mMashangDai.setOnClickListener(mListener);
		mShoujiDai.setOnClickListener(mListener);
		mYirenDai.setOnClickListener(mListener);
	}

	View.OnClickListener mListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.xinyongka_shenqing:
				GlobalUtil.openWebview(getActivity(), Constant.URL_XINYONGKA_HAODAI_HAODAI, "信用卡申请");
				break;
			case R.id.zhaodaikuan:
				GlobalUtil.openWebview(getActivity(), Constant.FIND_DAIKUAN, "找贷款");
				break;
			case R.id.xindaiyuan_qiangdan:
				GlobalUtil.openWebview(getActivity(), Constant.XinDaiYuan_url, "信贷员抢单");
				break;
			case R.id.d_haodaiwang:
				GlobalUtil.openWebview(getActivity(), Constant.URL_DAIKUAN_HAODAI, getString(R.string.d_haodai));
				break;
			case R.id.d_rong360:
				GlobalUtil.openWebview(getActivity(), Constant.URL_DAIKUAN_RONG360, getString(R.string.d_rong360));
				break;
				
			case R.id.d_paipaidai:
				GlobalUtil.openWebview(getActivity(), Constant.URL_DAIKUAN_PAIPAI, getString(R.string.d_paipai),true);

				break;
			case R.id.d_pinganhuipu:
				GlobalUtil.openWebview(getActivity(), Constant.DAIKUAN_PINGANHUIPU, getString(R.string.d_pingan));

				break;
			case R.id.d_shanyin:
				GlobalUtil.openWebview(getActivity(), Constant.DAIKUAN_SHANYIN, getString(R.string.d_shanyin));

				break;
//			case R.id.d_mashang:
//				GlobalUtil.openWebview(getActivity(), Constant.DAIKUAN_MASHANG, getString(R.string.d_shanyin));

//				break;
			case R.id.d_shoujidai:
				GlobalUtil.openWebview(getActivity(), Constant.DAIKUAN_SHOUJIDAI, getString(R.string.d_shoujidai));

				break;
			case R.id.d_yirendai:
				GlobalUtil.openWebview(getActivity(), Constant.DAIKUAN_YIRENDAI, getString(R.string.d_yirendai),true);

				break;
			default:
				break;
			}
		}
	};

	private void initData() {
		// 广点通广告，只在应用宝渠道保留
		// initAd();
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

		mHaoDai = view.findViewById(R.id.d_haodaiwang);
		mRong360 = view.findViewById(R.id.d_rong360);
		mPaiPaiDai = view.findViewById(R.id.d_paipaidai);
		mPingAn = view.findViewById(R.id.d_pinganhuipu);
		mShanyin = view.findViewById(R.id.d_shanyin);
//		mMashangDai = view.findViewById(R.id.d_mashang);
		mShoujiDai = view.findViewById(R.id.d_shoujidai);
		mYirenDai = view.findViewById(R.id.d_yirendai);

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

}

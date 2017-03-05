package com.daikuan;
import com.daikuan.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PageFragYiJianDai extends Fragment {
	
	private static final String TAG = PageFragYiJianDai.class
			.getSimpleName();
	
	ViewGroup mAdContainer;
	ImageView mDaikuanHaodai,mDaikuanRong360,mKaHaodai,mKaRong360;
	View mCloseTip;
	View mTipContainer;
	
	String title_daikuan = "申请贷款";
	String title_banka = "申请信用卡";
	View mZhaoDaikuan,mXinDaiYuan;
	
	public PageFragYiJianDai() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return LayoutInflater.from(getActivity()).inflate(
				R.layout.frag_layout_daikuanlist,
				container, false);
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
		mCloseTip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTipContainer.setVisibility(View.GONE);
			}
		});
		
		mDaikuanHaodai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.URL_DAIKUAN_HAODAI, title_daikuan);
			}
		});
		
		mDaikuanRong360.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.URL_DAIKUAN_RONG360, title_daikuan);
			}
		});
		
		mKaHaodai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.URL_XINYONGKA_HAODAI_HAODAI, title_banka);
			}
		});
		
		mKaRong360.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.URL_XINYONGKA_RONG360, title_banka);
			}
		});
		
		mZhaoDaikuan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.FIND_DAIKUAN, "找贷款");
			}
		});
		mXinDaiYuan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.XinDaiYuan_url, "信贷圈");
			}
		});
	}

	private void initData() {
		//广点通广告，只在应用宝渠道保留
//		initAd();
	}

	private void initView(View view) {
		mDaikuanHaodai = (ImageView) view.findViewById(R.id.daikuan_haodai);
		mDaikuanRong360 = (ImageView) view.findViewById(R.id.daikuan_rong360);
		mKaHaodai = (ImageView) view.findViewById(R.id.ka_haodai);
		mKaRong360 = (ImageView) view.findViewById(R.id.ka_rong360);
		mCloseTip = view.findViewById(R.id.close);
		mTipContainer = view.findViewById(R.id.tip_container);
		mZhaoDaikuan = view.findViewById(R.id.zhaodaikuan);
		mXinDaiYuan = view.findViewById(R.id.xindaiyuan);
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

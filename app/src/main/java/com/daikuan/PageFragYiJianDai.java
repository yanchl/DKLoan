package com.daikuan;
import com.daikuan.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class PageFragYiJianDai extends Fragment {
	
	private static final String TAG = PageFragYiJianDai.class
			.getSimpleName();
	
	ViewGroup mAdContainer;
	ImageView mKaHaodai;
//	View mCloseTip;
//	View mTipContainer;
	
	String title_daikuan = "申请贷款";
	String title_banka = "申请信用卡";
	View mXianshangbanka,mYuyuebanka,mJiandandai,mXindaiquan;
	View mZhengxin, mJisuanqi, mLaolai, mXinyongKaJindu;
	
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
		
		mKaHaodai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.URL_HAODAI_XINGYONGKA_NORMAL, title_banka,true);
			}
		});


		mXianshangbanka.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.URL_HAODAI_XINGYONGKA_ONLING, "线上办卡");
			}
		});
		mYuyuebanka.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.URL_HAODAI_XINYONGKA_OFFLINE, "预约办卡");
			}
		});
		mJiandandai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.URL_XIANJINBAIKA, "现金白卡简单贷",true);
			}
		});
		mXindaiquan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.URL_XINDAIQUAN, "信贷圈");
			}
		});
		mZhengxin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.ZhengXinUrl,
						"查征信");
			}
		});
		mXinyongKaJindu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(),"正在抓紧开发该功能，请等待",Toast.LENGTH_LONG).show();
			}
		});
		mJisuanqi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.DaiKuanJiSuanQi, "贷款计算器");
			}
		});
	}

	private void initData() {
		//广点通广告，只在应用宝渠道保留
//		initAd();
	}

	private void initView(View view) {
		mKaHaodai = (ImageView) view.findViewById(R.id.ka_haodai);
		mXianshangbanka = view.findViewById(R.id.xianshangbanka);
		mYuyuebanka = view.findViewById(R.id.yuyuebanka);
		mJiandandai = view.findViewById(R.id.jiandandai);
		mXindaiquan = view.findViewById(R.id.xindaiquan);
		mZhengxin = view.findViewById(R.id.zhengxin);
		mJisuanqi = view.findViewById(R.id.jisuanqi);
		mXinyongKaJindu = view.findViewById(R.id.xinyongka_jindu);
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

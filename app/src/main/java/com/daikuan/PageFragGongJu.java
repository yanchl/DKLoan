package com.daikuan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daikuan.R;

public class PageFragGongJu extends Fragment {

	private static final String TAG = PageFragGongJu.class.getSimpleName();
	View mGoHome;

	View mZhengxin, mJisuanqi, mLaolai, mXinyongKaJindu;

	public PageFragGongJu() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return LayoutInflater.from(getActivity()).inflate(
				R.layout.frag_layout_gongju, container, false);
	}

	@Override
	public void onViewCreated(final View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initData();
		initAction();
	}

	private void initAction() {
		mZhengxin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.ZhengXinUrl,
						"查征信");
			}
		});
		mJisuanqi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.DaiKuanJiSuanQi,
						"计算器");
			}
		});
		mLaolai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.openWebview(getActivity(), Constant.LaoLaiUrl,
						"老赖名单查询");
			}
		});
		mXinyongKaJindu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalUtil.shortToast(getActivity(), "小猿正在努力增加该功能");
			}
		});
	}

	private void initData() {
		// 广点通广告，只在应用宝渠道保留
		// initAd();
	}

	private void initView(View view) {
		mZhengxin = view.findViewById(R.id.zhengxin);
		mJisuanqi = view.findViewById(R.id.jisuanqi);
		mLaolai = view.findViewById(R.id.laolai);
		mXinyongKaJindu = view.findViewById(R.id.xinyongka_jindu);
	}

	String mAppCachePath;

	private String getAppCachePath() {
		if (mAppCachePath == null) {
			mAppCachePath = getActivity().getDir("appcache", 0).getPath();
		}
		return mAppCachePath;
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

	ProgressDialog mProgress;

	private ProgressDialog newLoadingDialog() {
		if (mProgress == null) {
			mProgress = new ProgressDialog(getActivity());
			mProgress.setTitle(null);
			mProgress.setMessage("加载中");
			mProgress.setIndeterminate(true);
			mProgress.setCancelable(true);
			mProgress.setCanceledOnTouchOutside(true);
			mProgress
					.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
						}
					});
			mProgress.setCanceledOnTouchOutside(false);
		}
		return mProgress;
	}

	private boolean isCurrentPage() {
		MainActivity parent = (MainActivity) getActivity();
		return parent.getCurrentItemNum() == 2;
	}

}

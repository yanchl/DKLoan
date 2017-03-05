package com.daikuan;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daikuan.R;

public class PageWebview extends Activity {
	WebView mWebview;
	LinearLayout mHeaderBack;
//	LinearLayout mLoadingArea;
//	LinearLayout mHeaderMoreInfo;
//	ProgressBar mProgressBar;
	TextView mTitle;

	String mTitleStr;
	String mUrl;
	boolean mMarginTop = false;
	public static final String KEY_TITLE = "title";
	public static final String KEY_URL = "url";
	public static final String KEY_MARGINTOP = "margintop";
	
	View mGoBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!handleIntent()) {
			finish();
			return;
		}
		setContentView(R.layout.page_webview_layout);
		initView();
		initData();
		initWebview();
		initAction();
		startLoading();
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onResume() {
		super.onResume();
		mWebview.onResume();
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onPause() {
		super.onPause();
		mWebview.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initData() {
		mTitle.setText(mTitleStr);
	}

	private void startLoading() {
		mWebview.loadUrl(mUrl);
	}

	private void initWebview() {
		WebSettings settings = mWebview.getSettings();
		settings.setUserAgentString(Constant.UA);
		settings.setJavaScriptEnabled(true);
		settings.setLoadsImagesAutomatically(true);
		settings.setSupportZoom(true);
		settings.setDomStorageEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);

        settings.setGeolocationEnabled(true);
        settings.setLightTouchEnabled(true);
        //delete by zhenchuan because api 19 not support
//        settings.setNavDump(enableNavDump());
        settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        settings.setMinimumFontSize(getMinimumFontSize());
//        settings.setMinimumLogicalFontSize(getMinimumFontSize());
        settings.setPluginState(PluginState.ON);
//        settings.setTextZoom(10);
        settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSavePassword(true);
        settings.setSaveFormData(true);
        settings.setUseWideViewPort(true);
        syncStaticSettings(settings);
	}
	
	  /**
     * Syncs all the settings that have no UI
     * These cannot change, so we only need to set them once per WebSettings
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void syncStaticSettings(WebSettings settings) {
        settings.setDefaultFontSize(16);
        settings.setDefaultFixedFontSize(13);

        // WebView inside Browser doesn't want initial focus to be set.
        settings.setNeedInitialFocus(false);
        // Browser supports multiple windows
        settings.setSupportMultipleWindows(true);
        // enable smooth transition for better performance during panning or
        // zooming
        try {
        	settings.setEnableSmoothTransition(true);
		} catch (Exception e) {
		}catch(Error e){
			
		}
			// TODO: handle exception
        // disable content url access
        settings.setAllowContentAccess(false);

        // HTML5 API flags
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        // HTML5 configuration parametersettings.
//        settings.setAppCacheMaxSize(getWebStorageSizeManager().getAppCacheMaxSize());
        settings.setAppCachePath(getAppCachePath());
        settings.setDatabasePath(getDir("databases", 0).getPath());
        settings.setGeolocationDatabasePath(getDir("geolocation", 0).getPath());
        // origin policy for file access
        //delete by zhenchuan
//        settings.setAllowUniversalAccessFromFileURLs(false);
//        settings.setAllowFileAccessFromFileURLs(false);
    }
    
    String mAppCachePath;
    private String getAppCachePath() {
        if (mAppCachePath == null) {
            mAppCachePath = getDir("appcache", 0).getPath();
        }
        return mAppCachePath;
    }

	private boolean handleIntent() {
		Intent i = getIntent();
		if (i != null) {
			mTitleStr = i.getStringExtra(KEY_TITLE);
			mUrl = i.getStringExtra(KEY_URL);
			mMarginTop = i.getBooleanExtra(KEY_MARGINTOP, false);
			return true;
		} else {
			return false;
		}
	}

	private void initAction() {
		
		mGoBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mWebview.canGoBack()) {
					mWebview.goBack();
				}else{
					finish();
				}
			}
		});
		
		mHeaderBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mWebview.setDownloadListener(new DownloadListener() {
			
			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype, long contentLength) {
				
			}
		});

		mWebview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				super.shouldOverrideUrlLoading(view, url);
				if(url.startsWith("itms-appss")){
					Toast.makeText(PageWebview.this, "暂时不用登录", Toast.LENGTH_SHORT).show();
					return true;
				}else{
					view.loadUrl(url);
					return false;
				}
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				if(GlobalUtil.isActivityExist(PageWebview.this)){
					newLoadingDialog().show();
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				if(GlobalUtil.isActivityExist(PageWebview.this)){
					newLoadingDialog().dismiss();
				}
			}
		});

		mWebview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if(newProgress>=70){
					newLoadingDialog().dismiss();
				}
			}
			
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
//				mTitle.setText(title);
			}
		});
	}

	private void initView() {
		mHeaderBack = (LinearLayout) findViewById(R.id.header_back);
		mTitle = (TextView) findViewById(R.id.header_middle_text);
		mWebview = (WebView)findViewById(R.id.page_webview);
		if (mMarginTop) {
			MarginLayoutParams params =(MarginLayoutParams) mWebview.getLayoutParams();
			params.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.page_header_actionbar_height), 0, 0);
		}
		mHeaderBack.setVisibility(View.VISIBLE);
		mGoBack = findViewById(R.id.goback);
	}

	@Override
	public void onBackPressed() {
		if (mWebview.canGoBack()) {
			mWebview.goBack();
		} else {
			super.onBackPressed();
		}
	}
	
	ProgressDialog mProgress;
	private ProgressDialog newLoadingDialog() {
		if (mProgress == null) {
			mProgress = new ProgressDialog(this);
			mProgress.setTitle(null);
			mProgress.setMessage("加载中");
			mProgress.setIndeterminate(true);
			mProgress.setCancelable(true);
			mProgress.setCanceledOnTouchOutside(true);
			mProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
						}
					});
			mProgress.setCanceledOnTouchOutside(false);
		}
		return mProgress;
	}
	
}

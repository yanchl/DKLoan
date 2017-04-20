package com.daikuan;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
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
import com.daikuan.util.ImageUtils;

import java.io.File;
import java.util.List;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class PageWebview extends Activity {
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
	WebView mWebview;
	LinearLayout mHeaderBack;
//	LinearLayout mLoadingArea;
//	LinearLayout mHeaderMoreInfo;
//	ProgressBar mProgressBar;
	TextView mTitle;

	String mTitleStr;
	String mUrl;
	boolean mMarginTop = false;
    boolean isIosUa = false;
	public static final String KEY_TITLE = "title";
	public static final String KEY_URL = "url";
	public static final String KEY_MARGINTOP = "margintop";
	public static final String KEY_USE_IOS_UA = "use_ios_ua";
	
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

        //从相机或相册取出照片后如果重新选择需要制空变量
        if (mUploadCallbackAboveL != null) {
            mUploadCallbackAboveL.onReceiveValue(null);
        }
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
        }
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
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});

		mWebview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, final String url) {

                if (url.startsWith("itms-appss")) {
                    Toast.makeText(PageWebview.this, "暂时不用登录", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (url.startsWith("tel:")) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PageWebview.this);
                    builder.setTitle("温馨提示").setMessage("是否询问客服？").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                            //android6.0后可以对权限判断
                            if (ActivityCompat.checkSelfPermission(PageWebview.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(PageWebview.this, "请在应用管理中打开“电话”访问权限！", Toast.LENGTH_SHORT).show();
//                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);//权限申请
                                return;
                            }
                            startActivity(intent);
                        }
                    }).show();
                    return true;
                } else{

                    return super.shouldOverrideUrlLoading(view, url);
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

			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				Toast.makeText(PageWebview.this,"记载失败"+description,Toast.LENGTH_LONG).show();
			};
		});

		mWebview.setWebChromeClient(new WebChromeClient() {
            //配置权限（同样在WebChromeClient中实现）
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }

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


            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                showOptions();

            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                showOptions();
            }

            // For Android > 5.0支持多张上传
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, WebChromeClient.FileChooserParams
                    fileChooserParams) {
                mUploadCallbackAboveL = uploadMsg;
                showOptions();
                return true;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                if (photos == null) {
                    Toast.makeText(PageWebview.this, "没有获取到照片",Toast.LENGTH_LONG).show();
                    return;
                }
                String imagepath = photos.get(0);
                //压缩图片
                Bitmap bitmap = ImageUtils.ratio(imagepath, 950, 1280);
                //得到图片新的路径
                String newfilepath=ImageUtils.saveMyBitmap(bitmap);
                //获取图片URI
                Uri uri =ImageUtils.getImageContentUri(PageWebview.this,new File(newfilepath));
                if (mUploadCallbackAboveL != null) {
                    Uri[] uris = new Uri[]{uri};
                    mUploadCallbackAboveL.onReceiveValue(uris);
                    mUploadCallbackAboveL = null;
                } else {
                    mUploadMessage.onReceiveValue(uri);
                    mUploadMessage = null;
                }
            }
            mUploadMessage = null;
        }
    }

    public void showOptions() {
        //如果大于6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionGen.with(PageWebview.this)
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .request();
        }else{
            try{
                PhotoPicker.builder().setPhotoCount(1).start(PageWebview.this);
            }catch (Exception e){
                Toast.makeText(this, "请打开相机拍照及读写内存卡的权限", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class ReOnCancelListener implements DialogInterface.OnCancelListener {

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
            }
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
                mUploadCallbackAboveL = null;
            }
        }
    }

    @PermissionSuccess(requestCode = 100)
    public void camera_success(){
        PhotoPicker.builder().setPhotoCount(1).start(PageWebview.this);
    }

    @PermissionFail(requestCode = 100)
    private void camera_fail() {
        Toast.makeText(this, "请打开相机拍照及读写内存卡的权限", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);

    }
	
}

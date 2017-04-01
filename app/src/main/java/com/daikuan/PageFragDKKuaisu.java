package com.daikuan;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by jason on 17/3/13.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class PageFragDKKuaisu extends Fragment {
    WebView mWebview;
    String mUrl;
    View mGoBack;
    View mHeadView;

    private static final String TAG = PageFragDKKuaisu.class
            .getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(
                R.layout.page_webview_layout,
                container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        freshData();
        initView(view);
        initData();
        initWebview();
        initAction();
        initContentObserver();

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
        settings.setPluginState(WebSettings.PluginState.ON);
//        settings.setTextZoom(10);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
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

    }

    private void initAction() {

        mWebview.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                try {
                    Uri uri = Uri.parse(url);
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_VIEW);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setData(uri);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                if(url.startsWith("itms-appss")){
                    Toast.makeText(getActivity(), "暂时不用登录", Toast.LENGTH_SHORT).show();
                    return true;
                }else{
                    view.loadUrl(url);
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//				mTitle.setText(title);
            }
        });
    }

    private void initData() {
        //广点通广告，只在应用宝渠道保留
//		initAd();
        mUrl = Constant.URL_DAIKUAN_HAODAI;
    }

    private void initView(View view) {
        mWebview = (WebView)view.findViewById(R.id.page_webview);

        mGoBack = view.findViewById(R.id.goback);
        mGoBack.setVisibility(View.GONE);

        mHeadView = view.findViewById(R.id.header_view);
        mHeadView.setVisibility(View.GONE);
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

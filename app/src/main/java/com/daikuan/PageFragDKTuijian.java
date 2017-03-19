package com.daikuan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.daikuan.adapter.LoanerListAdapter;
import com.daikuan.model.DKRecommend;
import com.daikuan.model.LoanItem;
import com.daikuan.util.NetworkUtil;
import com.daikuan.util.Umeng;
import com.daikuan.view.Banner;
import com.daikuan.view.FullListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by jason on 17/3/15.
 */

public class PageFragDKTuijian extends Fragment implements Banner.OnBannerListener {

    private static final String TAG = PageFragDKTuijian.class.getSimpleName();

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
    ScrollView contentLv;
    View errorView;

    FullListView mListView;
    LoanerListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(
                R.layout.frag_layout_dktuijian, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        freshData();
        initView(view);
        initAction();
        initData();
    }

    private void initAction() {
        mXinyongkaShenqing.setOnClickListener(mListener);
        mZhaoDaikuan.setOnClickListener(mListener);
        mXinDaiYuan.setOnClickListener(mListener);


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

        if(!NetworkUtil.isNetworkAvailable(getActivity())){
            contentLv.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(),"当前网络有问题",Toast.LENGTH_LONG).show();
            return;
        }


        // 广点通广告，只在应用宝渠道保留
        // initAd();
        List<Integer> bannerResIds = new ArrayList<>();
        bannerResIds.add(R.drawable.daikuan_haodai);
        bannerResIds.add(R.drawable.daikuan_rong360);

        mBanner.setImageLoader(new Banner.ResImageLoader())
                .setImages(bannerResIds)
                .setOnBannerListener(this).start();

        BmobQuery<DKRecommend> query = new BmobQuery<DKRecommend>();
        query.setLimit(50);
        query.order("order");
        query.include("loan");

        query.findObjects(new FindListener<DKRecommend>() {

            @Override
            public void done(List<DKRecommend> list, BmobException e) {
                if (e == null) {
                    if (list != null) {
                        List<LoanItem> loanItems = new ArrayList<LoanItem>();
                        for (DKRecommend dkRecommend : list) {
                            loanItems.add(dkRecommend.getLoan());
                        }

                        mAdapter = new LoanerListAdapter(getActivity(), loanItems);

                        mListView.setAdapter(mAdapter);
                    }else{
                        contentLv.setVisibility(View.GONE);
                        errorView.setVisibility(View.VISIBLE);
                    }

                } else {
                    contentLv.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"获取服务端资料出错:"+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });


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


        mBanner = (Banner) view.findViewById(R.id.banner);
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
     * 刷新数据源
     */
    private void freshData() {
    }


    @Override
    public void onBannerClick(int position) {

        switch (position) {
            case 0:
                Umeng.onEvent(getContext(), Umeng.EVENTID_BANNER_1);
                GlobalUtil.openWebview(getActivity(), Constant.URL_DAIKUAN_HAODAI, getString(R.string.d_haodai));
                break;
            case 1:
                Umeng.onEvent(getContext(), Umeng.EVENTID_BANNER_2);
                GlobalUtil.openWebview(getActivity(), Constant.URL_DAIKUAN_RONG360, getString(R.string.d_rong360));

                break;
        }
    }


}
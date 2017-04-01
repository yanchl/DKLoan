package com.daikuan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daikuan.adapter.CategoryLoanerListAdapter;
import com.daikuan.adapter.LoanerListAdapter;
import com.daikuan.model.DKCategory;
import com.daikuan.model.DKRecommend;
import com.daikuan.model.LoanItem;
import com.daikuan.util.NetworkUtil;
import com.daikuan.view.Banner;
import com.daikuan.view.FullListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by jason on 17/3/23.
 */

public class LoanCategoryActivity extends Activity{

    View errorView;

    ListView mListView;
    CategoryLoanerListAdapter mAdapter;
    private Integer category;

    LinearLayout mHeaderBack;
    TextView mTitle;

    String mTitleStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loancategory);
        if (!handleIntent()) {
            finish();
            return;
        }
        initView();
        initData();
    }

    private boolean handleIntent() {
        Intent i = getIntent();
        if (i != null) {
            mTitleStr = i.getStringExtra("title");
            category = i.getIntExtra("category",1);
            return true;
        } else {
            return false;
        }
    }

    public void initView(){
        errorView = findViewById(R.id.rv_empty_category);
        mListView = (ListView) findViewById(R.id.flv_list_category);
        mHeaderBack = (LinearLayout) findViewById(R.id.header_back);
        mTitle = (TextView) findViewById(R.id.header_middle_text);


        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });

        mHeaderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle.setText(mTitleStr);


    }

    public void initData(){
        if(!NetworkUtil.isNetworkAvailable(this)){
            mListView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            Toast.makeText(this,"当前网络有问题",Toast.LENGTH_LONG).show();
            return;
        }

        mListView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);


        // 广点通广告，只在应用宝渠道保留
        // initAd();

        BmobQuery<DKCategory> query = new BmobQuery<DKCategory>();
        query.setLimit(100);
        query.order("order");
        query.include("loanItem");
        query.addWhereEqualTo("categoryId",category);

        query.findObjects(new FindListener<DKCategory>() {

            @Override
            public void done(List<DKCategory> list, BmobException e) {
                if (e == null) {
                    if (list != null) {
                        List<LoanItem> loanItems = new ArrayList<LoanItem>();
                        for (DKCategory dkCategory : list) {
                            if(dkCategory.getLoanItem() != null){
                                loanItems.add(dkCategory.getLoanItem());
                            }
                        }

                        mAdapter = new CategoryLoanerListAdapter(LoanCategoryActivity.this, loanItems);

                        mListView.setAdapter(mAdapter);
                    }else{
                        errorView.setVisibility(View.VISIBLE);
                    }

                } else {
                    mListView.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                    Toast.makeText(LoanCategoryActivity.this,"获取服务端资料出错:"+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
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




}

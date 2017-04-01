package com.daikuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.daikuan.util.Umeng;


/**
 * Created by jason on 17/3/16.
 */

public class PageFragDKDaquan extends Fragment{

    private View loanView1,loanView2,loanView3,loanView4,loanView5,loanView6;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(
                R.layout.frag_layout_dkdaquan, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initAction();
        initData();
    }

    private void initAction() {
    }

    View.OnClickListener mListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent  = new Intent();
            intent.setClass(getActivity(),LoanCategoryActivity.class);

            int id = v.getId();
            switch (id) {
                case R.id.rl_loan_1:
                    intent.putExtra("category",1);
                    intent.putExtra("title","微额极速贷");
                    startActivity(intent);
                    Umeng.onEvent(getActivity(),Umeng.EVENTID_DAIKUAN_DAQUAN,Umeng.EVENTID_DAIKUAN_DAQUAN,"1");
                    break;
                case R.id.rl_loan_2:
                    intent.putExtra("category",2);
                    intent.putExtra("title","热门极速贷");
                    startActivity(intent);
                    Umeng.onEvent(getActivity(),Umeng.EVENTID_DAIKUAN_DAQUAN,Umeng.EVENTID_DAIKUAN_DAQUAN,"2");
                    break;
                case R.id.rl_loan_3:
                    intent.putExtra("category",3);
                    intent.putExtra("title","大额贷款");
                    startActivity(intent);
                    Umeng.onEvent(getActivity(),Umeng.EVENTID_DAIKUAN_DAQUAN,Umeng.EVENTID_DAIKUAN_DAQUAN,"3");
                    break;
                case R.id.rl_loan_4:
                    intent.putExtra("category",4);
                    intent.putExtra("title","信用卡贷款");
                    startActivity(intent);
                    Umeng.onEvent(getActivity(),Umeng.EVENTID_DAIKUAN_DAQUAN,Umeng.EVENTID_DAIKUAN_DAQUAN,"4");
                    break;
                case R.id.rl_loan_5:
                    intent.putExtra("category",5);
                    intent.putExtra("title","线下大额贷");
                    startActivity(intent);
                    Umeng.onEvent(getActivity(),Umeng.EVENTID_DAIKUAN_DAQUAN,Umeng.EVENTID_DAIKUAN_DAQUAN,"5");
                    break;
                case R.id.rl_loan_6:
                    intent.putExtra("category",6);
                    intent.putExtra("title","大学生贷款");
                    startActivity(intent);
                    Umeng.onEvent(getActivity(),Umeng.EVENTID_DAIKUAN_DAQUAN,Umeng.EVENTID_DAIKUAN_DAQUAN,"6");
                    break;
                default:
                    break;

            }
        }
    };

    private void initData() {




    }

    private void initView(View view) {
        loanView1 = view.findViewById(R.id.rl_loan_1);
        loanView2 = view.findViewById(R.id.rl_loan_2);
        loanView3 = view.findViewById(R.id.rl_loan_3);
        loanView4 = view.findViewById(R.id.rl_loan_4);
        loanView5 = view.findViewById(R.id.rl_loan_5);
        loanView6 = view.findViewById(R.id.rl_loan_6);

        loanView1.setOnClickListener(mListener);
        loanView2.setOnClickListener(mListener);
        loanView3.setOnClickListener(mListener);
        loanView4.setOnClickListener(mListener);
        loanView5.setOnClickListener(mListener);
        loanView6.setOnClickListener(mListener);
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





}

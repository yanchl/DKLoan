package com.daikuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.daikuan.GlobalUtil;
import com.daikuan.R;
import com.daikuan.model.LoanItem;
import com.daikuan.util.BitmapCache;
import com.daikuan.util.Umeng;

import java.util.List;

/**
 * Created by jason on 17/3/24.
 */

public class RecommendLoanerListAdapter extends BaseAdapter {

    List<LoanItem> mData;
    Context mContext;
    LayoutInflater mInflater;
    RequestQueue queue;
    ImageLoader loader;

    public RecommendLoanerListAdapter(Context context,List<LoanItem> data){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
        queue = Volley.newRequestQueue(context);
        loader = new ImageLoader(queue,new BitmapCache());
    }

    private void initData(){
//        mData = new ArrayList<>();
//        LoanerItem item = new LoanerItem();
//        item.setName("好贷网-极速贷").setTag("审批快,下款快").setResIconId(R.drawable.logo_haodai)
//                .setContent2("1000-3000000")
//                .setContent4("0.1%").setUmengEventID(Umeng.EVENTID_DK_HAODAI)
//                .setContent5("有身份证就能贷,当天放款,大额小额均可申请").setUrl(Constant.URL_DAIKUAN_HAODAI).setMagrinTop(false);
//        mData.add(item);
//
//        item = new LoanerItem();
//        item.setName("信而富-贷款快").setResIconId(R.drawable.logo_xinerfu)
//                .setContent2("100-5000").setTag("超低利率，绑卡就下款")
//                .setContent4("0.06%").setUmengEventID(Umeng.EVENTID_DK_XINERFU)
//                .setContent5("60秒自动审批下款,绑卡就能过").setUrl(Constant.URL_XINERFU).setMagrinTop(false);
//        mData.add(item);
//
//        item = new LoanerItem();
//        item.setName("2345贷款王").setResIconId(R.drawable.logo_2345_old).setTag("全民免息活动中")
//                .setContent2("500-5000")
//                .setContent4("0.1%").setUmengEventID(Umeng.EVENTID_DK_2345)
//                .setContent5("门槛低,有身份证就能借,A股上市公司").setUrl(Constant.URL_2345_DAIKUANWANG).setMagrinTop(true);
//        mData.add(item);
//
//        item = new LoanerItem();
//        item.setName("融360-极速贷").setResIconId(R.drawable.logo_rong360)
//                .setContent2("1000-500000")
//                .setContent4("0.2%").setUmengEventID(Umeng.EVENTID_DK_360)
//                .setContent5("有身份证就能贷,门槛低服务好,当天放款").setUrl(Constant.URL_DAIKUAN_RONG360).setMagrinTop(false);
//        mData.add(item);
//
//        item = new LoanerItem();
//        item.setName("现金白卡").setResIconId(R.drawable.logo_xianjinbaika)
//                .setContent2("1000-3000")
//                .setContent4("0.3%").setUmengEventID(Umeng.EVENTID_DK_BAIKA)
//                .setContent5("额度小,很容易申请成功").setUrl(Constant.URL_XIANJINBAIKA).setMagrinTop(true);
//        mData.add(item);
//
//        item = new LoanerItem();
//        item.setName("闪银信用贷").setResIconId(R.drawable.logo_shanyin)
//                .setContent2("100-200000").setContent3("参考日息:")
//                .setContent4("0.4%").setUmengEventID(Umeng.EVENTID_DK_SHANYIN)
//                .setContent5("3分钟出额度,10万以下可申请").setUrl(Constant.URL_SHANYIN).setMagrinTop(true);
//        mData.add(item);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LoanItem item = mData.get(position);
        RecommendLoanerListAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.loaner_list_item_recommend, parent, false);
            holder = new RecommendLoanerListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (RecommendLoanerListAdapter.ViewHolder) convertView.getTag();
        }

        holder.icon.setDefaultImageResId(R.mipmap.icon);
        holder.icon.setImageUrl(item.getIconUrl(),loader);
        holder.title.setText(item.getName());
        if(item.hasTag()){
            holder.tag.setText(item.getTag());
            holder.tag.setVisibility(View.VISIBLE);
        }else{
            holder.tag.setVisibility(View.GONE);
        }
        holder.desc.setText(item.getDesc());
        holder.personNum.setText(item.getContent4()+"人");
        holder.moneyNum.setText(item.getContent1());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Umeng.onEvent(mContext, Umeng.EVENTID_DAIKUAN_TUIJIAN,Umeng.EVENTID_DAIKUAN_TUIJIAN,item.getName());
                GlobalUtil.openWebview(mContext, "http://click.xuezhionline.com/union/KeRmjH3XSr6r7sQaHDmf1w%3D%3D", item.getName(),item.getMagrinTop());
            }
        });
        return convertView;
    }

    class ViewHolder{
        public NetworkImageView icon;
        public TextView title, desc,tag,personNum,moneyNum;

        public ViewHolder(View root){
            icon =(NetworkImageView) root.findViewById(R.id.iv_icon);
            title = (TextView) root.findViewById(R.id.tv_title);
            desc = (TextView) root.findViewById(R.id.tv_desc);
            tag = (TextView) root.findViewById(R.id.tv_tag);
            personNum = (TextView) root.findViewById(R.id.tv_content4);
            moneyNum = (TextView) root.findViewById(R.id.tv_content2);
        }
    }
}

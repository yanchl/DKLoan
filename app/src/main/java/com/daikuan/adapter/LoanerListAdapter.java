package com.daikuan.adapter;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daikuan.Constant;
import com.daikuan.GlobalUtil;
import com.daikuan.R;
import com.daikuan.model.LoanerItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  WQ
 * Version: v0.0.1
 * Date:    2017/3/5
 * Modification History:
 * Why & What modified:
 */

public class LoanerListAdapter extends BaseAdapter{

    List<LoanerItem> mData;
    Context mContext;
    LayoutInflater mInflater;

    public LoanerListAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initData();
    }

    private void initData(){
        mData = new ArrayList<>();
        LoanerItem item = new LoanerItem();
        item.setName("好贷网贷款").setTag("推荐").setResIconId(R.drawable.logo_haodai)
                .setContent1("content1").setContent2("content2")
                .setContent3("content 3").setContent4("content 4")
                .setContent5("额度很高呀").setUrl(Constant.URL_DAIKUAN_HAODAI);
        mData.add(item);
        item = new LoanerItem();
        item.setName("融360贷款").setTag("秒贷").setResIconId(R.drawable.logo_rong360)
                .setContent1("content1").setContent2("content2")
                .setContent3("content 3").setContent4("content 4")
                .setContent5("额度很高呀").setUrl(Constant.URL_DAIKUAN_RONG360);
        mData.add(item);
        item = new LoanerItem();
        item.setName("拍拍贷").setResIconId(R.drawable.logo_paipai)
                .setContent1("content1").setContent2("content2")
                .setContent3("content 3").setContent4("content 4")
                .setContent5("额度很高呀").setUrl(Constant.URL_DAIKUAN_PAIPAI);
        mData.add(item);
        item = new LoanerItem();
        item.setName("平安惠普").setResIconId(R.drawable.logo_pingan)
                .setContent1("content1").setContent2("content2")
                .setContent3("content 3").setContent4("content 4")
                .setContent5("额度很高呀").setUrl(Constant.DAIKUAN_PINGANHUIPU);
        mData.add(item);
        item = new LoanerItem();
        item.setName("闪银信用贷").setResIconId(R.drawable.logo_xinyongka)
                .setContent1("content1").setContent2("content2")
                .setContent3("content 3").setContent4("content 4")
                .setContent5("额度很高呀").setUrl(Constant.URL_DAIKUAN_HAODAI);
        mData.add(item);
        item = new LoanerItem();
        item.setName("好贷网贷款").setResIconId(R.drawable.logo_haodai)
                .setContent1("content1").setContent2("content2")
                .setContent3("content 3").setContent4("content 4")
                .setContent5("额度很高呀").setUrl(Constant.URL_DAIKUAN_HAODAI);
        mData.add(item);
        item = new LoanerItem();
        item.setName("宜人贷").setResIconId(R.drawable.logo_yirendai)
                .setContent1("content1").setContent2("content2")
                .setContent3("content 3").setContent4("content 4")
                .setContent5("额度很高呀").setUrl(Constant.URL_XINYONGKA_HAODAI_HAODAI);
        mData.add(item);
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
        final LoanerItem item = mData.get(position);
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.loaner_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setImageResource(item.getResIconId());
        holder.name.setText(item.getName());
        if(item.hasTag()){
            holder.tag.setText(item.getTag());
            holder.tag.setVisibility(View.VISIBLE);
        }else{
            holder.tag.setVisibility(View.GONE);
        }
        holder.content1.setText(item.getContent1());
        holder.content2.setText(item.getContent2());
        holder.content3.setText(item.getContent3());
        holder.content4.setText(item.getContent4());
        holder.content5.setText(item.getContent5());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalUtil.openWebview(mContext, item.getUrl(), item.getName());
            }
        });
        return convertView;
    }

    class ViewHolder{
        public ImageView icon;
        public TextView name, tag, content1, content2, content3,content4,content5;

        public ViewHolder(View root){
            icon =(ImageView) root.findViewById(R.id.iv_lli_icon);
            name = (TextView) root.findViewById(R.id.tv_lli_name);
            tag = (TextView) root.findViewById(R.id.tv_lli_tag);
            content1 = (TextView) root.findViewById(R.id.tv_lli_content_1);
            content2 = (TextView) root.findViewById(R.id.tv_lli_content_2);
            content3 = (TextView) root.findViewById(R.id.tv_lli_content_3);
            content4 = (TextView) root.findViewById(R.id.tv_lli_content_4);
            content5 = (TextView) root.findViewById(R.id.tv_lli_content_5);
        }
    }
}

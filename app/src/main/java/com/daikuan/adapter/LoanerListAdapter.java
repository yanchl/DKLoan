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
import com.daikuan.util.Umeng;

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
        item.setName("好贷网-极速贷").setTag("审批快,下款快").setResIconId(R.drawable.logo_yirendai)
                .setContent2("0.1-3000万")
                .setContent4(" -- ").setUmengEventID(Umeng.EVENTID_DK_HAODAI)
                .setContent5("1千-3000万,门槛低有身份证就能贷,当天放款,大额小额均可申请,准上市公司").setUrl(Constant.URL_DAIKUAN_HAODAI);
        mData.add(item);

        item = new LoanerItem();
        item.setName("信而富-贷款快").setResIconId(R.drawable.logo_yirendai)
                .setContent2("0.01-0.1万").setTag("绑卡即可下款")
                .setContent4("0.06%").setUmengEventID(Umeng.EVENTID_DK_XINERFU)
                .setContent5("500-6千,日息0.06%,60秒自动审批下款,绑卡就能过,准上市公司").setUrl(Constant.URL_XINERFU);
        mData.add(item);

        item = new LoanerItem();
        item.setName("2345贷款王").setResIconId(R.drawable.logo_yirendai).setTag("全民免息活动中")
                .setContent2("0.05-0.5千")
                .setContent4("0.1%").setUmengEventID(Umeng.EVENTID_DK_2345)
                .setContent5("500-5千,门槛低,有身份证就能借,日息0.1%,A股上市公司").setUrl(Constant.URL_2345_DAIKUANWANG);
        mData.add(item);

        item = new LoanerItem();
        item.setName("融360-极速贷").setResIconId(R.drawable.logo_yirendai).setTag("通过率高")
                .setContent2("0.1-50万")
                .setContent4(" -- ").setUmengEventID(Umeng.EVENTID_DK_360)
                .setContent5("1千-50万,有身份证就能贷,门槛低服务好,当天放款,准上市公司").setUrl(Constant.URL_DAIKUAN_RONG360);
        mData.add(item);

        item = new LoanerItem();
        item.setName("现金白卡").setResIconId(R.drawable.logo_yirendai).setTag("拒就赔")
                .setContent2("0.1-0.3万")
                .setContent4("0.30%").setUmengEventID(Umeng.EVENTID_DK_BAIKA)
                .setContent5("1000-5千,额度小,很容易申请成功,每天限1万名额参加拒就赔活动").setUrl(Constant.URL_XIANJINBAIKA);
        mData.add(item);

        item = new LoanerItem();
        item.setName("闪银信用贷").setResIconId(R.drawable.logo_yirendai)
                .setContent2("0.01-20.0万").setContent3("参考月利率 :")
                .setContent4("1.20%").setUmengEventID(Umeng.EVENTID_DK_SHANYIN)
                .setContent5("200-10万,官方说3分钟出额度,10万以下可尝试").setUrl(Constant.URL_SHANYIN);
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
//        holder.content1.setText(item.getContent1());
        holder.content2.setText(item.getContent2());
        holder.content3.setText(item.getContent3());
        holder.content4.setText(item.getContent4());
        holder.content5.setText(item.getContent5());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Umeng.onEvent(mContext, item.getUmengEventID());
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

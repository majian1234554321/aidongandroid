package com.example.aidong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.entity.model.IntegralDetailInfo;

import java.util.ArrayList;

public class LoveCoinAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<IntegralDetailInfo> integralInfos = new ArrayList<>();
    public LoveCoinAdapter(Context context){
        mContext = context;
    }
    @Override
    public int getCount() {
        return integralInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return integralInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_love_coin,null);
            holder = new ViewHolder();
            holder.txt_income_type = (TextView) convertView.findViewById(R.id.txt_income_type);
            holder.txt_date = (TextView) convertView.findViewById(R.id.txt_date);
            holder.txt_income_money = (TextView) convertView.findViewById(R.id.txt_income_money);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        IntegralDetailInfo integralInfo = integralInfos.get(position);
        if(TextUtils.equals("订单提成",integralInfo.getAction())){
            holder.txt_income_type.setText("订单提成 ("+integralInfo.getRemark()+")");
        }else{
            holder.txt_income_type.setText(""+integralInfo.getAction());
        }

        if(integralInfo.getAmount() < 0){
            holder.txt_income_money.setTextColor(Color.parseColor("#00a0df"));
            holder.txt_income_money.setText("- "+ Math.abs(integralInfo.getAmount())+".00");
        }else{
            holder.txt_income_money.setTextColor(Color.parseColor("#ff7674"));
            holder.txt_income_money.setText("+ "+integralInfo.getAmount()+".00");
        }

        holder.txt_date.setText(integralInfo.getCreateTime());

        return convertView;
    }

    public void freshData(ArrayList<IntegralDetailInfo> integralInfos) {
        this.integralInfos.clear();
        this.integralInfos.addAll(integralInfos);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<IntegralDetailInfo> integralInfos) {
        this.integralInfos.addAll(integralInfos);
        notifyDataSetChanged();
    }

    static class ViewHolder{
        TextView txt_income_type;
        TextView txt_date;
        TextView txt_income_money;
    }
}

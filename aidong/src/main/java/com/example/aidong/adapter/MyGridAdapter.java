package com.example.aidong.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.entity.MarketPartsBean;
import com.example.aidong.utils.GlideLoader;

import java.util.List;

public class MyGridAdapter extends BaseAdapter {


    public Context context;
    public List<MarketPartsBean> marketPartsBean;
    private ImageView iv;
    private TextView tv;

    public MyGridAdapter(Context context, List<MarketPartsBean> marketPartsBean) {
        this.context = context;
        this.marketPartsBean = marketPartsBean;
    }

    @Override
    public int getCount() {
        return marketPartsBean.size();
    }

    @Override
    public Object getItem(int i) {
        return marketPartsBean.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.mygridadapter, null);
            iv = view.findViewById(R.id.iv);
            tv = view.findViewById(R.id.tv);
        }


        GlideLoader.getInstance().displayImage2(marketPartsBean.get(i).cover,iv);
        tv.setText(marketPartsBean.get(i).name);

        return view;
    }
}
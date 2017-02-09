package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱币收支明细适配器
 * Created by song on 2016/10/24.
 */
public class LoveCoinAdapter extends RecyclerView.Adapter<LoveCoinAdapter.CoinHolder>{
    private Context context;
    private List<String> data = new ArrayList<>();

    public LoveCoinAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CoinHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_payment_detail,null);
        return new CoinHolder(view);
    }

    @Override
    public void onBindViewHolder(CoinHolder holder, int position) {

    }

    class CoinHolder extends RecyclerView.ViewHolder{
        private TextView type;
        private TextView time;
        private TextView money;

        public CoinHolder(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.tv_type);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            money = (TextView) itemView.findViewById(R.id.tv_money);
        }
    }
}

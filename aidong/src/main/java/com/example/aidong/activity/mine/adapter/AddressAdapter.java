package com.example.aidong.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.support.entity.data.AddressBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址适配器
 * Created by song on 2016/9/20.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder>{
    private Context context;
    private List<AddressBean> data = new ArrayList<>();

    public AddressAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AddressBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.item_address,null);
        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, int position) {
        AddressBean bean = data.get(position);
    }

    class AddressHolder extends RecyclerView.ViewHolder {
        RadioButton rbDefault;
        TextView edit;
        ImageView delete;
        TextView name;
        TextView phone;
        TextView address;

        public AddressHolder(View itemView) {
            super(itemView);
            rbDefault = (RadioButton) itemView.findViewById(R.id.rb_default);
            edit = (TextView) itemView.findViewById(R.id.tv_edit);
            delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            phone = (TextView) itemView.findViewById(R.id.tv_phone);
            address = (TextView) itemView.findViewById(R.id.tv_address);
        }
    }
}

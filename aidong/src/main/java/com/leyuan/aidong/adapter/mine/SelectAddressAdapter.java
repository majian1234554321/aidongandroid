package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AddressBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择地址适配器
 * Created by song on 2016/9/20.
 */
public class SelectAddressAdapter extends RecyclerView.Adapter<SelectAddressAdapter.AddressHolder> {
    private Context context;
    private List<AddressBean> data = new ArrayList<>();
    private String addressId;

    private OnItemClickListener itemClickListener;

    public SelectAddressAdapter(Context context) {
        this.context = context;
    }

    public void setItemClickListener(OnItemClickListener l) {
        this.itemClickListener = l;
    }

    public void setData(List<AddressBean> data,String addressId) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
        this.addressId = addressId;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_address,parent,false);
        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(final AddressHolder holder, final int position) {
        AddressBean bean = data.get(position);
        holder.name.setText(bean.getName());
        holder.phone.setText(bean.getMobile());
        holder.address.setText(new StringBuilder(bean.getCity().contains(bean.getProvince()) ? "" : bean.getProvince())
                .append(bean.getCity()).append(bean.getDistrict()).append(bean.getAddress()));
        holder.defaultAddress.setVisibility(bean.isDefault() ? View.VISIBLE :View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    holder.checkBox.setChecked(true);
                    itemClickListener.onItemClick(position);
                }
            }
        });

        holder.checkBox.setChecked(bean.getId().equals(addressId));
    }


    class AddressHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView defaultAddress;
        TextView name;
        TextView phone;
        TextView address;

        public AddressHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
            defaultAddress = (TextView) itemView.findViewById(R.id.tv_default_address);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            phone = (TextView) itemView.findViewById(R.id.tv_phone);
            address = (TextView) itemView.findViewById(R.id.tv_address);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

package com.example.aidong.adapter.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.AddressBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址适配器
 * Created by song on 2016/9/20.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {
    private Context context;
    private List<AddressBean> data = new ArrayList<>();

    private EditAddressListener editAddressListener;

    public AddressAdapter(Context context) {
        this.context = context;
    }

    public void setEditAddressListener(EditAddressListener l) {
        this.editAddressListener = l;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_address,parent,false);
        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(final AddressHolder holder, final int position) {
        final AddressBean bean = data.get(position);
        holder.name.setText(bean.getName());
        holder.phone.setText(bean.getMobile());
        holder.address.setText(new StringBuilder(bean.getCity().contains(bean.getProvince()) ? "" : bean.getProvince())
                .append(bean.getCity()).append(bean.getDistrict()).append(bean.getAddress()));
        if(bean.isDefault()){
            holder.rbDefault.setChecked(true);
            holder.tvDefault.setText("默认地址");
            holder.tvDefault.setTextColor(context.getResources().getColor(R.color.main_red));
        }else{
            holder.rbDefault.setChecked(false);
            holder.tvDefault.setText("设为默认");
            holder.tvDefault.setTextColor(context.getResources().getColor(R.color.c3));

            holder.rbDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editAddressListener != null){
                        editAddressListener.onChangeDefaultAddress(bean.getId(),position);
                    }
                }
            });
        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editAddressListener != null){
                    editAddressListener.onUpdateAddress(position);
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editAddressListener != null) {
                    showDeleteDialog(position);
                }
            }
        });
    }

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.delete_confirm))
                .setCancelable(true)
                .setPositiveButton(context.getString(R.string.sure), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(editAddressListener != null){
                            editAddressListener.onDeleteAddress(position);
                        }
                    }
                })
                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    class AddressHolder extends RecyclerView.ViewHolder {
        LinearLayout itemLayout;
        RadioButton rbDefault;
        TextView tvDefault;
        TextView edit;
        ImageView delete;
        TextView name;
        TextView phone;
        TextView address;

        public AddressHolder(View itemView) {
            super(itemView);
            itemLayout = (LinearLayout)itemView.findViewById(R.id.item_view);
            rbDefault = (RadioButton) itemView.findViewById(R.id.rb_default);
            tvDefault = (TextView) itemView.findViewById(R.id.tv_default);
            edit = (TextView) itemView.findViewById(R.id.tv_edit);
            delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            phone = (TextView) itemView.findViewById(R.id.tv_phone);
            address = (TextView) itemView.findViewById(R.id.tv_address);
        }
    }

    public interface EditAddressListener{
        void onDeleteAddress(int position);
        void onUpdateAddress(int position);
        void onChangeDefaultAddress(String id,int position);
    }
}

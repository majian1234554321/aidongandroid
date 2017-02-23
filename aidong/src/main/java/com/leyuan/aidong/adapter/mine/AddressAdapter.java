package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AddressBean;

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
        View view = View.inflate(context,R.layout.item_address,null);
        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, final int position) {
        AddressBean bean = data.get(position);
        holder.name.setText(bean.getName());
        holder.phone.setText(bean.getMobile());
        holder.address.setText(new StringBuilder(bean.getProvince()).append(bean.getCity())
                .append(bean.getDistrict()).append(bean.getAddress()));

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
                showDeleteDialog(position);
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
        TextView edit;
        ImageView delete;
        TextView name;
        TextView phone;
        TextView address;

        public AddressHolder(View itemView) {
            super(itemView);
            itemLayout = (LinearLayout)itemView.findViewById(R.id.item_view);
            rbDefault = (RadioButton) itemView.findViewById(R.id.rb_default);
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
    }
}

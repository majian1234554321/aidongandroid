package com.leyuan.aidong.ui.activity.mine.adapter;

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
import com.leyuan.aidong.ui.activity.mine.AddAddressActivity;
import com.leyuan.aidong.ui.activity.mine.AddressActivity;
import com.leyuan.aidong.entity.AddressBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址适配器
 * Created by song on 2016/9/20.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> implements View.OnClickListener{
    private Context context;
    private int position;
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
        this.position = holder.getAdapterPosition();

        AddressBean bean = data.get(position);
        holder.name.setText(bean.getName());
        holder.phone.setText(bean.getName());
        holder.address.setText(bean.getAddress());

        holder.edit.setOnClickListener(this);
        holder.itemLayout.setOnClickListener(this);
        holder.delete.setOnClickListener(this);
        holder.rbDefault.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_view:
            case R.id.tv_edit:
                AddAddressActivity.actionStart(context,data.get(position));
                break;
            case R.id.iv_delete:
                showDeleteDialog();
                break;
            case R.id.rb_default:

                break;
            default:
                break;
        }
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.delete_confirm))
                .setCancelable(true)
                .setPositiveButton(context.getString(R.string.sure), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((AddressActivity)context).deleteAddress(position);
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
}

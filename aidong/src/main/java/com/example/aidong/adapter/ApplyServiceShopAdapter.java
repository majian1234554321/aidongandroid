package com.example.aidong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.entity.GoodsBean;
import com.example.aidong.utils.GlideLoader;
import com.example.aidong.utils.ToastGlobal;

import java.util.ArrayList;

/**
 * Created by user on 2017/3/24.
 */
public class ApplyServiceShopAdapter extends RecyclerView.Adapter<ApplyServiceShopAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<GoodsBean> array;
    private int count;

    public ApplyServiceShopAdapter(Context context, ArrayList<GoodsBean> array) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.array = array;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_apply_service_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final GoodsBean bean = array.get(position);
        final int amount = bean.getCan_return();
        GlideLoader.getInstance().displayImage(bean.getCover(), holder.dvCover);
        holder.tvName.setText(bean.getName());
        holder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivAdd.setBackgroundResource(R.drawable.icon_add);

                count = Integer.parseInt(holder.tvCount.getText().toString());
                count--;
                if (count <= 0) {
                    count = 0;
                    holder.ivMinus.setBackgroundResource(R.drawable.icon_minus_gray);
                    bean.setItem(null);
                } else {
                    bean.setItem(bean.getId() + "-" + count);
                }
                holder.tvCount.setText(String.valueOf(count));

            }
        });
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(holder.tvCount.getText().toString());
                count++;
                if (count > amount) {

                    count = amount;

                    ToastGlobal.showShort("超出最大数量");
                }
                if (count == amount) {
                    holder.ivAdd.setBackgroundResource(R.drawable.icon_add_gray);
                }
                if (count >= 1) {
                    holder.ivMinus.setBackgroundResource(R.drawable.icon_minus);
                }
                holder.tvCount.setText(String.valueOf(count));
                bean.setItem(bean.getId() + "-" + count);
            }
        });

        if (bean.is_virtual()) {
            holder.ivMinus.setVisibility(View.GONE);
            holder.ivAdd.setVisibility(View.GONE);
            holder.tvCount.setText("" + amount);
            bean.setItem(bean.getId() + "-" + amount);
        }

    }

    @Override
    public int getItemCount() {
        if (array == null)
            return 0;
        return array.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView dvCover;
        private TextView tvName;
        private ImageView ivMinus;
        private TextView tvCount;
        private ImageView ivAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            dvCover = itemView.findViewById(R.id.dv_cover);
            tvName = itemView.findViewById(R.id.tv_name);
            ivMinus = itemView.findViewById(R.id.iv_minus);
            tvCount = itemView.findViewById(R.id.tv_count);
            ivAdd = itemView.findViewById(R.id.iv_add);
        }
    }
}

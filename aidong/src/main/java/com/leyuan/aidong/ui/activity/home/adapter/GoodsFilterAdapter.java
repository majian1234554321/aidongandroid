package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.entity.NurtureBean;
import com.leyuan.aidong.ui.activity.home.GoodsDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品筛选界面适配器
 * Created by song on 2016/8/17.
 */
public class GoodsFilterAdapter extends RecyclerView.Adapter<GoodsFilterAdapter.FilterViewHolder>{
    private Context context;
    private List<NurtureBean> data = new ArrayList<>();

    public GoodsFilterAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NurtureBean> data) {
        if(data != null){
            this.data = data;
        }else{
            for (int i = 0; i < 15; i++) {

                if( i % 2 == 0){
                    NurtureBean bean = new NurtureBean();
                    bean.setCover("http://ww3.sinaimg.cn/mw690/a20a9b80jw1f9bexb25ojj20dh0m3tcp.jpg");
                    bean.setMarket_price("ABC");
                    bean.setPrice("0.1");
                    bean.setName("百度小易带你看娱乐史上首次全程直播明星与黑粉“约架”！黑粉见面会和你相约网易新闻客户端11月2日晚18:30，不管");
                    this.data.add(bean);
                }else{
                    NurtureBean bean = new NurtureBean();
                    bean.setCover("http://ww2.sinaimg.cn/mw690/61ecbb3dgw1f9cavlzy69j218g0yr11q.jpg");
                    bean.setMarket_price("GNC");
                    bean.setPrice("0.1111");
                    bean.setName("从歌手到演员，从青涩的“国民校草”到成熟的“国民男神”，@李易峰 出道九年，一路蜕变，在他塑造的N种角色中，总有");
                    this.data.add(bean);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.item_nurture_filter,null);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        NurtureBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.name.setText(bean.getName());
        holder.brand.setText(bean.getMarket_price());
        holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailActivity.start(context,"1");
            }
        });
    }

    class FilterViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cover;
        TextView name;
        TextView brand;
        TextView price;

        public FilterViewHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_nurture_cover);
            name = (TextView)itemView.findViewById(R.id.tv_nurture_name);
            brand = (TextView)itemView.findViewById(R.id.tv_nurture_brand);
            price = (TextView)itemView.findViewById(R.id.tv_nurture_price);
        }
    }
}

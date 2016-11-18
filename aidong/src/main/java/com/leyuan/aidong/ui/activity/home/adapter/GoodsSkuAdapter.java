package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsSkuBean;
import com.leyuan.aidong.entity.GoodsSkuValueBean;
import com.leyuan.aidong.entity.LocalGoodsSkuBean;
import com.leyuan.aidong.ui.activity.home.view.GoodsSkuPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情页规格适配器
 * Created by song on 2016/11/9.
 */
public class GoodsSkuAdapter extends RecyclerView.Adapter<GoodsSkuAdapter.SkuHolder>{
    private Context context;
    private GoodsSkuPopupWindow popupWindow;
    private List<LocalGoodsSkuBean> localSkuList;
    private List<GoodsSkuBean> skuList;
    private int lastSelectedSkuPosition = -1;   //最后选中的sku值

    public GoodsSkuAdapter(GoodsSkuPopupWindow popupWindow, List<LocalGoodsSkuBean> localSkuList, List<GoodsSkuBean> skuList) {
        this.context = popupWindow.context;
        this.popupWindow = popupWindow;
        this.localSkuList = localSkuList;
        this.skuList = skuList;
    }

    @Override
    public int getItemCount() {
        return localSkuList.size();
    }

    @Override
    public SkuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sku_list,parent,false);
        return new SkuHolder(view);
    }

    @Override
    public void onBindViewHolder(SkuHolder holder, final int position) {
        LocalGoodsSkuBean bean = localSkuList.get(position);
        holder.skuName.setText(bean.getSkuName());
        GridLayoutManager manager = new GridLayoutManager(context,3);
        holder.skuValues.setLayoutManager(manager);
        GoodsSkuValuesAdapter skuValueAdapter = new GoodsSkuValuesAdapter(context,bean.getSkuValues());
        holder.skuValues.setAdapter(skuValueAdapter);
        skuValueAdapter.setItemClickListener(new GoodsSkuValuesAdapter.OnItemClickListener() {
            @Override
            public void onSelectItem(int itemPosition) {
                localSkuList.get(position).setSelected(true);
                for (LocalGoodsSkuBean localGoodsSkuBean : localSkuList) {
                    localGoodsSkuBean.setLastSelected(false);
                }
                lastSelectedSkuPosition = position;
                localSkuList.get(position).setLastSelected(true);

                List<GoodsSkuValueBean> skuValues = localSkuList.get(position).getSkuValues();
                for (int i = 0; i < skuValues.size(); i++) {
                    skuValues.get(i).setSelected(i == itemPosition);
                }
            }

            @Override
            public void onCancelSelectItem(int itemPosition) {
                localSkuList.get(position).setSelected(false);
                localSkuList.get(position).setLastSelected(false);

                List<GoodsSkuValueBean> skuValues = localSkuList.get(position).getSkuValues();
                for (int i = 0; i < skuValues.size(); i++) {
                    if(i == itemPosition){
                        skuValues.get(i).setSelected(false);
                    }
                }
            }

            @Override
            public void onItemClick() {
                setUnSelectedStatus();
                setSelectedNodeStatus();
                notifyDataSetChanged();
            }
        });

    }

    //获取当前选中的Sku值如['黄'] ['红'，'大']
    private List<String> getSelectedNodes(){
        List<String> selectedNodes = new ArrayList<>();
        for (LocalGoodsSkuBean localGoodsSkuBean : localSkuList) {
            for (GoodsSkuValueBean valueBean : localGoodsSkuBean.getSkuValues()) {
                if(valueBean.isSelected()){
                    selectedNodes.add(valueBean.getValue());
                }
            }
        }
        return selectedNodes;
    }

    //获取最后一个选中的Sku值如当前选中['红'，'大'] 最后选中的是红 返回[红]
    private List<String> getLastSelectedNodes(){
        List<String> selectedNodes = new ArrayList<>();
        for (LocalGoodsSkuBean localGoodsSkuBean : localSkuList) {
            List<GoodsSkuValueBean> skuValues = localGoodsSkuBean.getSkuValues();
            for (int i = 0; i < skuValues.size(); i++) {
                if( i == lastSelectedSkuPosition){
                    selectedNodes.add(skuValues.get(i).getValue());
                    break;
                }
            }
        }
        return selectedNodes;
    }

    //获取包含指定属性节点的所有路线
    private List<GoodsSkuBean> getLines(List<String> selectedValues){
        ArrayList<GoodsSkuBean> usefulGoodsSkuBean = new ArrayList<>();
        for (GoodsSkuBean goodsSkuBean : skuList) {
            for (String selectedValue : selectedValues) {
                if(goodsSkuBean.value.contains(selectedValue)){
                    usefulGoodsSkuBean.add(goodsSkuBean);
                }
            }
        }
        return usefulGoodsSkuBean;
    }

    //获取包含选中的属性节点路线上的所有节点
    public List<String> getLineNodes(List<String> selectedSkuValues){
        List<GoodsSkuBean> lines = getLines(selectedSkuValues);
        List<String> allLineNodes = new ArrayList<>();
        for (GoodsSkuBean line : lines) {
            for (String s : line.value) {
                allLineNodes.add(s);
            }
        }
        return allLineNodes;
    }

    class SkuHolder extends RecyclerView.ViewHolder{
        TextView skuName;
        RecyclerView skuValues;

        public SkuHolder(View itemView) {
            super(itemView);
            skuName = (TextView)itemView.findViewById(R.id.tv_sku_name);
            skuValues = (RecyclerView) itemView.findViewById(R.id.rv_sku_value);
        }
    }

    //设置未选择属性中的节点状态
    private void setUnSelectedStatus(){
        List<LocalGoodsSkuBean> localUnselectedSkuList = popupWindow.getUnSelectedSku();
        List<String> selectedSkuValues = getSelectedNodes();
        List<String> lineNodes = getLineNodes(selectedSkuValues);
        for (LocalGoodsSkuBean localGoodsSkuBean : localUnselectedSkuList) {
            for (GoodsSkuValueBean valueBean : localGoodsSkuBean.getSkuValues()) {
                if(lineNodes.isEmpty()){
                    valueBean.setAvailable(true);
                }else{
                    if(lineNodes.contains(valueBean.getValue())){
                        valueBean.setAvailable(true);
                    }else {
                        valueBean.setAvailable(false);
                    }
                }
            }
        }
    }

    //设置已选属性（除了最后一个确定的）中的相邻节点状态
    private void setSelectedNodeStatus(){
        List<LocalGoodsSkuBean> localExceptLastSelectedSkuList = popupWindow.getExceptLastSelectedSku();
        if(localExceptLastSelectedSkuList == null || localExceptLastSelectedSkuList.isEmpty()){
            return;
        }

        List<String> selectedNodes = getLastSelectedNodes();
        List<String> lineNodes = getLineNodes(selectedNodes);
        for (LocalGoodsSkuBean localGoodsSkuBean : localExceptLastSelectedSkuList) {
            for (GoodsSkuValueBean valueBean : localGoodsSkuBean.getSkuValues()) {
                if(lineNodes.isEmpty()){
                    valueBean.setAvailable(true);
                }else{
                    if(lineNodes.contains(valueBean.getValue())){
                        valueBean.setAvailable(true);
                    }else {
                        valueBean.setAvailable(false);
                    }
                }
            }
        }
    }
}

package com.leyuan.aidong.ui.home.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsSkuBean;
import com.leyuan.aidong.entity.GoodsSkuValueBean;
import com.leyuan.aidong.entity.LocalGoodsSkuBean;
import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情页规格适配器
 * Created by song on 2016/11/9.
 */
public class GoodsSkuAdapter extends RecyclerView.Adapter<GoodsSkuAdapter.SkuHolder>{
    private Context context;
    private List<LocalGoodsSkuBean> localSkuList;
    private List<GoodsSkuBean> skuList;
    private SelectSkuListener selectSkuListener;
    private List<String> selectedSkuValues = new ArrayList<>();   //已经选中的sku值

    public GoodsSkuAdapter(Context context, List<LocalGoodsSkuBean> localSkuList, List<GoodsSkuBean> skuList,List<String> selectedSkuVaules) {
        this.context = context;
        this.localSkuList = localSkuList;
        this.skuList = skuList;
        this.selectedSkuValues = selectedSkuVaules;
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
        GoodsSkuValuesAdapter skuValueAdapter = new GoodsSkuValuesAdapter(context,bean.getSkuValues());
        holder.skuValues.setAdapter(skuValueAdapter);
        FlowLayoutManager manager = new FlowLayoutManager();
        manager.setAutoMeasureEnabled(true);
        manager.setAlignment(Alignment.LEFT);
        holder.skuValues.setLayoutManager(manager);
        holder.skuValues.setNestedScrollingEnabled(false);

        //初始化sku值按钮状态
        if(!selectedSkuValues.isEmpty()){
            setUnSelectedStatus();
            setSelectedNodeStatus();
        }

        skuValueAdapter.setItemClickListener(new GoodsSkuValuesAdapter.OnItemClickListener() {
            @Override
            public void onSelectItem(int itemPosition) {
                localSkuList.get(position).setSelected(true);
                List<GoodsSkuValueBean> skuValues = localSkuList.get(position).getSkuValues();
                for (int i = 0; i < skuValues.size(); i++) {
                    skuValues.get(i).setSelected(i == itemPosition);
                }
            }

            @Override
            public void onCancelSelectItem(int itemPosition) {
                localSkuList.get(position).setSelected(false);
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

                if(selectSkuListener != null){
                    selectSkuListener.onSelectSkuChanged(getAllSelectedNodes());
                }
            }
        });
    }

    //设置未选择属性中的节点状态
    private void setUnSelectedStatus(){
        List<LocalGoodsSkuBean> localUnselectedSkuList = new ArrayList<>();
        if(selectSkuListener != null){
             localUnselectedSkuList = selectSkuListener.onGetUnSelectSku();
        }

        if(localUnselectedSkuList.isEmpty()){
            return;
        }

        List<String> selectedSkuValues = getAllSelectedNodes();
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

    //设置已选属性行中的相邻节点状态
    private void setSelectedNodeStatus(){
        List<LocalGoodsSkuBean> localSelectedSkuList = new ArrayList<>();
        if(selectSkuListener != null){
            localSelectedSkuList = selectSkuListener.onGetSelectSku();
        }
        if(localSelectedSkuList.isEmpty()){
            return;
        }

        for (LocalGoodsSkuBean localGoodsSkuBean : localSelectedSkuList) {
            List<String> otherLineSelectedNodes = getExceptCurrLineSelectedNodes(localGoodsSkuBean);
            List<String> lineNodes = getLineNodes(otherLineSelectedNodes);
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

    //获取当前选中的Sku值如['黄'] ['红'，'大']
    private List<String> getAllSelectedNodes(){
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

    //获取当前行选中的Sku值
    private String getCurrentLineSelectedNode(LocalGoodsSkuBean localGoodsSkuBean){
        String currLineSelectedNode = "";
        for (GoodsSkuValueBean valueBean : localGoodsSkuBean.getSkuValues()) {
            if(valueBean.isSelected()){
                currLineSelectedNode = valueBean.getValue();
                break;
            }
        }
        return currLineSelectedNode;
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

    //获取包含指定属性节点的所有路线
    private List<GoodsSkuBean> getLines(List<String> selectedValues){
        ArrayList<GoodsSkuBean> usefulGoodsSkuBean = new ArrayList<>();
        for (GoodsSkuBean goodsSkuBean : skuList) {
            if(goodsSkuBean.value.containsAll(selectedValues)){
                usefulGoodsSkuBean.add(goodsSkuBean);
            }
        }
        return usefulGoodsSkuBean;
    }

    //获取除去当前行选中的Sku值，剩余Sku值的集合 如当前选中['红'，'大'] 当前行选中的是红 返回[大]
    private List<String> getExceptCurrLineSelectedNodes(LocalGoodsSkuBean localGoodsSkuBean){
        String currLineSelectedNode = getCurrentLineSelectedNode(localGoodsSkuBean);
        List<String> allSelectedNodes = getAllSelectedNodes();
        List<String> otherLineSelectedNodes = new ArrayList<>();
        for (String allSelectedNode : allSelectedNodes) {
            if(!allSelectedNode.equals(currLineSelectedNode)){
                otherLineSelectedNodes.add(allSelectedNode);
            }
        }
        return otherLineSelectedNodes;
    }

    class SkuHolder extends RecyclerView.ViewHolder{
        TextView skuName;
        RecyclerView skuValues;

        public SkuHolder(View itemView) {
            super(itemView);
            skuName = (TextView)itemView.findViewById(R.id.tv_sku_name);
            skuValues = (RecyclerView) itemView.findViewById(R.id.rv_sku_value);
            skuValues.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(0,0,40,20);
                }
            });
        }
    }

    public void setSelectSkuListener(SelectSkuListener listener) {
        this.selectSkuListener = listener;
    }

    public interface SelectSkuListener {
        List<LocalGoodsSkuBean> onGetSelectSku();
        List<LocalGoodsSkuBean> onGetUnSelectSku();
        void onSelectSkuChanged(List<String> allSelectedNodes );
    }
}

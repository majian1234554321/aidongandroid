package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.DynamicBean;

import java.util.List;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public interface SportCircleFragmentView {

    /**
     * 更新列表
     * @param dynamicBeanList
     */
    void updateRecyclerView(List<DynamicBean> dynamicBeanList);

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();



    void addLikeResult(int position,BaseBean baseBean);


    void cancelLikeResult(int position,BaseBean baseBean);

}

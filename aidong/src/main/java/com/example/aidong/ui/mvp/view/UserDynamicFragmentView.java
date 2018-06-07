package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.DynamicBean;

import java.util.List;

/**
 * 用户资料--动态
 * Created by song on 2017/1/16.
 */
public interface UserDynamicFragmentView {

    void updateDynamic(List<DynamicBean> dynamicBeanList);

    void showEndFooterView();

    void showEmptyLayout();

    void addLikeResult(int position,BaseBean baseBean);

    void canLikeResult(int position,BaseBean baseBean);
}

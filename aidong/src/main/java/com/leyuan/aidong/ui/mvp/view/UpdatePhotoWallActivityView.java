package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;

/**
 * update photo wall activity interface
 * Created by song on 2017/2/20.
 */
public interface UpdatePhotoWallActivityView {

    /**
     * the result of delete photo from photo wall
     * @param baseBean the result status
     */
    void deletePhotoResult(BaseBean baseBean,int position);

    /**
     * the result of add photos to photo wall
     * @param baseBean the result status
     */
    void addPhotosResult(BaseBean baseBean);
}

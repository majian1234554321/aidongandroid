package com.example.aidong.ui.home.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.BannerBean;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .utils.GlideLoader;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 首页活动广告弹框
 * Created by song on 2016/11/25.
 */
public class HomeBannerDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private ImageView dvCover;
    private BGABanner bgaBanner;
    private TextView tvFinish;
    private TextView tvDesc;
    private List<BannerBean> banner;

    public HomeBannerDialog(Context context, List<BannerBean> banner) {
        super(context,R.style.MyDialog);
        this.context = context;
        this.banner = banner;
        initView();
        setListener();
    }

    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_home_banner,null);
        dvCover = (ImageView)view.findViewById(R.id.dv_cover);
        bgaBanner = (BGABanner) view.findViewById(R.id.banner);
        tvFinish = (TextView)view.findViewById(R.id.tv_finish);
        tvDesc = (TextView)view.findViewById(R.id.tv_desc);

        //如果banner集合为空不会进到这个界面，不用校验
        if(banner.size() == 1){
            dvCover.setVisibility(View.VISIBLE);
            bgaBanner.setVisibility(View.GONE);
            GlideLoader.getInstance().displayImage(banner.get(0).getImage(), dvCover);
        }else {
            dvCover.setVisibility(View.GONE);
            bgaBanner.setVisibility(View.VISIBLE);
            bgaBanner.setAdapter(new BGABanner.Adapter() {
                @Override
                public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                    GlideLoader.getInstance().displayImage((String)model, (ImageView)view);
                }
            });
            bgaBanner.setData(banner,null);
        }
        super.setContentView(view);
    }

    private void setListener() {
        dvCover.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        tvDesc.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dv_cover:
                ((BaseActivity)context).toTargetActivity(banner.get(0));
                dismiss();
                break;
            case R.id.tv_finish:
                dismiss();
                break;
            case R.id.tv_desc:
                ((BaseActivity)context).toTargetActivity(banner.get(0));
                dismiss();
                break;
            default:
                break;
        }
    }
}

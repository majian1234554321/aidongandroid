package com.leyuan.aidong.widget.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.VersionInformation;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.VersionManager;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by user on 2017/6/6.
 */
public class VersionDialog extends BaseDialog {

    private ImageView dvCover;
    private BGABanner bgaBanner;
    private TextView tvFinish;
    private TextView tvDesc;

    VersionInformation versionInformation;

    public VersionDialog(Context context, VersionInformation versionInformation) {
        super(context);
        this.versionInformation = versionInformation;
    }


    @Override
    protected View inflateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_home_banner, null, false);
        dvCover = (ImageView) view.findViewById(R.id.dv_cover);
        tvFinish = (TextView) view.findViewById(R.id.tv_finish);
        tvDesc = (TextView) view.findViewById(R.id.tv_desc);
        return view;
    }


    @Override
    protected void initData() {

    }


    public BaseDialog setData() {
        GlideLoader.getInstance().displayImage(versionInformation.getImage(), dvCover);

        tvDesc.setText("更新");

        if (versionInformation.isUpdate_force() || VersionManager.mustUpdate(versionInformation.getVersion(), context)) {
            tvFinish.setVisibility(View.GONE);
        } else {
            tvFinish.setText("取消");
            tvFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        tvDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload(versionInformation.getApk_url());
            }
        });

        return this;
    }

    private void startDownload(String downloadUrl) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse(downloadUrl);
            intent.setData(content_url);
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } catch (Exception e) {
            e.printStackTrace();
            ToastGlobal.showShort("下载地址解析失败");
        }
    }
}

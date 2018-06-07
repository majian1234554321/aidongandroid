package com.leyuan.aidong.ui.home.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.richtext.RichWebView;

/**
 * 商品详情售后服务
 * Created by song on 2016/9/12.
 */
public class GoodsServiceFragment extends BaseFragment {
    private String content;
    private TextView tvContent;
    RichWebView richWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            content = bundle.getString("serviceText");
        }
        return inflater.inflate(R.layout.fragment_goods_service, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        tvContent = (TextView) view.findViewById(R.id.tv_content);
        richWebView = (RichWebView) view.findViewById(R.id.web_view);
        if (!TextUtils.isEmpty(content)) {
            richWebView.setRichText(content);
            Logger.largeLog("RichText", content);

//            content = "<p><img src=\"http://upload-images.jianshu.io/upload_images/" +
//                    "3682352-3f50173a87599b0c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240\" /></p>";
//            RichText.from(content)
//                    .type(RichType.HTML).autoFix(true)
//                    .autoPlay(true) // gif图片是否自动播放
//                    .scaleType(ImageHolder.ScaleType.FIT_CENTER) // 图片缩放方式
//                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
//                    .into(tvContent); // 设置目标TextView

//            RichText.from(content, RichType.MARKDOWN).into(tvContent);
//                    .placeHolder(R.drawable.place_holder_logo)
//                    .error(R.drawable.place_holder_logo)
        }
    }
}

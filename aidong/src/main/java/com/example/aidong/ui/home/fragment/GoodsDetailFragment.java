package com.example.aidong.ui.home.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .ui.BaseFragment;
import com.example.aidong .utils.Logger;
import com.example.aidong .widget.richtext.RichWebView;

/**
 * 商品图文详情
 * Created by song on 2016/9/12.
 */
public class GoodsDetailFragment extends BaseFragment {
    private String content;
    private TextView tvContent;
    private RichWebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            content = bundle.getString("detailText");
        }
        return inflater.inflate(R.layout.fragment_goods_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        webView = (RichWebView) view.findViewById(R.id.web_view);

        if (!TextUtils.isEmpty(content)) {
            Logger.i("RichText", " body = " + content);
            webView.setRichText(content);
//            RichText.from(content)
//                    .autoFix(true)
//                    .scaleType(ImageHolder.ScaleType.CENTER_CROP)
//                    .size(300,300)
//                    .resetSize(true)
//                    .imageGetter(new ImageGetter() {
//                        @Override
//                        public Drawable getDrawable(ImageHolder holder, RichTextConfig config, TextView textView) {
//                            holder.getSource()
//                            return null;
//                        }
//
//                        @Override
//                        public void registerImageLoadNotify(ImageLoadNotify imageLoadNotify) {
//
//                        }
//
//                        @Override
//                        public void recycle() {
//
//                        }
//                    })
//                    .placeHolder(R.drawable.place_holder_logo)
//                    .error(R.drawable.place_holder_logo).into(tvContent);
        }
    }


}
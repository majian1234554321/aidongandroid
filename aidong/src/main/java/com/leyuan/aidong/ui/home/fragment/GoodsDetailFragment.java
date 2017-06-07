package com.leyuan.aidong.ui.home.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.richtext.RichWebView;

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
//        tvContent = (TextView) view.findViewById(R.id.tv_content);
        webView = (RichWebView) view.findViewById(R.id.web_view);

        if (!TextUtils.isEmpty(content)) {
            Logger.i("RichText", " body = " + content);
//            ((XRichText) view.findViewById(R.id.richText)).text(content);
//            tvContent.setText(Html.fromHtml(content, new Html.ImageGetter() {
//                @Override
//                public Drawable getDrawable(String source) {
//                    final Drawable[] drawable = {null};
//                    Glide.with(GoodsDetailFragment.this)
//                            .load(source)
//                            .asBitmap()
//                            .into(new SimpleTarget<Bitmap>() {
//                                @Override
//                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                    drawable[0] = new BitmapDrawable(getActivity().getResources(),resource);
//                                }
//                            });

//                    return drawable[0];
//                }
//            }, null).toString());
//            WebSettings settings = webView.getSettings();
//            settings.setJavaScriptEnabled(true);
//            webView.setWebViewClient(new WebViewClient(){
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    super.onPageFinished(view, url);
//                    webView.loadUrl("javascript:(function(){"
//                            + "var objs = document.getElementsByTagName('img'); "
//                            + "for(var i=0;i<objs.length;i++)  " + "{"
//                            + "var img = objs[i];   "
//                            + "    img.style.width = '100%';   "
//                            + "    img.style.height = 'auto';   "
//                            + "}" + "})()");
//
//                }
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    view.loadUrl(url);
//                    return true;
//                }
//
//            });
//            webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);

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

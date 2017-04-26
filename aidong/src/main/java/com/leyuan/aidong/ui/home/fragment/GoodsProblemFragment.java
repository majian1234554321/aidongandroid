package com.leyuan.aidong.ui.home.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;
import com.zzhoujay.richtext.RichText;

/**
 * 商品详情常见问题
 * Created by song on 2016/9/12.
 */
public class GoodsProblemFragment extends BaseFragment {
    private String content;
    private TextView tvContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            content = bundle.getString("problemText");
        }
        return inflater.inflate(R.layout.fragment_goods_problem,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        String IMAGE1 = "<h1>RichText</h1><p>Android平台下的富文本解析器</p>" +
                 "<img title=\"\" src=\"https://www.huayubx.com/data/cms/image/20170302/1488421057515013918.jpg\""
                 + "style=\"cursor: pointer;\">"
                 + "<br><br>"
                 + "<h3>点击菜单查看更多Demo</h3>"
                 + "<img src=\"http://ww2.sinaimg.cn/bmiddle/813a1fc7jw1ee4xpejq4lj20g00o0gnu.jpg\" />"
                 + "<p><a href=\"http://www.baidu.com\">baidu</a>"
                 + "hello asdkjfgsduk <a href=\"http://www.jd.com\">jd</a></p>";
        if(!TextUtils.isEmpty(content)){
            RichText.from(IMAGE1).placeHolder(R.drawable.place_holder_logo)
                    .error(R.drawable.place_holder_logo).into(tvContent);
        }
    }
}

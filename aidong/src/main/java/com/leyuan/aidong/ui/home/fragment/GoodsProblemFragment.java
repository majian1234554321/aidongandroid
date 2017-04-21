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
        if(!TextUtils.isEmpty(content)){
            RichText.from(content).placeHolder(R.drawable.place_holder_logo)
                    .error(R.drawable.place_holder_logo).into(tvContent);
        }
    }
}

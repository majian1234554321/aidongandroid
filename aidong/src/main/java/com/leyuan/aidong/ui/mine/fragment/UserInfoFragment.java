package com.leyuan.aidong.ui.mine.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ProfileBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.Utils;
import com.leyuan.aidong.widget.ExtendTextView;

import java.text.DecimalFormat;

/**
 * 资料
 * Created by song on 2016/12/27.
 */
public class UserInfoFragment extends BaseFragment{
    private ExtendTextView id;
    private ExtendTextView identify;
    private ExtendTextView hot;
    private ExtendTextView signature;
    private ExtendTextView address;
    private ExtendTextView birthday;
    private ExtendTextView zodiac;
    private ExtendTextView height;
    private ExtendTextView weight;
    private ExtendTextView bmi;
    private ExtendTextView frequency;

    private ProfileBean profileBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            profileBean = bundle.getParcelable("profile");
        }
        return inflater.inflate(R.layout.fragment_user_info,container,false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setView();
    }

    private void initView(View view){
        id = (ExtendTextView) view.findViewById(R.id.id);
        identify = (ExtendTextView) view.findViewById(R.id.identify);
        hot = (ExtendTextView) view.findViewById(R.id.hot);
        signature = (ExtendTextView) view.findViewById(R.id.signature);
        address = (ExtendTextView) view.findViewById(R.id.address);
        birthday = (ExtendTextView) view.findViewById(R.id.birthday);
        zodiac = (ExtendTextView) view.findViewById(R.id.zodiac);
        height = (ExtendTextView) view.findViewById(R.id.height);
        weight = (ExtendTextView) view.findViewById(R.id.weight);
        bmi = (ExtendTextView) view.findViewById(R.id.bmi);
        frequency = (ExtendTextView) view.findViewById(R.id.frequency);
    }

    private void setView(){
        id.setRightContent(profileBean.getId());
        identify.setRightContent("coach".equals(profileBean.getUserType()) ? "教练" : "健身爱好者");
        hot.setRightContent(profileBean.getPopularity());
        signature.setRightContent(profileBean.getSignature());
        address.setRightContent(profileBean.getProvince() + profileBean.getCity() + profileBean.getArea());
        birthday.setRightContent(profileBean.getBirthday());
        String birthday = profileBean.getBirthday();
        try {
            if(!TextUtils.isEmpty(birthday)) {
                String mothers = birthday.substring(birthday.indexOf("-") + 1, birthday.lastIndexOf("-"));
                String days = birthday.substring(birthday.lastIndexOf("-") + 1);
                zodiac.setRightContent(Utils.getConstellation(FormatUtil.parseInt(mothers), FormatUtil.parseInt(days)));
            }else {
                zodiac.setRightContent("");
            }
        }catch (Exception e){
            e.printStackTrace();
            zodiac.setRightContent("");
        }
        height.setRightContent(!TextUtils.isEmpty(profileBean.getHeight())
                ? profileBean.getHeight() + "cm" : "");
        weight.setRightContent(!TextUtils.isEmpty(profileBean.getWeight())
                ? profileBean.getWeight() + "kg" : "");
        frequency.setRightContent(!TextUtils.isEmpty(profileBean.getFrequency())
                ? profileBean.getFrequency()+"周/次" :"");
        DecimalFormat df = new java.text.DecimalFormat("#.00");
        bmi.setRightContent(!TextUtils.isEmpty(profileBean.getHeight()) && !TextUtils.isEmpty(profileBean.getWeight())
                ? df.format(Utils.calBMI(FormatUtil.parseFloat(profileBean.getWeight()),
                FormatUtil.parseFloat(profileBean.getHeight())/100)):"");
    }

    public void refreshUserInfo(ProfileBean profileBean){
        this.profileBean = profileBean;
        setView();
    }
}

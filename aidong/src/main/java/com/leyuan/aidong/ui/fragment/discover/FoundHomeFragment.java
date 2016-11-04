package com.leyuan.aidong.ui.fragment.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;

/**
 * Created by user on 2016/11/4.
 */
public class FoundHomeFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_found_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_gymnasium).setOnClickListener(this);
        view.findViewById(R.id.btn_person).setOnClickListener(this);
        view.findViewById(R.id.btn_news).setOnClickListener(this);
        view.findViewById(R.id.btn_sport_circle).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gymnasium:
                break;
            case R.id.btn_person:
                //TODO implement
                break;
            case R.id.btn_news:
                //TODO implement
                break;
            case R.id.btn_sport_circle:
                //TODO implement
                break;
        }
    }
}

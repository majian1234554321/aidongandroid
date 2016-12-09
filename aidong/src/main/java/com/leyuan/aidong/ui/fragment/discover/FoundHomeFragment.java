package com.leyuan.aidong.ui.fragment.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.activity.discover.DiscoverUserActivity;
import com.leyuan.aidong.ui.activity.discover.DiscoverVenuesActivity;
import com.leyuan.aidong.ui.activity.discover.SportNewsActivity;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gymnasium:
                startActivity(new Intent(getContext(), DiscoverVenuesActivity.class));
                break;
            case R.id.btn_person:
                startActivity(new Intent(getContext(), DiscoverUserActivity.class));
                //TODO implement
                break;
            case R.id.btn_news:
                startActivity(new Intent(getContext(), SportNewsActivity.class));
                //TODO implement
                break;
            case R.id.btn_sport_circle:
                //TODO implement

                break;
        }
    }
}

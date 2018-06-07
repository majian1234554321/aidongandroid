package com.leyuan.aidong.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.adapter.Fragment1Adapter;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View view  =  View.inflate(inflater.getContext(), R.layout.fragment1,null);
      RecyclerView recyclerView =  view.findViewById(R.id.recyclerView);
      List<String> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            list.add(0+"");
        }

      recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new Fragment1Adapter(inflater.getContext(),list));
        return view;
    }
}

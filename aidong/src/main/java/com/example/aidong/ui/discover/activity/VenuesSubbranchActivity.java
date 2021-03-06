package com.example.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.aidong.R;
import com.example.aidong .adapter.discover.StoreListAdapter;
import com.example.aidong .entity.VenuesBean;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;

/**
 *
 * Created by user on 2017/3/17.
 */
public class VenuesSubbranchActivity extends BaseActivity {


    private ImageView ivBack;
    private RecyclerView rvVenues;
    private ArrayList<VenuesBean> venuesList;

    public static void start(Context context, ArrayList<VenuesBean> venuesList) {
        Intent intent = new Intent(context, VenuesSubbranchActivity.class);
        intent.putParcelableArrayListExtra("venuesList", venuesList);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues_subbranch);
        venuesList = getIntent().getParcelableArrayListExtra("venuesList");

        ivBack = (ImageView) findViewById(R.id.iv_back);
        rvVenues = (RecyclerView) findViewById(R.id.rv_venues);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvVenues.setLayoutManager(new LinearLayoutManager(this));

        StoreListAdapter adapter = new StoreListAdapter(this, venuesList);

        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);

        rvVenues.setAdapter(headerAndFooterRecyclerViewAdapter);

        rvVenues.addOnScrollListener(new EndlessRecyclerOnScrollListener(){

            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);

            }
        });
    }
}

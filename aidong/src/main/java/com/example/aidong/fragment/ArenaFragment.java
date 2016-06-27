package com.example.aidong.fragment;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.adapter.ArenaListAdapter;
import com.example.aidong.adapter.ListViewPinPaiAdapter;
import com.example.aidong.model.ArenaBean;
import com.example.aidong.utils.PullToRefreshUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/*
* 场馆
*
* */
public class ArenaFragment extends BaseFragment implements
        OnRefreshListener2<ListView>, View.OnClickListener {

    private View view;
    private ArenaListAdapter adapter;
    private PullToRefreshListView mListView;
    private int page = 1;
    private TextView text_logo, text_sq, text_sx;
    private LinearLayout  linearLayout;
    private PopupWindow mPop;
    private List<String>  stringList=new ArrayList<>();
    private List<ArenaBean>  arenaBeanList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.arena_fragment, null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        text_logo = (TextView) view.findViewById(R.id.text_logo);
        text_sq = (TextView) view.findViewById(R.id.text_sq);
        text_sx = (TextView) view.findViewById(R.id.text_sx);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        text_logo.setOnClickListener(this);
        text_sq.setOnClickListener(this);
        text_sx.setOnClickListener(this);
        mListView = (PullToRefreshListView) view.findViewById(R.id.list);
        PullToRefreshUtil.setPullListView(mListView, Mode.BOTH);
        mListView.getRefreshableView().setDivider(new ColorDrawable(Color.parseColor("#eaebf0")));
        mListView.getRefreshableView().setDividerHeight(20);
        mListView.setOnRefreshListener(this);
        ArenaBean arenaBean=new ArenaBean();
        for(int i=0;i<10;i++){
            arenaBean.setName("asda");
            arenaBeanList.add(arenaBean);


        }
        adapter = new ArenaListAdapter(getActivity(),arenaBeanList);
        mListView.setAdapter(adapter);

    }

    private void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }




    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page = 1;
        mListView.onRefreshComplete();
    }


    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        mListView.onRefreshComplete();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.text_logo:
                foodNamePop();
                break;
            case R.id.text_sq:
                foodNamePop();
                break;
            case R.id.text_sx:
                foodNamePop();
                break;

        }
    }

private void getdate(){

    for(int i=0;i<10;i++){

        stringList.add(i+"");
    }

}

    private void foodNamePop() {
        getdate();
        if (mPop == null || !mPop.isShowing()) {

            View layout = getActivity().getLayoutInflater().inflate(R.layout.layout_filter_popup, null);
            final ListView listView = (ListView) layout.findViewById(R.id.listView_filter_area);
            final ListViewPinPaiAdapter adapter = new ListViewPinPaiAdapter(getActivity(), stringList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    mPop.dismiss();
                }
            });
            ImageView cancleImg = (ImageView) layout.findViewById(R.id.img_pop_down);
            cancleImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPop.dismiss();
                }
            });
            mPop = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            // 需要设置一下此参数，点击外边可消失
            mPop.setBackgroundDrawable(new BitmapDrawable());
            // 设置点击窗口外边窗口消失
            mPop.setOutsideTouchable(true);
            // 设置此参数获得焦点，否则无法点击
            mPop.setFocusable(true);
            mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });
            mPop.showAsDropDown(linearLayout, 0, 2);
        }
    }
}

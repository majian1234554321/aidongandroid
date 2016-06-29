package com.example.aidong.activity.home.action;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.ActionHomeContentAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1 on 2016/6/28.
 */
public class ActionHomeActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2 {

    private LinearLayout layout_actionhome_select;
    private TextView txt_actionhome_type_name;
    private ImageView img_actionhome_back, img_actionhome_type_cancle;
    private PullToRefreshListView list;

    private List<String> contentList = new ArrayList<String>();

    private ActionHomeContentAdapter contentAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionhome);
        initView();
        initData();
        setClick();
    }

    private void initView() {
        layout_actionhome_select = (LinearLayout) findViewById(R.id.layout_actionhome_select);
        txt_actionhome_type_name = (TextView) findViewById(R.id.txt_actionhome_type_name);
        img_actionhome_back = (ImageView) findViewById(R.id.img_actionhome_back);
        img_actionhome_type_cancle = (ImageView) findViewById(R.id.img_actionhome_type_cancle);
        list = (PullToRefreshListView) findViewById(R.id.list);
        list.setMode(PullToRefreshBase.Mode.BOTH);

    }

    private void initData() {

        for (int i = 0; i < 20; i++) {
            contentList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
        }
        contentAdapter = new ActionHomeContentAdapter(ActionHomeActivity.this, contentList);
        list.setAdapter(contentAdapter);
    }

    private void setClick() {
        list.setOnRefreshListener(this);
        img_actionhome_back.setOnClickListener(this);
        img_actionhome_type_cancle.setOnClickListener(this);

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            private SparseArray recordSp = new SparseArray(0);
            private int mCurrentfirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mCurrentfirstVisibleItem = firstVisibleItem;
                View firstView = view.getChildAt(0);
                if (null != firstView) {
                    ItemRecod itemRecord = (ItemRecod) recordSp.get(firstVisibleItem);
                    if (null == itemRecord) {
                        itemRecord = new ItemRecod();
                    }
                    itemRecord.height = firstView.getHeight();
                    itemRecord.top = firstView.getTop();
                    recordSp.append(firstVisibleItem, itemRecord);
                }
                int scrollY = getScrollY();
                //第一个可见项
                int p = list.getRefreshableView().getFirstVisiblePosition();
                contentAdapter.setChange(scrollY);
                contentAdapter.notifyDataSetChanged();
            }

            /**
             * 获取滑动高度
             * @return
             */
            private int getScrollY() {
                int height = 0;
                for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
                    ItemRecod itemRecod = (ItemRecod) recordSp.get(i);
                    height += itemRecod.height;
                }
                ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);
                if (null == itemRecod) {
                    itemRecod = new ItemRecod();
                }
                return height - itemRecod.top;
            }

            class ItemRecod {
                int height = 0;
                int top = 0;
            }
        });

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        stopLoading();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        stopLoading();

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_actionhome_back:
                finish();
                break;
            case R.id.img_actionhome_type_cancle:
                layout_actionhome_select.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public void stopLoading() {
        try {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    list.onRefreshComplete();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.example.aidong.ui.home.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.PersonHorizontalAdapter;
import com.example.aidong .entity.UserBean;

import java.util.List;

/**
 * Created by user on 2018/1/4.
 */
public class PersonHorizontalView extends RelativeLayout {


    private TextView txtLeftTitle;
    private TextView txtCheckAll;
    private RecyclerView recyclerView;
    private PersonHorizontalAdapter adapter;

    public PersonHorizontalView(Context context) {
        this(context,null,0);
    }

    public PersonHorizontalView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PersonHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_person_horizontal,this,true);
        txtLeftTitle = (TextView) view.findViewById(R.id.txt_left_title);
        txtCheckAll = (TextView) view.findViewById(R.id.txt_check_all);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_person);

        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);

        adapter = new PersonHorizontalAdapter(context);
        recyclerView.setAdapter(adapter);

    }
    
    public void setLeftTitle(String title){
        txtLeftTitle.setText(title);
    }

    public void setCheckAllClickListener(OnClickListener onClickListener) {
        txtCheckAll.setOnClickListener(onClickListener);
    }

    public void setUserData(List<UserBean> userBeanList) {
        adapter.setData(userBeanList);
    }
}

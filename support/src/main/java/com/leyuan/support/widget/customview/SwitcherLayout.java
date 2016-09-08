package com.leyuan.support.widget.customview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.leyuan.support.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * 动态显示加载中,加载失败,正常加载的布局
 * @author Mehdi Sakout
 * @author Danny Tsegai
 */
public class SwitcherLayout {

    private Context mContext;
    private LayoutInflater mInflater;

    private View mTargetView;
    private ViewSwitcher mSwitcher;
    private RelativeLayout mContainer;
    private ArrayList<View> mCustomViews;
    private ArrayList<View> mDefaultViews;
    private OnClickListener mClickListener;

    // Default Tags
    private final String TAG_EMPTY       =  "NO_CONTENT";
    private final String TAG_LOADING     =  "LOADING_CONTENT";
    private final String TAG_EXCEPTION   =  "EXCEPTION";

    private final String[] mSupportedAbsListViews = new String[]{"listview","gridview","expandablelistview"};
    private final String[] mSupportedViewGroups = new String[]{"linearlayout","relativelayout", "framelayout", "scrollview", "recyclerview", "viewgroup"};

    public SwitcherLayout(Context context, View targetView){
        this.mContext 		= context;
        this.mInflater 		= ((Activity)mContext).getLayoutInflater();
        this.mTargetView 	= targetView;
        this.mContainer 	= new RelativeLayout(mContext);
        this.mCustomViews 	= new ArrayList<>();
        this.mDefaultViews	= new ArrayList<>();

        Class viewClass = mTargetView.getClass();
        Class superViewClass = viewClass.getSuperclass();
        String viewType = viewClass.getName().substring(viewClass.getName().lastIndexOf('.')+1).toLowerCase(Locale.getDefault());
        String superViewType = superViewClass.getName().substring(superViewClass.getName().lastIndexOf('.')+1).toLowerCase(Locale.getDefault());

        if(Arrays.asList(mSupportedAbsListViews).contains(viewType)|| Arrays.asList(mSupportedAbsListViews).contains(superViewType)) {
            initializeAbsListView();
        }else if(Arrays.asList(mSupportedViewGroups).contains(viewType)|| Arrays.asList(mSupportedViewGroups).contains(superViewType)) {
            initializeViewCroup();
        }else {
            throw new IllegalArgumentException("TargetView type [" + superViewType + "] is not supported !");
        }

    }

    public SwitcherLayout(Context context, int viewID){
        this.mContext 		= context;
        this.mInflater 		= ((Activity)mContext).getLayoutInflater();
        this.mTargetView 	= mInflater.inflate(viewID, null, false);
        this.mContainer 	= new RelativeLayout(mContext);
        this.mCustomViews 	= new ArrayList<>();
        this.mDefaultViews	= new ArrayList<>();

        Class viewClass = mTargetView.getClass();
        Class superViewClass = viewClass.getSuperclass();
        String viewType = viewClass.getName().substring(viewClass.getName().lastIndexOf('.') + 1).toLowerCase(Locale.getDefault());
        String superViewType = superViewClass.getName().substring(superViewClass.getName().lastIndexOf('.')+1).toLowerCase(Locale.getDefault());

        if(Arrays.asList(mSupportedAbsListViews).contains(viewType)|| Arrays.asList(mSupportedAbsListViews).contains(superViewType)){
            initializeAbsListView();
        }else if(Arrays.asList(mSupportedViewGroups).contains(viewType)|| Arrays.asList(mSupportedViewGroups).contains(superViewType)){
            initializeViewCroup();
        }else {
            throw new IllegalArgumentException("TargetView type ["+superViewType+"] is not supported !");
        }
    }

    private void initializeAbsListView(){

        setDefaultViews();

        AbsListView abslistview = (AbsListView)mTargetView;
        abslistview.setVisibility(View.GONE);
        ViewGroup parent = (ViewGroup) abslistview.getParent();
        if(mContainer!=null){
            parent.addView(mContainer);
            abslistview.setEmptyView(mContainer);
        }else {
            throw new IllegalArgumentException("mContainer is null !");
        }
    }

    private void initializeViewCroup(){

        setDefaultViews();

        // init viewSwitcher
        mSwitcher = new ViewSwitcher(mContext);
        ViewSwitcher.LayoutParams params = new ViewSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mSwitcher.setLayoutParams(params);

        // remove targetView
        ViewGroup group = (ViewGroup)mTargetView.getParent();
        int index = 0;
        Clone target= new Clone(mTargetView);
        if(group!=null){
            index = group.indexOfChild(mTargetView);
            group.removeView(mTargetView);
        }

        // viewSwitcher add target view and other view
        mSwitcher.addView(mContainer,0);
        mSwitcher.addView(target.getView(),1);
        mSwitcher.setDisplayedChild(1);

        // add viewSwitcher
        if(group!=null){
            group.addView(mSwitcher,index);
        }else{
            ((Activity)mContext).setContentView(mSwitcher);
        }
    }

    private void setDefaultViews(){
        View loadingView = initView(R.layout.view_loading_content, TAG_LOADING);
        View emptyView = initView(R.layout.view_no_content, TAG_EMPTY);
        View exceptionView = initView(R.layout.view_exception, TAG_EXCEPTION);

        mDefaultViews.add(0,emptyView);
        mDefaultViews.add(1,loadingView);
        mDefaultViews.add(2,exceptionView);

        // Hide all layouts at first initialization
        emptyView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        exceptionView.setVisibility(View.GONE);

        // init  RelativeLayout Wrapper
        RelativeLayout.LayoutParams containerParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        containerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        containerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        mContainer.setLayoutParams(containerParams);
        mContainer.addView(loadingView);
        mContainer.addView(emptyView);
        mContainer.addView(exceptionView);
    }

    public void showLoadingLayout(){
        show(TAG_LOADING);
    }

    public void showEmptyLayout(){
        show(TAG_EMPTY);
    }

    public void showExceptionLayout(){
        show(TAG_EXCEPTION);
    }

    public void showCustomView(String tag){
        show(tag);
    }

    public void showNormalContentView(){
        ArrayList<View> views =  new ArrayList<>(mDefaultViews);
        views.addAll(mCustomViews);
        for(View view : views){
            view.setVisibility(View.GONE);
        }
        if(mSwitcher!=null){
            mSwitcher.setDisplayedChild(1);
        }
    }
    private void show(String tag){
        ArrayList<View> views =  new ArrayList<>(mDefaultViews);
        views.addAll(mCustomViews);
        for(View view : views){
            if(view.getTag()!=null && view.getTag().toString().equals(tag)){
                view.setVisibility(View.VISIBLE);
            }else{
                view.setVisibility(View.GONE);
            }
        }
        if(mSwitcher!=null && mSwitcher.getDisplayedChild()!=0){
            mSwitcher.setDisplayedChild(0);
        }
    }
    /**
     * Return a view based on layout id
     * @param layout Layout Id
     * @param tag Layout Tag
     * @return View
     */
    private View initView(int layout, String tag){
        View view = mInflater.inflate(layout, null,false);

        view.setTag(tag);
        view.setVisibility(View.GONE);

        View buttonView = view.findViewById(R.id.bt_retry);
        if(buttonView!=null) {
            buttonView.setOnClickListener(this.mClickListener);
        }
        return view;
    }

    public void setClickListener(View.OnClickListener clickListener){
        this.mClickListener = clickListener;

        for(View view : mDefaultViews){
            View buttonView = view.findViewById(R.id.bt_retry);
            if(buttonView!=null)
                buttonView.setOnClickListener(this.mClickListener);
        }
    }

    public void addCustomView(View customView,String tag){
        customView.setTag(tag);
        customView.setVisibility(View.GONE);
        mCustomViews.add(customView);
        mContainer.addView(customView);
    }

    /**
     * 备份View
     */
    private class Clone {
        private View mView;

        public Clone(View view){
            this.setView(view);
        }

        public View getView() {
            return mView;
        }

        public void setView(View mView) {
            this.mView = mView;
        }
    }
}
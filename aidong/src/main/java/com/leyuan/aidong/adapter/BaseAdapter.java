package com.leyuan.aidong.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseAdapter简单封装,子类只需实现getContentView()
 * 和initView(View view, int position)
 * getContentView()返回的是一个布局文件id
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    public List<T> list;

    public BaseAdapter() {
        list = new ArrayList<T>();
    }

    public BaseAdapter(List<T> list) {
        this.list = list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void cleanList() {
        if (null != list) {
            this.list.clear();
            notifyDataSetChanged();
        }
    }

    public void addList(List<T> list) {
        if (null != list && !list.isEmpty()) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }
    public void addTopList(List<T> list) {
        if (null != list && !list.isEmpty()) {
            this.list.addAll(0, list);
            notifyDataSetChanged();
        }
    }
    public void removeItem(int position) {
        if (null != list && position < list.size()) {
            list.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        if (list == null) {
            return null;
        } else if(position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflateView(getContentView(), parent.getContext());
            setLayoutParams(convertView);
        }
        initView(convertView, position,parent);
        return convertView;
    }

    private View inflateView(int resId, Context context) {
        return View.inflate(context, resId, null);
    }

    /**
     * @Title: get
     * @Description:  SparseArray缓存View
     * @param view converView
     * @param id 控件的id
     * @param @return
     * @return E  返回<E extends View>
     * @throws
     */
    @SuppressWarnings("unchecked")
    protected <E extends View> E getView(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (null == viewHolder) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (null == childView) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (E) childView;
    }

    /**
     * @Title: getContentView
     * @Description: 子类实现
     * @return int   返回item布局id
     * @throws
     */
    public abstract int getContentView();

    /**
     * @Title: initView
     * @Description: 获取控件 (Button btn = get(view, R.id.btn);
     * @param  view
     * @param  position
     * @return void
     * @throws
     */
    public abstract void initView(View view, int position,ViewGroup parent);

    public void setLayoutParams(View view){

    }

}

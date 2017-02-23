package com.leyuan.aidong.ui.mine.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.leyuan.aidong.R;

import com.leyuan.aidong.entity.AlbumFolderInfoBean;
import com.leyuan.aidong.adapter.mine.AlbumFolderAdapter;
import com.leyuan.aidong.utils.ScreenUtil;

import java.util.ArrayList;

/**
 * 选择相册文件夹弹框
 * Created by song on 2016/9/26.
 */
public class ImgFolderPopWindow extends PopupWindow {
    private View view;
    private ListView listView;
    private int width;
    private int height;
    private Context context;
    private ArrayList<AlbumFolderInfoBean> folderList;
    public OnFolderClickListener onFolderClickListener;
    private int currFolder;

    public ImgFolderPopWindow(Context context, int width, int height, ArrayList<AlbumFolderInfoBean> folderList, int currentFolder) {
        this.context = context;
        this.width = width;
        this.height = height;
        this.folderList = folderList;
        this.currFolder = currentFolder;
        view = LayoutInflater.from(context).inflate(R.layout.popup_folder, null);
        setContentView(view);
        initPopWindow();
        initListView();
        setUpListener();
    }

    private void initListView() {
        listView = (ListView) view.findViewById(R.id.listview);
        AlbumFolderAdapter imgFolderAdapter = new AlbumFolderAdapter(context, folderList, currFolder);
        listView.setAdapter(imgFolderAdapter);
        listView.getLayoutParams().height = height;
    }


    public interface OnFolderClickListener {
        void OnFolderClick(int position);
    }

    public void setOnFolderClickListener(OnFolderClickListener onFolderClickListener) {
        this.onFolderClickListener = onFolderClickListener;
    }

    private void setUpListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onFolderClickListener.OnFolderClick(position);
            }
        });
    }


    private void initPopWindow() {
        this.setWidth(width);
        this.setHeight(ScreenUtil.getScreenHeight(context));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(android.R.style.Animation_Dialog);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        this.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                Rect rect = new Rect(listView.getLeft(), listView.getTop(), listView.getRight(), listView.getBottom());
                if (!rect.contains((int) x, (int) y)) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }
}

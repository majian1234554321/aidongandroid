package com.leyuan.aidong.ui.mine.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AlbumFolderInfoBean;
import com.leyuan.aidong.entity.ImageInfoBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.adapter.mine.AlbumRecyclerAdapter;
import com.leyuan.aidong.ui.mine.view.ImageScan;
import com.leyuan.aidong.ui.mine.view.ImgFolderPopWindow;
import com.leyuan.aidong.ui.mine.view.MediaScanner;
import com.leyuan.aidong.ui.mine.view.SpaceItemDecoration;
import com.leyuan.aidong.utils.ScreenUtil;

import java.io.File;
import java.util.ArrayList;

import static com.leyuan.aidong.utils.Constant.DEFAULT_MAX_UPLOAD_IMAGE_COUNT;


/**
 * 相册
 * Created by song on 2016/9/26.
 */
public class AlbumActivity extends BaseActivity implements AlbumRecyclerAdapter.OnImgSelectListener, View.OnClickListener, ImgFolderPopWindow.OnFolderClickListener {
    private static final int CODE_START_CAMERA = 0;
    private static final int PERMISSION_CAMERA = 1;
    private RelativeLayout titleLayout;
    private TextView tvCancel;
    private LinearLayout llFolder;
    private TextView tvFolderName;
    private ImageView ivArrow;
    private TextView tvNext;
    private RecyclerView rvAlbum;

    private int currFolderIndex;
    private AlbumRecyclerAdapter albumAdapter;
    private ArrayList<AlbumFolderInfoBean> folders = new ArrayList<>();
    private ArrayList<ImageInfoBean> selectImages = new ArrayList<>();

    private ImgFolderPopWindow popWindow;

    private String imageRootPath;
    private String imageName;

    private MediaScanner mMediaScanner;

    private int usefulImageSize;

    //TODO handler leak
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initRecyclerView(currFolderIndex);
        }
    };

    public static void start(Context context,ArrayList<ImageInfoBean> selectedImages) {
        Intent starter = new Intent(context, AlbumActivity.class);
        starter.putExtra("selectImages",selectedImages);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        currFolderIndex = 0;
        mMediaScanner = new MediaScanner(this);

        imageName = System.currentTimeMillis() + ".jpg";
        String outFilePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/";
        File dir = new File(outFilePath);
        if (!dir.exists()) dir.mkdirs();
        imageRootPath = outFilePath + imageName;// 指定路径;

        if (getIntent() != null) {
            if( getIntent().getParcelableArrayListExtra("selectImages") != null) {
                selectImages = getIntent().getParcelableArrayListExtra("selectImages");
            }
            usefulImageSize = DEFAULT_MAX_UPLOAD_IMAGE_COUNT - selectImages.size();
        }

        initView();
        setListener();

        new ImageScan(this, getLoaderManager()) {
            @Override
            public void onScanFinish(ArrayList<AlbumFolderInfoBean> folderList) {
                folders = folderList;
                setAlreadySelectFile(selectImages, folders);
                Message message = Message.obtain();
                handler.sendMessage(message);
            }
        };
        changeSendButtonBg(tvNext, selectImages.size());
    }


    private void initView() {
        titleLayout = (RelativeLayout) findViewById(R.id.rl_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        llFolder = (LinearLayout) findViewById(R.id.ll_folder);
        tvFolderName = (TextView) findViewById(R.id.tv_folder_name);
        ivArrow = (ImageView) findViewById(R.id.iv_arrow);
        tvNext = (TextView) findViewById(R.id.tv_next);
        rvAlbum = (RecyclerView) findViewById(R.id.rv_album);
        rvAlbum.setLayoutManager(new GridLayoutManager(this, 3));
        rvAlbum.addItemDecoration(new SpaceItemDecoration(2));
        albumAdapter = new AlbumRecyclerAdapter(this);
        rvAlbum.setAdapter(albumAdapter);
    }

    private void setListener() {
        tvCancel.setOnClickListener(this);
        llFolder.setOnClickListener(this);
        tvNext.setOnClickListener(this);
    }


    private void setAlreadySelectFile(ArrayList<ImageInfoBean> selectImageList, ArrayList<AlbumFolderInfoBean> folderList) {
        if (selectImageList == null || selectImageList.size() == 0) {
            return;
        }
        String selectPath;
        ArrayList<ImageInfoBean> allImg = (ArrayList<ImageInfoBean>) folderList.get(0).getImageInfoList();
        for (ImageInfoBean selectInfo : selectImageList) {
            //拿到绝对路径之后
            if (selectInfo == null) return;
            selectPath = selectInfo.getImageFile().getAbsolutePath();
            //根据绝对路路径取出对应的imageInfo，修改select的值
            for (int i = 0; i < allImg.size(); i++) {
                if (selectPath.equals(allImg.get(i).getImageFile().getAbsolutePath())) {
                    allImg.get(i).setIsSelected(true);
                }
            }
        }
    }

    private void initRecyclerView(int position) {
        if (!folders.isEmpty()) {
            albumAdapter.setOnImgSelectListener(this);
            albumAdapter.setData(folders.get(position).getImageInfoList(), selectImages);
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void changeSendButtonBg(TextView textView, int length) {
        if (length > 0) {
            textView.setBackground(getResources().getDrawable(R.drawable.bg_send_corners_highlight));
            textView.setTextColor(Color.parseColor("#fbffff"));
            textView.setText("选取 " + length + "/" + usefulImageSize);
            textView.setEnabled(true);
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.bg_send_corners));
            textView.setTextColor(Color.parseColor("#b3b3b3"));
            textView.setText("选取 0/" + usefulImageSize);
            textView.setEnabled(false);
        }
    }

    @Override
    public void OnSelect(ArrayList<ImageInfoBean> imageInfo) {
        selectImages = imageInfo;
        changeSendButtonBg(tvNext, selectImages.size());
    }

    @Override
    public void OnDisSelect(ArrayList<ImageInfoBean> imageInfo) {
        selectImages = imageInfo;
        changeSendButtonBg(tvNext, selectImages.size());
    }

    @Override
    public void onStartCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);             // 启动系统相机
        Uri photoUri = Uri.fromFile(new File(imageRootPath));                  // 传递路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);                      // 更改系统默认存储路径
        startActivityForResult(intent, CODE_START_CAMERA);
    }

    @Override
    public void OnFolderClick(int position) {
        tvFolderName.setText(folders.get(position).getFolderName());
        currFolderIndex = position;
        initRecyclerView(position);
        popWindow.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_next:
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("selectImages", selectImages);
                //setResult(CODE_ADD_IMAGE, intent);
                finish();
                break;
            case R.id.ll_folder:
                popWindow = new ImgFolderPopWindow(this, ScreenUtil.getScreenWidth(this),
                        (int) (ScreenUtil.getScreenHeight(this) * 0.6), folders, currFolderIndex);
                popWindow.setOnFolderClickListener(this);
                popWindow.showAsDropDown(titleLayout);
                popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                       // ivArrow.setImageResource(R.drawable.video_detail_down_arrow);
                    }
                });
              //  ivArrow.setImageResource(R.drawable.bt_up_arrow);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_START_CAMERA) {
            if (resultCode == RESULT_OK) { // 拍照成功
                ImageInfoBean imageInfoBean = new ImageInfoBean();
                imageInfoBean.setImageFile(new File(imageRootPath));
                imageInfoBean.setIsSelected(true);
                selectImages.add(0, imageInfoBean);

                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("selectImages", selectImages);
              //  setResult(CODE_ADD_IMAGE, intent);

                updateGallery(imageRootPath);
            }
            finish();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 启动系统相机
                Uri photoUri = Uri.fromFile(new File(imageRootPath));             // 传递路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);          // 更改系统默认存储路径*/
                startActivityForResult(intent, CODE_START_CAMERA);
            }
        }
    }

    /**
     * 更新相册
     */
    private void updateGallery(String filePath) {
        if (mMediaScanner != null) {
            mMediaScanner.scanFile(filePath, "image/jpeg");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaScanner != null) {
            mMediaScanner.unScanFile();
        }
    }
}

package com.example.aidong.ui.discover.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer.util.Util;
import com.iknow.android.TrimmerActivity;
import com.iknow.android.utils.TrimVideoUtil;
import com.example.aidong.R;
import com.example.aidong .adapter.discover.PublishDynamicAdapter;
import com.example.aidong .entity.CircleDynamicBean;
import com.example.aidong .entity.data.DynamicsData;
import com.example.aidong .module.chat.CMDMessageManager;
import com.example.aidong .module.photopicker.boxing.Boxing;
import com.example.aidong .module.photopicker.boxing.model.config.BoxingConfig;
import com.example.aidong .module.photopicker.boxing.model.entity.BaseMedia;
import com.example.aidong .module.photopicker.boxing.model.entity.impl.VideoMedia;
import com.example.aidong .module.photopicker.boxing_impl.ui.BoxingActivity;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.home.view.ItemDragHelperCallback;
import com.example.aidong .ui.mvp.presenter.DynamicPresent;
import com.example.aidong .ui.mvp.presenter.impl.DynamicPresentImpl;
import com.example.aidong .ui.mvp.view.PublishDynamicActivityView;
import com.example.aidong .ui.video.activity.PlayerActivity;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.DialogUtils;
import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .utils.UiManager;
import com.example.aidong .utils.qiniu.IQiNiuCallback;
import com.example.aidong .utils.qiniu.UploadToQiNiuManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.aidong .utils.Constant.REQUEST_SELECT_VIDEO;

/**
 * 发表动态
 * Created by song on 2017/2/2.
 */
public class PublishDynamicActivity extends BaseActivity implements PublishDynamicActivityView,
        View.OnClickListener, PublishDynamicAdapter.OnItemClickListener, View.OnKeyListener {
    protected static final int REQUEST_MEDIA = 1;
    protected static final int MAX_TEXT_COUNT = 180;
    protected static final int REQUEST_CIRCLE = 101;
    protected static final int REQUEST_LOCATION = 102;
    protected static final int REQUEST_USER = 103;

    protected ImageView ivBack;
    protected EditText etContent;
    protected TextView tvContentCount;
    protected RecyclerView recyclerView;
    protected Button btSend;

    protected boolean isPhoto;    //区分上传图片还是视频
    protected PublishDynamicAdapter mediaAdapter;
    protected ArrayList<BaseMedia> selectedMedia;
    private ArrayList<BaseMedia> selectedMediaNewSelf;
    protected DynamicPresent dynamicPresent;

    protected LinearLayout layoutAddCircle;
    protected ImageView imgAddCircle;
    protected TextView txtAddCircle;
    protected LinearLayout layoutAddLocation;
    protected ImageView imgAddLocation;
    protected TextView txtLocation;
    protected ImageButton bt_circle_delete, bt_location_delete;
    protected String type;
    protected String link_id;
    protected String position_name;
    protected String user_id;
    protected String name;
    protected String latitude = App.lat + "";
    protected String longitude = App.lon + "";
    protected String content;


    public static void startForResult(Fragment fragment, boolean isPhoto, ArrayList<BaseMedia> selectedMedia, int requestCode) {
        Intent starter = new Intent(fragment.getContext(), PublishDynamicActivity.class);
        starter.putExtra("isPhoto", isPhoto);
        starter.putParcelableArrayListExtra("selectedMedia", selectedMedia);
        fragment.startActivityForResult(starter, requestCode);
    }

    public static void startForResult(Activity activity, boolean isPhoto, ArrayList<BaseMedia> selectedMedia, int requestCode) {
        Intent starter = new Intent(activity, PublishDynamicActivity.class);
        starter.putExtra("isPhoto", isPhoto);
        starter.putParcelableArrayListExtra("selectedMedia", selectedMedia);
        activity.startActivityForResult(starter, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamic);
        setSlideAnimation();
        dynamicPresent = new DynamicPresentImpl(this, this);
        if (getIntent() != null) {
            isPhoto = getIntent().getBooleanExtra("isPhoto", true);
            selectedMedia = getIntent().getParcelableArrayListExtra("selectedMedia");
        }
        initView();
        setListener();
    }

    private void initView() {

        layoutAddCircle = (LinearLayout) findViewById(R.id.layout_add_circle);
        imgAddCircle = (ImageView) findViewById(R.id.img_add_circle);
        txtAddCircle = (TextView) findViewById(R.id.txt_add_circle);
        bt_circle_delete = (ImageButton) findViewById(R.id.bt_circle_delete);
        bt_location_delete = (ImageButton) findViewById(R.id.bt_location_delete);

        layoutAddLocation = (LinearLayout) findViewById(R.id.layout_add_location);
        imgAddLocation = (ImageView) findViewById(R.id.img_add_location);
        txtLocation = (TextView) findViewById(R.id.txt_location);


        ivBack = (ImageView) findViewById(R.id.iv_back);
        etContent = (EditText) findViewById(R.id.et_content);
        tvContentCount = (TextView) findViewById(R.id.tv_content_count);
        recyclerView = (RecyclerView) findViewById(R.id.rv_image);
        btSend = (Button) findViewById(R.id.bt_send);
        btSend.setEnabled(!selectedMedia.isEmpty());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        mediaAdapter = new PublishDynamicAdapter();
        recyclerView.setAdapter(mediaAdapter);
        ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback(mediaAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        mediaAdapter.setData(selectedMedia, isPhoto);

    }

    private void setListener() {
        findViewById(R.id.bt_circle_delete).setOnClickListener(this);
        findViewById(R.id.bt_location_delete).setOnClickListener(this);
        layoutAddCircle.setOnClickListener(this);
        layoutAddLocation.setOnClickListener(this);

        ivBack.setOnClickListener(this);
        mediaAdapter.setOnItemClickListener(this);
        btSend.setOnClickListener(this);
        etContent.addTextChangedListener(new OnTextWatcher());
        etContent.setOnKeyListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:

                finish();

                break;

            case R.id.bt_send:

                if (FormatUtil.parseInt(tvContentCount.getText().toString()) > MAX_TEXT_COUNT) {
                    ToastGlobal.showLong(String.format(getString(R.string.too_many_text), MAX_TEXT_COUNT));
                    return;
                }

                uploadToQiNiu();

                break;
            case R.id.bt_circle_delete:

                this.type = null;
                this.link_id = null;
                imgAddCircle.setImageResource(R.drawable.circle_select_no);
                txtAddCircle.setTextColor(R.color.c3);
                txtAddCircle.setText(R.string.add_to_circle);
                bt_circle_delete.setVisibility(View.GONE);

                break;

            case R.id.bt_location_delete:

                this.position_name = null;
                imgAddLocation.setImageResource(R.drawable.icon_course_location);
                txtLocation.setTextColor(R.color.c3);
                txtLocation.setText(R.string.add_location);
                bt_location_delete.setVisibility(View.GONE);

                break;
            case R.id.layout_add_circle:

                UiManager.activityJumpForResult(this, new Bundle(), SelectedCircleActivity.class, REQUEST_CIRCLE);

                break;
            case R.id.layout_add_location:

                UiManager.activityJumpForResult(this, new Bundle(), SelectedLocationActivity.class, REQUEST_LOCATION);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAddMediaClick() {
        BoxingConfig config = new BoxingConfig(isPhoto ? BoxingConfig.Mode.MULTI_IMG
                : BoxingConfig.Mode.VIDEO).needCamera();
        if (isPhoto) {
            Boxing.of(config).withIntent(this, BoxingActivity.class, selectedMedia)
                    .start(this, REQUEST_MEDIA);
        } else {
            Boxing.of(config).withIntent(this, BoxingActivity.class, selectedMedia)
                    .start(this, REQUEST_SELECT_VIDEO);
        }

    }

    @Override
    public void onDeleteMediaClick(int position) {
        selectedMedia.remove(position);
        mediaAdapter.notifyItemRemoved(position);
        mediaAdapter.notifyItemRangeChanged(position, selectedMedia.size());
        btSend.setEnabled(!selectedMedia.isEmpty());

        for (BaseMedia media : selectedMedia) {
            Logger.i("Publish", "onDeleteMediaClick selectedMedia = " + media.getPath());
        }

    }

    @Override
    public void onMediaItemClick(BaseMedia baseMedia) {
        if (!isPhoto) {
            Logger.i("baseMedia getPath = " + baseMedia.getPath());
            Intent intent = new Intent(this, PlayerActivity.class)
                    .setData(Uri.parse("file://" + baseMedia.getPath()))
                    .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_OTHER);
            startActivity(intent);

        }
    }

    protected void uploadToQiNiu() {
        showProgressDialog();
        for (BaseMedia media : selectedMedia) {
            Logger.i("Publish", "uploadToQiNiu selectedMedia = " + media.getPath());
        }

        UploadToQiNiuManager.getInstance().uploadMedia(isPhoto, selectedMedia, new IQiNiuCallback() {
            @Override
            public void onSuccess(List<String> urls) {
                uploadToServer(urls);
            }

            @Override
            public void onFail() {
                dismissProgressDialog();
                ToastGlobal.showLong("上传失败");
            }
        });
    }

    protected Map<String, String> itUser = new HashMap<>();

    protected void uploadToServer(List<String> qiNiuMediaUrls) {
        content = etContent.getText().toString();
        String[] media = new String[qiNiuMediaUrls.size()];
        for (int i = 0; i < qiNiuMediaUrls.size(); i++) {
            media[i] = qiNiuMediaUrls.get(i);
        }
        dynamicPresent.postDynamic(isPhoto, content, type, link_id, position_name, latitude, longitude, itUser, media);


    }

    @Override
    public void publishDynamicResult(DynamicsData dynamicData) {
        dismissProgressDialog();
        if (dynamicData == null) return;
        selectedMedia.clear();
        ToastGlobal.showLong("上传成功");

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_PUBLISH_DYNAMIC_SUCCESS));

        CMDMessageManager.sendCMDMessageAite(App.getInstance().getUser().getAvatar(),
                App.getInstance().getUser().getName(), dynamicData.dynamic_id, content, dynamicData.cover, CircleDynamicBean.ActionType.AITER, null,
                isPhoto ? 0 : 1, null, itUser);

        setResult(RESULT_OK, null);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CIRCLE) {
                this.type = data.getStringExtra("type");
                this.link_id = data.getStringExtra("link_id");
                imgAddCircle.setImageResource(R.drawable.circle_selected);
                txtAddCircle.setTextColor(ActivityCompat.getColor(this,R.color.main_red));
                txtAddCircle.setText(data.getStringExtra("name"));
                bt_circle_delete.setVisibility(View.VISIBLE);


            } else if (requestCode == REQUEST_LOCATION) {

                this.position_name = data.getStringExtra("position_address");
                this.latitude = data.getStringExtra("latitude");
                this.longitude = data.getStringExtra("longitude");
                //this.position_address = data.getStringExtra("position_address");


                imgAddLocation.setImageResource(R.drawable.location_selected);
                txtLocation.setTextColor(ActivityCompat.getColor(this,R.color.main_red));
                txtLocation.setText(position_name);
                bt_location_delete.setVisibility(View.VISIBLE);


            } else if (requestCode == REQUEST_USER) {
                this.user_id = data.getStringExtra("user_id");
                this.name = data.getStringExtra("name");
                itUser.put(name, user_id);

                Logger.i(" itUser.put( name =  " + name + ", user_id = " + user_id);
                String newContent = etContent.getText().toString() + name;
                etContent.setText(newContent);
                etContent.setSelection(newContent.length());
                etContent.requestFocus();

            } else if (requestCode == REQUEST_MEDIA) {
                if (recyclerView == null || mediaAdapter == null) {
                    return;
                }
                final List<BaseMedia> medias = Boxing.getResult(data);
                selectedMedia.clear();
                selectedMedia.addAll(medias);
                for (BaseMedia media : selectedMedia) {
                    Logger.i("Publish", "onActivityResult selectedMedia = " + media.getPath());
                }

                mediaAdapter.setData(selectedMedia, isPhoto);
                btSend.setEnabled(!selectedMedia.isEmpty());
            } else if (requestCode == REQUEST_SELECT_VIDEO) {

                if (recyclerView == null || mediaAdapter == null) {
                    return;
                }

                selectedMediaNewSelf = Boxing.getResult(data);
                if (selectedMediaNewSelf != null && selectedMediaNewSelf.size() > 0) {

                    int duration = TrimVideoUtil.VIDEO_MAX_DURATION;

                    if (selectedMediaNewSelf.get(0) instanceof VideoMedia) {
                        VideoMedia media = (VideoMedia) selectedMediaNewSelf.get(0);
                        duration = (int) (FormatUtil.parseLong(media.getmDuration()) / 1000 + 1);
                        Logger.i("TrimmerActivity", "onActivityResult media.getDuration() = " + media.getDuration());
                    }
                    Logger.i("TrimmerActivity", "onActivityResult  durantion = " + duration);

                    TrimmerActivity.startForResult(this, selectedMediaNewSelf.get(0).getPath(), duration, Constant.REQUEST_VIDEO_TRIMMER);

//                    TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), Constant.REQUEST_VIDEO_TRIMMER);
                }


            } else if (requestCode == Constant.REQUEST_VIDEO_TRIMMER) {
                DialogUtils.showDialog(this, "", true);
                Logger.i("contest video ", "requestCode == Constant.REQUEST_VIDEO_TRIMMER ");
                if (selectedMediaNewSelf != null && selectedMediaNewSelf.size() > 0) {

                    selectedMediaNewSelf.get(0).setPath(data.getStringExtra(Constant.VIDEO_PATH));

                    selectedMedia.clear();
                    selectedMedia.addAll(selectedMediaNewSelf);
                    for (BaseMedia media : selectedMedia) {
                        Logger.i("Publish", "onActivityResult selectedMedia = " + media.getPath());
                    }

                    mediaAdapter.setData(selectedMedia, isPhoto);
                    btSend.setEnabled(!selectedMedia.isEmpty());

                }


            }


        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //屏蔽掉换行健
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            ToastGlobal.showShort("不支持换行符");
            return true;
        }
        return false;
    }

    private class OnTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            Logger.i("beforeTextChanged : " + s + "");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            byte[] bytes = s.toString().getBytes();
            tvContentCount.setText(String.valueOf(s.toString().length()));
            tvContentCount.setTextColor(s.length() > MAX_TEXT_COUNT ? getResources()
                    .getColor(R.color.main_red) : getResources().getColor(R.color.c9));
            if (TextUtils.equals("@", s.toString().substring(start, start + count))) {
//                ToastGlobal.showShortConsecutive("跳到我关注人");

                UiManager.activityJumpForResult(PublishDynamicActivity.this, new Bundle(), SelectedUserActivity.class, REQUEST_USER);
            }

//            Logger.i("onTextChanged : " + s + " start " + start + " before " + before + " count " + count + " sub " + );
        }

        @Override
        public void afterTextChanged(Editable s) {
//            Logger.i("afterTextChanged : " + s + "");
        }
    }
}

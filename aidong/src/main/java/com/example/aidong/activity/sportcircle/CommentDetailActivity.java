package com.example.aidong.activity.sportcircle;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.BaseApp;
import com.example.aidong.BaseUrlLink;
import com.example.aidong.R;
import com.example.aidong.activity.media.ImageShowerActivity;
import com.example.aidong.activity.media.VideoPlayerActivity;
import com.example.aidong.activity.mine.account.LoginActivity;
import com.example.aidong.adapter.DynamicPhotoGridAdapter;
import com.example.aidong.adapter.ExpressionAdapter;
import com.example.aidong.adapter.ExpressionPagerAdapter;
import com.example.aidong.adapter.HorizontalListAdapterLikeUser;
import com.example.aidong.adapter.ListAdapterCommentDetail;
import com.example.aidong.common.Constant;
import com.example.aidong.common.MXLog;
import com.example.aidong.common.UrlLink;
import com.example.aidong.http.HttpConfig;
import com.example.aidong.model.AttributeDynamics;
import com.example.aidong.model.AttributeDynamics.LikeUser;
import com.example.aidong.model.AttributeFilm;
import com.example.aidong.model.AttributeFindDynamics;
import com.example.aidong.model.AttributeImages;
import com.example.aidong.model.AttributeVideo;
import com.example.aidong.model.Comment;
import com.example.aidong.model.Dynamic;
import com.example.aidong.model.UserCoach;
import com.example.aidong.model.result.CommentsResult;
import com.example.aidong.model.result.DynamicDetailResult;
import com.example.aidong.model.result.MsgResult;
import com.example.aidong.utils.ActivityTool;
import com.example.aidong.utils.MyDbUtils;
import com.example.aidong.utils.SmileUtils;
import com.example.aidong.utils.Utils;
import com.example.aidong.view.CircleImageView;
import com.example.aidong.view.ExpandGridView;
import com.example.aidong.view.HorizontalListView;
import com.example.aidong.view.PasteEditText;
import com.example.aidong.view.PhotoGridView;
import com.example.aidong.view.SquareRelativeLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class CommentDetailActivity extends BaseActivity implements
        IHttpCallback, OnRefreshListener2<ListView> {
    // 换性别
    String TAG = "CommentDetailActivity";

    ArrayList<Comment> array = new ArrayList<Comment>();
    PopupWindow window = null;
    TextView mTitleView;
    TextView mTitleRightView;
    ImageButton mBtnTopLeft;

    ListView mListView;
    ListAdapterCommentDetail mListAdapterCommentDetail;
    ImageView mimg_layout_comment_detail;

    LinearLayout more;
    protected Intent intent;
    private ImageView iv_emoticons_checked;
    private ImageView userIcon;
    private RelativeLayout edittext_layout;
    private LinearLayout emojiIconContainer;
    private PasteEditText mEditTextContent;

    private InputMethodManager manager;
    private ViewPager expressionViewpager;
    // private PowerManager.WakeLock wakeLock;
    private List<String> reslist;
    boolean showDelButton;
    private AttributeDynamics attributeDynamics = new AttributeDynamics();
    private int mPage = 1;
    private int goodNo = 0;
    private String s;

    protected DisplayImageOptions options;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    //	private RelativeLayout btn_share;
    private int page = 1;
    private static final int COMMENTS_CODE = 0;
    private static final int REPORT = 1;
    private static final int POSTCOMMENTS_CODE = 2;
    private static final int ZANPERSON = 3;
    private static final int DELETE = 4;

    private static final int COMMENTS_CODE_TWO = 5;

    protected static final int ZAN = 6;

    private static final int DYNAMIC_DETAILE = 7;

    String replyPrdfix = "";

    /**
     * 赞按钮
     */
    private RelativeLayout layoutbtnGood;
    /**
     * 赞的图标
     */
    private ImageView btn_zan;
    /**
     * 赞的数量
     */
    private TextView zan_num;

    private View head_view;


    private RelativeLayout layout_like_user;

    private HorizontalListAdapterLikeUser adapter_like_user;

    private ArrayList<AttributeDynamics.LikeUser> like_user;

    private View foot_view;


    /**
     * 该值绝对不等于0
     */
    private int id_dynamic;

    private Dynamic dynamic;

    private int mxid;

    private int width_big_img;
    private int width_small_img;

    protected boolean flag = true;

    private CircleImageView img_head_portrait;

    private TextView usernameView;

    private TextView timeView;

    private RelativeLayout mBtnComment;

    private ImageView identityView;

    private TextView txtSubjectConentView;

    private LinearLayout layout_commment_content;

    private SquareRelativeLayout videoLayout;

    private ImageView videoCover;

    private ImageButton videoPlayBtn;

    private RelativeLayout btnGood_rel_share;

    private PhotoGridView girdview_main;

    private GridView girdview_below;

    private RelativeLayout layout_three_imgs;

    private ImageView three_img_first;

    private ImageView three_img_second;

    private ImageView three_img_third;

    private HorizontalListView list_horizontal;

    private RelativeLayout rel_subject_conent;

    private TextView btnComment;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        initData();
    }

    protected void setupView() {
        setContentView(R.layout.comment_detail_layout);
        initTop(getResources().getString(R.string.commentDetail), true,
                getResources().getString(R.string.report));

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_picture)
                .showImageForEmptyUri(R.drawable.icon_picture)
                .showImageOnFail(R.drawable.icon_picture).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        //如果传过来的是AttributeFindDynamics，就取这个
        AttributeFindDynamics localDynamic = (AttributeFindDynamics) getIntent()
                .getSerializableExtra("localDynamic");
        //如果传过来的是Dynamic，就取这个
        dynamic = (Dynamic) getIntent().getSerializableExtra("dynamic");
        //如果传过来的是动态id，就取这个，这三个值传过过来任意一个都可以
        id_dynamic = getIntent().getIntExtra("id", -1);

        //把AttributeFindDynamics转换为Dynamic
        if (localDynamic != null) {
            dynamic = new Dynamic();
            AttributeDynamics attributDynamic = localDynamic.getDynamic();
            dynamic.setComments(attributDynamic.getComments());
            dynamic.setContent(attributDynamic.getContent());
            dynamic.setCreated(attributDynamic.getCreated());
            dynamic.setFilm(attributDynamic.getFilm());
            dynamic.setId(attributDynamic.getNo());
            dynamic.setImage(attributDynamic.getImages());
            dynamic.setLike_user(attributDynamic.getLike_user());
            dynamic.setLikes(attributDynamic.getLikes());
            dynamic.setPublisher(localDynamic.getUser());
        }
        initHeadView();
        //如果传过来的是一个完整的动态类，直接对头部赋值
        if (dynamic != null) {
            mxid = dynamic.getPublisher().getMxid();
            id_dynamic = dynamic.getId();
            setHeadViewData(dynamic);
        }

        if (BaseApp.mInstance.isLogin()) {
            if (mxid == BaseApp.mInstance.getUser()
                    .getMxid()) {
                initTop(getResources().getString(R.string.commentDetail), true,
                        getResources().getString(R.string.delete));
            }
        }
        //如果传过来只有动态id，调接口对头部赋值
        if (dynamic == null) {
            addTask(this, new IHttpTask(UrlLink.DYNAMIC_DETAIL, paramsDynamic(id_dynamic),
                    DynamicDetailResult.class), HttpConfig.GET, DYNAMIC_DETAILE);
        }

        initView();
        setViewClick();


    }

    private List<BasicNameValuePair> paramsDynamic(int id_dynamic2) {
        ArrayList<BasicNameValuePair> prirs = new ArrayList<BasicNameValuePair>();
        prirs.add(new BasicNameValuePair("id", "" + id_dynamic2));
        return prirs;
    }

    private void initHeadView() {

        adapter_like_user = new HorizontalListAdapterLikeUser(this);
        width_big_img = ((Utils.getScreenWidth(this) * 2) - Utils.dip2px(this, 6)) / 3;
        width_small_img = (Utils.getScreenWidth(this) - Utils.dip2px(this, 9)) / 3;
        head_view = View.inflate(this, R.layout.head_dynamic_detail,
                null);
        img_head_portrait = (CircleImageView) head_view
                .findViewById(R.id.imgIconView);
        usernameView = (TextView) head_view
                .findViewById(R.id.txtUsername);
        timeView = (TextView) head_view.findViewById(R.id.txtTime);

        mBtnComment = (RelativeLayout) head_view
                .findViewById(R.id.btnComment_rel);
        btn_zan = (ImageView) head_view
                .findViewById(R.id.btnGood_rel_zan_img);

        identityView = (ImageView) head_view
                .findViewById(R.id.imgTeacher);
        txtSubjectConentView = (TextView) head_view
                .findViewById(R.id.txtSubjectConent);
        layoutbtnGood = (RelativeLayout) head_view
                .findViewById(R.id.btnGood_rel_zan);
        zan_num = (TextView) head_view
                .findViewById(R.id.btnGood_rel_zan_txt_data);
        layout_commment_content = (LinearLayout) head_view
                .findViewById(R.id.layout_commment_content);

        videoLayout = (SquareRelativeLayout) head_view
                .findViewById(R.id.videoLayout);
        videoCover = (ImageView) head_view
                .findViewById(R.id.video_image);
        videoPlayBtn = (ImageButton) head_view
                .findViewById(R.id.video_play_btn);

        btnGood_rel_share = (RelativeLayout) head_view
                .findViewById(R.id.btnGood_rel_share);


        girdview_main = (PhotoGridView) head_view
                .findViewById(R.id.girdview_main);
        girdview_below = (GridView) head_view
                .findViewById(R.id.girdview_below);

        layout_three_imgs = (RelativeLayout) head_view
                .findViewById(R.id.layout_three_imgs);
        three_img_first = (ImageView) head_view
                .findViewById(R.id.three_img_first);
        three_img_second = (ImageView) head_view
                .findViewById(R.id.three_img_second);
        three_img_third = (ImageView) head_view
                .findViewById(R.id.three_img_third);
        list_horizontal = (HorizontalListView) head_view
                .findViewById(R.id.list_horizontal);
        layout_like_user = (RelativeLayout) head_view
                .findViewById(R.id.layout_like_user);
        rel_subject_conent = (RelativeLayout) head_view
                .findViewById(R.id.rel_subject_conent);
        btnComment = (TextView) head_view
                .findViewById(R.id.btnComment);
        // 设置头部数据
        layout_commment_content.setVisibility(View.GONE);


    }

    public void showBigImage(ArrayList<AttributeImages> imageArray,
                             int currentPosition) {
        Intent i = new Intent(this, ImageShowerActivity.class);
        i.putParcelableArrayListExtra(Constant.BUNDLE_BIGIMAGEITEM,
                imageArray);
        i.putExtra(Constant.BUNDLE_BIGIMAGEITEM_INDEX, currentPosition);
        startActivity(i);
    }

    private void setHeadViewData(final Dynamic dynamic) {


        final UserCoach user = dynamic.getPublisher();

        if (user != null) {
            ArrayList<Integer> tags = user.getTags();
            usernameView.setText(user.getName());
            // 设置头像跳转
            img_head_portrait.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityTool.startShowActivity(CommentDetailActivity.this,
                            user);
                }
            });
            imageLoader.displayImage(user.getAvatar(), img_head_portrait, options);

            if (tags.get(Constant.ID_COACH) > 0) {
                identityView.setVisibility(View.VISIBLE);
            }
        }

        if (dynamic != null) {
            // 设置时间
            long createTime = dynamic.getCreated();
            long currentTime = System.currentTimeMillis() / 1000;
            long during = currentTime - createTime;
            String timestr = null;
            if (during < 3600) {
                if ((during / 60) == 0) {
                    timestr = "刚刚";
                } else {
                    timestr = String.valueOf((during / 60))
                            + getResources().getString(
                            R.string.s_minute);
                }
            } else if (during < 3600 * 24) {
                timestr = String.valueOf((during / 3600))
                        + getResources().getString(R.string.s_hour);
            } else if (during < 3600 * 24 * 7) {
                timestr = String.valueOf((during / (3600 * 24)))
                        + getResources().getString(R.string.s_day);
            } else if (during < 3600 * 24 * 30) {
                timestr = String.valueOf((during / (3600 * 24 * 7)))
                        + getResources().getString(R.string.s_week);
            } else if (during < 3600 * 24 * 365) {
                timestr = String.valueOf((during / (3600 * 24 * 30)))
                        + getResources().getString(R.string.s_month);
            } else {
                timestr = String.valueOf((during / (3600 * 24 * 365)))
                        + getResources().getString(R.string.s_year);
            }
            timeView.setText(timestr.toString());


            txtSubjectConentView.setText(dynamic.getContent());


            if (txtSubjectConentView.getText().toString().trim().equals("")) {
                txtSubjectConentView.setVisibility(View.GONE);
                rel_subject_conent.setVisibility(View.GONE);
            }

            // 设置赞数据

            if (BaseApp.mInstance.isLogin()) {
                int goodNo = dynamic.getId();
                if (MyDbUtils.isZan("" + goodNo)) {
                    btn_zan.setImageResource(R.drawable.btn_praise_pressed);

                }
            }
            if (dynamic.getComments() != null) {
                int commentCount = dynamic.getComments().getCount();
                // 设置评论数量
                btnComment.setText("" + commentCount);
            }

            long likes = dynamic.getLikes();
            final ArrayList<AttributeImages> images = dynamic.getImage();
            AttributeFilm film = dynamic.getFilm();
            //			ArrayList<AttributeCommentItem> itemArray = dynamic.getComments()
            //					.getItem();
            //			Log.e(TAG, "itemArray = " + itemArray);


            // 设置赞数量
            zan_num.setText(String.valueOf(likes));
            // 赞点击
            layoutbtnGood.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (BaseApp.mInstance.isLogin()) {

                        int goodNo = dynamic.getId();
                        MXLog.out("no---------------:" + goodNo);
                        if (!MyDbUtils.isZan("" + goodNo)) {
                            layoutbtnGood.setClickable(false);
                            // 刷新赞的人列表，并等下加
                            addTask(CommentDetailActivity.this, new IHttpTask(
                                    UrlLink.DYNAMICZAN_URL,
                                    initParamsZan(String.valueOf(goodNo)),
                                    MsgResult.class), HttpConfig.POST, ZAN);
                        }
                    } else {
                        Intent i = new Intent(CommentDetailActivity.this,
                                LoginActivity.class);
                        startActivity(i);
                    }
                }
            });
            // 分享按钮点击
            btnGood_rel_share.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    StringBuffer buffer = new StringBuffer();
                    // 正式平台 http://www.e-mxing.com/share/
                    buffer.append(BaseUrlLink.SHARE_URL);
                    buffer.append(dynamic.getId());
                    buffer.append("/dynamics");
                    String titleUrl = buffer.toString();
                    //                    oks.setTitleUrl(titleUrl);
                    //                    if (BaseApp.mInstance.isLogin()) {
                    //                        oks.setText("我的美型号"
                    //                                + BaseApp.mInstance.getUser().getMxid()
                    //                                + "这里都是型男美女“小鲜肉”，全民老公vs梦中女神，速速围观" + titleUrl);
                    //                    } else {
                    //                        oks.setText("这里都是型男美女“小鲜肉”，全民老公vs梦中女神，速速围观" + titleUrl);
                    //                    }
                    //                    oks.setUrl(titleUrl);
                    //                    oks.setTheme(OnekeyShareTheme.CLASSIC);
                    //                    oks.show(CommentDetailActivity.this);
                    //                    oks.setTitle(titleUrl);
                    //                    if (dynamic.getContent() == null
                    //                            || dynamic.getContent().intern().equals("")) {
                    //                        oks.setTitle("最近用美型App，国内首家健身社交App");
                    //                    } else {
                    //                        if (dynamic.getContent().length() > 30) {
                    //                            oks.setTitle(dynamic.getContent().substring(0, 30));
                    //                        } else {
                    //                            oks.setTitle(dynamic.getContent());
                    //                        }
                    //
                    //                    }

                    //                    if (dynamic.getCover() != null
                    //                            && dynamic.getCover().size() > 0) {
                    //                        oks.setImageUrl(dynamic.getCover().get(0).getUrl());
                    //                    } else if (dynamic.getFilm() != null) {
                    //                        oks.setImageUrl(dynamic.getFilm().getCover());
                    //
                    //                    }

                }
            });

            // 视频数据
            if (film != null) {
                if (!TextUtils.isEmpty(film.getCover())) {
                    videoLayout.setVisibility(View.VISIBLE);
                    int color = getResources().getColor(
                            R.color.color_black);
                    videoCover.setBackgroundColor(color);
                    imageLoader.displayImage(film.getCover(), videoCover,
                            options);

                    videoPlayBtn.setVisibility(View.VISIBLE);
                    videoCover.setVisibility(View.VISIBLE);

                    videoPlayBtn.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            AttributeVideo mvideo = new AttributeVideo();
                            mvideo.setNo(dynamic.getId());
                            mvideo.setContent(dynamic.getContent());
                            mvideo.setFilm(dynamic.getFilm());

                            Intent i = new Intent(CommentDetailActivity.this,
                                    VideoPlayerActivity.class);
                            i.putExtra(Constant.BUNDLE_VIDEO_URL, mvideo
                                    .getFilm().getFilm());
                            i.putExtra("videofilm", mvideo);
                            startActivity(i);

                        }
                    });

                }
            }


            // 图片显示
            if (images != null && images.size() > 0) {
                switch (images.size()) {
                    case 1:

                        girdview_main.setVisibility(View.VISIBLE);
                        girdview_main.setNumColumns(1);
                        girdview_main
                                .setAdapter(new DynamicPhotoGridAdapter(
                                        this, images));

                        break;
                    case 2:

                        girdview_main.setVisibility(View.VISIBLE);
                        girdview_main.setNumColumns(2);
                        girdview_main
                                .setAdapter(new DynamicPhotoGridAdapter(
                                        this, images));

                        break;
                    case 3:


                        layout_three_imgs
                                .setVisibility(View.VISIBLE);

                        LayoutParams params1 = three_img_first.getLayoutParams();
                        params1.width = width_big_img;
                        params1.height = width_big_img;
                        three_img_first.setLayoutParams(params1);
                        LayoutParams params2 = three_img_second.getLayoutParams();
                        params2.width = width_small_img;
                        params2.height = width_small_img;
                        three_img_second.setLayoutParams(params2);
                        LayoutParams params3 = three_img_third.getLayoutParams();
                        params3.width = width_small_img;
                        params3.height = width_small_img;
                        three_img_third.setLayoutParams(params3);


                        imageLoader.displayImage(images.get(0).getUrl(width_big_img, width_big_img),
                                three_img_first, options);
                        imageLoader.displayImage(images.get(1).getUrl(width_small_img, width_small_img),
                                three_img_second, options);
                        imageLoader.displayImage(images.get(2).getUrl(width_small_img, width_small_img),
                                three_img_third, options);
                        three_img_first
                                .setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        showBigImage(images, 0);
                                    }
                                });
                        three_img_second
                                .setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        showBigImage(images, 1);
                                    }
                                });
                        three_img_third
                                .setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        showBigImage(images, 2);
                                    }
                                });

                        break;
                    case 4:

                        girdview_main.setVisibility(View.VISIBLE);
                        girdview_main.setNumColumns(2);
                        girdview_main
                                .setAdapter(new DynamicPhotoGridAdapter(
                                        this, images));

                        break;
                    case 5:

                        girdview_main.setVisibility(View.VISIBLE);
                        girdview_below.setVisibility(View.VISIBLE);
                        girdview_main.setNumColumns(2);
                        girdview_main
                                .setAdapter(new DynamicPhotoGridAdapter(
                                        this, images.subList(0, 2)));
                        girdview_below.setNumColumns(3);
                        girdview_below
                                .setAdapter(new DynamicPhotoGridAdapter(
                                        this, images.subList(2, 5)));
                        girdview_below
                                .setOnItemClickListener(new OnItemClickListener() {

                                    @Override
                                    public void onItemClick(
                                            AdapterView<?> parent, View view,
                                            int position, long id) {
                                        showBigImage(images, position + 2);
                                    }
                                });

                        break;
                    case 6:

                        girdview_main.setVisibility(View.VISIBLE);
                        girdview_main.setNumColumns(3);
                        girdview_main
                                .setAdapter(new DynamicPhotoGridAdapter(
                                        this, images));

                        break;

                    default:
                        break;
                }

                girdview_main
                        .setOnItemClickListener(new OnItemClickListener() {

                            @Override
                            public void onItemClick(
                                    AdapterView<?> parent, View view,
                                    int position, long id) {
                                showBigImage(images, position);
                            }
                        });
            }

            like_user = dynamic.getLike_user();
            final int dynamicId = dynamic.getId();
            list_horizontal.setAdapter(adapter_like_user);
            list_horizontal.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position, long id) {
                    if (position < like_user.size()) {
                        ActivityTool
                                .startShowActivity(CommentDetailActivity.this, like_user.get(position).getUser());
                    } else {
                        Intent intent = new Intent(CommentDetailActivity.this, UserWhoClickLikeActivity.class);
                        intent.putExtra("id", dynamicId);
                        startActivity(intent);

                    }

                }
            });

            if (like_user != null) {
                if (like_user.size() > 0) {
                    layout_like_user.setVisibility(View.VISIBLE);
                    //					adapter_like_user = new HorizontalListAdapterLikeUser(
                    //							context, like_user);
                    //					list_horizontal
                    //							.setAdapter(adapter_like_user);
                    adapter_like_user.freshData(like_user);
                    //					list_horizontal.setOnItemClickListener(new OnItemClickListener() {
                    //
                    //						@Override
                    //						public void onItemClick(AdapterView<?> parent,
                    //								View view, int position, long id) {
                    //							if (position < like_user.size()) {
                    //								ActivityTool
                    //								.startShowActivity(context, like_user.get(position).getUser());
                    //							}
                    //							else{
                    //								Intent intent = new Intent(context, UserWhoClickLikeActivity.class);
                    //								intent.putExtra("id", dynamicId);
                    //								context.startActivity(intent);
                    ////								Toast.makeText(context, "跳到like我的人界面", 0).show();
                    //							}
                    //
                    //						}
                    //					});
                } else {
                    layout_like_user.setVisibility(View.GONE);
                }
            } else {
                layout_like_user.setVisibility(View.GONE);
            }

        }
    }

    private void initView() {
        intent = new Intent();
        initFoorView();

        userIcon = (ImageView) findViewById(R.id.userIcon);
        iv_emoticons_checked = (ImageView) findViewById(R.id.iv_emoticons_checked);
        emojiIconContainer = (LinearLayout) findViewById(R.id.ll_face_container);
        iv_emoticons_checked.setVisibility(View.VISIBLE);
        emojiIconContainer.setVisibility(View.GONE);
        expressionViewpager = (ViewPager) findViewById(R.id.vPager);
        mEditTextContent = (PasteEditText) findViewById(R.id.et_sendmessage);
        edittext_layout = (RelativeLayout) findViewById(R.id.edittext_layout);
        edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_normal);
        more = (LinearLayout) findViewById(R.id.more);


        mListView = (ListView) findViewById(R.id.list);
        mListView.addHeaderView(head_view);
        mListAdapterCommentDetail = new ListAdapterCommentDetail(this);
        mListView.setAdapter(mListAdapterCommentDetail);
        mListView.setOnItemClickListener(
                new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {

                        Log.i("comment", "适配器总个数：" + mListAdapterCommentDetail.getCount() + ",脚个数： " + mListView.getFooterViewsCount());


                        if (mEditTextContent.getText() == null
                                || mEditTextContent.getText().toString()
                                .equals("")) {
                            if (arg2 == 0) {
                                replyPrdfix = "";
                                mEditTextContent.setHint("");

                                return;
                            }
                            if (mListView.getFooterViewsCount() > 0 && arg2 == mListAdapterCommentDetail.getCount() + 1) {
                                return;
                            }
                            replyPrdfix = "回复 "
                                    + mListAdapterCommentDetail
                                    .getItem(arg2 - 1).getUser()
                                    .getName() + ":";
                            mEditTextContent.setHint(replyPrdfix);
                        }
                    }
                });


        mListView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                System.out.println("总个数：" + view.getCount() + ", 最后一个可见条目：" +
                        view.getLastVisiblePosition());
                if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                    page++;
                    addTask(CommentDetailActivity.this, new IHttpTask(UrlLink.COMMENTS_URL,
                                    commentsParamsinit(page), CommentsResult.class),
                            HttpConfig.GET, COMMENTS_CODE_TWO);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {


            }
        });
        mListAdapterCommentDetail
                .setAvatarClickListener(mOnAvatarClickListener);


        if (BaseApp.mInstance.isLogin()) {
            imageLoader.displayImage(BaseApp.mInstance.getUser().getAvatar(),
                    userIcon, options);
        } else {
            userIcon.setImageResource(R.drawable.xin_geren);
        }

        mEditTextContent.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edittext_layout
                            .setBackgroundResource(R.drawable.input_bar_bg_active);
                } else {
                    edittext_layout
                            .setBackgroundResource(R.drawable.input_bar_bg_normal);
                }
            }
        });

        // 表情list
        reslist = getExpressionRes(35);
        // 初始化表情viewpager
        List<View> views = new ArrayList<View>();
        View gv1 = getGridChildView(1);
        View gv2 = getGridChildView(2);
        views.add(gv1);
        views.add(gv2);
        expressionViewpager.setAdapter(new ExpressionPagerAdapter(views));
        edittext_layout.requestFocus();

        // clipboard = (ClipboardManager)
        // getSystemService(Context.CLIPBOARD_SERVICE);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // getWindow().setSoftInputMode(
        // WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE))
        // .newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "demo");

        goodNo = id_dynamic;

        //		if (MyDbUtils.isZan("" + goodNo)) {
        //			btnGood_rel_zan_img.setImageResource(R.drawable.zan);
        //		} else {
        //			btnGood_rel_zan_img.setImageResource(R.drawable.unzan);
        //		}
        //		btnGood_rel_zan_txt_dataView.setText(""
        //				+ localDynamic.getDynamic().getLikes());

    }

    private void initFoorView() {
        foot_view = View.inflate(this, R.layout.foot_dynamic_detail, null);

    }

    @Override
    protected void rightTxtOnClick() {
        super.rightTxtOnClick();
        if (BaseApp.mInstance.isLogin()
                && mxid == BaseApp.mInstance
                .getUser().getMxid()) {
            Builder builder = new Builder(
                    CommentDetailActivity.this);
            builder.setMessage("是否确定删除动态？");
            builder.setTitle("删除动态");
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialo0g, int which) {
                            setLoadingDialog(R.string.tip_sending);
                            addTask(CommentDetailActivity.this,
                                    new IHttpTask(
                                            UrlLink.DELETE_DYNAMIC_URL
                                                    + id_dynamic,
                                            new ArrayList<BasicNameValuePair>(),
                                            MsgResult.class),
                                    HttpConfig.DELETE, DELETE);
                        }

                    });
            builder.setNegativeButton("取消", null);
            builder.show();
        } else {
            showReportMenu();
        }

    }

    public void stoploadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void setLoadingDialog(String string) {
        dialog = ProgressDialog.show(this, "提示", string);
    }

    public void setLoadingDialog(int string) {
        dialog = ProgressDialog.show(this, "提示", getResources().getString(string));
    }


    private OnClickListener mOnAvatarClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Object o = v.getTag();
            if (o != null) {
                if (o instanceof UserCoach) {
                    UserCoach u = (UserCoach) v.getTag();
                    ActivityTool.startShowActivity(CommentDetailActivity.this, u);
                }
            }
        }
    };


    private View getGridChildView(int i) {
        View view = View.inflate(this, R.layout.expression_gridview, null);
        ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);
        List<String> list = new ArrayList<String>();
        if (i == 1) {
            List<String> list1 = reslist.subList(0, 20);
            list.addAll(list1);
        } else if (i == 2) {
            list.addAll(reslist.subList(20, reslist.size()));
        }
        list.add("delete_expression");
        final ExpressionAdapter expressionAdapter = new ExpressionAdapter(this,
                1, list);
        gv.setAdapter(expressionAdapter);
        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String filename = expressionAdapter.getItem(position);
                try {
                    if (filename != "delete_expression") {
                        Class clz = Class
                                .forName("com.jianshi.mx_c_app.utils.SmileUtils");
                        Field field = clz.getField(filename);
                        mEditTextContent.append(SmileUtils.getSmiledText(
                                CommentDetailActivity.this,
                                (String) field.get(null)));
                    } else {
                        if (!TextUtils.isEmpty(mEditTextContent.getText())) {
                            int selectionStart = mEditTextContent
                                    .getSelectionStart();
                            if (selectionStart > 0) {
                                String body = mEditTextContent.getText()
                                        .toString();
                                String tempStr = body.substring(0,
                                        selectionStart);
                                int i = tempStr.lastIndexOf("[");
                                if (i != -1) {
                                    CharSequence cs = tempStr.substring(i,
                                            selectionStart);
                                    if (SmileUtils.containsKey(cs.toString()))
                                        mEditTextContent.getEditableText()
                                                .delete(i, selectionStart);
                                    else
                                        mEditTextContent.getEditableText()
                                                .delete(selectionStart - 1,
                                                        selectionStart);
                                } else {
                                    mEditTextContent.getEditableText().delete(
                                            selectionStart - 1, selectionStart);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    public List<String> getExpressionRes(int getSum) {
        List<String> reslist = new ArrayList<String>();
        for (int x = 1; x <= getSum; x++) {
            String filename = "ee_" + x;
            reslist.add(filename);
        }
        return reslist;
    }

    //	@SuppressLint("NewApi")
    //	private void freshDynaicView() {
    //		long createTime = localDynamic.getDynamic().getCreated();
    //		long currentTime = System.currentTimeMillis() / 1000;
    //		long during = currentTime - createTime;
    //		String timestr = null;
    //		if (during < 3600) {
    //			timestr = String.valueOf((during / 60))
    //					+ getResources().getString(R.string.s_minute);
    //		} else if (during < 3600 * 24) {
    //			timestr = String.valueOf((during / 3600))
    //					+ getResources().getString(R.string.s_hour);
    //		} else if (during < 3600 * 24 * 7) {
    //			timestr = String.valueOf((during / (3600 * 24)))
    //					+ getResources().getString(R.string.s_day);
    //		} else if (during < 3600 * 24 * 30) {
    //			timestr = String.valueOf((during / (3600 * 24 * 7)))
    //					+ getResources().getString(R.string.s_week);
    //		} else if (during < 3600 * 24 * 365) {
    //			timestr = String.valueOf((during / (3600 * 24 * 30)))
    //					+ getResources().getString(R.string.s_month);
    //		} else {
    //			timestr = String.valueOf((during / (3600 * 24 * 365)))
    //					+ getResources().getString(R.string.s_year);
    //		}
    //		String time[] = ActivityTool.getTimeCalShow(this, localDynamic
    //				.getDynamic().getCreated());
    ////		txtTimeView.setText(timestr);
    //		// Gender g =
    //		// ActivityTool.getGender(localDynamic.getUser().getGender());
    //		// img_comment_detailsView.setImageResource(g.drawableRes);
    //
    ////		txtAgeView
    ////				.setText(String.valueOf(localDynamic.getUser().getTrue_age()));
    ////		if (localDynamic.getUser().getTrue_age() < 1) {
    ////			txtAgeView.setVisibility(View.GONE);
    ////		}
    //		// else{
    //		// txtAgeView.setVisibility(View.VISIBLE);
    //		// }
    //		// layout2.setBackgroundResource(g.drawableRes);
    ////		if (localDynamic.getUser().getGender() == 0) {
    ////			// layout2.setBackground(getResources()
    ////			// .getDrawable(R.drawable.sex_nan));
    ////			Utils.setLayoutBackgroud(layout2,
    ////					getResources().getDrawable(R.drawable.sex_nan));
    ////			img_sex.setImageResource(R.drawable.sex_nan_fuhao);
    ////		} else {
    ////			// layout2.setBackground(getResources().getDrawable(R.drawable.sex_nv));
    ////			Utils.setLayoutBackgroud(layout2,
    ////					getResources().getDrawable(R.drawable.sex_nv));
    ////			img_sex.setImageResource(R.drawable.sen_nv_fuhao);
    ////		}
    //		ArrayList<Integer> tags = localDynamic.getUser().getTags();
    //		// Utils.setUserTagView(tags, imgVipView, imgTeacherView, null);
    ////		if (tags.get(Constant.ID_VIP) > 0) {
    ////			imgVipView.setVisibility(View.VISIBLE);
    ////		} else {
    ////			imgVipView.setVisibility(View.GONE);
    ////		}
    ////		if (tags.get(Constant.ID_OFFICAL) > 0) {
    ////			imgOffical.setVisibility(View.VISIBLE);
    ////			layout2.setVisibility(View.GONE);
    ////			txtUsername.setTextColor(context.getResources().getColor(
    ////					R.color.color_black));
    ////		} else {
    ////			imgOffical.setVisibility(View.GONE);
    ////			layout2.setVisibility(View.VISIBLE);
    ////		}
    ////		if (tags.get(Constant.ID_COACH) > 0) {
    ////			imgTeacherView.setVisibility(View.VISIBLE);
    ////			txtUsername.setTextColor(context.getResources().getColor(
    ////					R.color.color_black));
    ////		} else {
    ////			imgTeacherView.setVisibility(View.GONE);
    ////		}
    ////		if (localDynamic.getDynamic().getContent().equals("")) {
    ////			txtSubjectConentView.setHeight(1);
    ////		} else {
    ////			txtSubjectConentView
    ////					.setText(localDynamic.getDynamic().getContent());
    ////		}
    ////
    ////		btnGood_rel_zan_txt_dataView.setText(String.valueOf(localDynamic
    ////				.getDynamic().getLikes()));
    //
    //		ArrayList<AttributeImages> images = localDynamic.getDynamic()
    //				.getImages();
    //		AttributeFilm film = localDynamic.getDynamic().getFilm();
    //
    ////		if (film != null) {
    ////			videoLayout.setVisibility(View.VISIBLE);
    ////			int color = context.getResources().getColor(R.color.color_black);
    ////			videoCover.setBackgroundColor(color);
    ////			imageLoader.displayImage(film.getCover(), videoCover, options);
    ////
    ////			videoPlayBtn.setVisibility(View.VISIBLE);
    ////			videoCover.setVisibility(View.VISIBLE);
    ////			videoPlayBtn.setTag(film);
    ////			videoPlayBtn.setOnClickListener(new OnClickListener() {
    ////				@Override
    ////				public void onClick(View v) {
    ////					// mVideoView.setVideoPath(film.getFilm().getFilm());
    ////					// isPlaying = true;
    ////					// freshVideo();
    ////
    ////					AttributeVideo mvideo = new AttributeVideo();
    ////					mvideo.setFilm(localDynamic.getDynamic().getFilm());
    ////					intent.setClass(getApplicationContext(),
    ////							VideoPlayerActivity.class);
    ////					intent.putExtra(BUNDLE_VIDEO_URL, mvideo.getFilm()
    ////							.getFilm());
    ////					intent.putExtra("videofilm", mvideo);
    ////					startActivity(intent);
    ////				}
    ////			});
    ////			// if (mVideoClickListener != null) {
    ////			// videoPlayBtn
    ////			// .setOnClickListener(mVideoClickListener);
    ////			// }
    ////		} else {
    ////			videoLayout.setVisibility(View.GONE);
    ////		}
    //
    ////		if (images != null && images.size() > 0) {
    ////			PhotoGridAdpater _PhotoGridAdpater = new PhotoGridAdpater(context,
    ////					images, gridPhotoView);
    ////			gridPhotoView.setAdapter(_PhotoGridAdpater);
    ////			ivPhotoView.setVisibility(View.GONE);
    ////			gridPhotoView.setVisibility(View.VISIBLE);
    ////			gridPhotoView.setTag(images);
    ////			gridPhotoView.setOnItemClickListener(new OnItemClickListener() {
    ////				@Override
    ////				public void onItemClick(AdapterView<?> adapterView, View v,
    ////						int position, long arg3) {
    ////					ArrayList<AttributeImages> imageArray = (ArrayList<AttributeImages>) adapterView
    ////							.getTag();
    ////					showBigImage(imageArray, position);
    ////				}
    ////			});
    ////
    ////		}
    //		// else if (images != null && images.size() > 0) {
    //		// ivPhotoView.setVisibility(View.VISIBLE);
    //		// gridPhotoView.setVisibility(View.GONE);
    //		// gridPhotoView.setAdapter(null);
    //		// gridPhotoView.setOnItemClickListener(null);
    //		// LayoutParams params = ivPhotoView.getLayoutParams();
    //		// params.height = images.get(0).getHeight();
    //		// params.width = images.get(0).getWidth();
    //		// ivPhotoView.setLayoutParams(params);
    //		// if (imageLoader != null) {
    //		// imageLoader.displayImage(images.get(0).getThumb(), ivPhotoView,
    //		// options);
    //		// }
    //		// ivPhotoView.setTag(images);
    //		// ivPhotoView.setOnClickListener(new OnClickListener() {
    //		//
    //		// @Override
    //		// public void onClick(View arg0) {
    //		// ArrayList<AttributeImages> imageArray = (ArrayList<AttributeImages>)
    //		// arg0
    //		// .getTag();
    //		// showBigImage(imageArray, 0);
    //		// }
    //		// });
    //		// }
    ////		else {
    ////			ivPhotoView.setVisibility(View.GONE);
    ////			gridPhotoView.setVisibility(View.GONE);
    ////			gridPhotoView.setAdapter(null);
    ////			gridPhotoView.setOnItemClickListener(null);
    ////		}
    ////
    ////		imageLoader.displayImage(localDynamic.getUser().getAvatar(),
    ////				imgIconView, options);
    ////		imgIconView.setOnClickListener(new OnClickListener() {
    ////			@Override
    ////			public void onClick(View v) {
    ////				ActivityTool.startShowActivity(CommentDetailActivity.this,
    ////						localDynamic.getUser());
    ////			}
    ////		});
    //	}

    private void setViewClick() {
        mEditTextContent.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return imm.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), 0);
            }
        });
        // mTitleRightView.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // mimg_layout_comment_detail.setVisibility(View.VISIBLE);
        // showReportMenu();
        // }
        // });
        // mBtnTopLeft.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        // finish();
        // }
        // });
        //		btnGood_rel_zanLayout.setOnClickListener(new View.OnClickListener() {
        //			@Override
        //			public void onClick(View v) {
        //				// TODO Auto-generated method stub
        //				if (BaseApp.mInstance.isLogin()) {
        //					MXLog.out("no---------------:" + goodNo);
        //					if (!MyDbUtils.isZan("" + goodNo)) {
        //						MyDbUtils.saveZan("" + goodNo, true);
        //						btnGood_rel_zan_img.setImageResource(R.drawable.zan);
        //						localDynamic.getDynamic().setLikes(
        //								localDynamic.getDynamic().getLikes() + 1);
        //						btnGood_rel_zan_txt_dataView.setText(""
        //								+ localDynamic.getDynamic().getLikes());
        //						addTask(CommentDetailActivity.this, new IHttpTask(
        //								UrlLink.DYNAMICZAN_URL,
        //								paramsinitZanPerson(String.valueOf(goodNo)),
        //								MsgResult.class), HttpConfig.POST, ZANPERSON);
        //
        //					} else {
        //						// showShortToast(getResources().getString(
        //						// R.string.tip_has_good));
        //					}
        //				} else {
        //					Intent i = new Intent(getApplicationContext(),
        //							LoginActivity.class);
        //					startActivity(i);
        //				}
        //
        //			}
        //		});
        //		btnComment_del.setOnClickListener(new OnClickListener() {
        //			@Override
        //			public void onClick(View v) {
        //				// TODO Auto-generated method stub
        //				// setLoadingDialog(R.string.tip_deleting);
        //				// MxHttpClient.httpDynamicDelete(
        //				// String.valueOf(BaseApp.localDynamic.getNo()),
        //				// BaseApp.loginToken);
        //			}
        //		});

        findViewById(R.id.btn_send).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (BaseApp.mInstance.isLogin()) {
                    s = mEditTextContent.getText().toString();
                    if (s == null || s.trim().length() == 0) {
                        ToastUtil.show(getResources().getString(
                                R.string.error_comment_null),CommentDetailActivity.this);
                        return;
                    }
                    // setLoadingDialog(R.string.tip_sending);
                    addTask(CommentDetailActivity.this, new IHttpTask(
                                    UrlLink.COMMENTS_URL, getParams(replyPrdfix + s),
                                    MsgResult.class), HttpConfig.POST,
                            POSTCOMMENTS_CODE);
                } else {
                    intent.setClass(getApplicationContext(),
                            LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        iv_emoticons_checked.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e(TAG, "iv_emoticons_checked onclick show emotion");
                if (more.getVisibility() == View.GONE) {
                    more.setVisibility(View.VISIBLE);
                    emojiIconContainer.setVisibility(View.VISIBLE);
                    hideKeyboard();
                } else {
                    more.setVisibility(View.GONE);
                    emojiIconContainer.setVisibility(View.GONE);
                }

                // if (emojiIconContainer.getVisibility() == View.GONE) {
                // emojiIconContainer.setVisibility(View.VISIBLE);
                // hideKeyboard();
                // } else {
                // emojiIconContainer.setVisibility(View.GONE);
                // }
            }
        });
        // mListView.setOnTouchListener(new OnTouchListener() {
        // @Override
        // public boolean onTouch(View v, MotionEvent event) {
        // hideKeyboard();
        // emojiIconContainer.setVisibility(View.GONE);
        // return false;
        // }
        // });
        mEditTextContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edittext_layout
                        .setBackgroundResource(R.drawable.input_bar_bg_active);
                emojiIconContainer.setVisibility(View.GONE);
                more.setVisibility(View.GONE);
            }
        });
        mEditTextContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edittext_layout
                        .setBackgroundResource(R.drawable.input_bar_bg_active);
                iv_emoticons_checked.setVisibility(View.VISIBLE);
                emojiIconContainer.setVisibility(View.GONE);
            }
        });

    }

    protected void initData() {
        page = 1;
        addTask(this, new IHttpTask(UrlLink.COMMENTS_URL,
                        commentsParamsinit(page), CommentsResult.class),
                HttpConfig.GET, COMMENTS_CODE);
    }

    public List<BasicNameValuePair> commentsParamsinit(int page) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("no", id_dynamic + ""));
        paramsaaa.add(new BasicNameValuePair("page", page + ""));
        return paramsaaa;
    }

    private List<BasicNameValuePair> getParams(String content) {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("no", id_dynamic + ""));
        params.add(new BasicNameValuePair("content", content));

        return params;
    }

    public List<BasicNameValuePair> paramsinitZanPerson(String no) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("no", no));
        return paramsaaa;
    }

    protected List<BasicNameValuePair> initParamsZan(String id) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("no", id));
        return paramsaaa;
    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void showReportMenu() {
        LayoutInflater layoutIn = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutIn.inflate(R.layout.popmenu_report, null);
        Button btn_popmenu_report_1 = (Button) view
                .findViewById(R.id.popmenu_report_1);
        Button btn_popmenu_report_2 = (Button) view
                .findViewById(R.id.popmenu_report_2);
        Button btn_popmenu_report_3 = (Button) view
                .findViewById(R.id.popmenu_report_3);
        Button btn_popmenu_report_4 = (Button) view
                .findViewById(R.id.popmenu_report_4);
        Button btn_popmenu_report_5 = (Button) view
                .findViewById(R.id.popmenu_report_5);
        Button btn_popmenu_report_6 = (Button) view
                .findViewById(R.id.popmenu_report_6);
        btn_popmenu_report_1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                addTask(CommentDetailActivity.this,
                        new IHttpTask(UrlLink.REPORT_URL, paramsinitReport(
                                Constant.REPORT_DYNAMIC,
                                attributeDynamics.getNo(),
                                getResources().getString(
                                        R.string.popmenu_report_1)),
                                MsgResult.class), HttpConfig.POST, REPORT);

            }
        });
        btn_popmenu_report_2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                addTask(CommentDetailActivity.this,
                        new IHttpTask(UrlLink.REPORT_URL, paramsinitReport(
                                Constant.REPORT_DYNAMIC,
                                attributeDynamics.getNo(),
                                getResources().getString(
                                        R.string.popmenu_report_2)),
                                MsgResult.class), HttpConfig.POST, REPORT);

            }
        });
        btn_popmenu_report_3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                addTask(CommentDetailActivity.this,
                        new IHttpTask(UrlLink.REPORT_URL, paramsinitReport(
                                Constant.REPORT_DYNAMIC,
                                attributeDynamics.getNo(),
                                getResources().getString(
                                        R.string.popmenu_report_3)),
                                MsgResult.class), HttpConfig.POST, REPORT);

            }
        });
        btn_popmenu_report_4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                addTask(CommentDetailActivity.this,
                        new IHttpTask(UrlLink.REPORT_URL, paramsinitReport(
                                Constant.REPORT_DYNAMIC,
                                attributeDynamics.getNo(),
                                getResources().getString(
                                        R.string.popmenu_report_4)),
                                MsgResult.class), HttpConfig.POST, REPORT);

            }
        });
        btn_popmenu_report_5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                addTask(CommentDetailActivity.this,
                        new IHttpTask(UrlLink.REPORT_URL, paramsinitReport(
                                Constant.REPORT_DYNAMIC,
                                attributeDynamics.getNo(),
                                getResources().getString(
                                        R.string.popmenu_report_5)),
                                MsgResult.class), HttpConfig.POST, REPORT);

            }
        });
        btn_popmenu_report_6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        if (window == null) {
            window = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, true);
            window.setAnimationStyle(R.style.popuStyle);
            window.setBackgroundDrawable(new BitmapDrawable());
        }
        window.showAtLocation(view.findViewById(R.id.popmenu_report),
                Gravity.BOTTOM, 0, 0);
        window.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // mimg_layout_comment_detail.setVisibility(View.GONE);
            }
        });
    }

    // type类型1-举报人，2-举报动态
    public List<BasicNameValuePair> paramsinitReport(int type, int id,
                                                     String content) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("type", "" + type));
        paramsaaa.add(new BasicNameValuePair("id", "" + id));
        paramsaaa.add(new BasicNameValuePair("content", content));
        return paramsaaa;
    }

    @Override
    public void onGetData(Object data, int requestCode, String response) {
        stoploadingDialog();
        stopLoading();
        switch (requestCode) {
            case COMMENTS_CODE:
                mListView.removeFooterView(foot_view);
                CommentsResult cres = (CommentsResult) data;
                if (cres.getCode() == 1) {
                    array.clear();
                    if (cres.getData().getComments() != null) {
                        array.addAll(cres.getData().getComments());
                        if (cres.getData().getComments().size() == 0) {
                            mListView.addFooterView(foot_view);
                        }
                    }

                    mListAdapterCommentDetail.freshArrayData(array);
                }

                break;
            case COMMENTS_CODE_TWO:
                CommentsResult comments = (CommentsResult) data;
                if (comments.getCode() == 1) {
                    if (comments.getData().getComments() != null && comments.getData().getComments().size() > 0) {
                        array.addAll(comments.getData().getComments());

                        mListAdapterCommentDetail.freshArrayData(array);
                    }
                }
                break;
            case REPORT:
                MsgResult msgResult = (MsgResult) data;
                if (msgResult.getCode() == 1) {
                    ToastUtil.show(getResources()
                            .getString(R.string.report_success),CommentDetailActivity.this);
                }
                break;
            case POSTCOMMENTS_CODE:
                MsgResult mres = (MsgResult) data;
                if (mres.getCode() == 1) {
                    Comment c = new Comment();
                    c.setUser(BaseApp.mInstance.getUser());

                    c.setContent(replyPrdfix
                            + mEditTextContent.getText().toString());
                    long time = System.currentTimeMillis() / 1000;
                    c.setCreated("" + time);
                    mListAdapterCommentDetail.addItem(c);
                    replyPrdfix = "";
                    mEditTextContent.setText("");

                }
                break;
            //		case ZANPERSON:
            //			MsgResult msg = (MsgResult) data;
            //			if (msg.getCode() != 1) {
            //				MyDbUtils.saveVideoZan("" + goodNo, false);
            //				localDynamic.getDynamic().setLikes(
            //						localDynamic.getDynamic().getLikes() - 1);
            //				btnGood_rel_zan_img.setImageResource(R.drawable.zan);
            //				btnGood_rel_zan_txt_dataView.setText(String
            //						.valueOf(localDynamic.getDynamic().getLikes()));
            //			}
            //			break;
            case DELETE:
                MsgResult msgResult2 = (MsgResult) data;
                if (msgResult2.getCode() == 1) {
                    ToastUtil.show(getResources()
                            .getString(R.string.delete_dynamic),CommentDetailActivity.this);
                    finish();
                }
                break;
            case ZAN:

                MsgResult res_zan = (MsgResult) data;
                if (res_zan.getCode() == 1) {
                    MyDbUtils.saveZan("" + goodNo, true);
                    btn_zan.setImageResource(R.drawable.btn_praise_pressed);
                    String num = zan_num.getText().toString().trim();
                    try {
                        int number = Integer.parseInt(num);
                        zan_num.setText("" + (1 + number));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    // 加一个赞的人


                    LikeUser user = new AttributeDynamics().new LikeUser();
                    user.setUser(BaseApp.mInstance.getUser());
                    like_user.add(0, user);

                    layout_like_user.setVisibility(View.VISIBLE);
                    adapter_like_user.freshData(like_user);

                } else {
                    layoutbtnGood.setClickable(true);

                }
                break;
            case DYNAMIC_DETAILE:
                DynamicDetailResult result_dy = (DynamicDetailResult) data;
                if (result_dy.getCode() == 1) {
                    if (result_dy.getData() != null && result_dy.getData().getDynamic() != null) {
                        setHeadViewData(result_dy.getData().getDynamic());
                    }
                }


                break;
            default:
                break;
        }

    }

    @Override
    public void onError(String reason, int requestCode) {
        stopLoading();
        switch (requestCode) {
            //		case ZANPERSON:
            //			MyDbUtils.saveVideoZan("" + goodNo, false);
            //			localDynamic.getDynamic().setLikes(
            //					localDynamic.getDynamic().getLikes() - 1);
            //			btnGood_rel_zan_img.setImageResource(R.drawable.zan);
            //			btnGood_rel_zan_txt_dataView.setText(String.valueOf(localDynamic
            //					.getDynamic().getLikes()));
            //			break;
            case ZAN:
                layoutbtnGood.setClickable(true);
                break;
            default:
                break;
        }

    }

    private void stopLoading() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //				mListView.onRefreshComplete();
            }
        });
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page = 1;
        addTask(this, new IHttpTask(UrlLink.COMMENTS_URL,
                        commentsParamsinit(page), CommentsResult.class),
                HttpConfig.GET, COMMENTS_CODE);

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

        page++;
        addTask(this, new IHttpTask(UrlLink.COMMENTS_URL,
                        commentsParamsinit(page), CommentsResult.class),
                HttpConfig.GET, COMMENTS_CODE_TWO);


    }

}

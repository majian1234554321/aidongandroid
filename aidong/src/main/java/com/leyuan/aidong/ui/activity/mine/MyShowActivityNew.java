package com.leyuan.aidong.ui.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.BaseApp;
import com.leyuan.aidong.utils.common.BaseUrlLink;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.mine.account.LoginActivity;
import com.leyuan.aidong.ui.activity.vedio.media.ImageShowerActivity;
import com.leyuan.aidong.ui.activity.vedio.media.TabPhotoWallActivity;
import com.leyuan.aidong.ui.activity.vedio.media.TabTheIndividualDynaminActivity;
import com.leyuan.aidong.ui.activity.vedio.media.VideoPlayerActivity;
import com.leyuan.aidong.ui.activity.discover.CommentDetailActivity;
import com.leyuan.aidong.adapter.DynamicPhotoGridAdapter;
import com.leyuan.aidong.adapter.MyShowDynamicAdapter;
import com.leyuan.aidong.utils.common.Constant;
import com.leyuan.aidong.utils.common.MXLog;
import com.leyuan.aidong.utils.common.UrlLink;
import com.leyuan.aidong.http.HttpConfig;
import com.leyuan.aidong.utils.interfaces.OnCommentAndLikeClickListenner;
import com.leyuan.aidong.utils.interfaces.OnMoreCommentClickListenner;
import com.leyuan.aidong.entity.model.AttributeDynamics;
import com.leyuan.aidong.entity.model.AttributeFindDynamics;
import com.leyuan.aidong.entity.model.AttributeImages;
import com.leyuan.aidong.entity.model.AttributeVideo;
import com.leyuan.aidong.entity.model.Dynamic;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.entity.model.location.ImageItem;
import com.leyuan.aidong.entity.model.result.DynamicListResult;
import com.leyuan.aidong.entity.model.result.FanProfileResult;
import com.leyuan.aidong.entity.model.result.MsgResult;
import com.leyuan.aidong.entity.model.result.MxPhotosResult;
import com.leyuan.aidong.utils.ActivityTool;
import com.leyuan.aidong.utils.MyDbUtils;
import com.leyuan.aidong.utils.PopupWindowUitl;
import com.leyuan.aidong.utils.Utils;
import com.leyuan.aidong.utils.photo.Bimp;
import com.leyuan.aidong.widget.customview.PhotoGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

//import cn.sharesdk.onekeyshare.OnekeyShare;
//import cn.sharesdk.onekeyshare.OnekeyShareTheme;

public class MyShowActivityNew extends BaseActivity implements IHttpCallback, OnClickListener, OnRefreshListener2<ListView> {
    //	ViewPager viewPager;
    //	private List<View> listViews;
    private ListView mListView;
    MyShowDynamicAdapter adapter;
    private UserCoach user;
    //	private LinearLayout sv_l_layout;
    private PhotoGridView gridview_title;
    private RelativeLayout rel_title_picture;
    private RelativeLayout rel_add_picture;
    private ImageView img_head_portrait, img_gender;
    private TextView txt_user_name, txt_age, txt_user_id, txt_like_number;
    private RelativeLayout age_bg_layout;
    private RatingBar shopXing;

    /**
     * 资料控件
     */
    //	private EditText identity_et;
    //	private EditText signature_et;
    //	private HorizontalListView hobby_hlistview;

    private RelativeLayout mLayoutTalk;
    private RelativeLayout mLayoutAddFriend;
    //	private MxPullToRefreshScrollView scrollView;
    private PullToRefreshListView list;


    private ImageView cursor;
    private int offset = 0;

    protected PopupWindow window;


    private static final int MYSHOWVIDEO = 0;
    private static final int MYSHOWPHOTOWALL = 1;
    private static final int MTYSHOWDYNMAIC = 2;
    private static final int PERSON = 3;
    private static final int ZANPERSON = 4;
    private static final int REPORT = 5;
    private static final int GETLIKES = 6;
    private static final int LIKE = 7;
    protected static final int ZAN = 8;
    private static final int UPMTYSHOWDYNMAIC = 9;


    protected DisplayImageOptions options;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private boolean ismine = false;

    private ArrayList<AttributeImages> imageArray = new ArrayList<AttributeImages>();
    private int page = 1;

//    private OnekeyShare oks;
    private Integer currentPosition;
    protected int goodNo;
    private ImageView bg_imageView;

    private RelativeLayout rel_like;
    private ImageView img_like;
    private LinearLayout tabLayout, xing_layout;
    private TextView address_tv, signature_tv;
    private View hazy_view;
    private FloatingActionMenu rightLowerMenu;
    private ImageView fabIconNew;
    private View view_pop_bg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        initData();
    }

    protected void setupView() {
        setContentView(R.layout.my_show_layout_new2);
//        oks = new OnekeyShare();

        user = (UserCoach) getIntent().getSerializableExtra("user");
        if (BaseApp.mInstance.isLogin()) {
            if (user.getMxid() == BaseApp.mInstance.getUser().getMxid()) {
                ismine = true;
                initTop("我的型秀", true, R.drawable.icon_edix);
                showfabMenu();
            } else {
                initTop(user.getName(), true, R.drawable.icon_ellipsis);
            }
        } else {
            initTop(user.getName(), true, R.drawable.icon_ellipsis);
        }
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_avatar_default)
                .showImageForEmptyUri(R.drawable.icon_avatar_default)
                .showImageOnFail(R.drawable.icon_avatar_default)
                .cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        adapter = new MyShowDynamicAdapter(this, user);
        findView();
        //		findViewById(R.id.dynamiclist_rl).setSelected(true);
        initCursorView();
        initView();
        refreshUserProfileView();
        initPopupWindow();
    }

    /**
     * 标题右边图标点击事件
     */
    @Override
    protected void rightImageOnClick() {
        if (BaseApp.mInstance.isLogin()) {
            //			if(!ismine){
            //				showReportMenu();
            //			}else{
            popWindow.show();
            //			}
        } else {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), LoginActivity.class);
            //			intent.putExtra(Constant.BUNDLE_CLASS,
            //					TabFoundServiceInterfaceActivity.class);
            startActivity(intent);
        }

    }

    private void findView() {

        list = (PullToRefreshListView) findViewById(R.id.list);
        list.setMode(Mode.DISABLED);
        list.setOnRefreshListener(this);
        LayoutInflater mInflater = getLayoutInflater();
        View v = mInflater.inflate(R.layout.my_show_layout_new2_header, null);

        //		sv_l_layout = (LinearLayout) v.findViewById(R.id.sv_l_layout);
        rel_title_picture = (RelativeLayout) v.findViewById(R.id.rel_title_picture);
        //		rel_title_picture.setBackgroundResource(R.drawable.mine_top_bg);
        gridview_title = (PhotoGridView) v.findViewById(R.id.gridview_title);
        rel_add_picture = (RelativeLayout) v.findViewById(R.id.rel_add_picture);
        //		rel_add_picture.setBackgroundResource(R.drawable.mine_top_bg);
        bg_imageView = (ImageView) v.findViewById(R.id.bg_imageView);

        img_head_portrait = (ImageView) v.findViewById(R.id.img_head_portrait);
        txt_user_name = (TextView) v.findViewById(R.id.txt_user_name);
        age_bg_layout = (RelativeLayout) v.findViewById(R.id.age_bg_layout);
        img_gender = (ImageView) v.findViewById(R.id.img_gender);
        txt_age = (TextView) v.findViewById(R.id.txt_age);
        txt_user_id = (TextView) v.findViewById(R.id.txt_user_id);
        shopXing = (RatingBar) v.findViewById(R.id.shopXing);
        txt_like_number = (TextView) v.findViewById(R.id.txt_like_number);
        mLayoutTalk = (RelativeLayout) findViewById(R.id.tabTalkLayout);
        mLayoutTalk.setOnClickListener(this);
        mLayoutAddFriend = (RelativeLayout) findViewById(R.id.tabSportsWayLayout);
        //		scrollView = (MxPullToRefreshScrollView) findViewById(R.id.scrollView);
        tabLayout = (LinearLayout) findViewById(R.id.tabLayout);
        view_pop_bg = findViewById(R.id.view_pop_bg);


        //		scrollView.setMode(Mode.DISABLED);
        //		scrollView.setOnRefreshListener(this);
        mLayoutAddFriend.setOnClickListener(this);
        rel_add_picture.setOnClickListener(this);
        address_tv = (TextView) v.findViewById(R.id.address_tv);
        address_tv.setVisibility(View.VISIBLE);
        signature_tv = (TextView) v.findViewById(R.id.signature_tv);
        signature_tv.setVisibility(View.VISIBLE);

        rel_like = (RelativeLayout) v.findViewById(R.id.rel_like);
        img_like = (ImageView) v.findViewById(R.id.img_like);
        xing_layout = (LinearLayout) v.findViewById(R.id.xing_layout);
        xing_layout.setVisibility(View.GONE);
        mListView = list.getRefreshableView();
        mListView.addHeaderView(v);


        if (ismine) {
            tabLayout.setVisibility(View.GONE);
        } else {
            //如果是其他人 ，需要筛选是否已是好友
            //            try {
            //                if (BaseApp.mInstance.getContactList().containsKey("" + user.getMxid())) {
            //                    mLayoutAddFriend.setVisibility(View.GONE);
            //                }
            //            } catch (Exception e) {
            //                // TODO Auto-generated catch block
            //                e.printStackTrace();
            //            }
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        /**
         * 获取个人信息
         */
        addTask(this, new IHttpTask(UrlLink.FANPERSONALDATA_IRL, paramsinit2(""
                        + user.getMxid()), FanProfileResult.class), HttpConfig.GET,
                PERSON);
        page = 1;
        /**
         * 获取动态列表
         */
        addTask(this, new IHttpTask(UrlLink.MYSHOWDYNAMICS_URL,
                paramsinitDynamic("" + user.getMxid(), "" + page),
                DynamicListResult.class), HttpConfig.GET, UPMTYSHOWDYNMAIC);
    }

    private void initCursorView() {
        cursor = (ImageView) findViewById(R.id.cursortwo);
        // bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
        // .getWidth();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        int cw = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int ch = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        cursor.measure(cw, ch);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cursor
                .getLayoutParams();

        int height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 4, getResources()
                        .getDisplayMetrics());
        params.width = screenW / 2;
        params.height = height;
        cursor.setLayoutParams(params);
        cursor.invalidate();

        offset = params.width;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);
    }

    private void initView() {
        //		viewPager = (ViewPager) findViewById(R.id.vp);
        //		listViews = new ArrayList<View>();
        //		LayoutInflater mInflater = getLayoutInflater();
        //		listViews.add(mInflater.inflate(R.layout.my_show_pageview1, null));
        //		listViews.add(mInflater.inflate(R.layout.my_show_pageview2, null));
        //		mListView = (ListView) listViews.get(0).findViewById(R.id.inSListView1);

        mListView.setAdapter(adapter);
        mListView.setFocusable(false);
        //		sv_l_layout.addView(listViews.get(1));
        //		sv_l_layout.addView(listViews.get(0));
        //		listViews.get(1).setVisibility(View.GONE);
        list.setMode(Mode.PULL_FROM_END);

        //		identity_et = (EditText) listViews.get(1).findViewById(R.id.identity_et);
        //		signature_et = (EditText) listViews.get(1).findViewById(R.id.signature_et);
        //		hobby_hlistview = (HorizontalListView) listViews.get(1).findViewById(R.id.hobby_hlistview);
    }


    private void refreshUserProfileView() {

        try {
            txt_user_name.setText(user.getName());
            txt_user_id.setText("ID:" + String.valueOf(user.getMxid()));
            address_tv.setText(user.getAddress());
            signature_tv.setText(user.getSignature());
            adapter.setUser(user);

            // gender = ActivityTool.getGender(user.getGender());
            // imgGenderView.setBackgroundResource(gender.drawableRes);
            if (user.getGender() == 0) {
                //			layout_myshow.setBackground(getResources().getDrawable(
                //					R.drawable.sex_nan));
                Utils.setLayoutBackgroud(age_bg_layout, getResources()
                        .getDrawable(R.drawable.sex_nan));
                img_gender.setImageResource(R.drawable.sex_nan_fuhao);
            } else {

                //			layout_myshow.setBackground(getResources().getDrawable(
                //					R.drawable.sex_nv));
                Utils.setLayoutBackgroud(age_bg_layout, getResources()
                        .getDrawable(R.drawable.sex_nv));
                img_gender.setImageResource(R.drawable.sen_nv_fuhao);
            }
            txt_age.setText(String.valueOf(user.getTrue_age()));
            if (user.getTrue_age() < 1) {
                txt_age.setVisibility(View.GONE);
            }

            //			signature_et.setText(user.getSignature());
            //			identity_et.setText(Constants.IDENTITY_RES[user.getIdentity()]);
            imageLoader.displayImage(user.getAvatar(), img_head_portrait, options);
            txt_like_number.setText(user.getLikes() + "");
            if (BaseApp.mInstance.isLogin()) {
                if (MyDbUtils.isLike("" + user.getMxid())) {
                    //赞过，灰色
                    rel_like.setBackgroundResource(R.drawable.shape_edge_gray);
                    img_like.setImageResource(R.drawable.icon_lesson_like_gray);
                    txt_like_number.setTextColor(Color
                            .parseColor("#8d97a6"));
                } else {

                    rel_like.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            // 没赞过
                            rel_like.setClickable(false);

                            addTask(MyShowActivityNew.this,
                                    new IHttpTask(
                                            UrlLink.ZANPENRSON_URL,
                                            initParamsZan(String
                                                    .valueOf(user.getMxid())),
                                            MsgResult.class),
                                    HttpConfig.POST, ZAN);
                        }
                    });
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    protected List<BasicNameValuePair> initParamsZan(String id) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("mxid", id));
        return paramsaaa;
    }


    private void initPopupWindow() {
        if (ismine) {
            popWindow = new PopupWindowUitl(this, "修改照片墙", "添加动态", "编辑个人资料", view_pop_bg);
            popWindow.setOnPopupWindowItemClickListener(new PopupWindowUitl.OnPopupWindowItemClickListener() {

                Intent intent = new Intent();

                @Override
                public void onFirstItemClick() {
                    if (imageArray.size() > 0) {
                        Bimp.tempSelectBitmap = new ArrayList<ImageItem>();
                        for (AttributeImages aimage : imageArray) {
                            ImageItem imageitem = new ImageItem();
                            imageitem.imagePath = aimage.getUrl(MyShowActivityNew.this, 80, 80);
                            imageitem.imageserId = "" + aimage.getNo();
                            Bimp.tempSelectBitmap.add(imageitem);
                            intent.putExtra("hasphoto", true);
                        }
                    }
                    intent.setClass(MyShowActivityNew.this, TabPhotoWallActivity.class);
                    startActivity(intent);

                }

                @Override
                public void onSecondItemClick() {
                    //					intent.setClass(MyShowActivityNew.this, HomeActivity.class);
                    //					intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    //					startActivity(intent);
                    rightLowerMenu.open(true);
                    fabIconNew.setVisibility(View.VISIBLE);

                }

                @Override
                public void onThirdItemClick() {
                    //打开个人资料
                    intent.setClass(MyShowActivityNew.this,
                            TabMinePersonalDataActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFourthItemClick() {

                }

                @Override
                public void onFiveItemClick() {

                }


                @Override
                public void onDismissListener() {

                }
            });
        } else {
            popWindow = new PopupWindowUitl(this, "骚扰信息", "色情相关", "资料不当", "盗用他人资料", "垃圾广告", view_pop_bg);
            popWindow.setOnPopupWindowItemClickListener(new PopupWindowUitl.OnPopupWindowItemClickListener() {
                @Override
                public void onFirstItemClick() {

                    addTask(MyShowActivityNew.this,
                            new IHttpTask(UrlLink.REPORT_URL, paramsinitReport(
                                    Constant.REPORT_USER, BaseApp.mInstance
                                            .getUser().getMxid(), getResources()
                                            .getString(R.string.popmenu_report_1)),
                                    MsgResult.class), HttpConfig.POST, REPORT);
                }

                @Override
                public void onSecondItemClick() {

                    addTask(MyShowActivityNew.this,
                            new IHttpTask(UrlLink.REPORT_URL, paramsinitReport(
                                    Constant.REPORT_USER, BaseApp.mInstance
                                            .getUser().getMxid(), getResources()
                                            .getString(R.string.popmenu_report_2)),
                                    MsgResult.class), HttpConfig.POST, REPORT);
                }

                @Override
                public void onThirdItemClick() {
                    addTask(MyShowActivityNew.this,
                            new IHttpTask(UrlLink.REPORT_URL, paramsinitReport(
                                    Constant.REPORT_USER, BaseApp.mInstance
                                            .getUser().getMxid(), getResources()
                                            .getString(R.string.popmenu_report_3)),
                                    MsgResult.class), HttpConfig.POST, REPORT);
                }

                @Override
                public void onFourthItemClick() {
                    addTask(MyShowActivityNew.this,
                            new IHttpTask(UrlLink.REPORT_URL, paramsinitReport(
                                    Constant.REPORT_USER, BaseApp.mInstance
                                            .getUser().getMxid(), getResources()
                                            .getString(R.string.popmenu_report_4)),
                                    MsgResult.class), HttpConfig.POST, REPORT);
                }

                @Override
                public void onFiveItemClick() {
                    addTask(MyShowActivityNew.this,
                            new IHttpTask(UrlLink.REPORT_URL, paramsinitReport(
                                    Constant.REPORT_USER, BaseApp.mInstance
                                            .getUser().getMxid(), getResources()
                                            .getString(R.string.popmenu_report_5)),
                                    MsgResult.class), HttpConfig.POST, REPORT);
                }


                @Override
                public void onDismissListener() {

                }
            });

        }

    }

    public void showBigImage(ArrayList<AttributeImages> imageArray,
                             int currentPosition) {
        Intent i = new Intent(this, ImageShowerActivity.class);
        i.putParcelableArrayListExtra(Constant.BUNDLE_BIGIMAGEITEM,
                imageArray);
        i.putExtra(Constant.BUNDLE_BIGIMAGEITEM_INDEX, currentPosition);
        startActivity(i);
    }


    protected void initData() {
        gridview_title.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                showBigImage(imageArray, position);

            }
        });

        //		/**
        //		 * 获取照片墙
        //		 */
        //		addTask(this,
        //				new IHttpTask(UrlLink.MYSHOWPHOTOWALL_URL,
        //						paramsinitPhotowall("" + user.getMxid()),
        //						MxPhotosResult.class), HttpConfig.GET, MYSHOWPHOTOWALL);
        //		/**
        //		 * 获取likes
        //		 */
        //		addTask(this, new IHttpTask(UrlLink.LIKES_URL, paramsinitLikePerson(""
        //				+ user.getMxid()), LikesResult.class), HttpConfig.GET, GETLIKES);
        adapter.setOnAvatarClickListener(mOnAvatarClickListener);

        if (mOnMoreCommentClickListenner != null) {
            adapter
                    .setOnMoreCommentClickListenner(mOnMoreCommentClickListenner);
        }
        if (mOnCommentAndLikeClickListenner != null) {
            adapter
                    .setOnCommentAndLikeClickListenner(mOnCommentAndLikeClickListenner);
        }
        if (mVideoClickListener != null) {
            adapter
                    .setVideoClickListener(mVideoClickListener);
        }


        adapter.setOnShareClickListenner(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dynamic dynamic = (Dynamic) v
                        .getTag();
                StringBuffer buffer = new StringBuffer();
                // 正式平台 http://www.e-mxing.com/share/
                buffer.append(BaseUrlLink.SHARE_URL);
                buffer.append(dynamic.getId());
                buffer.append("/dynamics");
                String titleUrl = buffer.toString();
//                oks.setTitleUrl(titleUrl);
//                if (BaseApp.mInstance.isLogin()) {
//                    oks.setText("我的美型号" + BaseApp.mInstance.getUser().getMxid()
//                            + "这里都是型男美女“小鲜肉”，全民老公vs梦中女神，速速围观" + titleUrl);
//                } else {
//                    oks.setText("这里都是型男美女“小鲜肉”，全民老公vs梦中女神，速速围观" + titleUrl);
//                }
//                oks.setUrl(titleUrl);
//                oks.setTheme(OnekeyShareTheme.CLASSIC);
//                oks.show(MyShowActivityNew.this);
//                oks.setTitle(titleUrl);
//                if (dynamic.getContent() == null
//                        || dynamic.getContent().intern()
//                        .equals("")) {
//                    oks.setTitle("最近用美型App，国内首家健身社交App");
//                } else {
//                    if (dynamic.getContent().length() > 30) {
//                        oks.setTitle(dynamic.getContent()
//                                .substring(0, 30));
//                    } else {
//                        oks.setTitle(dynamic.getContent());
//                    }
//
//                }
//
//                if (dynamic.getCover() != null
//                        && dynamic.getCover().size() > 0) {
//                    oks.setImageUrl(dynamic.getCover().get(0).getUrl());
//                } else if (dynamic.getFilm() != null) {
//                    oks.setImageUrl(dynamic.getFilm().getCover());
//
//                } else {
//
//                }

            }
        });
    }

    private OnClickListener mOnAvatarClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Object o = v.getTag();
            if (o != null) {
                if (o instanceof UserCoach) {
                    UserCoach u = (UserCoach) v.getTag();
                    ActivityTool.startShowActivity(MyShowActivityNew.this, u);

                }

                //                else if (o instanceof AttributeFindRecommend) {
                //                    // 点击对话头像跳群组界面
                //                }
            }
        }
    };

    private OnMoreCommentClickListenner mOnMoreCommentClickListenner = new OnMoreCommentClickListenner() {


        @Override
        public void onMoreCommentClick(View v) {
            currentPosition = (Integer) v.getTag();
            Dynamic dynamic = adapter.getItem(currentPosition);
            AttributeFindDynamics localDynamic = dynamic.translationToAttribute();
            localDynamic.setUser(user);
            Intent i = new Intent(MyShowActivityNew.this, CommentDetailActivity.class);
            i.putExtra("localDynamic", localDynamic);
            startActivity(i);
        }
    };


    private OnCommentAndLikeClickListenner mOnCommentAndLikeClickListenner = new OnCommentAndLikeClickListenner() {
        @Override
        public void onLikeClick(View v) {
            if (BaseApp.mInstance.isLogin()) {
                currentPosition = (Integer) v.getTag();
                Dynamic dynamics = adapter.getItem(currentPosition);
                goodNo = dynamics.getId();
                MXLog.out("no---------------:" + goodNo);
                if (!MyDbUtils.isZan("" + goodNo)) {
                    MyDbUtils.saveZan("" + goodNo, true);
                    Dynamic dynamic = adapter.getItem(currentPosition);

                    long goodCount = dynamic.getLikes() + 1;
                    dynamic.setLikes(goodCount);
                    ArrayList<AttributeDynamics.LikeUser> like = dynamic.getLike_user();
                    AttributeDynamics.LikeUser user = new AttributeDynamics().new LikeUser();
                    user.setUser(BaseApp.mInstance.getUser());
                    like.add(0, user);
                    adapter.notifyDataSetChanged();
                    addTask(MyShowActivityNew.this,
                            new IHttpTask(UrlLink.DYNAMICZAN_URL,
                                    initParamsZan(String.valueOf(goodNo)),
                                    MsgResult.class), HttpConfig.POST, ZAN);
                }
            } else {
                Intent i = new Intent(MyShowActivityNew.this, LoginActivity.class);
                startActivity(i);
            }
        }

        protected List<BasicNameValuePair> initParamsZan(String id) {
            List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
            paramsaaa.add(new BasicNameValuePair("no", id));
            return paramsaaa;
        }

        @Override
        public void onCommentClick(View v) {

            currentPosition = (Integer) v.getTag();
            Dynamic dynamic = adapter.getItem(currentPosition);
            AttributeFindDynamics localDynamic = dynamic.translationToAttribute();
            localDynamic.setUser(user);
            System.out.println("美型id ： " + localDynamic.getUser().getMxid());
            Intent i = new Intent(MyShowActivityNew.this, CommentDetailActivity.class);
            i.putExtra("localDynamic", localDynamic);
            startActivity(i);

        }
    };
    private OnClickListener mVideoClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Dynamic dyn = (Dynamic) v.getTag();
            AttributeVideo mvideo = new AttributeVideo();
            mvideo.setNo(dyn.getId());
            mvideo.setContent(dyn.getContent());
            mvideo.setFilm(dyn.getFilm());

            Intent i = new Intent(MyShowActivityNew.this, VideoPlayerActivity.class);
            i.putExtra(Constant.BUNDLE_VIDEO_URL, mvideo.getFilm()
                    .getFilm());
            i.putExtra("videofilm", mvideo);
            startActivity(i);
        }
    };
    private PopupWindowUitl popWindow;


    public List<BasicNameValuePair> paramsinit2(String mxid) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("mxid", mxid));
        return paramsaaa;
    }

    public List<BasicNameValuePair> paramsinitPhotowall(String mxid) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("mxid", mxid));
        return paramsaaa;
    }

    public List<BasicNameValuePair> paramsinitDynamic(String mxid, String page) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("mxid", mxid));
        paramsaaa.add(new BasicNameValuePair("page", page));
        return paramsaaa;
    }

    public List<BasicNameValuePair> paramsinitLikePerson(String mxid) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("mxid", mxid));
        return paramsaaa;
    }


    public void titleOnClick(View v) {
        Animation animation = null;
        switch (v.getId()) {
            case R.id.dynamiclist_rl:
                if (v.isSelected())
                    return;
                v.setSelected(true);
                findViewById(R.id.information_rl).setSelected(false);
                animation = new TranslateAnimation(offset, 0, 0, 0);
                animation.setFillAfter(true);
                animation.setDuration(300);
                cursor.startAnimation(animation);
                //			sv_l_layout.removeView(listViews.get(1));
                //			sv_l_layout.addView(listViews.get(0));
                //			listViews.get(0).setVisibility(View.VISIBLE);
                //			listViews.get(1).setVisibility(View.GONE);
                adapter.setType(0);
                list.setMode(Mode.PULL_FROM_END);
                break;
            case R.id.information_rl:
                if (v.isSelected())
                    return;
                v.setSelected(true);
                findViewById(R.id.dynamiclist_rl).setSelected(false);
                animation = new TranslateAnimation(0, offset, 0, 0);
                animation.setFillAfter(true);
                animation.setDuration(300);
                cursor.startAnimation(animation);
                //			sv_l_layout.removeView(listViews.get(0));
                //			sv_l_layout.addView(listViews.get(1));
                //			listViews.get(1).setVisibility(View.VISIBLE);
                //			listViews.get(0).setVisibility(View.GONE);
                adapter.setType(1);
                list.setMode(Mode.DISABLED);
                break;
            default:
                break;
        }


    }


    @Override
    public void onGetData(Object data, int requestCode, String response) {
        // TODO Auto-generated method stub
        stopLoading();
        switch (requestCode) {
            case REPORT:
                MsgResult msgResult = (MsgResult) data;
                if (msgResult.getCode() == 1) {
                    ToastUtil.show(getResources()
                            .getString(R.string.report_success), this);
                }
                break;
            case UPMTYSHOWDYNMAIC:
                DynamicListResult updynamicsResult = (DynamicListResult) data;
                if (updynamicsResult.getCode() == 1) {
                    adapter.clearData();

                }
            case MTYSHOWDYNMAIC:
                DynamicListResult dynamicsResult = (DynamicListResult) data;
                if (dynamicsResult.getCode() == 1) {
                    adapter.addData(dynamicsResult.getData().getDynamic());

                }

                break;
            case MYSHOWPHOTOWALL:
                MxPhotosResult saresphotowall = (MxPhotosResult) data;
                if (saresphotowall.getCode() == 1) {
                    if (saresphotowall.getData().getPhotos() != null) {
                        if (saresphotowall.getData().getPhotos().size() > 0) {
                            rel_add_picture.setVisibility(View.GONE);
                            rel_title_picture.setVisibility(View.VISIBLE);
                            imageArray = saresphotowall.getData().getPhotos();
                            gridview_title.setAdapter(new DynamicPhotoGridAdapter(
                                    this, imageArray));


                        } else {
                            rel_add_picture.setVisibility(View.VISIBLE);
                            rel_title_picture.setVisibility(View.GONE);
                        }
                    } else {
                        rel_add_picture.setVisibility(View.VISIBLE);
                        rel_title_picture.setVisibility(View.GONE);
                    }

                }
                break;
            case PERSON:
                FanProfileResult person = (FanProfileResult) data;
                if (person.getCode() == 1) {
                    if (person.getData() != null
                            && person.getData().getUser() != null) {
                        user = person.getData().getUser();
                        refreshUserProfileView();
                        if (person.getData().getUser().getPhotowall() != null) {
                            if (person.getData().getUser().getPhotowall().size() > 0) {
                                imageArray = person.getData().getUser().getPhotowall();
                                rel_add_picture.setVisibility(View.GONE);
                                rel_title_picture.setVisibility(View.VISIBLE);
                                gridview_title.setAdapter(new DynamicPhotoGridAdapter(
                                        this, person.getData().getUser().getPhotowall()));
                                bg_imageView.setImageResource(R.drawable.mine_top_bg);
                            } else {
                                if (ismine) {
                                    rel_add_picture.setVisibility(View.VISIBLE);
                                    rel_title_picture.setVisibility(View.GONE);
                                    bg_imageView.setImageResource(R.drawable.mine_top_bg);
                                } else {
                                    rel_add_picture.setVisibility(View.GONE);
                                    rel_title_picture.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            if (ismine) {
                                rel_add_picture.setVisibility(View.VISIBLE);
                                rel_title_picture.setVisibility(View.GONE);
                                bg_imageView.setImageResource(R.drawable.mine_top_bg);
                            } else {
                                rel_add_picture.setVisibility(View.GONE);
                                rel_title_picture.setVisibility(View.GONE);
                            }
                        }
                    }
                }
                break;
            case ZAN:
                MsgResult zan = (MsgResult) data;
                if (zan.getCode() == 1) {
                    MyDbUtils.saveLike("" + user.getMxid());
                    rel_like.setBackgroundResource(R.drawable.shape_edge_gray);
                    img_like.setImageResource(R.drawable.icon_lesson_like_gray);
                    txt_like_number.setTextColor(Color.parseColor("#8d97a6"));
                    user.setLikes(user.getLikes() + 1);
                    txt_like_number.setText(user.getLikes() + "");
                } else if (zan.getCode() == -2) {
                    MyDbUtils.saveLike("" + user.getMxid());
                    rel_like.setBackgroundResource(R.drawable.shape_edge_gray);
                    img_like.setImageResource(R.drawable.icon_lesson_like_gray);
                    txt_like_number.setTextColor(Color.parseColor("#8d97a6"));
                    txt_like_number.setText((user.getLikes()) + "");
                } else {
                    rel_like.setClickable(true);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String reason, int requestCode) {
        // TODO Auto-generated method stub
        stopLoading();
        if (requestCode == ZAN) {
            rel_like.setClickable(true);
        }

    }

    private void stopLoading() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                list.onRefreshComplete();

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
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.tabTalkLayout:
                if (BaseApp.mInstance.isLogin()) {

                    //                    intent.setClass(MyShowActivityNew.this,
                    //                            TabMsgDialogueInterfaceActivity.class);
                    //                    intent.putExtra(BaseActivity.BUNDLE_USER, user);
                    //                    intent.putExtra("mxid", user.getMxid());
                    //                    intent.putExtra(BaseActivity.BUNDLE_CHAT_TYPE,
                    //                            BaseActivity.CHATTYPE_SINGLE);
                    //                    startActivity(intent);
                } else {
                    intent.putExtra(Constant.BUNDLE_CLASS, MyShowActivityNew.class);
                    intent.setClass(MyShowActivityNew.this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.tabSportsWayLayout:
                if (BaseApp.mInstance.isLogin()) {
                    //                    if (BaseApp.getContactList() != null
                    //                            && BaseApp.getContactList().containsKey(
                    //                            String.valueOf(user.getMxid()))) {
                    //                        // 提示已在好友列表中，无需添加
                    //                        showShortToast(getResources().getString(
                    //                                R.string.tip_friendHasAdded));
                    //                        // 黑名单
                    //                        // if(EMContactManager.getInstance().getBlackListUsernames().contains(nameText.getText().toString())){
                    //                        // startActivity(new Intent(this,
                    //                        // AlertDialog.class).putExtra("msg",
                    //                        // "此用户已是你好友(被拉黑状态)，从黑名单列表中移出即可"));
                    //                        // return;
                    //                        // }
                    //                        // String strin =
                    //                        // getString(R.string.This_user_is_already_your_friend);
                    //                        // startActivity(new Intent(this,
                    //                        // AlertDialog.class).putExtra("msg", strin));
                    //                        return;
                    //                    }
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                String reason = getResources().getString(
                                        R.string.app_name);
                                //                                EMContactManager.getInstance().addContact(
                                //                                        String.valueOf(user.getMxid()), reason);
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        ToastUtil.show(getResources()
                                                .getString(
                                                        R.string.tip_friendAddSuccess), MyShowActivityNew.this);
                                    }
                                });
                            } catch (final Exception e) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        ToastUtil.show(getResources()
                                                .getString(R.string.error_net), MyShowActivityNew.this);
                                    }
                                });
                            }
                        }
                    }).start();
                } else {
                    intent.setClass(MyShowActivityNew.this, LoginActivity.class);
                    intent.putExtra(Constant.BUNDLE_CLASS, MyShowActivityNew.class);
                    startActivity(intent);
                }
                break;
            case R.id.rel_add_picture:
                intent.setClass(getApplicationContext(), TabPhotoWallActivity.class);
                //			intent.putExtra(BUNDLE_CLASS, TabMineMyShowActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        /**
         * 获取动态列表
         */
        addTask(this, new IHttpTask(UrlLink.MYSHOWDYNAMICS_URL,
                paramsinitDynamic("" + user.getMxid(), "" + page),
                DynamicListResult.class), HttpConfig.GET, MTYSHOWDYNMAIC);

    }

    private void showfabMenu() {
        hazy_view = new View(this);
        hazy_view.setBackgroundColor(0xcc000000);
        hazy_view.setVisibility(View.GONE);
        FrameLayout.LayoutParams hazy_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        ((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content)).addView(hazy_view, hazy_params);
        ;

        int redActionButtonSize = getResources().getDimensionPixelSize(
                R.dimen.pref_50dp);
        int redActionButtonMargin = getResources().getDimensionPixelOffset(
                R.dimen.pref_3dp);
        int redActionMenuRadius = getResources().getDimensionPixelSize(
                R.dimen.pref_150dp);
        int blueSubActionButtonSize = getResources().getDimensionPixelSize(
                R.dimen.pref_80dp);

        fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(getResources().getDrawable(
                R.drawable.btn_close));
        FloatingActionButton.LayoutParams cParams = new FloatingActionButton.LayoutParams(
                redActionButtonSize, redActionButtonSize);
        // cParams.setMargins(0,0,0,redActionButtonMargin);
        fabIconNew.setLayoutParams(cParams);
        FloatingActionButton.LayoutParams cbParams = new FloatingActionButton.LayoutParams(
                FloatingActionButton.LayoutParams.WRAP_CONTENT,
                FloatingActionButton.LayoutParams.WRAP_CONTENT);
        cbParams.setMargins(0, 0, 0, redActionButtonMargin);

        FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(
                this).setContentView(fabIconNew, cParams)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER)
                .setLayoutParams(cbParams).build();
        rightLowerButton.setBackgroundDrawable(null);

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        rLSubBuilder.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.sub_selector));
        FrameLayout.LayoutParams blueParams = new FrameLayout.LayoutParams(
                blueSubActionButtonSize, blueSubActionButtonSize);
        rLSubBuilder.setLayoutParams(blueParams);

        ImageView rlIcon1 = new ImageView(this);
        // ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        // ImageView rlIcon4 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);

        rlIcon1.setImageDrawable(getResources().getDrawable(
                R.drawable.icon_albm));
        // rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.icon_file_video));
        rlIcon2.setImageDrawable(getResources().getDrawable(
                R.drawable.icon_photograph));
        rlIcon3.setImageDrawable(getResources().getDrawable(
                R.drawable.icon_video_orange));
        // rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.icon_video));

        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        // TextView tv3 = new TextView(this);
        // TextView tv4 = new TextView(this);
        tv1.setText("照片");
        tv1.setPadding(Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 15));
        tv1.setTextColor(getResources().getColor(R.color.color_white));
        tv2.setText("拍照");
        tv2.setPadding(Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 15));
        tv2.setTextColor(getResources().getColor(R.color.color_white));
        tv3.setText("视频");
        tv3.setPadding(Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 0),
                Utils.dip2px(getApplicationContext(), 15));
        tv3.setTextColor(getResources().getColor(R.color.color_white));
        // tv3.setText("测试3");
        // tv4.setText("测试4");

        // Build the menu with default options: light theme, 90 degrees, 72dp
        // radius.
        // Set 4 default SubActionButtons

        SubActionButton tcSub1 = rLSubBuilder.setContentView(rlIcon1,
                blueParams).build();
        SubActionButton tcSub2 = rLSubBuilder.setContentView(rlIcon2,
                blueParams).build();
        SubActionButton tcSub3 = rLSubBuilder.setContentView(rlIcon3,
                blueParams).build();

        rightLowerMenu = new FloatingActionMenu.Builder(
                this)
                .addSubActionView(tcSub1)
                // .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                //				.addSubActionView(tcSub2)
                .addSubActionView(tcSub3)
                // .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
//                .addSubTextView(rLSubBuilder.setContentView(tv1, null).build())
//                //				.addSubTextView(rLSubBuilder.setContentView(tv2, null).build())
//                .addSubTextView(rLSubBuilder.setContentView(tv3, null).build())
                // .addSubTextView(rLSubBuilder.setContentView(tv3).build())
                // .addSubTextView(rLSubBuilder.setContentView(tv4).build())
                .attachTo(rightLowerButton).setRadius(redActionMenuRadius)
                .setStartAngle(-120).setEndAngle(-60).build();

        tcSub1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (BaseApp.mInstance.isLogin()) {
                    Intent intent = new Intent(MyShowActivityNew.this,
                            TabTheIndividualDynaminActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MyShowActivityNew.this,
                            LoginActivity.class);
                    startActivity(intent);
                }

                rightLowerMenu.close(true);

            }
        });
        tcSub2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (BaseApp.mInstance.isLogin()) {
                    Intent intent = new Intent(MyShowActivityNew.this,
                            TabTheIndividualDynaminActivity.class);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MyShowActivityNew.this,
                            LoginActivity.class);
                    startActivity(intent);
                }

                rightLowerMenu.close(true);

            }
        });
        tcSub3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (BaseApp.mInstance.isLogin()) {
                    Intent intent = new Intent(MyShowActivityNew.this,
                            TabTheIndividualDynaminActivity.class);
                    intent.putExtra("type", 3);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MyShowActivityNew.this,
                            LoginActivity.class);
                    startActivity(intent);
                }

                rightLowerMenu.close(true);

            }
        });
        fabIconNew.setVisibility(View.GONE);
        rightLowerMenu
                .setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
                    @Override
                    public void onMenuOpened(FloatingActionMenu menu) {
                        // Rotate the icon of rightLowerButton 45 degrees
                        // clockwise
                        hazy_view.setVisibility(View.VISIBLE);
                        fabIconNew.setImageResource(R.drawable.btn_close);
                    }

                    @Override
                    public void onMenuClosed(FloatingActionMenu menu) {
                        // Rotate the icon of rightLowerButton 45 degrees
                        // counter-clockwise
                        hazy_view.setVisibility(View.GONE);
                        fabIconNew.setImageResource(R.drawable.btn_close);
                        fabIconNew.setVisibility(View.GONE);
                    }
                });

    }


}

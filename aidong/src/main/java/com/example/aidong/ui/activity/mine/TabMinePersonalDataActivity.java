package com.example.aidong.ui.activity.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.ui.BaseApp;
import com.example.aidong.R;
import com.example.aidong.ui.activity.vedio.media.AlbumActivity;
import com.example.aidong.adapter.GralleryAdapter;
import com.example.aidong.utils.common.Constant;
import com.example.aidong.utils.common.UrlLink;
import com.example.aidong.http.HttpConfig;
import com.example.aidong.entity.model.result.MsgResult;
import com.example.aidong.entity.model.result.MxPersonalDataResult;
import com.example.aidong.utils.ActivityTool;
import com.example.aidong.utils.Constants;
import com.example.aidong.utils.FileUtil;
import com.example.aidong.utils.photo.Bimp;
import com.example.aidong.widget.customview.CircleImageView;
import com.example.aidong.widget.wheelcity.AbstractWheelTextAdapter;
import com.example.aidong.widget.wheelcity.AddressData;
import com.example.aidong.widget.wheelcity.ArrayWheelAdapter;
import com.example.aidong.widget.wheelcity.MyAlertDialog;
import com.example.aidong.widget.wheelcity.OnWheelChangedListener;
import com.example.aidong.widget.wheelcity.WheelView;
import com.example.aidong.widget.wheelview.ScreenInfo;
import com.example.aidong.widget.wheelview.WheelMain;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabMinePersonalDataActivity extends BaseActivity implements
		IHttpCallback {
	// 换性别
	String TAG = "TabMinePersonalDataActivity";
	private ImageView mlayout_tab_mine_personal_data_title_img_cancel,
			mlayout_tab_mine_personal_data_individuality_singnature_img,
			mlayout_tab_mine_personal_data_nickname_img,
			mlayout_tab_mine_personal_data_the_purpose_of_body_building_img,
			mlayout_tab_mine_personal_data_fitness_is_good_at_img,
			mlayout_tab_mine_personal_data_often_go_to_the_gym_img,
			mlayout_tab_mine_personal_data_interest_in_fitness_img_go,
			mimageview_tab_mine_personal_data,
			mlayout_tab_mine_personal_data_sex_img,
			mlayout_tab_mine_personal_data_age_img, mimag_picture,
			mlayout_tab_mine_personal_data_the_host_city_img,
			layout_tab_mine_personal_data_portrait_img_go;
	private RelativeLayout mlayout_tab_mine_personal_data_interest_in_fitness_rel,
			mlayout_tab_mine_personal_data_individuality_singnature_rel,
			mlayout_tab_mine_personal_data_nickname_rel,
			mlayout_tab_mine_personal_data_the_purpose_of_body_building_rel,
			mlayout_tab_mine_personal_data_fitness_is_good_at_rel,
			mlayout_tab_mine_personal_data_often_go_to_the_gym_rel,
			mlayout_tab_mine_personal_data_the_host_city_rel,
			mlayout_tab_mine_personal_data_the_basic_information_rel,
			mlayout_tab_mine_personal_data_sex_rel,
			mlayout_tab_mine_personal_data_age_rel;
	private TextView mlayout_tab_mine_personal_data_title_txt_submit,
			mlayout_tab_mine_personal_data_title_txt_complete,
			mlayout_tab_mine_personal_data_the_host_city_txt1,
			mlayout_tab_mine_personal_data_age_txt1,
			mlayout_tab_mine_personal_data_sex_txt1,
			mlayout_tab_mine_personal_data_the_purpose_of_body_building_txt1,
			mlayout_tab_mine_personal_data_fitness_is_good_at_txt1,
			mlayout_tab_mine_personal_data_individuality_singnature_content_txt1,
			mlayout_tab_mine_personal_data_nickname_txt1,
			mlayout_tab_mine_personal_data_often_go_to_the_gym_txt1,
			mlayout_tab_mine_personal_data_mxhao_txt1,
			mlayout_tab_mine_personal_data_id_txt1,
			mlayout_tab_mine_personal_data_interest_in_fitness_txt,
			layout_tab_mine_personal_data_interest_in_fitness_txt;
	private String cityTxt;
	private String picturePath;
	private static final int PERSON = 0;
	private static final int PERSONS = 1;
	private String currentAddFileName;
	private String key;
	private String brithdayAge;
	private CircleImageView mlayout_tab_mine_personal_data_head_portrait_img;
	WheelMain wheelMain;
	private Gallery mlayout_tab_mine_personal_data_interest_in_fitness_gallery;
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	EditText txttime;
	private int selectedItem = -1;
	private PopupWindow window = null;
	ActivityTool.Gender gender;
	public boolean canEdit = false;// 登录后未点击修改按钮
	private ArrayList<String> array = new ArrayList<String>();
	GralleryAdapter gralleryAdapter;
	String interested;
	File cover;
	byte modifyGenderCode = -1;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Bitmap bm = null;
	private Intent intent;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}

	protected void setupView() {
		setContentView(R.layout.layout_tab_mine_personal_data);
		init();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.place_holder_logo)
				.showImageForEmptyUri(R.drawable.place_holder_logo)
				.showImageOnFail(R.drawable.place_holder_logo).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	protected void initData() {
		onClick();
		data();
		addTask(TabMinePersonalDataActivity.this, new IHttpTask(
				UrlLink.PERSONALDATA_IRL, paramsinit2(""
						+ BaseApp.mInstance.getUser().getMxid()),
				MxPersonalDataResult.class), HttpConfig.GET, PERSON);
	}

	@SuppressLint("CutPasteId")
	private void init() {
		canEdit = false;
		interested = BaseApp.mInstance.getUser().getInterests();
		setInterested(interested);
		modifyGenderCode = (byte) BaseApp.mInstance.getUser().getGender();
		layout_tab_mine_personal_data_interest_in_fitness_txt = (TextView) findViewById(R.id.layout_tab_mine_personal_data_interest_in_fitness_txt);
		layout_tab_mine_personal_data_portrait_img_go = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_portrait_img_go);
		mlayout_tab_mine_personal_data_interest_in_fitness_txt = (TextView) findViewById(R.id.layout_tab_mine_personal_data_interest_in_fitness_txt);
		mlayout_tab_mine_personal_data_title_img_cancel = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_title_img_cancel);
		mlayout_tab_mine_personal_data_interest_in_fitness_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_data_interest_in_fitness_rel);
		mlayout_tab_mine_personal_data_individuality_singnature_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_data_individuality_singnature_rel);
		mlayout_tab_mine_personal_data_nickname_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_data_nickname_rel);
		mlayout_tab_mine_personal_data_the_purpose_of_body_building_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_data_the_purpose_of_body_building_rel);
		mlayout_tab_mine_personal_data_fitness_is_good_at_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_data_fitness_is_good_at_rel);
		mlayout_tab_mine_personal_data_often_go_to_the_gym_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_data_often_go_to_the_gym_rel);
		mlayout_tab_mine_personal_data_title_txt_submit = (TextView) findViewById(R.id.layout_tab_mine_personal_data_title_txt_submit);
		mlayout_tab_mine_personal_data_individuality_singnature_img = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_individuality_singnature_img);
		mlayout_tab_mine_personal_data_nickname_img = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_nickname_img);
		mlayout_tab_mine_personal_data_the_purpose_of_body_building_img = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_the_purpose_of_body_building_img);
		mlayout_tab_mine_personal_data_fitness_is_good_at_img = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_fitness_is_good_at_img);
		mlayout_tab_mine_personal_data_often_go_to_the_gym_img = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_often_go_to_the_gym_img);
		mlayout_tab_mine_personal_data_interest_in_fitness_img_go = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_interest_in_fitness_img_go);
		mlayout_tab_mine_personal_data_title_txt_complete = (TextView) findViewById(R.id.layout_tab_mine_personal_data_title_txt_complete);
		mlayout_tab_mine_personal_data_the_host_city_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_data_the_host_city_rel);
		mlayout_tab_mine_personal_data_the_host_city_txt1 = (TextView) findViewById(R.id.layout_tab_mine_personal_data_the_host_city_txt1);
		mlayout_tab_mine_personal_data_age_txt1 = (TextView) findViewById(R.id.layout_tab_mine_personal_data_age_txt1);
		mlayout_tab_mine_personal_data_the_basic_information_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_data_the_basic_information_rel);
		mlayout_tab_mine_personal_data_sex_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_data_sex_rel);
		mlayout_tab_mine_personal_data_age_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_data_age_rel);
		mimageview_tab_mine_personal_data = (ImageView) findViewById(R.id.imageview_tab_mine_personal_data);
		mlayout_tab_mine_personal_data_sex_txt1 = (TextView) findViewById(R.id.layout_tab_mine_personal_data_sex_txt1);
		mlayout_tab_mine_personal_data_sex_img = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_sex_img);
		mlayout_tab_mine_personal_data_age_img = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_age_img);
		mlayout_tab_mine_personal_data_the_host_city_img = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_the_host_city_img);
		mlayout_tab_mine_personal_data_the_purpose_of_body_building_txt1 = (TextView) findViewById(R.id.layout_tab_mine_personal_data_the_purpose_of_body_building_txt1);
		mlayout_tab_mine_personal_data_fitness_is_good_at_txt1 = (TextView) findViewById(R.id.layout_tab_mine_personal_data_fitness_is_good_at_txt1);
		mlayout_tab_mine_personal_data_individuality_singnature_content_txt1 = (TextView) findViewById(R.id.layout_tab_mine_personal_data_individuality_singnature_content_txt1);
		mlayout_tab_mine_personal_data_nickname_txt1 = (TextView) findViewById(R.id.layout_tab_mine_personal_data_nickname_txt1);
		mlayout_tab_mine_personal_data_often_go_to_the_gym_txt1 = (TextView) findViewById(R.id.layout_tab_mine_personal_data_often_go_to_the_gym_txt1);
		mlayout_tab_mine_personal_data_mxhao_txt1 = (TextView) findViewById(R.id.layout_tab_mine_personal_data_mxhao_txt1);
		mlayout_tab_mine_personal_data_id_txt1 = (TextView) findViewById(R.id.layout_tab_mine_personal_data_id_txt1);
		mimag_picture = (ImageView) findViewById(R.id.img_tab_mine_personal_data);
		mlayout_tab_mine_personal_data_interest_in_fitness_gallery = (Gallery) findViewById(R.id.layout_tab_mine_personal_data_interest_in_fitness_gallery);
		mlayout_tab_mine_personal_data_head_portrait_img = (CircleImageView) findViewById(R.id.layout_tab_mine_personal_data_head_portrait_img);
		intent = new Intent();
		imageLoader.displayImage(BaseApp.mInstance.getUser().getAvatar(),
				mlayout_tab_mine_personal_data_head_portrait_img);
		mlayout_tab_mine_personal_data_nickname_txt1.setText(BaseApp.mInstance
				.getUser().getName());
		mlayout_tab_mine_personal_data_individuality_singnature_content_txt1
				.setText(BaseApp.mInstance.getUser().getSignature());
		gender = ActivityTool
				.getGender(BaseApp.mInstance.getUser().getGender());
		mlayout_tab_mine_personal_data_sex_txt1.setText(gender.genderString);
		if (gender == null) {
			mimag_picture.setImageResource(R.drawable.zhinan);
		} else {
			if (gender.genderString == R.string.men) {
				mimag_picture.setImageResource(R.drawable.zhinan);
			} else {
				mimag_picture.setImageResource(R.drawable.zhinv);
			}
		}
		mlayout_tab_mine_personal_data_age_txt1.setText(String
				.valueOf(BaseApp.mInstance.getUser().getTrue_age()));
		if (BaseApp.mInstance.getUser().getTrue_age() < 1) {
			mlayout_tab_mine_personal_data_age_txt1.setText("");
		}
		mlayout_tab_mine_personal_data_the_host_city_txt1
				.setText(BaseApp.mInstance.getUser().getAddress());
		mlayout_tab_mine_personal_data_the_purpose_of_body_building_txt1
				.setText(BaseApp.mInstance.getUser().getTarget());
		mlayout_tab_mine_personal_data_fitness_is_good_at_txt1
				.setText(BaseApp.mInstance.getUser().getSkill());
		mlayout_tab_mine_personal_data_often_go_to_the_gym_txt1
				.setText(BaseApp.mInstance.getUser().getOften());
		mlayout_tab_mine_personal_data_mxhao_txt1.setText(String
				.valueOf(BaseApp.mInstance.getUser().getMxid()));
		mlayout_tab_mine_personal_data_id_txt1
				.setText(Constants.IDENTITY_RES[BaseApp.mInstance.getUser()
						.getIdentity()]);
		gralleryAdapter = new GralleryAdapter(TabMinePersonalDataActivity.this, array);
		mlayout_tab_mine_personal_data_interest_in_fitness_gallery
				.setAdapter(gralleryAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// imageLoader.displayImage(BaseApp.mInstance.getUser().getAvatar(),
		// mlayout_tab_mine_personal_data_head_portrait_img);
		// mlayout_tab_mine_personal_data_nickname_txt1.setText(BaseApp.mInstance
		// .getUser().getName());
		// mlayout_tab_mine_personal_data_individuality_singnature_content_txt1
		// .setText(BaseApp.mInstance.getUser().getSignature());
		// gender = ActivityTool
		// .getGender(BaseApp.mInstance.getUser().getGender());
		// mlayout_tab_mine_personal_data_sex_txt1.setText(gender.genderString);
		// if (gender == null) {
		// mimag_picture.setImageResource(R.drawable.zhinan);
		// } else {
		// if (gender.genderString == R.string.men) {
		// mimag_picture.setImageResource(R.drawable.zhinan);
		// } else {
		// mimag_picture.setImageResource(R.drawable.zhinv);
		// }
		// }
		// mlayout_tab_mine_personal_data_age_txt1.setText(String
		// .valueOf(BaseApp.mInstance.getUser().getAge()));
		// mlayout_tab_mine_personal_data_the_host_city_txt1
		// .setText(BaseApp.mInstance.getUser().getAddress());
		// mlayout_tab_mine_personal_data_the_purpose_of_body_building_txt1
		// .setText(BaseApp.mInstance.getUser().getTarget());
		// mlayout_tab_mine_personal_data_fitness_is_good_at_txt1
		// .setText(BaseApp.mInstance.getUser().getSkill());
		// mlayout_tab_mine_personal_data_often_go_to_the_gym_txt1
		// .setText(BaseApp.mInstance.getUser().getOften());
		// mlayout_tab_mine_personal_data_mxhao_txt1.setText(String
		// .valueOf(BaseApp.mInstance.getUser().getMxid()));
		// mlayout_tab_mine_personal_data_id_txt1
		// .setText(Constants.IDENTITY_RES[BaseApp.mInstance.getUser()
		// .getIdentity()]);
		// gralleryAdapter = new GralleryAdapter(context, array);
		// mlayout_tab_mine_personal_data_interest_in_fitness_gallery
		// .setAdapter(gralleryAdapter);
	}

	private void onClick() {
		mlayout_tab_mine_personal_data_interest_in_fitness_img_go
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						intent.setClass(getApplicationContext(),
//								ClassTypeActivity.class);
//						intent.putExtra(BUNDLE_FIT_INTEREST, interested);
//						startActivityForResult(intent, RC_FITTYPE);

					}
				});
		mlayout_tab_mine_personal_data_interest_in_fitness_txt
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						intent.setClass(getApplicationContext(),
//								ClassTypeActivity.class);
//						intent.putExtra(BUNDLE_FIT_INTEREST, interested);
//						startActivityForResult(intent, RC_FITTYPE);

					}
				});
		mlayout_tab_mine_personal_data_the_basic_information_rel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						mimageview_tab_mine_personal_data
//								.setVisibility(View.VISIBLE);
						
						intent = new Intent(TabMinePersonalDataActivity.this, AlbumActivity.class);
						intent.putExtra(Constant.BUNDLE_PHOTOWALL_POSITION, 0);
						intent.putExtra(Constant.BUNDLE_CLASS, TabMinePersonalDataActivity.class);
						//逻辑与video一样，所以直接传videoShow
						intent.putExtra("videoShow", true);
						startActivityForResult(intent, Constant.RC_SELECTABLUMN);
//						showOutMenuHead();
					}
				});
		mlayout_tab_mine_personal_data_title_img_cancel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
		mlayout_tab_mine_personal_data_interest_in_fitness_rel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						intent.setClass(getApplicationContext(),
//								ClassTypeActivity.class);
//						intent.putExtra(BUNDLE_FIT_INTEREST, interested);
//						startActivityForResult(intent, RC_FITTYPE);
					}
				});

		mlayout_tab_mine_personal_data_individuality_singnature_rel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						intent.setClass(
								getApplicationContext(),
								TabMinePersonalDataIndividualitySignatureActivity.class);
						String singnature = mlayout_tab_mine_personal_data_individuality_singnature_content_txt1
								.getText().toString();
						intent.putExtra("singnature", singnature);
						startActivityForResult(intent, 0);
					}
				});
		mlayout_tab_mine_personal_data_nickname_rel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						intent.setClass(getApplicationContext(),
								TabMinePersonalDataNicknameActivity.class);
						String name = mlayout_tab_mine_personal_data_nickname_txt1
								.getText().toString();
						intent.putExtra("name", name);
						startActivityForResult(intent, 3);
					}
				});
		mlayout_tab_mine_personal_data_the_purpose_of_body_building_rel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						intent.setClass(getApplicationContext(),
								TabMinePersonalDataFitnessGoalsActivity.class);
						String goals = mlayout_tab_mine_personal_data_the_purpose_of_body_building_txt1
								.getText().toString();
						intent.putExtra("goals", goals);
						startActivityForResult(intent, 1);
					}
				});
		mlayout_tab_mine_personal_data_fitness_is_good_at_rel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						intent.setClass(getApplicationContext(),
								TabMinePersonalDataFitnessIsGoodActivity.class);
						String good = mlayout_tab_mine_personal_data_fitness_is_good_at_txt1
								.getText().toString();
						intent.putExtra("good", good);
						startActivityForResult(intent, 2);
					}
				});
		mlayout_tab_mine_personal_data_often_go_to_the_gym_rel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						intent.setClass(getApplicationContext(),
								TabMinePersonalDataOftenGymActivity.class);
						String gym = mlayout_tab_mine_personal_data_often_go_to_the_gym_txt1
								.getText().toString();
						intent.putExtra("gym", gym);
						startActivityForResult(intent, 5);
					}
				});
		mlayout_tab_mine_personal_data_title_txt_submit
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (canEdit == false) {
							canEdit = true;
						}
						changeLayoutClick();
						mlayout_tab_mine_personal_data_individuality_singnature_img
								.setVisibility(View.VISIBLE);
						mlayout_tab_mine_personal_data_nickname_img
								.setVisibility(View.VISIBLE);
						mlayout_tab_mine_personal_data_the_purpose_of_body_building_img
								.setVisibility(View.VISIBLE);
						mlayout_tab_mine_personal_data_fitness_is_good_at_img
								.setVisibility(View.VISIBLE);
						mlayout_tab_mine_personal_data_often_go_to_the_gym_img
								.setVisibility(View.VISIBLE);
						mlayout_tab_mine_personal_data_interest_in_fitness_img_go
								.setVisibility(View.VISIBLE);
						mlayout_tab_mine_personal_data_title_txt_complete
								.setVisibility(View.VISIBLE);
						mlayout_tab_mine_personal_data_sex_img
								.setVisibility(View.VISIBLE);
						mlayout_tab_mine_personal_data_age_img
								.setVisibility(View.VISIBLE);
						mlayout_tab_mine_personal_data_the_host_city_img
								.setVisibility(View.VISIBLE);
						layout_tab_mine_personal_data_portrait_img_go
								.setVisibility(View.VISIBLE);
						mlayout_tab_mine_personal_data_title_txt_submit
								.setVisibility(View.GONE);

					}
				});
		mlayout_tab_mine_personal_data_title_txt_complete
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (canEdit == true) {
							canEdit = false;
						}
						changeLayoutClick();
						// Gender g = ActivityTool.getGender(
						// TabMinePersonalDataActivity.this,
						// getResources().getString(gender.genderString));
						String identity = mlayout_tab_mine_personal_data_id_txt1
								.getText().toString();
						String address = mlayout_tab_mine_personal_data_the_host_city_txt1
								.getText().toString();
						String name = mlayout_tab_mine_personal_data_nickname_txt1
								.getText().toString();
						String signature = mlayout_tab_mine_personal_data_individuality_singnature_content_txt1
								.getText().toString();
						String target = mlayout_tab_mine_personal_data_the_purpose_of_body_building_txt1
								.getText().toString();
						String skill = mlayout_tab_mine_personal_data_fitness_is_good_at_txt1
								.getText().toString();
						String often = mlayout_tab_mine_personal_data_often_go_to_the_gym_txt1
								.getText().toString();
						System.out.println("cover====================" + cover);
						// String birthday =
						// mlayout_tab_mine_personal_data_age_txt1
						// .getText().toString();
						System.out.println("brithdayAge++++++++++++++++++"
								+ brithdayAge);
						Log.e(TAG, "brithdayAge = " + brithdayAge);

						Map<String, Object[]> mapFiles = new HashMap<String, Object[]>();
						mapFiles.put("avatar", new File[] { cover });

						addTask(TabMinePersonalDataActivity.this,
								new IHttpTask(UrlLink.PERSONALDATA_IRL,
										paramsinit(name, signature, String
												.valueOf(modifyGenderCode),
												identity, brithdayAge, address,
												target, skill, often, interested),
										mapFiles, MsgResult.class),
								HttpConfig.POST, PERSONS);
						mlayout_tab_mine_personal_data_title_txt_complete
								.setVisibility(View.GONE);
						mlayout_tab_mine_personal_data_title_txt_submit
								.setVisibility(View.VISIBLE);
						mlayout_tab_mine_personal_data_individuality_singnature_img
								.setVisibility(View.GONE);
						mlayout_tab_mine_personal_data_nickname_img
								.setVisibility(View.GONE);
						mlayout_tab_mine_personal_data_the_purpose_of_body_building_img
								.setVisibility(View.GONE);
						mlayout_tab_mine_personal_data_fitness_is_good_at_img
								.setVisibility(View.GONE);
						mlayout_tab_mine_personal_data_often_go_to_the_gym_img
								.setVisibility(View.GONE);
						mlayout_tab_mine_personal_data_interest_in_fitness_img_go
								.setVisibility(View.GONE);
						mlayout_tab_mine_personal_data_title_txt_complete
								.setVisibility(View.GONE);
						mlayout_tab_mine_personal_data_sex_img
								.setVisibility(View.GONE);
						mlayout_tab_mine_personal_data_age_img
								.setVisibility(View.GONE);
						mlayout_tab_mine_personal_data_the_host_city_img
								.setVisibility(View.GONE);
						layout_tab_mine_personal_data_portrait_img_go
								.setVisibility(View.GONE);
						setResult(RESULT_OK, null);
					}
				});
		mlayout_tab_mine_personal_data_the_host_city_rel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						View view = dialogm();
						final MyAlertDialog dialog1 = new MyAlertDialog(
								TabMinePersonalDataActivity.this).builder()
								.setView(view)
								.setNegativeButton("取消", new OnClickListener() {
									@Override
									public void onClick(View v) {

									}
								});
						dialog1.setPositiveButton("确定", new OnClickListener() {
							@Override
							public void onClick(View v) {
								mlayout_tab_mine_personal_data_the_host_city_txt1
										.setText(cityTxt.toString());
							}
						});
						dialog1.show();

					}
				});
		mlayout_tab_mine_personal_data_age_rel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						LayoutInflater inflater1 = LayoutInflater
								.from(TabMinePersonalDataActivity.this);
						View timepickerview1 = inflater1.inflate(
								R.layout.timepicker, null);
						ScreenInfo screenInfo1 = new ScreenInfo(
								TabMinePersonalDataActivity.this);
						wheelMain = new WheelMain(timepickerview1);
						wheelMain.screenheight = screenInfo1.getHeight();
						Calendar calendar1 = Calendar.getInstance();
						int year1 = calendar1.get(Calendar.YEAR);
						int month1 = calendar1.get(Calendar.MONTH);
						int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
						wheelMain.initDateTimePicker(year1-30, month1, day1);
						MyAlertDialog dialog = new MyAlertDialog(
								TabMinePersonalDataActivity.this).builder()
								.setTitle("选择日期").setView(timepickerview1)
								.setNegativeButton("取消", new OnClickListener() {
									@Override
									public void onClick(View v) {

									}
								});
						dialog.setPositiveButton("保存", new OnClickListener() {
							@Override
							public void onClick(View v) {
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyyMM");
								String date = sdf.format(new java.util.Date());
								long systime = Long.parseLong(date.substring(0,
										4));
								long chosetime = Long.parseLong(wheelMain
										.getTime().substring(0, 4));
								long age = systime - chosetime;
								mlayout_tab_mine_personal_data_age_txt1
										.setText(String.valueOf(age));
								brithdayAge = wheelMain.getTime();
							}
						});
						dialog.show();
					}
				});
		mlayout_tab_mine_personal_data_sex_rel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						showOutMenu();
						mimageview_tab_mine_personal_data
								.setVisibility(View.VISIBLE);
					}
				});
	}

	private void changeLayoutClick() {
		if (canEdit == false) {
			mlayout_tab_mine_personal_data_individuality_singnature_rel
					.setClickable(false);
			mlayout_tab_mine_personal_data_the_basic_information_rel
					.setClickable(false);
			mlayout_tab_mine_personal_data_nickname_rel.setClickable(false);
			mlayout_tab_mine_personal_data_sex_rel.setClickable(false);
			mlayout_tab_mine_personal_data_age_rel.setClickable(false);
			mlayout_tab_mine_personal_data_the_host_city_rel
					.setClickable(false);
			mlayout_tab_mine_personal_data_the_purpose_of_body_building_rel
					.setClickable(false);
			mlayout_tab_mine_personal_data_fitness_is_good_at_rel
					.setClickable(false);
			mlayout_tab_mine_personal_data_often_go_to_the_gym_rel
					.setClickable(false);
			mlayout_tab_mine_personal_data_interest_in_fitness_rel
					.setClickable(false);
			layout_tab_mine_personal_data_interest_in_fitness_txt
					.setClickable(false);
			mlayout_tab_mine_personal_data_interest_in_fitness_gallery
					.setOnTouchListener(null);
		} else {
			mlayout_tab_mine_personal_data_individuality_singnature_rel
					.setClickable(true);
			mlayout_tab_mine_personal_data_the_basic_information_rel
					.setClickable(true);
			mlayout_tab_mine_personal_data_nickname_rel.setClickable(true);
			mlayout_tab_mine_personal_data_sex_rel.setClickable(true);
			mlayout_tab_mine_personal_data_age_rel.setClickable(true);
			mlayout_tab_mine_personal_data_the_host_city_rel.setClickable(true);
			mlayout_tab_mine_personal_data_the_purpose_of_body_building_rel
					.setClickable(true);
			mlayout_tab_mine_personal_data_fitness_is_good_at_rel
					.setClickable(true);
			mlayout_tab_mine_personal_data_often_go_to_the_gym_rel
					.setClickable(true);
			mlayout_tab_mine_personal_data_interest_in_fitness_rel
					.setClickable(true);
			layout_tab_mine_personal_data_interest_in_fitness_txt
					.setClickable(true);
			mlayout_tab_mine_personal_data_interest_in_fitness_gallery
					.setOnTouchListener(new OnTouchListener() {
						float x;
						float y;

						@Override
						public boolean onTouch(View arg0, MotionEvent arg1) {
							// TODO Auto-generated method stub
							switch (arg1.getAction()) {
							case MotionEvent.ACTION_DOWN:
								x = arg1.getX();
								y = arg1.getY();
								break;
							case MotionEvent.ACTION_UP:
								int ux = (int) Math.abs(arg1.getX() - x);
								int uy = (int) Math.abs(arg1.getY() - y);
								if (ux < 5 && uy < 5) {
//									intent.setClass(getApplicationContext(),
//											ClassTypeActivity.class);
//									intent.putExtra(BUNDLE_FIT_INTEREST,
//											interested);
//									startActivityForResult(intent, RC_FITTYPE);
								}
								break;

							default:
								break;
							}

							return false;
						}
					});
		}
	}

	public List<BasicNameValuePair> paramsinit(String name, String signature,
			String gender, String identity, String birthday, String address,
			String target, String skill, String often, String interests) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		if (name != null)
			paramsaaa.add(new BasicNameValuePair("name", name));
		if (signature != null)
			paramsaaa.add(new BasicNameValuePair("signature", signature));
		if (gender != null)
			paramsaaa.add(new BasicNameValuePair("gender", gender));
		if (identity != null)
			paramsaaa.add(new BasicNameValuePair("identity", identity));
		if (birthday != null)
			paramsaaa.add(new BasicNameValuePair("birthday", birthday));
		if (address != null)
			paramsaaa.add(new BasicNameValuePair("address", address));
		if (target != null)
			paramsaaa.add(new BasicNameValuePair("target", target));
		if (skill != null)
			paramsaaa.add(new BasicNameValuePair("skill", skill));
		if (often != null)
			paramsaaa.add(new BasicNameValuePair("often", often));
		if (interests != null) {
			paramsaaa.add(new BasicNameValuePair("interests", interests));
		}
		return paramsaaa;
	}

	public List<BasicNameValuePair> paramsinit2(String mxid) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		paramsaaa.add(new BasicNameValuePair("mxid", mxid));
		return paramsaaa;
	}

	private View dialogm() {
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.wheelcity_cities_layout, null);
		final WheelView country = (WheelView) contentView
				.findViewById(R.id.wheelcity_country);
		country.setVisibleItems(3);
		country.setViewAdapter(new CountryAdapter(this));
		final String cities[][] = AddressData.CITIES;
		final String ccities[][][] = AddressData.COUNTIES;
		final WheelView city = (WheelView) contentView
				.findViewById(R.id.wheelcity_city);
		city.setVisibleItems(0);

		final WheelView ccity = (WheelView) contentView
				.findViewById(R.id.wheelcity_ccity);
		ccity.setVisibleItems(0);

		country.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateCities(city, cities, newValue);
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ " | "
						+ AddressData.CITIES[country.getCurrentItem()][city
								.getCurrentItem()]
						+ " | "
						+ AddressData.COUNTIES[country.getCurrentItem()][city
								.getCurrentItem()][ccity.getCurrentItem()];
			}
		});

		city.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updatecCities(ccity, ccities, country.getCurrentItem(),
						newValue);
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ " | "
						+ AddressData.CITIES[country.getCurrentItem()][city
								.getCurrentItem()]
						+ " | "
						+ AddressData.COUNTIES[country.getCurrentItem()][city
								.getCurrentItem()][ccity.getCurrentItem()];
			}
		});

		ccity.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ " | "
						+ AddressData.CITIES[country.getCurrentItem()][city
								.getCurrentItem()]
						+ " | "
						+ AddressData.COUNTIES[country.getCurrentItem()][city
								.getCurrentItem()][ccity.getCurrentItem()];
			}
		});

		country.setCurrentItem(1);
		city.setCurrentItem(1);
		ccity.setCurrentItem(1);
		return contentView;
	}

	/**
	 * Updates the city wheel
	 */
	private void updateCities(WheelView city, String cities[][], int index) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities[index]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);
	}

	/**
	 * Updates the ccity wheel
	 */
	private void updatecCities(WheelView city, String ccities[][][], int index,
			int index2) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				ccities[index][index2]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);
	}

	/**
	 * Adapter for countries
	 */
	private class CountryAdapter extends AbstractWheelTextAdapter {
		// Countries names
		private String countries[] = AddressData.PROVINCES;

		/**
		 * Constructor
		 */
		protected CountryAdapter(Context context) {
			super(context, R.layout.wheelcity_country_layout, NO_RESOURCE);
			setItemTextResource(R.id.wheelcity_country_name);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return countries.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return countries[index];
		}
	}

	private void data() {
		changeLayoutClick();
	}

	private void freeBitmap() {
		if (bm != null) {
			bm.recycle();
			bm = null;
		}
		System.gc();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		freeBitmap();
	}

	private void showOutMenu() {
		LayoutInflater layoutIn = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutIn.inflate(R.layout.popmenu_personal_data_sex, null);
		RelativeLayout popmenu_personal_data_sex_2_rel = (RelativeLayout) view
				.findViewById(R.id.popmenu_personal_data_sex_2_rel);
		RelativeLayout popmenu_personal_data_sex_3_rel = (RelativeLayout) view
				.findViewById(R.id.popmenu_personal_data_sex_3_rel);
		Button popmenu_personal_data_sex_5 = (Button) view
				.findViewById(R.id.popmenu_personal_data_sex_5);
		popmenu_personal_data_sex_2_rel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						window.dismiss();
						mlayout_tab_mine_personal_data_sex_txt1
								.setText(R.string.men);
						mimag_picture.setImageResource(R.drawable.zhinan);
						modifyGenderCode = 0;
					}
				});
		popmenu_personal_data_sex_3_rel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						window.dismiss();
						mlayout_tab_mine_personal_data_sex_txt1
								.setText(R.string.women);
						mimag_picture.setImageResource(R.drawable.zhinv);
						modifyGenderCode = 1;
					}
				});
		popmenu_personal_data_sex_5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				window.dismiss();
			}
		});
		window = null;
		if (window == null) {
			window = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, true);
			window.setAnimationStyle(R.style.popuStyle);
			window.setBackgroundDrawable(new BitmapDrawable());
		}
		window.showAtLocation(
				view.findViewById(R.id.personal_data_sex_popmenu),
				Gravity.BOTTOM, 0, 0);
		window.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mimageview_tab_mine_personal_data.setVisibility(View.GONE);
			}
		});
	}
	public  FileUtil MxFileUtil = FileUtil.getInstance();
	private void showOutMenuHead() {
		LayoutInflater layoutIn = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutIn.inflate(R.layout.popmenu_personal_data_head, null);
		Button pop_personal_data_head_btn1 = (Button) view
				.findViewById(R.id.pop_personal_data_head_btn1);
		Button pop_personal_data_head_btn2 = (Button) view
				.findViewById(R.id.pop_personal_data_head_btn2);
		Button pop_personal_data_head_btn3 = (Button) view
				.findViewById(R.id.pop_personal_data_head_btn3);
		pop_personal_data_head_btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// startActivityForResult(camera, 100);
				File photoFileFolder = MxFileUtil.getTakePhotoFileFolder();
				if (photoFileFolder == null) {
					ToastUtil.show(getResources().getString(R.string.invalidSD),TabMinePersonalDataActivity.this);
				} else {
					currentAddFileName = String.valueOf(System
							.currentTimeMillis());
					File photoFile = MxFileUtil.getTakePhotoFile(
							photoFileFolder, currentAddFileName);
					if (photoFile != null) {
						Uri u = Uri.fromFile(photoFile);
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
						/*
						 * 指定一个路径，可以获得到照片原始尺寸大小图片，
						 * 且onresult中的data会为null，需要从uri中获取图片 若不指定，只能获取到照片缩略图
						 */
						intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
						startActivityForResult(intent, 100);
					} else {
						ToastUtil.show(getResources().getString(
								R.string.error_create_photo_file),TabMinePersonalDataActivity.this);
					}
				}
			}
		});
		pop_personal_data_head_btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, 101);
			}
		});
		pop_personal_data_head_btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				window.dismiss();
			}
		});
		window = null;
		if (window == null) {
			window = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, true);
			window.setAnimationStyle(R.style.popuStyle);
			window.setBackgroundDrawable(new BitmapDrawable());
		}
		window.showAtLocation(
				view.findViewById(R.id.popmenu_personal_data_head),
				Gravity.BOTTOM, 0, 0);
		window.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mimageview_tab_mine_personal_data.setVisibility(View.GONE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100) {
			if (resultCode == RESULT_OK) {
				freeBitmap();
				if (data != null && data.getExtras() != null) {
					bm = (Bitmap) data.getExtras().getParcelable("data");
				}
				try {
					File photoFile = MxFileUtil.getTakePhotoFile(
							MxFileUtil.getTakePhotoFileFolder(),
							currentAddFileName);
					Uri u = Uri.parse(photoFile.getAbsolutePath());
					if (u != null) {
						DisplayMetrics dm = getResources()
								.getDisplayMetrics();
						int dpWSize = 120;
						int dpHSize = 120;
						// bm = MxImageUtil.resizeImage(
						// photoFile.getAbsolutePath(), dpWSize, dpHSize);
						// ((CircleImageView)
						// findViewById(R.id.layout_tab_mine_personal_data_head_portrait_img))
						// .setImageBitmap(bm);
						imageLoader
								.displayImage(
										"file://" + photoFile.getAbsolutePath(),
										(CircleImageView) findViewById(R.id.layout_tab_mine_personal_data_head_portrait_img),
										options);
					} else {
						ToastUtil.show(getResources().getString(
								R.string.error_no_get_origin_pic),this);
					}
					// bm写成一个File对象，用cover来存
					if (photoFile != null) {
						// try {
						// FileOutputStream out = new FileOutputStream(
						// photoFile);
						// bm.compress(Bitmap.CompressFormat.JPEG, 80, out);
						// out.flush();
						// out.close();
						// } catch (IOException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
						cover = photoFile;
					} else {
						ToastUtil.show(getResources().getString(
								R.string.invalidSD),this);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtil.show(getResources().getString(
							R.string.error_no_get_origin_pic),this);
				}
				window.dismiss();
			}
		} 
		else if(requestCode == Constant.RC_SELECTABLUMN && resultCode == RESULT_OK){
			if (Bimp.tempSelectBitmap.size() > 0) {
				String picturePath = Bimp.tempSelectBitmap.get(0).getImagePath();
				imageLoader.displayImage(
						"file://" + picturePath,
						(CircleImageView) findViewById(R.id.layout_tab_mine_personal_data_head_portrait_img),
						options);
		        cover = new File(picturePath);
				
				
			}	
			
			
			
		}
//		else if (requestCode == 101 && resultCode == RESULT_OK
//				&& null != data) 
//		
//		{
//		
//			Uri selectedImage = data.getData();
//
//			String[] filePathColumns = { MediaStore.Images.Media.DATA };
//			Cursor cursor = this.getContentResolver().query(selectedImage,
//					filePathColumns, null, null, null);
//			// Cursor cursor = managedQuery(selectedImage, filePathColumns,
//			// null, null, null);
//			if (cursor != null) {
//				if (cursor.moveToFirst()) {
//					int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
//					picturePath = cursor.getString(columnIndex);
//					// cover = MxFileUtil.loadFile(picturePath);
//					imageLoader
//							.displayImage(
//									"file://" + picturePath,
//									(CircleImageView) findViewById(R.id.layout_tab_mine_personal_data_head_portrait_img),
//									options);
//					cover = new File(picturePath);
//				}
//				// 4.0以上会自动关闭
//				if (VERSION.SDK_INT < 14) {
//					cursor.close();
//				}
//
//			} else {
//				imageLoader
//						.displayImage(
//								"file://" + selectedImage.getPath(),
//								(CircleImageView) findViewById(R.id.layout_tab_mine_personal_data_head_portrait_img),
//								options);
//				cover = new File(selectedImage.getPath());
//			}
//			// freeBitmap();
//			// bm = BitmapFactory.decodeFile(picturePath);
//			// mlayout_tab_mine_personal_data_head_portrait_img.setImageBitmap(bm);
//			window.dismiss();
//		} 
		
		
		else if (requestCode == Constant.RC_FITTYPE) {
			if(resultCode == 100){
				return;
			}
			if(resultCode == 200){
				return;
			}
			interested = data.getStringExtra(Constant.BUNDLE_FIT_INTEREST);
				setInterested(interested);
				gralleryAdapter.freshData(array);
			
		} else {
			if (0 == requestCode && data != null) {
				String individualitysignature = data.getExtras().getString(
						"individualitysignature");
				mlayout_tab_mine_personal_data_individuality_singnature_content_txt1
						.setText(individualitysignature);
			}
			if (1 == requestCode && data != null) {
				String fitness_goals = data.getExtras().getString(
						"fitnessgoals");
				mlayout_tab_mine_personal_data_the_purpose_of_body_building_txt1
						.setText(fitness_goals);
			}
			if (2 == requestCode && data != null) {
				String fitnessgood = data.getExtras().getString("fitnessgood");
				mlayout_tab_mine_personal_data_fitness_is_good_at_txt1
						.setText(fitnessgood);
			}
			if (3 == requestCode && data != null) {
				String nickname = data.getExtras().getString("nickname");
				mlayout_tab_mine_personal_data_nickname_txt1.setText(nickname);
			}
			if (5 == requestCode && data != null) {
				String often = data.getExtras().getString("often");
				mlayout_tab_mine_personal_data_often_go_to_the_gym_txt1
						.setText(often);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onGetData(Object data, int requestCode, String response) {
		switch (requestCode) {
		case PERSON:
			MxPersonalDataResult sares = (MxPersonalDataResult) data;
			if (sares.getCode() == 1) {
				if (sares.getData() != null
						&& sares.getData().getProfile() != null) {
					String token = BaseApp.mInstance.getUser().getToken();
					sares.getData().getProfile().setToken(token);
					BaseApp.mInstance.setUser(sares.getData().getProfile());
					imageLoader.displayImage(BaseApp.mInstance.getUser()
							.getAvatar(),
							mlayout_tab_mine_personal_data_head_portrait_img);
					// BaseApp.mInstance.getUser().setInterests(sares.getData().getProfile()
					// .getInterests());
					mlayout_tab_mine_personal_data_nickname_txt1
							.setText(BaseApp.mInstance.getUser().getName());
					mlayout_tab_mine_personal_data_individuality_singnature_content_txt1
							.setText(sares.getData().getProfile()
									.getSignature());
					Log.e(TAG, "user gender data = "
							+ sares.getData().getProfile().getGender());
					gender = ActivityTool.getGender(sares.getData()
							.getProfile().getGender());
					mlayout_tab_mine_personal_data_sex_txt1
							.setText(gender.genderString);
					if (gender == null) {
						mimag_picture.setImageResource(R.drawable.zhinan);
					} else {
						if (gender.genderString == R.string.men) {
							mimag_picture.setImageResource(R.drawable.zhinan);
						} else {
							mimag_picture.setImageResource(R.drawable.zhinv);
						}
					}
					mlayout_tab_mine_personal_data_age_txt1
							.setText(String.valueOf(BaseApp.mInstance.getUser()
									.getTrue_age()));
					if (BaseApp.mInstance.getUser().getTrue_age() < 1) {
						mlayout_tab_mine_personal_data_age_txt1.setText("");
					}
					mlayout_tab_mine_personal_data_the_host_city_txt1
							.setText(BaseApp.mInstance.getUser().getAddress());

					mlayout_tab_mine_personal_data_the_purpose_of_body_building_txt1
							.setText(BaseApp.mInstance.getUser().getTarget());

					mlayout_tab_mine_personal_data_fitness_is_good_at_txt1
							.setText(BaseApp.mInstance.getUser().getSkill());

					mlayout_tab_mine_personal_data_often_go_to_the_gym_txt1
							.setText(BaseApp.mInstance.getUser().getOften());

					mlayout_tab_mine_personal_data_id_txt1
							.setText(Constants.IDENTITY_RES[BaseApp.mInstance
									.getUser().getIdentity()]);
					array.clear();
					setInterested(BaseApp.mInstance.getUser().getInterests());
					gralleryAdapter.freshData(array);
				}
			}
			break;
		case PERSONS:
			MsgResult sares2 = (MsgResult) data;
			if (sares2.getCode() == 1) {
				ToastUtil.show(getResources().getString(R.string.dataupdate),this);
			}
			break;
		default:
			break;
		}
	}

	private void setInterested(String interest) {
		array.clear();
//		if (interest != null && interest.trim().length() > 0) {
//			String[] interested = interest.split(Constants.STR_SPLITE_DOT);
//			if (interested != null && interested.length > 0
//					&& BaseApp.mInstance.getIconmap().size() > 0) {
//				for (int i = 0; i < interested.length; i++) {
//					int id = Integer.parseInt(interested[i]);
//					array.add(BaseApp.mInstance.iconGet(id).getIcon());
//				}
//			}
//		}
	}

	@Override
	public void onError(String reason, int requestCode) {

	}

}

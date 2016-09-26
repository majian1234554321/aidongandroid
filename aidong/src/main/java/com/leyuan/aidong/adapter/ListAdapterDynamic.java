package com.leyuan.aidong.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.leyuan.aidong.ui.BaseApp;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.vedio.media.ImageShowerActivity;
import com.leyuan.aidong.ui.activity.discover.UserWhoClickLikeActivity;
import com.leyuan.aidong.utils.common.Constant;
import com.leyuan.aidong.utils.interfaces.OnCommentAndLikeClickListenner;
import com.leyuan.aidong.utils.interfaces.OnMoreCommentClickListenner;
import com.leyuan.aidong.entity.model.AttributeCommentItem;
import com.leyuan.aidong.entity.model.AttributeDynamics;
import com.leyuan.aidong.entity.model.AttributeFilm;
import com.leyuan.aidong.entity.model.AttributeImages;
import com.leyuan.aidong.entity.model.Dynamic;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.utils.ActivityTool;
import com.leyuan.aidong.utils.MyDbUtils;
import com.leyuan.aidong.utils.SmileUtils;
import com.leyuan.aidong.utils.Utils;
import com.leyuan.aidong.widget.customview.CircleImageView;
import com.leyuan.aidong.widget.customview.HorizontalListView;
import com.leyuan.aidong.widget.customview.PhotoGridView;
import com.leyuan.aidong.widget.customview.SquareRelativeLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

public class ListAdapterDynamic extends AbstractCommonAdapter {
	String TAG = "ListAdapterSportsClub";
	// 换性别
	private Context context;
	private ArrayList<Dynamic> array;
	private OnMoreCommentClickListenner mOnMoreCommentClickListenner;
	private OnCommentAndLikeClickListenner mOnCommentAndLikeClickListenner;
	private OnClickListener mOnAvatarClickListener;
	private OnClickListener mVideoClickListener;
	private MediaController mMediaCtrl;
	private OnClickListener mShareClickListener;
	public VideoView mVideoView;
	public int playPosition = -1;
	public int currentIndex = -1;
	public boolean isPaused;
	public boolean isPlaying;
	private boolean canGetImage;
	private int width_big_img;
	private int width_small_img;
	
	private DisplayImageOptions option_three_one = new DisplayImageOptions.Builder()
//	.showImageOnLoading(R.drawable.icon_default_number_three)
//	.showImageForEmptyUri(R.drawable.icon_default_number_three)
//	.showImageOnFail(R.drawable.icon_default_number_three)
	.cacheInMemory(true)
	.cacheOnDisk(true).considerExifParams(true)
	.bitmapConfig(Bitmap.Config.RGB_565).build();
	
	
	private DisplayImageOptions option_three_three = new DisplayImageOptions.Builder()
//	.showImageOnLoading(R.drawable.icon_default_number_six)
//	.showImageForEmptyUri(R.drawable.icon_default_number_six)
//	.showImageOnFail(R.drawable.icon_default_number_six)
	.cacheInMemory(true)
	.cacheOnDisk(true).considerExifParams(true)
	.bitmapConfig(Bitmap.Config.RGB_565).build();
	
	private DisplayImageOptions option_head = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.icon_avatar_default)
	.showImageForEmptyUri(R.drawable.icon_avatar_default)
	.showImageOnFail(R.drawable.icon_avatar_default)
	.cacheInMemory(true)
	.cacheOnDisk(true).considerExifParams(true)
	.bitmapConfig(Bitmap.Config.RGB_565).build();

	public ListAdapterDynamic(Context context) {
		this.context = context;
		this.array = new ArrayList<Dynamic>();
		WindowManager wm = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		width_big_img = ((width * 2) - Utils.dip2px(context, 6) ) / 3;
		width_small_img = ((width - Utils.dip2px(context, 9)) /3);
		
		
	}

	public void freshData(ArrayList<Dynamic> array) {
		this.array.clear();
		this.array.addAll(array);
		notifyDataSetChanged();
	}
	
	public void addData(ArrayList<Dynamic> array){
		this.array.addAll(array);
		notifyDataSetChanged();
	}
	
	public void clearData(){
		this.array.clear();
	}

	public void setOnAvatarClickListener(OnClickListener l) {
		mOnAvatarClickListener = l;
	}

	public void setOnMoreCommentClickListenner(OnMoreCommentClickListenner l) {
		mOnMoreCommentClickListenner = l;
	}

	public void setOnShareClickListenner(OnClickListener l) {
		mShareClickListener = l;
	}

	public void setOnCommentAndLikeClickListenner(
			OnCommentAndLikeClickListenner l) {
		mOnCommentAndLikeClickListenner = l;
	}

	public void setVideoClickListener(OnClickListener l) {
		mVideoClickListener = l;
	}

	public void startGetIconImage() {
		canGetImage = true;
		notifyDataSetChanged();
	}

	public void stopGetIconImage() {
		canGetImage = false;
		notifyDataSetChanged();
	}

	public void stopVideo() {
		if (mVideoView != null && isPlaying) {
			currentIndex = -1;
			playPosition = -1;
			mVideoView.pause();
			mVideoView.stopPlayback();
			mVideoView.seekTo(0);
			isPlaying = false;
			isPaused = false;
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return array.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final int mPosition = position;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_sports_club_copy, null);
			holder = new ViewHolder();
			holder.iconView = (CircleImageView) convertView
					.findViewById(R.id.imgIconView);
			holder.usernameView = (TextView) convertView
					.findViewById(R.id.txtUsername);
//			holder.ageVew = (TextView) convertView.findViewById(R.id.txtAge);
			holder.timeView = (TextView) convertView.findViewById(R.id.txtTime);
			// holder.mPhotoGridView = (PhotoGridView) convertView
			// .findViewById(R.id.gridPhotoView);

			holder.mMoreCommentView = (TextView) convertView
					.findViewById(R.id.txtCommentMore);
			holder.mBtnComment = (RelativeLayout) convertView
					.findViewById(R.id.btnComment_rel);
			holder.btnGood_rel_zan_img = (ImageView) convertView
					.findViewById(R.id.btnGood_rel_zan_img);
			holder.vipView = (ImageView) convertView.findViewById(R.id.imgVip);
			holder.officalView = (ImageView) convertView
					.findViewById(R.id.imgVerify);
			holder.identityView = (ImageView) convertView
					.findViewById(R.id.imgTeacher);
			holder.txtSubjectConentView = (TextView) convertView
					.findViewById(R.id.txtSubjectConent);
			holder.layoutbtnGood = (RelativeLayout) convertView
					.findViewById(R.id.btnGood_rel_zan);
			holder.goodCount = (TextView) convertView
					.findViewById(R.id.btnGood_rel_zan_txt_data);
//			holder.genderView = (ImageView) convertView
//					.findViewById(R.id.img_sports_club);
			holder.layout_commment_content = (LinearLayout) convertView
					.findViewById(R.id.layout_commment_content);
			holder.com[0] = (TextView) convertView
					.findViewById(R.id.txtComment1);
			holder.com[1] = (TextView) convertView
					.findViewById(R.id.txtComment2);
			// holder.view3 = (RelativeLayout) convertView
			// .findViewById(R.id.view3);
			// holder.view2 = (View) convertView.findViewById(R.id.view2);
			// holder.view1 = (View) convertView.findViewById(R.id.view1);
			holder.videoLayout = (SquareRelativeLayout) convertView
					.findViewById(R.id.videoLayout);
			holder.videoCover = (ImageView) convertView
					.findViewById(R.id.video_image);
			holder.videoPlayBtn = (ImageButton) convertView
					.findViewById(R.id.video_play_btn);
//			holder.layout2 = (RelativeLayout) convertView
//					.findViewById(R.id.layout2);
			holder.btnGood_rel_share = (RelativeLayout) convertView
					.findViewById(R.id.btnGood_rel_share);
			// holder.ivPhotoView = (ImageView) convertView
			// .findViewById(R.id.ivPhotoView);
			holder.txt_allwen = (TextView) convertView
					.findViewById(R.id.txt_allwen);
			holder.girdview_main = (PhotoGridView) convertView
					.findViewById(R.id.girdview_main);
			holder.girdview_below = (GridView) convertView
					.findViewById(R.id.girdview_below);

			holder.layout_three_imgs = (RelativeLayout) convertView
					.findViewById(R.id.layout_three_imgs);
			holder.three_img_first = (ImageView) convertView
					.findViewById(R.id.three_img_first);
			holder.three_img_second = (ImageView) convertView
					.findViewById(R.id.three_img_second);
			holder.three_img_third = (ImageView) convertView
					.findViewById(R.id.three_img_third);
			holder.list_horizontal = (HorizontalListView) convertView
					.findViewById(R.id.list_horizontal);
			holder.layout_like_user = (RelativeLayout) convertView
					.findViewById(R.id.layout_like_user);
			holder.rel_subject_conent = (RelativeLayout) convertView
					.findViewById(R.id.rel_subject_conent);
			holder.btnComment = (TextView) convertView
					.findViewById(R.id.btnComment);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (array.size() > 0) {
			Dynamic dynamic = array.get(position);
			UserCoach user = dynamic.getPublisher();

			if (user != null) {
				// Gender gender = ActivityTool.getGender(user.getGender());
				ArrayList<Integer> tags = user.getTags();
				holder.usernameView.setText(user.getName());
				holder.iconView.setTag(user);
				holder.iconView.setOnClickListener(mOnAvatarClickListener);
				imageLoader.displayImage(user.getAvatar(), holder.iconView,
						option_head);
//				holder.ageVew.setText(String.valueOf(user.getTrue_age()));
//				if (user.getTrue_age() < 1) {
//					holder.ageVew.setVisibility(View.GONE);
//					;
//				}
				// holder.genderView.setImageResource(gender.drawableRes);
//				if (user.getGender() == 0) {
//					// holder.layout2.setBackground(context.getResources().getDrawable(R.drawable.sex_nan));
//					Utils.setLayoutBackgroud(holder.layout2, context
//							.getResources().getDrawable(R.drawable.sex_nan));
////					holder.genderView
////							.setImageResource(R.drawable.sex_nan_fuhao);
//				} else {
//					// holder.layout2.setBackground(context.getResources().getDrawable(R.drawable.sex_nv));
//					Utils.setLayoutBackgroud(holder.layout2, context
//							.getResources().getDrawable(R.drawable.sex_nv));
//					holder.genderView.setImageResource(R.drawable.sen_nv_fuhao);
//				}
				if (tags.get(Constant.ID_VIP) > 0) {
					holder.vipView.setVisibility(View.VISIBLE);
				} else {
					holder.vipView.setVisibility(View.GONE);
				}
				holder.usernameView.setTextColor(context.getResources()
						.getColor(R.color.color_black));
				if (tags.get(Constant.ID_OFFICAL) > 0) {
					holder.officalView.setVisibility(View.VISIBLE);
//					holder.layout2.setVisibility(View.GONE);
					holder.usernameView.setTextColor(context.getResources()
							.getColor(R.color.color_tab_bg));
				} else {
					holder.officalView.setVisibility(View.GONE);
//					holder.layout2.setVisibility(View.VISIBLE);
				}
				if (tags.get(Constant.ID_COACH) > 0) {
					holder.identityView.setVisibility(View.VISIBLE);
					holder.usernameView.setTextColor(context.getResources()
							.getColor(R.color.color_tab_bg));
				} else {
					holder.identityView.setVisibility(View.GONE);
				}
			}

			if (dynamic != null) {
				long createTime = dynamic.getCreated();
				long currentTime = System.currentTimeMillis() / 1000;
				long during = currentTime - createTime;
				String timestr = null;
				if (during < 3600) {
					if ((during / 60) == 0) {
						timestr = "刚刚";
					} else {
						timestr = String.valueOf((during / 60))
								+ context.getResources().getString(
										R.string.s_minute);
					}
				} else if (during < 3600 * 24) {
					timestr = String.valueOf((during / 3600))
							+ context.getResources().getString(R.string.s_hour);
				} else if (during < 3600 * 24 * 7) {
					timestr = String.valueOf((during / (3600 * 24)))
							+ context.getResources().getString(R.string.s_day);
				} else if (during < 3600 * 24 * 30) {
					timestr = String.valueOf((during / (3600 * 24 * 7)))
							+ context.getResources().getString(R.string.s_week);
				} else if (during < 3600 * 24 * 365) {
					timestr = String.valueOf((during / (3600 * 24 * 30)))
							+ context.getResources()
									.getString(R.string.s_month);
				} else {
					timestr = String.valueOf((during / (3600 * 24 * 365)))
							+ context.getResources().getString(R.string.s_year);
				}
				holder.timeView.setText(timestr.toString());
				holder.txtSubjectConentView.setText(dynamic.getContent());
				holder.txtSubjectConentView.getLineCount();
				holder.txtSubjectConentView.setEllipsize(TextUtils.TruncateAt
						.valueOf("END"));
				Layout l = ((TextView) holder.txtSubjectConentView
						.findViewById(R.id.txtSubjectConent)).getLayout();
				if (l != null) {
					int lines = l.getLineCount();
					if (lines > 0) {
						if (l.getEllipsisCount(lines - 1) > 0) {
							holder.txt_allwen.setVisibility(View.VISIBLE);
						} else if (lines > 4) {
							holder.txt_allwen.setVisibility(View.VISIBLE);
						} else {
							holder.txt_allwen.setVisibility(View.GONE);
						}
					}
				}
				if (holder.txtSubjectConentView.getText().toString().trim()
						.equals("")) {
					holder.txtSubjectConentView.setVisibility(View.GONE);
					holder.rel_subject_conent.setVisibility(View.GONE);
				} else {
					holder.txtSubjectConentView.setVisibility(View.VISIBLE);
					holder.rel_subject_conent.setVisibility(View.VISIBLE);
				}
				holder.btnGood_rel_zan_img.setImageResource(R.drawable.btn_praise_normal);
				if (BaseApp.mInstance.isLogin()) {
					int goodNo = dynamic.getId();
					if (MyDbUtils.isZan("" + goodNo)) {
						holder.btnGood_rel_zan_img
								.setImageResource(R.drawable.btn_praise_pressed);
						holder.goodCount.setTextColor(Color.parseColor("#FF572F"));
					} else {
						holder.btnGood_rel_zan_img
								.setImageResource(R.drawable.btn_praise_normal);
						holder.goodCount.setTextColor(Color.parseColor("#8d97a6"));
					}
				}

				if (dynamic.getComments() != null) {
					int commentCount = dynamic.getComments()
							.getCount();
					long likes = dynamic.getLikes();
					final ArrayList<AttributeImages> images = dynamic
							.getImage();
					AttributeFilm film = dynamic.getFilm();
					ArrayList<AttributeCommentItem> itemArray = dynamic.getComments().getItem();
					Log.e(TAG, "itemArray = " + itemArray);
					holder.btnComment.setText("" +commentCount);
					if (commentCount > 0) {
						String text = "";
						if (commentCount > 2) {
							text = context.getResources().getString(
									R.string.more)
									+ (commentCount - 2)
									+ context.getResources().getString(
											R.string.comment_count);
							holder.mMoreCommentView.setVisibility(View.VISIBLE);
						} else {
							holder.mMoreCommentView.setVisibility(View.GONE);
						}

						holder.mMoreCommentView.setTag(position);
						holder.mMoreCommentView.setText(text);
						holder.mMoreCommentView
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										mOnMoreCommentClickListenner
												.onMoreCommentClick(v);
									}
								});
                        
						
					} else {
						holder.mMoreCommentView.setVisibility(View.GONE);
						
						
					}
					holder.goodCount.setText(String.valueOf(likes));
					holder.layoutbtnGood.setTag(position);
					holder.mBtnComment.setTag(position);
					holder.txt_allwen.setTag(position);
					holder.txt_allwen.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							mOnCommentAndLikeClickListenner.onCommentClick(v);
						}
					});
					holder.layoutbtnGood
							.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									mOnCommentAndLikeClickListenner
											.onLikeClick(v);
								}
							});
					holder.mBtnComment
							.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									mOnCommentAndLikeClickListenner
											.onCommentClick(v);
								}
							});
					holder.btnGood_rel_share.setTag(dynamic);
					holder.btnGood_rel_share
							.setOnClickListener(mShareClickListener);
					
					if (film != null){
					if(!TextUtils.isEmpty(film.getCover()))
					 {
						holder.videoLayout.setVisibility(View.VISIBLE);
						holder.girdview_main.setVisibility(View.GONE);
						holder.girdview_below.setVisibility(View.GONE);
						holder.layout_three_imgs.setVisibility(View.GONE);
						int color = context.getResources().getColor(
								R.color.color_black);
						holder.videoCover.setBackgroundColor(color);
						imageLoader.displayImage(film.getCover(),
								holder.videoCover, options);

						holder.videoPlayBtn.setVisibility(View.VISIBLE);
						holder.videoCover.setVisibility(View.VISIBLE);
						holder.videoPlayBtn.setTag(dynamic);
						if (mVideoClickListener != null) {
							holder.videoPlayBtn
									.setOnClickListener(mVideoClickListener);
						}
					} else {
						holder.videoLayout.setVisibility(View.GONE);
					}
					}else{
						holder.videoLayout.setVisibility(View.GONE);
					}

					if (images != null) {
						switch (images.size()) {
						
						case 0:
							holder.girdview_below.setVisibility(View.GONE);
							holder.layout_three_imgs.setVisibility(View.GONE);
							holder.girdview_main.setVisibility(View.GONE);
							break;
						case 1:
							holder.girdview_below.setVisibility(View.GONE);
							holder.layout_three_imgs.setVisibility(View.GONE);
							holder.girdview_main.setVisibility(View.VISIBLE);
							holder.girdview_main.setNumColumns(1);
							holder.girdview_main
									.setAdapter(new DynamicPhotoGridAdapter(
											context, images));

							break;
						case 2:
							holder.girdview_below.setVisibility(View.GONE);
							holder.layout_three_imgs.setVisibility(View.GONE);
							holder.girdview_main.setVisibility(View.VISIBLE);
							holder.girdview_main.setNumColumns(2);
							holder.girdview_main
									.setAdapter(new DynamicPhotoGridAdapter(
											context, images));

							break;
						case 3:
							holder.girdview_main.setVisibility(View.GONE);
							holder.girdview_below.setVisibility(View.GONE);
							holder.layout_three_imgs
									.setVisibility(View.VISIBLE);
							
							LayoutParams params1 = holder.three_img_first.getLayoutParams();
							params1.width = width_big_img;
							params1.height = width_big_img;
							 holder.three_img_first.setLayoutParams(params1);
							LayoutParams params2 = holder.three_img_second.getLayoutParams();
							params2.width = width_small_img;
							params2.height = width_small_img;
							 holder.three_img_second.setLayoutParams(params2);
							 LayoutParams params3 = holder.three_img_third.getLayoutParams();
							 params3.width = width_small_img;
							 params3.height = width_small_img;
								 holder.three_img_third.setLayoutParams(params3);
							
							
							
							
							imageLoader.displayImage(images.get(0).getUrl(0, 0),
									holder.three_img_first, option_three_one);
							imageLoader.displayImage(images.get(1).getUrl(0, 0),
									holder.three_img_second, option_three_three);
							imageLoader.displayImage(images.get(2).getUrl(0, 0),
									holder.three_img_third, option_three_three);
							holder.three_img_first
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											showBigImage(images, 0);
										}
									});
							holder.three_img_second
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											showBigImage(images, 1);
										}
									});
							holder.three_img_third
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											showBigImage(images, 2);
										}
									});

							break;
						case 4:
							holder.girdview_below.setVisibility(View.GONE);
							holder.layout_three_imgs.setVisibility(View.GONE);
							holder.girdview_main.setVisibility(View.VISIBLE);
							holder.girdview_main.setNumColumns(2);
							holder.girdview_main
									.setAdapter(new DynamicPhotoGridAdapter(
											context, images));

							break;
						case 5:
							holder.layout_three_imgs.setVisibility(View.GONE);
							holder.girdview_main.setVisibility(View.VISIBLE);
							holder.girdview_below.setVisibility(View.VISIBLE);
							holder.girdview_main.setNumColumns(2);
							holder.girdview_main
									.setAdapter(new DynamicPhotoGridAdapter(
											context, images.subList(0, 2)));
							holder.girdview_below.setNumColumns(3);
							holder.girdview_below
									.setAdapter(new DynamicPhotoGridAdapter(
											context, images.subList(2, 5)));
							holder.girdview_below
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(
										AdapterView<?> parent, View view,
										int position, long id) {
									showBigImage(images, position +2);
								}
							});

							break;
						case 6:
							holder.girdview_below.setVisibility(View.GONE);
							holder.layout_three_imgs.setVisibility(View.GONE);
							holder.girdview_main.setVisibility(View.VISIBLE);
							holder.girdview_main.setNumColumns(3);
							holder.girdview_main
									.setAdapter(new DynamicPhotoGridAdapter(
											context, images));

							break;

						default:
							break;
						}
						holder.girdview_main
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										showBigImage(images, position);
									}
								});

					} else {
						holder.girdview_main.setVisibility(View.GONE);
						holder.girdview_below.setVisibility(View.GONE);
						holder.layout_three_imgs.setVisibility(View.GONE);

					}


					final ArrayList<AttributeDynamics.LikeUser> like_user = dynamic.getLike_user();
					final int dynamicId = dynamic.getId();
					if (like_user != null) {
						if (like_user.size() > 0) {
							holder.layout_like_user.setVisibility(View.VISIBLE);
							holder.list_horizontal
									.setAdapter(new LikeUserHorizontalListAdapter(
											context, like_user));
							holder.list_horizontal.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									if (position < like_user.size()) {
										ActivityTool
										.startShowActivity(context, like_user.get(position).getUser());
									}
									else{
										Intent intent = new Intent(context, UserWhoClickLikeActivity.class);
										intent.putExtra("id", dynamicId);
										context.startActivity(intent);
//										Toast.makeText(context, "跳到like我的人界面", 0).show();
									}
									
								}
							});
						}
						else {
							holder.layout_like_user.setVisibility(View.GONE);
						}
					} else {
						holder.layout_like_user.setVisibility(View.GONE);
					}

					if (itemArray != null && itemArray.size() > 0) {
						holder.layout_commment_content
								.setVisibility(View.VISIBLE);
						int size = itemArray.size();
						if (size < 2) {
							AttributeCommentItem item = itemArray.get(0);
							holder.com[0].setText(item.getUser().getName()
									+ ":" + item.getContent());
							holder.com[0].setVisibility(View.VISIBLE);
							holder.com[1].setVisibility(View.GONE);

							String content = holder.com[0].getText().toString();
							Spannable builder = new SpannableStringBuilder(
									content);
							ForegroundColorSpan blackSpan = new ForegroundColorSpan(
									Color.BLACK);
							builder.setSpan(blackSpan, item.getUser().getName()
									.length() + 1, content.length(),
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							SmileUtils.addSmiles(context, builder);
							holder.com[0].setText(builder);

						} else {
							for (int i = 0; i < 2; i++) {
								AttributeCommentItem item = itemArray.get(i);
								holder.com[i].setText(item.getUser().getName()
										+ ":" + item.getContent());
								holder.com[i].setVisibility(View.VISIBLE);

								String content = holder.com[i].getText()
										.toString();
								SpannableStringBuilder builder = new SpannableStringBuilder(
										content);
								ForegroundColorSpan blackSpan = new ForegroundColorSpan(
										Color.BLACK);
								builder.setSpan(blackSpan, item.getUser()
										.getName().length() + 1,
										content.length(),
										Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
								SmileUtils.addSmiles(context, builder);
								holder.com[i].setText(builder);
							}
						}
					} else {
						holder.layout_commment_content.setVisibility(View.GONE);
						for (TextView t : holder.com) {
							t.setVisibility(View.GONE);
						}
					}

				}
			}
		}
		return convertView;
	}

	public void showBigImage(ArrayList<AttributeImages> imageArray,
			int currentPosition) {
		Intent i = new Intent(context, ImageShowerActivity.class);
		i.putParcelableArrayListExtra(Constant.BUNDLE_BIGIMAGEITEM,
				imageArray);
		i.putExtra(Constant.BUNDLE_BIGIMAGEITEM_INDEX, currentPosition);
		context.startActivity(i);
	}

	private class ViewHolder {
		CircleImageView iconView;
//		ImageView genderView;
		// , ivPhotoView
		TextView usernameView;
//		TextView ageVew;
		TextView timeView;;
		TextView txtSubjectConentView;
		TextView goodCount;
		TextView[] com = new TextView[2];
		// PhotoGridView mPhotoGridView;
		TextView mMoreCommentView;

		// RelativeLayout view3;
		// View view2;
		// View view1;
		TextView txt_allwen;
		ImageView vipView;
		ImageView officalView;
		ImageView identityView;
		ImageView btnGood_rel_zan_img;
		RelativeLayout layoutbtnGood;
		RelativeLayout mBtnComment;
		SquareRelativeLayout videoLayout;
//		RelativeLayout layout2;
		RelativeLayout btnGood_rel_share;
		ImageView videoCover;
		ImageButton videoPlayBtn;
		LinearLayout layout_commment_content;
		PhotoGridView girdview_main;
		GridView girdview_below;
		RelativeLayout layout_three_imgs;
		ImageView three_img_first;
		ImageView three_img_second;
		ImageView three_img_third;
		HorizontalListView list_horizontal;
		RelativeLayout layout_like_user;
		RelativeLayout rel_subject_conent;
		TextView btnComment;

	}

}

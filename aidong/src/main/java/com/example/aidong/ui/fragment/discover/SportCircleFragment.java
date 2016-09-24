package com.example.aidong.ui.fragment.discover;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aidong.ui.BaseApp;
import com.example.aidong.ui.BaseFragment;
import com.example.aidong.utils.common.BaseUrlLink;
import com.example.aidong.R;
import com.example.aidong.ui.activity.vedio.media.VideoPlayerActivity;
import com.example.aidong.ui.activity.mine.account.LoginActivity;
import com.example.aidong.ui.activity.discover.CommentDetailActivity;
import com.example.aidong.adapter.ListAdapterDynamic;
import com.example.aidong.utils.common.Constant;
import com.example.aidong.utils.common.MXLog;
import com.example.aidong.utils.common.UrlLink;
import com.example.aidong.http.HttpConfig;
import com.example.aidong.utils.interfaces.OnCommentAndLikeClickListenner;
import com.example.aidong.utils.interfaces.OnMoreCommentClickListenner;
import com.example.aidong.entity.model.AttributeDynamics;
import com.example.aidong.entity.model.AttributeFindDynamics;
import com.example.aidong.entity.model.AttributeVideo;
import com.example.aidong.entity.model.Dynamic;
import com.example.aidong.entity.model.UserCoach;
import com.example.aidong.entity.model.result.FoundDynamicResult;
import com.example.aidong.entity.model.result.MsgResult;
import com.example.aidong.utils.ActivityTool;
import com.example.aidong.utils.MyDbUtils;
import com.example.aidong.utils.PullToRefreshUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.example.aidong.utils.LogUtil;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

public class SportCircleFragment extends BaseFragment implements
		OnRefreshListener2<ListView>, IHttpCallback {

	private static final int DYAMICS_DOWN = 1;
	private static final int DYAMICS_UP = 2;
	protected static final int ZAN = 3;
	private View view;
	private ListAdapterDynamic adapter;
	private PullToRefreshListView mListView;
	private int page = 1;
	private OnekeyShare oks;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.yes_finished_fragment, null);
		oks = new OnekeyShare();
		initView();
		initData();
		setClick();
		return view;
	}

	private void initView() {
		mListView = (PullToRefreshListView) view.findViewById(R.id.list);
		PullToRefreshUtil.setPullListView(mListView, Mode.BOTH);
		mListView.getRefreshableView().setDivider(new ColorDrawable(Color.parseColor("#eaebf0")));
		mListView.getRefreshableView().setDividerHeight(20);
		mListView.setOnRefreshListener(this);
		adapter = new ListAdapterDynamic(getActivity());
		mListView.setAdapter(adapter);

	}

	private void initData() {
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
//		Log.i("dynamic", "resume方法被调用");
		addTask(this,
				new IHttpTask(UrlLink.FOUND_DYNAMIC, paramsinit(page),
						FoundDynamicResult.class), HttpConfig.GET, DYAMICS_DOWN);
	}
	

	private void setClick() {
		
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
				oks.setTitleUrl(titleUrl);
				if (BaseApp.mInstance.isLogin()) {
					oks.setText("我的美型号" + BaseApp.mInstance.getUser().getMxid()
							+ "这里都是型男美女“小鲜肉”，全民老公vs梦中女神，速速围观" + titleUrl);
				} else {
					oks.setText("这里都是型男美女“小鲜肉”，全民老公vs梦中女神，速速围观" + titleUrl);
				}
				oks.setUrl(titleUrl);
				oks.setTheme(OnekeyShareTheme.CLASSIC);
				oks.show(getActivity());
				oks.setTitle(titleUrl);
				if (dynamic.getContent() == null
						|| dynamic.getContent().intern()
								.equals("")) {
					oks.setTitle("最近用美型App，国内首家健身社交App");
				} else {
					if (dynamic.getContent().length() > 30) {
						oks.setTitle(dynamic.getContent()
								.substring(0, 30));
					} else {
						oks.setTitle(dynamic.getContent());
					}

				}

				if (dynamic.getImage() != null
						&& dynamic.getImage().size() > 0) {
					oks.setImageUrl(dynamic.getImage().get(0).getUrl());
				} else if (dynamic.getFilm() != null) {
					oks.setImageUrl(dynamic.getFilm().getCover());

				} else {

				}
			
			}
		});

	}
	private Integer currentPosition;
	protected int goodNo;
	
	private OnClickListener mVideoClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Dynamic dyn = (Dynamic) v.getTag();
			AttributeVideo mvideo = new AttributeVideo();
			mvideo.setNo(dyn.getId());
			mvideo.setContent(dyn.getContent());
			mvideo.setFilm(dyn.getFilm());

			Intent i = new Intent(getActivity(), VideoPlayerActivity.class);
			i.putExtra(Constant.BUNDLE_VIDEO_URL, mvideo.getFilm()
					.getFilm());
			i.putExtra("videofilm", mvideo);
			startActivity(i);
		}
	};
	
	

	private OnCommentAndLikeClickListenner mOnCommentAndLikeClickListenner = new OnCommentAndLikeClickListenner() {
		@Override
		public void onLikeClick(View v) {
			if (BaseApp.mInstance.isLogin()) {
				currentPosition = (Integer) v.getTag();
				Dynamic dynamic = array
						.get(currentPosition);
				goodNo = dynamic.getId();
				MXLog.out("no---------------:" + goodNo);
				if (!MyDbUtils.isZan("" + goodNo)) {
					MyDbUtils.saveZan("" + goodNo, true);
				
					long goodCount = dynamic.getLikes() + 1;
					dynamic.setLikes(goodCount);
					ArrayList<AttributeDynamics.LikeUser> like = dynamic.getLike_user();
					AttributeDynamics.LikeUser user =  new AttributeDynamics().new LikeUser();
					user.setUser(BaseApp.mInstance.getUser());
					like.add(0, user);
					adapter.freshData(array);
					addTask(SportCircleFragment.this,
							new IHttpTask(UrlLink.DYNAMICZAN_URL,
									initParamsZan(String.valueOf(goodNo)),
									MsgResult.class), HttpConfig.POST, ZAN);
				} else {
					ToastUtil.show(getResources().getString(
							R.string.tip_has_good),getActivity());
				}
			} else {
				Intent i = new Intent(getActivity(), LoginActivity.class);
				startActivity(i);
			}
		}

		@Override
		public void onCommentClick(View v) {
			
			currentPosition = (Integer) v.getTag();
			  Dynamic dynamic = array.get(currentPosition);
			AttributeFindDynamics localDynamic = dynamic.translationToAttribute();

			Intent i = new Intent(getActivity(), CommentDetailActivity.class);
			i.putExtra("localDynamic", localDynamic);
			startActivity(i);

		}
	};

	
	
	private OnMoreCommentClickListenner mOnMoreCommentClickListenner = new OnMoreCommentClickListenner() {
		


		@Override
		public void onMoreCommentClick(View v) {
			currentPosition = (Integer) v.getTag();
			  Dynamic dynamic = array.get(currentPosition);
				AttributeFindDynamics localDynamic = dynamic.translationToAttribute();
			Intent i = new Intent(getActivity(), CommentDetailActivity.class);
			i.putExtra("localDynamic", localDynamic);
			startActivity(i);
		}
	};
	
	private OnClickListener mOnAvatarClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Object o = v.getTag();
			if (o != null) {
				if (o instanceof UserCoach) {
					UserCoach u = (UserCoach) v.getTag();
					ActivityTool.startShowActivity(getActivity(), u);
					
				}
//				else if (o instanceof AttributeFindRecommend) {
//					// 点击对话头像跳群组界面
//				}
			}
		}
	};


	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		page = 1;
		addTask(this,
				new IHttpTask(UrlLink.FOUND_DYNAMIC, paramsinit(page),
						FoundDynamicResult.class), HttpConfig.GET, DYAMICS_DOWN);
	}

	protected List<BasicNameValuePair> initParamsZan(String id) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		paramsaaa.add(new BasicNameValuePair("no", id));
		return paramsaaa;
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

		page++;
		addTask(this,
				new IHttpTask(UrlLink.FOUND_DYNAMIC, paramsinit(page),
						FoundDynamicResult.class), HttpConfig.GET, DYAMICS_UP);

	}

	public List<BasicNameValuePair> paramsinit(int page) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		paramsaaa.add(new BasicNameValuePair("page", "" + page));
		return paramsaaa;
	}
	private ArrayList<Dynamic> array = new ArrayList<Dynamic>();
	@Override
	public void onGetData(Object data, int requestCode, String response) {
		stopLoading();
		LogUtil.d("111",data.toString());
		switch (requestCode) {
		case DYAMICS_DOWN:
			FoundDynamicResult result_down = (FoundDynamicResult) data;
			if (result_down.getCode() ==1) {
				if (result_down.getData() != null) {
					if (result_down.getData().getDynamic() !=null) {
						array.clear();
						array.addAll(result_down.getData().getDynamic());
						adapter.freshData(array);
					}
				}
			}
			
			
			
			
			
		
			

			break;
		case DYAMICS_UP:
			FoundDynamicResult result_up = (FoundDynamicResult) data;
			if (result_up.getCode() ==1) {
				if (result_up.getData() != null) {
					if (result_up.getData().getDynamic() !=null) {
						array.addAll(result_up.getData().getDynamic());
						adapter.freshData(array);
					}
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
	}

	public void stopLoading() {
		try {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					mListView.onRefreshComplete();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

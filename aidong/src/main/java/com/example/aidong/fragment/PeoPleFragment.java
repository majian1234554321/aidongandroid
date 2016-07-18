 package com.example.aidong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.adapter.ListAdapterDynamic;
import com.example.aidong.common.UrlLink;
import com.example.aidong.http.HttpConfig;
import com.example.aidong.model.Dynamic;
import com.example.aidong.model.result.FoundDynamicResult;
import com.example.aidong.utils.PullToRefreshUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现-人
 */
public class PeoPleFragment extends BaseFragment implements OnRefreshListener2<GridView>, IHttpCallback {

	private static final int PULL_DOWN_TO_REFRESH = 1;
	private static final int PULL_UP_LOAD_MORE = 2;

	private int page = 1;
	private PullToRefreshGridView gridView;
	private ListAdapterDynamic adapter;
	private ArrayList<Dynamic> date = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_find_people, null);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		gridView = (PullToRefreshGridView) view.findViewById(R.id.gridView);
		PullToRefreshUtil.setPullListView(gridView, Mode.BOTH);

		adapter = new ListAdapterDynamic(getActivity());
		gridView.setAdapter(adapter);
		gridView.setOnRefreshListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		addTask(this,new IHttpTask(UrlLink.FOUND_DYNAMIC, paramsInit(page),FoundDynamicResult.class), HttpConfig.GET, PULL_DOWN_TO_REFRESH);
	}

	@Override
	public void onGetData(Object data, int requestCode, String response) {
		stopLoading();
		switch (requestCode) {
			case PULL_DOWN_TO_REFRESH:

				FoundDynamicResult result_down = (FoundDynamicResult) data;
				if (result_down.getCode() ==1) {
					if (result_down.getData() != null) {
						if (result_down.getData().getDynamic() !=null) {
							date.clear();
							date.addAll(result_down.getData().getDynamic());
							adapter.freshData(date);
						}
					}
				}
				break;

			case PULL_UP_LOAD_MORE:
				FoundDynamicResult result_up = (FoundDynamicResult) data;
				if (result_up.getCode() ==1) {
					if (result_up.getData() != null) {
						if (result_up.getData().getDynamic() !=null) {
							date.addAll(result_up.getData().getDynamic());
							adapter.freshData(date);
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
					gridView.onRefreshComplete();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
		page = 1;
		ToastUtil.show("下拉刷新",getActivity());
		addTask(this,new IHttpTask(UrlLink.FOUND_DYNAMIC, paramsInit(page),FoundDynamicResult.class), HttpConfig.GET, PULL_DOWN_TO_REFRESH);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
		page++;
		ToastUtil.show("上拉加载",getActivity());
		addTask(this,new IHttpTask(UrlLink.FOUND_DYNAMIC, paramsInit(page),FoundDynamicResult.class), HttpConfig.GET, PULL_UP_LOAD_MORE);
	}

	private List<BasicNameValuePair> paramsInit(int page) {
		List<BasicNameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("page", "" + page));
		return params;
	}

}

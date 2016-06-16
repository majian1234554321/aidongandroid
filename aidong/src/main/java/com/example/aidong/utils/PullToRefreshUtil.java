package com.example.aidong.utils;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by huyushuai on 4/7/16.
 */
public class PullToRefreshUtil {
	public static void setPullListView(PullToRefreshGridView list, PullToRefreshGridView.Mode mode) {
		list.setMode(mode);
		ILoadingLayout startLabels = list.getLoadingLayoutProxy(true, false);
		ILoadingLayout endLabels = list.getLoadingLayoutProxy(false, true);
		startLabels.setPullLabel("下拉查看最新内容");
		startLabels.setRefreshingLabel("加载中");
		startLabels.setReleaseLabel("松开手指刷新");
		endLabels.setPullLabel("上拉查看往期内容");
		endLabels.setRefreshingLabel("加载中");
		endLabels.setReleaseLabel("松开手指刷新");
	}

	public static void setPullListView(PullToRefreshListView list, PullToRefreshListView.Mode mode) {
		list.setMode(mode);
		ILoadingLayout startLabels = list.getLoadingLayoutProxy(true, false);
		ILoadingLayout endLabels = list.getLoadingLayoutProxy(false, true);
		startLabels.setPullLabel("下拉查看最新内容");
		startLabels.setRefreshingLabel("加载中");
		startLabels.setReleaseLabel("松开手指刷新");
		endLabels.setPullLabel("上拉查看往期内容");
		endLabels.setRefreshingLabel("加载中");
		endLabels.setReleaseLabel("松开手指刷新");
	}


}

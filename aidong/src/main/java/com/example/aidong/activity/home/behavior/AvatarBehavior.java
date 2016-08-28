package com.example.aidong.activity.home.behavior;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;

/**
 * 课程详情页头像Behavior
 * Created by song on 2016/8/26.
 */
public class AvatarBehavior extends CoordinatorLayout.Behavior<ImageView>{
    public AvatarBehavior() {
        super();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }
}

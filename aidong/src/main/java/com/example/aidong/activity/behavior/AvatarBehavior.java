package com.example.aidong.activity.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

/**
 * 课程详情页头像Behavior
 * Created by song on 2016/8/26.
 */
public class AvatarBehavior extends CoordinatorLayout.Behavior<View>{
    public AvatarBehavior() {
        super();
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }
}

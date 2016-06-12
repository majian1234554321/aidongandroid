package com.leyuan.commonlibrary.util;

import android.util.SparseArray;
import android.view.View;

public class AdapterUtil {
    
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View convertView, int id) {
        SparseArray<View> viewHolds = (SparseArray<View>) convertView.getTag();
        if (viewHolds == null) {
            viewHolds = new SparseArray<>();
            convertView.setTag(viewHolds);
        }

        View childView = viewHolds.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            viewHolds.put(id, childView);
        }
        return (T) childView;
    }
}

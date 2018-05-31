package com.leyuan.aidong.ui.mine.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

public class EEditText extends EditText   {
    private String hint;

    public EEditText(Context context) {
        super(context);
        init();
    }

    public EEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 获得hint值
     *
     * @author admin 2016-9-5 下午4:32:19
     */
    private void init() {
        hint = getHint().toString().trim();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        if (focused) {
            setHint("评论");
        } else {
            getText().clear();
            setHint(hint);
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }
}

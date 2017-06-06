package com.leyuan.aidong.widget.ninephotoview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.widget.ninephotoview.adapter.PhotoContentsBaseAdapter;
import com.leyuan.aidong.widget.ninephotoview.adapter.observer.PhotoBaseDataObserver;
import com.leyuan.aidong.widget.ninephotoview.util.SimpleObjectPool;

import org.apmem.tools.layouts.FlowLayout;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by 大灯泡 on 2016/11/9.
 * <p>
 * 适用于朋友圈的九宫格图片显示(用于listview等)
 */

public class PhotoLayout extends FlowLayout {
    private final int INVALID_POSITION = -1;

    private PhotoContentsBaseAdapter adapter;
    private PhotoImageAdapterObserver adapterObserver = new PhotoImageAdapterObserver();
    private InnerRecyclerHelper recycler;
    private int itemCount;
    private boolean dataChanged;
    private int itemMargin;
    private int multiChildSize;

    private int maxSingleWidth;
    private int maxSingleHeight;

    private float singleAspectRatio = 16f / 16f;   //宽高比
    private int selectedPosition = INVALID_POSITION;
    private Rect touchFrame;
    private Runnable touchReset;

    public PhotoLayout(Context context) {
        super(context);
        init();
    }

    public PhotoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        itemMargin = dp2Px(5f);
        recycler = new InnerRecyclerHelper();
        setOrientation(HORIZONTAL);
        setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int childRestWidth = widthSize - getPaddingLeft() - getPaddingRight();
        updateItemCount();
        multiChildSize = (childRestWidth - itemMargin * 3)/3;
        if (maxSingleWidth == 0) {
            maxSingleWidth = childRestWidth * 2 /  3;
            maxSingleHeight = (int) (maxSingleWidth / singleAspectRatio);
        }

        if (dataChanged) {
            if (adapter == null || itemCount == 0) {
                resetContainer();
                return;
            }
            final int childCount = getChildCount();
            final int oldChildCount = childCount;
            if (oldChildCount > 0) {
                if (oldChildCount == 1) {
                    recycler.addSingleCachedView((ImageView) getChildAt(0));
                } else {
                    for (int i = 0; i < oldChildCount; i++) {
                        View v = getChildAt(i);
                        recycler.addCachedView(i, (ImageView) v);
                    }
                }
            }

            updateItemCount();
            detachAllViewsFromParent();     //清除旧的view

            int newChildCount = itemCount;
            if (newChildCount > 0) {
                fillView(newChildCount);
            }
            dataChanged = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void fillView(int childCount) {
        int lineCount = childCount == 4 ? 2 : 3;
        if (childCount == 1) {
            fillSingleView();
        } else {
            for (int i = 0; i < childCount; i++) {
                final ImageView child = obtainView(i);
                if (i == lineCount) {
                    setupViewAndAddView(i, child, true, false);
                } else {
                    setupViewAndAddView(i, child, false, false);
                }
            }
        }
    }

    private void fillSingleView() {
        final ImageView singleChild = obtainView(0);
        singleChild.setAdjustViewBounds(true);
        singleChild.setMaxWidth(maxSingleWidth);
        singleChild.setMaxHeight(maxSingleHeight);
       // singleChild.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setupViewAndAddView(0, singleChild, false, true);
    }

    private void setupViewAndAddView(int position, @NonNull ImageView v, boolean newLine, boolean isSingle) {
        setItemLayoutParams(v, newLine, isSingle);
        if (onSetUpChildLayoutParamsListener != null) {
            onSetUpChildLayoutParamsListener.onSetUpParams(v, (LayoutParams) v.getLayoutParams(), position, isSingle);
        }
        adapter.onBindData(position, v);
        addViewInLayout(v, position, v.getLayoutParams(), true);
    }


    private void setItemLayoutParams(@NonNull ImageView v, boolean needLine, boolean isSingle) {
        ViewGroup.LayoutParams p = v.getLayoutParams();
        if (p == null || !(p instanceof LayoutParams)) {
            LayoutParams childLP = generateDefaultMultiLayoutParams(isSingle);
            childLP.setNewLine(needLine);
            v.setLayoutParams(childLP);
        } else {
            ((LayoutParams) p).setNewLine(needLine);
        }
    }


    public void setAdapter(PhotoContentsBaseAdapter adapter) {
        if (this.adapter != null && adapterObserver != null) {
            this.adapter.unregisterDataSetObserver(adapterObserver);
        }

        resetContainer();

        this.adapter = adapter;
        adapterObserver = new PhotoImageAdapterObserver();
        this.adapter.registerDataSetObserver(adapterObserver);
        dataChanged = true;
        requestLayout();
    }

    public PhotoContentsBaseAdapter getAdapter() {
        return adapter;
    }


    private void resetContainer() {
        recycler.clearCache();
        removeAllViewsInLayout();
        invalidate();
    }

    private void updateItemCount() {
        itemCount = adapter == null ? 0 : adapter.getCount();
    }

    private ImageView obtainView(int position) {

        ImageView cachedView;
        ImageView child;
        if (itemCount == 1) {
            cachedView = null;
           // cachedView = recycler.getSingleCachedView();
        } else {
            cachedView = recycler.getCachedView(position);
        }
        child = adapter.onCreateView(cachedView, this, position);

        if (child != cachedView) {
            if (itemCount == 1) {
                recycler.addSingleCachedView(child);
            } else {
                recycler.addCachedView(position, child);
            }
        }
        return child;
    }

    protected LayoutParams generateDefaultMultiLayoutParams(boolean isSingle) {
        LayoutParams p;
        if (isSingle) {
            p = new PhotoLayout.LayoutParams(maxSingleWidth, maxSingleHeight);
        } else {
            p = new PhotoLayout.LayoutParams(multiChildSize, multiChildSize);
        }
        p.rightMargin = itemMargin;
        p.bottomMargin = itemMargin;
        return p;
    }


    private int dp2Px(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    public static class LayoutParams extends FlowLayout.LayoutParams {

        public LayoutParams(int width, int height) {
            this(new ViewGroup.LayoutParams(width, height));
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }


    private class InnerRecyclerHelper {
        private SparseArray<ImageView> cachedViews;
        private SimpleObjectPool<ImageView> singleCachedViews;

        InnerRecyclerHelper() {
            cachedViews = new SparseArray<>();
            singleCachedViews = new SimpleObjectPool<>(9);
        }

        ImageView getCachedView(int position) {
            final ImageView imageView = cachedViews.get(position);
            if (imageView != null) {
                cachedViews.remove(position);
                return imageView;
            }
            return null;
        }

        ImageView getSingleCachedView() {
            return singleCachedViews.get();
        }

        void addCachedView(int position, ImageView view) {
            cachedViews.put(position, view);
        }

        void addSingleCachedView(ImageView imageView) {
            singleCachedViews.put(imageView);
        }

        void clearCache() {
            cachedViews.clear();
            singleCachedViews.clearPool();
        }
    }

    private class PhotoImageAdapterObserver extends PhotoBaseDataObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            updateItemCount();
            dataChanged = true;
            requestLayout();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            invalidate();
        }
    }


    public boolean performItemClick(ImageView view, int position) {
        final boolean result;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, position);
            result = true;
        } else {
            result = false;
        }
        return result;
    }
    //------------------------------------------TouchEvent-----------------------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return isClickable() || isLongClickable();
        }
        final int actionMasked = event.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN: {
                onTouchDown(event);
                break;
            }

            case MotionEvent.ACTION_UP: {
                onTouchUp(event);
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                setPressed(false);
                break;
            }
        }
        return true;
    }

    private void onTouchDown(MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        if (!dataChanged) {
            final int selectionPosition = pointToPosition(x, y);
            if (checkPositionValid(selectionPosition)) {
                View view = getChildAt(selectionPosition);
                if (view != null && view.isEnabled()) {
                    updateChildPressState(selectionPosition, true);
                    this.selectedPosition = selectionPosition;
                } else {
                    this.selectedPosition = INVALID_POSITION;
                }
            }else {
                this.selectedPosition = INVALID_POSITION;
            }
        } else {
            this.selectedPosition = INVALID_POSITION;
        }
    }

    private void onTouchUp(MotionEvent event) {
        final int selectionPosition = selectedPosition;
        if (!dataChanged) {
            if (checkPositionValid(selectionPosition)) {
                final View child = getChildAt(selectionPosition);
                updateChildPressState(selectionPosition, true);
                performItemClick((ImageView) child, selectionPosition);
                if (touchReset != null) {
                    removeCallbacks(touchReset);
                }
                touchReset = new Runnable() {
                    @Override
                    public void run() {
                        child.setPressed(false);
                    }
                };
                postDelayed(touchReset, ViewConfiguration.getPressedStateDuration());
            }
        } else {
            if (checkPositionValid(selectionPosition)) updateChildPressState(selectionPosition, false);
        }
    }


    private boolean checkPositionValid(int position) {
        boolean result = true;
        final int childCount = getChildCount();
        if (dataChanged) {
            result = false;
        }
        if (position <= INVALID_POSITION || position > childCount - 1) {
            result = false;
        }
        return result;
    }

    public void updateChildPressState(int position, boolean press) {
        if (checkPositionValid(position)) {
            final View child = getChildAt(position);
            if (press) {
                child.requestFocus();
            }
            child.setPressed(press);
        }
    }


    /**
     * 捕获点击的view并返回其position
     *
     * @param x
     * @param y
     * @return
     */
    public int pointToPosition(int x, int y) {
        if (touchFrame == null) touchFrame = new Rect();
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() == VISIBLE) {
                child.getHitRect(touchFrame);
                if (touchFrame.contains(x, y)) {
                    return i;
                }
            }
        }

        return INVALID_POSITION;
    }


    public List<Rect> getContentViewsGlobalVisibleRects() {
        final int childCount = getChildCount();
        if (childCount <= 0) return null;
        List<Rect> viewRects = new LinkedList<>();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if (v != null) {
                Rect rect = new Rect();
                v.getGlobalVisibleRect(rect);
                viewRects.add(rect);
            }
        }
        return viewRects;
    }

    public List<Rect> getContentViewsDrawableRects() {
        final int childCount = getChildCount();
        if (childCount <= 0) return null;
        List<Rect> viewRects = new LinkedList<>();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if (v != null) {
                Rect rect = getDrawableBoundsInView((ImageView) v);
                viewRects.add(rect);
            }
        }
        return viewRects;
    }

    public List<Matrix> getContentViewsDrawableMatrixList() {
        final int childCount = getChildCount();
        if (childCount <= 0) return null;
        List<Matrix> viewMatrixs = new LinkedList<>();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if (v instanceof ImageView && ((ImageView) v).getDrawable() != null) {
                Matrix matrix = ((ImageView) v).getImageMatrix();
                viewMatrixs.add(matrix);
            }
        }
        return viewMatrixs;
    }

    private Rect getDrawableBoundsInView(ImageView iv) {
        if (iv == null || iv.getDrawable() == null) return null;
        Drawable d = iv.getDrawable();
        Rect result = new Rect();
        iv.getGlobalVisibleRect(result);
        Rect tDrawableRect = d.getBounds();
        Matrix drawableMatrix = iv.getImageMatrix();

        float[] values = new float[9];
        if (drawableMatrix != null) {
            drawableMatrix.getValues(values);
        }

        result.left = result.left + (int) values[Matrix.MTRANS_X];
        result.top = result.top + (int) values[Matrix.MTRANS_Y];
        result.right = (int) (result.left + tDrawableRect.width() * (values[Matrix.MSCALE_X] == 0 ? 1.0f : values[Matrix.MSCALE_X]));
        result.bottom = (int) (result.top + tDrawableRect.height() * (values[Matrix.MSCALE_Y] == 0 ? 1.0f : values[Matrix.MSCALE_Y]));

        return result;
    }



    //------------------------------------------getter/setter-----------------------------------------------

    public float getSingleAspectRatio() {
        return singleAspectRatio;
    }

    public void setSingleAspectRatio(float singleAspectRatio) {
        if (this.singleAspectRatio != singleAspectRatio && maxSingleWidth != 0) {
            this.maxSingleHeight = (int) (maxSingleWidth / singleAspectRatio);
        }
        this.singleAspectRatio = singleAspectRatio;
    }

    public int getMaxSingleWidth() {
        return maxSingleWidth;
    }

    public void setMaxSingleWidth(int maxSingleWidth) {
        this.maxSingleWidth = maxSingleWidth;
    }

    public int getMaxSingleHeight() {
        return maxSingleHeight;
    }

    public void setMaxSingleHeight(int maxSingleHeight) {
        this.maxSingleHeight = maxSingleHeight;
    }

    public OnSetUpChildLayoutParamsListener getOnSetUpChildLayoutParamsListener() {
        return onSetUpChildLayoutParamsListener;
    }

    public void setOnSetUpChildLayoutParamsListener(OnSetUpChildLayoutParamsListener onSetUpChildLayoutParamsListener) {
        this.onSetUpChildLayoutParamsListener = onSetUpChildLayoutParamsListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //------------------------------------------Interface-----------------------------------------------
    private OnSetUpChildLayoutParamsListener onSetUpChildLayoutParamsListener;

    public interface OnSetUpChildLayoutParamsListener {
        void onSetUpParams(ImageView child, LayoutParams p, int position, boolean isSingle);
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ImageView view, int position);
    }
}

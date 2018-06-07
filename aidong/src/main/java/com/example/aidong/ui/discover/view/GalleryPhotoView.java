package com.example.aidong.ui.discover.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;


import com.example.aidong .utils.Logger;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by 大灯泡 on 2016/12/20.
 * <p>
 * 用于图片浏览页面的photoview，主要负责展开动画和回退动画的计算
 * <p>
 * <p>
 * 本代码部分参考bm-x photoview(用scroller实现动画)：https://github.com/bm-x/PhotoView65
 * 本代码大部分参考photoview的实现方式：https://github.com/chrisbanes/PhotoView
 */

public class GalleryPhotoView extends PhotoView {
    private static final int ANIMAL_DURATION = 250;

    private BitmapTransform bitmapTransform;
    private OnEnterAnimalEndListener onEnterAnimalEndListener;
    private OnExitAnimalEndListener onExitAnimalEndListener;
    private boolean isPlayingEnterAnimal = false;
    private boolean isPlayingExitAnimal = false;

    private Point globalOffset;
    private float[] scaleRatios;
    private RectF clipBounds;

    public GalleryPhotoView(Context context) {
        super(context);
    }

    public GalleryPhotoView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public GalleryPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        bitmapTransform = new BitmapTransform();
        globalOffset = new Point();
    }

    @Override
    public void draw(Canvas canvas) {
        if (clipBounds != null) {
            canvas.clipRect(clipBounds);
            Log.w("clip", "clipBounds  >>  " + clipBounds.toShortString());
            clipBounds = null;
        }
        super.draw(canvas);
    }

    public void playEnterAnimal(final Rect from, @Nullable final OnEnterAnimalEndListener l) {
        this.onEnterAnimalEndListener = l;
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                playEnterAnimalInternal(from);
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    public void playExitAnimal(Rect to, @Nullable View alphaView, @Nullable final OnExitAnimalEndListener l) {
        this.onExitAnimalEndListener = l;
        playExitAnimalInternal(to, alphaView);
    }

    private void playEnterAnimalInternal(final Rect from) {
        if (isPlayingEnterAnimal || isPlayingExitAnimal || from == null) return;

        final Rect fromRect = new Rect(from);
        final Rect toRect = getViewBounds();

        fromRect.offset(-globalOffset.x, -globalOffset.y);
        toRect.offset(-globalOffset.x, -globalOffset.y);

        scaleRatios = calculateRatios(fromRect, toRect);

        this.setPivotX(fromRect.centerX() / toRect.width());
        this.setPivotY(fromRect.centerY() / toRect.height());

        final AnimatorSet enterSet = new AnimatorSet();
        enterSet.play(ObjectAnimator.ofFloat(this, View.X, fromRect.left, toRect.left))
                .with(ObjectAnimator.ofFloat(this, View.Y, fromRect.top, toRect.top))
                .with(ObjectAnimator.ofFloat(this, View.SCALE_X, scaleRatios[0], 1f))
                .with(ObjectAnimator.ofFloat(this, View.SCALE_Y, scaleRatios[1], 1f));

        enterSet.setDuration(ANIMAL_DURATION);
        enterSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isPlayingEnterAnimal = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isPlayingEnterAnimal = false;
                if (onEnterAnimalEndListener != null) {
                    onEnterAnimalEndListener.onEnterAnimalEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isPlayingEnterAnimal = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isPlayingEnterAnimal = true;
            }
        });
        enterSet.start();
    }

    private void playExitAnimalInternal(final Rect to, @Nullable View alphaView) {
        if (isPlayingEnterAnimal || isPlayingExitAnimal || to == null || mAttacher == null) return;

        final float currentScale = getScale();
        if (currentScale > 1.0f) setScale(1.0f);

        final Rect fromRect = getViewBounds();
        final Rect drawableBounds = getDrawableBounds(getDrawable());
        final Rect toRect = new Rect(to);
        fromRect.offset(-globalOffset.x, -globalOffset.y);
        toRect.offset(-globalOffset.x, -globalOffset.y);
        if (drawableBounds == null) {
            if (onExitAnimalEndListener != null) {
                onExitAnimalEndListener.onExitAnimalEnd();
            }
            return;
        }

        //bitmap位移
        bitmapTransform.animalTranslate(fromRect.centerX(), toRect.centerX(), fromRect.centerY(), toRect.centerY());

        float scale = calculateScaleByCenterCrop(fromRect, drawableBounds, toRect);
        //等比缩放
        bitmapTransform.animalScale(getScale(), scale, fromRect.centerX(), fromRect.centerY());

        if (alphaView != null) {
            bitmapTransform.animalAlpha(alphaView, 1.0f, 0);
        }

        if (toRect.width() < fromRect.width() || toRect.height() < fromRect.height()) {
            bitmapTransform.animalClip(fromRect, toRect);
        }

        bitmapTransform.start(new OnAllFinishListener() {
            @Override
            public void onAllFinish() {
                if (onExitAnimalEndListener != null) {
                    onExitAnimalEndListener.onExitAnimalEnd();
                }
            }
        });
    }

    private float calculateScaleByCenterCrop(Rect from, Rect drawableBounds, Rect target) {
        int viewWidth = from.width();
        int viewHeight = from.height();
        int imageHeight = drawableBounds.height();
        int imageWidth = drawableBounds.width();

        float result;
        /**
         * 假设原始图片高h，宽w ，ImageView的高y，宽x
         * 判断高宽比例，如果目标高宽比例大于原图，则原图高度不变，宽度为(w1 = (h * x) / y)拉伸
         * 画布宽高(w1,h),在原图的((w - w1) / 2, 0)位置进行切割
         */
        int t = (viewHeight / viewWidth) - (imageHeight / imageWidth);

        if (t > 0) {
            int w1 = (imageHeight * viewWidth) / viewHeight;
            result = Math.min(target.width() * 1.0f / w1 * 1.0f, target.height() * 1.0f / imageHeight * 1.0f);

        } else {
            int h1 = (viewHeight * imageWidth) / viewWidth;
            result = Math.max(target.width() * 1.0f / imageWidth * 1.0f, target.height() * 1.0f / h1 * 1.0f);
        }

        return result;
    }


    public Rect getViewBounds() {
        Rect result = new Rect();
        this.getGlobalVisibleRect(result, globalOffset);
        Logger.i(result.toShortString());
        return result;
    }

    private Rect getDrawableBounds(Drawable d) {
        if (d == null) return null;
        Rect result = new Rect();
        Rect tDrawableRect = d.getBounds();
        Matrix drawableMatrix = getImageMatrix();

        float[] values = new float[9];
        drawableMatrix.getValues(values);

        result.left = (int) values[Matrix.MTRANS_X];
        result.top = (int) values[Matrix.MTRANS_Y];
        result.right = (int) (result.left + tDrawableRect.width() * values[Matrix.MSCALE_X]);
        result.bottom = (int) (result.top + tDrawableRect.height() * values[Matrix.MSCALE_Y]);

        return result;
    }

    private float[] calculateRatios(Rect startBounds, Rect endBounds) {
        float[] result = new float[2];
        float widthRatio = startBounds.width() * 1.0f / endBounds.width() * 1.0f;
        float heightRatio = startBounds.height() * 1.0f / endBounds.height() * 1.0f;
        result[0] = widthRatio;
        result[1] = heightRatio;
        return result;
    }

    private class BitmapTransform implements Runnable {

        static final float PRECISION = 10000f;
        View targetView;
        volatile boolean isRunning;

        Scroller translateScroller;
        Scroller scaleScroller;
        Scroller alphaScroller;
        Scroller clipScroller;

        Interpolator defaultInterpolator = new DecelerateInterpolator();


        int scaleCenterX;
        int scaleCenterY;

        float scaleX;
        float scaleY;

        float alpha;

        int dx;
        int dy;

        int preTranslateX;
        int preTranslateY;

        RectF mClipRect;
        RectF clipTo;
        RectF clipFrom;
        Matrix tempMatrix;

        OnAllFinishListener onAllFinishListener;


        BitmapTransform() {
            isRunning = false;
            translateScroller = new Scroller(getContext(), defaultInterpolator);
            scaleScroller = new Scroller(getContext(), defaultInterpolator);
            alphaScroller = new Scroller(getContext(), defaultInterpolator);
            clipScroller = new Scroller(getContext(), defaultInterpolator);
            mClipRect = new RectF();
            tempMatrix = new Matrix();
        }

        void animalScale(float fromX, float toX, float fromY, float toY, int centerX, int centerY) {
            this.scaleCenterX = centerX;
            this.scaleCenterY = centerY;
            scaleScroller.startScroll((int) (fromX * PRECISION), (int) (fromY * PRECISION), (int) ((toX - fromX) * PRECISION), (int) ((toY - fromY) * PRECISION), ANIMAL_DURATION);
        }

        void animalScale(float from, float to, int centerX, int centerY) {
            animalScale(from, to, from, to, centerX, centerY);
        }

        void animalTranslate(int fromX, int toX, int fromY, int toY) {
            preTranslateX = 0;
            preTranslateY = 0;
            translateScroller.startScroll(0, 0, toX - fromX, toY - fromY, ANIMAL_DURATION);
            Logger.i("animalTranslate", (toX - fromX) +":" +(toY - fromY));
        }

        void animalAlpha(View target, float fromAlpha, float toAlpha) {
            this.targetView = target;
            alphaScroller.startScroll((int) (fromAlpha * PRECISION), 0, (int) ((toAlpha - fromAlpha) * PRECISION), 0, ANIMAL_DURATION);
        }

        void animalClip(Rect clipFrom, Rect clipTo) {
            this.clipFrom = new RectF(clipFrom);
            this.clipTo = new RectF(clipTo);
            Logger.i("clip", "clipFrom  >>>  " + clipFrom.toShortString());
            Logger.i("clip", "clipTo  >>>  " + clipTo.toShortString());

            if (!clipFrom.isEmpty() && !clipTo.isEmpty()) {
                //算出裁剪比率
                float dx = Math.min(1.0f, clipTo.width() * 1.0f / clipFrom.width() * 1.0f);
                float dy = Math.min(1.0f, clipTo.height() * 1.0f / clipFrom.height() * 1.0f);
                //因为scroller是对起始值和终点值之间的数值影响，所以减去1，如果为0，意味着不裁剪，因为目标值比开始值大，而画布无法再扩大了，所以忽略
                dx = dx - 1;
                dy = dy - 1;
                //从1开始,乘以1w保证精度
                clipScroller.startScroll((int) (0 * PRECISION), (int) (0 * PRECISION), (int) (dx * PRECISION), (int) (dy * PRECISION), ANIMAL_DURATION);
            }
        }

        @Override
        public void run() {

            boolean isAllFinish = true;

            if (scaleScroller.computeScrollOffset()) {
                scaleX = (float) scaleScroller.getCurrX() / PRECISION;
                scaleY = (float) scaleScroller.getCurrY() / PRECISION;

                isAllFinish = false;
            }

            if (translateScroller.computeScrollOffset()) {
                int curX = translateScroller.getCurrX();
                int curY = translateScroller.getCurrY();

                dx += curX - preTranslateX;
                dy += curY - preTranslateY;

                Logger.i("animalTranslate", dx+" :"+ dy);

                preTranslateX = curX;
                preTranslateY = curY;

                isAllFinish = false;
            }

            if (alphaScroller.computeScrollOffset()) {
                alpha = (float) alphaScroller.getCurrX() / PRECISION;
                isAllFinish = false;
            }

            if (clipScroller.computeScrollOffset()) {

                float curX = Math.abs((float) clipScroller.getCurrX() / PRECISION);
                float curY = Math.abs((float) clipScroller.getCurrY() / PRECISION);

                Logger.i("clip", curX+""+ curY);

                //算出当前的移动像素的综合
                float dx = clipFrom.width() * curX;
                float dy = clipFrom.height() * curY;


                /*
                 裁剪动画算法是一个。。。初中的简单方程题

                 设裁剪过程中，左边缘移动dl个像素，右边缘移动dr个像素
                 由上面dx算出dl+dr=dx;
                 因为d和dr的比率可知，因此可以知道dLeft和dRight的相对速率
                 比如左边裁剪1像素，而右边宽度是左边的3倍，则右边应该裁剪3像素才追得上左边
                 联立方程：
                 1：dl+dr=dx;
                 2：dl/dr=a;
                 则由2可得
                 dl=a*dr;
                 代入1
                 dr*(a+1)=dx;
                 可以算得出dr
                 再次代入1可知
                 dl=dx-dr
                 */


                float ratioLeftAndRight = Math.abs(clipFrom.left - clipTo.left) / Math.abs(clipFrom.right - clipTo.right);
                float ratioTopAndBottom = Math.abs(clipFrom.top - clipTo.top) / Math.abs(clipFrom.bottom - clipTo.bottom);

                float dClipRight = dx / (ratioLeftAndRight + 1);
                float dClipLeft = dx - dClipRight;
                float dClipBottom = dy / (ratioTopAndBottom + 1);
                float dClipTop = dy - dClipBottom;


                mClipRect.left = clipFrom.left + dClipLeft;
                mClipRect.right = clipFrom.right - dClipRight;

                mClipRect.top = clipFrom.top + dClipTop;
                mClipRect.bottom = clipFrom.bottom - dClipBottom;

                if (!mClipRect.isEmpty()) {
                    clipBounds = mClipRect;
                }

                isAllFinish = false;

            }

            if (!isAllFinish) {
                setMatrixValue();
                postExecuteSelf();
            } else {
                isRunning = false;
                reset();
                if (onAllFinishListener != null) {
                    onAllFinishListener.onAllFinish();
                }
            }

        }

        private void setMatrixValue() {
            if (mAttacher == null) return;
            resetSuppMatrix();
            postMatrixScale(scaleX, scaleY, scaleCenterX, scaleCenterY);
            postMatrixTranslate(dx, dy);
            if (targetView != null) targetView.setAlpha(alpha);
            applyMatrix();
        }

        private void postExecuteSelf() {
            if (isRunning) post(this);
        }

        private void reset() {
            scaleCenterX = 0;
            scaleCenterY = 0;

            scaleX = 0;
            scaleY = 0;

            dx = 0;
            dy = 0;

            preTranslateX = 0;
            preTranslateY = 0;

            alpha = 0;
        }

        void stop(boolean reset) {
            removeCallbacks(this);
            scaleScroller.abortAnimation();
            translateScroller.abortAnimation();
            alphaScroller.abortAnimation();
            clipScroller.abortAnimation();

            isRunning = false;
            onAllFinishListener = null;
            if (reset) reset();
        }

        void start(@Nullable OnAllFinishListener onAllFinishListener) {
            if (isRunning) stop(false);
            this.onAllFinishListener = onAllFinishListener;
            isRunning = true;
            postExecuteSelf();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        bitmapTransform.stop(true);
        super.onDetachedFromWindow();
    }

    public interface OnEnterAnimalEndListener {
        void onEnterAnimalEnd();
    }

    public interface OnExitAnimalEndListener {
        void onExitAnimalEnd();
    }

    interface OnAllFinishListener {
        void onAllFinish();
    }
}

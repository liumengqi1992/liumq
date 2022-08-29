package com.deepblue.library.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatImageView;

/**
 * Created by Weidongjian on 2015/7/15.
 * @modify caojun
 * @date 2019/04/15
 */
public class ProgressImageView extends AppCompatImageView implements Animatable {
    private ProgressDrawable drawable;

    public interface onAnimFinish {
        void onFinish();
    }
    private onAnimFinish listener;

    public ProgressImageView(Context context) {
        this(context, null);
    }

    public ProgressImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        drawable = new ProgressDrawable(50);
//        drawable.setColorDefault(this.getSolidColor());
        drawable.setAnimatable(this);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void startRotate() {
        this.setImageDrawable(drawable);
        drawable.startRotate();
    }

    public void animFinish() {
        drawable.animFinish();
    }

    public void animError() {
        drawable.animError();
    }

    public void removeDrawable() {
        this.setImageDrawable(null);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        drawable.stopRotate();
    }

    @Override
    public void start() {
        startRotate();
    }

    @Override
    public void stop() {

        if (listener != null) {
            listener.onFinish();
        }
    }

    public void setOnAnimFinishListener(onAnimFinish listener) {
        this.listener = listener;
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}

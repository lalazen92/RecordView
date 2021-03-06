package com.devlomi.record_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Devlomi on 13/12/2017.
 */

public class RecordButton extends android.support.v7.widget.AppCompatImageView implements View.OnTouchListener {

    private ScaleAnim scaleAnim;
    private RecordView recordView;
    private boolean listenForRecord = true;
    private OnRecordClickListener onRecordClickListener;
    private float onClickX = 0;


    public void setRecordView(RecordView recordView) {
        this.recordView = recordView;
    }

    public RecordButton(Context context) {
        super(context);
        init(context, null);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecordButton);

            int imageResource = typedArray.getResourceId(R.styleable.RecordButton_src, -1);


            if (imageResource != -1) {
                setTheImageResource(imageResource);
            }

            typedArray.recycle();
        }


        scaleAnim = new ScaleAnim(this);
        this.setOnTouchListener(this);

    }

    private void setTheImageResource(int imageResource) {
        Drawable image = AppCompatResources.getDrawable(getContext(), imageResource);
        setImageDrawable(image);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (listenForRecord)
                    recordView.onActionDown((RecordButton) v, event);

                else {
                    onClickX = event.getX();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (listenForRecord)
                    recordView.onActionMove((RecordButton) v, event);
                break;
            case MotionEvent.ACTION_UP:
                if (listenForRecord)
                    recordView.onActionUp((RecordButton) v);

                else {
                    if (onClickX == event.getX() && onRecordClickListener != null) {
                        onRecordClickListener.onClick(v);
                    }
                }
                break;
        }
        return true;
    }


    protected void startScale() {
        scaleAnim.start();
    }

    protected void stopScale() {
        scaleAnim.stop();
    }

    public void setListenForRecord(boolean listenForRecord) {
        this.listenForRecord = listenForRecord;
    }

    public boolean isListenForRecord() {
        return listenForRecord;
    }

    public void setOnRecordClickListener(OnRecordClickListener onRecordClickListener) {
        this.onRecordClickListener = onRecordClickListener;
    }
}

package com.app.namllahuser.presentation.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.SupportMapFragment;

public class CustomMap extends SupportMapFragment {


    public View mOriginalContentView;
    public TouchableWrapper mTouchView;
    private NonConsumingTouchListener mListener;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mOriginalContentView = super.onCreateView(inflater, parent, savedInstanceState);
        mTouchView = new TouchableWrapper(getActivity());
        mTouchView.addView(mOriginalContentView);
        return mTouchView;
    }

    @Override
    public View getView() {
        return mOriginalContentView;
    }

    public void setNonConsumingTouchListener(NonConsumingTouchListener listener) {
        mListener = listener;
    }

    public interface NonConsumingTouchListener {
        boolean onTouch(MotionEvent motionEvent);
    }

    public class TouchableWrapper extends FrameLayout {

        public TouchableWrapper(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            if (mListener != null) {
                mListener.onTouch(event);
            }
            return super.dispatchTouchEvent(event);
        }
    }
}

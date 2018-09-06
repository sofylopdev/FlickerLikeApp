package edu.galileo.android.flickerapp.searchresults.ui;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener{

    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private SwipeGestureListener listener;

    public SwipeGestureDetector(SwipeGestureListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float diffY = e2.getY() - e1.getY();
        float diffX = e2.getX() - e1.getX();

        if (Math.abs(diffY) > Math.abs(diffX)) {
            if (diffY != 0
                    && Math.abs(diffY) > SWIPE_THRESHOLD
                    && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                listener.onDismiss();
            }
        }else {
            if (diffX != 0
                    && Math.abs(diffX) > SWIPE_THRESHOLD
                    && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                listener.onSave();
            }
        }
        return false;
    }
}

package edu.galileo.android.flickerapp.support;

import android.view.MotionEvent;
import android.widget.ImageView;

import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.shadows.ShadowView;

@Implements(ImageView.class)
public class ShadowImageView extends ShadowView{

    @RealObject
    ImageView realImageView;

    public void performSwipe(float xStart, float yStart, float xEnd, float yEnd){

        MotionEvent motionEventStart = MotionEvent.obtain(
                0, 0, MotionEvent.ACTION_DOWN, xStart, yStart,0);
        MotionEvent motionEventMove = MotionEvent.obtain(
                0, 50/2, MotionEvent.ACTION_MOVE, xEnd/2, yEnd/2, 0);
        MotionEvent motionEventEnd = MotionEvent.obtain(
                0, 50, MotionEvent.ACTION_UP, xEnd, yEnd, 0);

        realImageView.dispatchTouchEvent(motionEventStart);
        realImageView.dispatchTouchEvent(motionEventMove);
        realImageView.dispatchTouchEvent(motionEventEnd);
    }
}

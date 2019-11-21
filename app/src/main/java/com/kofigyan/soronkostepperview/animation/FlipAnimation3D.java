package com.kofigyan.soronkostepperview.animation;


/**
 * Created by Kofi Gyan .
 */

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.kofigyan.soronkostepperview.animation.listener.ViewFlipperListener;


public class FlipAnimation3D extends Animation {

    private Camera camera;

    private View fromView;
    private View toView;

    private float centerX;
    private float centerY;

    private boolean forward = true;

    // static ViewFlipperListener viewFlipperListener;
    ViewFlipperListener viewFlipperListener;

//    public static void setViewFlipperListener(ViewFlipperListener listener) {
//        viewFlipperListener = listener;
//    }

    public void setViewFlipperListener(ViewFlipperListener listener) {
        viewFlipperListener = listener;
    }

    //    public static ViewFlipperListener getViewFlipperListener() {
//        return viewFlipperListener;
//    }
    public ViewFlipperListener getViewFlipperListener() {
        return viewFlipperListener;
    }


    /**
     * Creates a 3D flip animation between two views.
     *
     * @param fromView First view in the transition.
     * @param toView   Second view in the transition.
     */
    public FlipAnimation3D(View fromView, View toView) {
        this.fromView = fromView;
        this.toView = toView;

        //setDuration(700);
        setDuration(500);
        setFillAfter(false);
        setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void reverse() {
        forward = false;
        View switchView = toView;
        toView = fromView;
        fromView = switchView;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        centerX = width / 2;
        centerY = height / 2;
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        // Angle around the y-axis of the rotation at the given time
        // calculated both in radians and degrees.
        final double radians = Math.PI * interpolatedTime;
        float degrees = (float) (180.0 * radians / Math.PI);

        // Once we reach the midpoint in the animation, we need to hide the
        // source view and show the destination view. We also need to change
        // the angle by 180 degrees so that the destination does not come in
        // flipped around
        if (interpolatedTime >= 0.5f) {
            degrees -= 180.f;
            fromView.setVisibility(View.GONE);
            toView.setVisibility(View.VISIBLE);
        }

        if (forward)
            degrees = -degrees; //determines direction of rotation when flip begins

        /**
         final Matrix matrix = t.getMatrix();
         camera.save();
         camera.rotateY(degrees);
         camera.getMatrix(matrix);
         camera.restore();
         matrix.preTranslate(-centerX, -centerY);
         matrix.postTranslate(centerX, centerY);
         **/

        final Matrix matrix = t.getMatrix();
        camera.save();
        camera.translate(0, 0, Math.abs(degrees) * 2);
        camera.getMatrix(matrix);
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }


    public void startAnim(View view, Animation anim) {

        anim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                Log.d("LISTENER", "animation end");
                if (viewFlipperListener != null)
                    viewFlipperListener.onViewFlipped();
            }
        });

        view.startAnimation(anim);

    }

}




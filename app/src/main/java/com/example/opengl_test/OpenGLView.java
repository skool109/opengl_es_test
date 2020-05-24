package com.example.opengl_test;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

class OpenglView extends GLSurfaceView {

    private final OpenGLRenderer mRenderer;

    public OpenglView(Context context) {
        super(context);

        // OpenGL ES 2.0 context 생성
        setEGLContextClientVersion(2);

        mRenderer = new OpenGLRenderer();

        // GLSurfaceView에 객체를 그리는 renderer 설정
        setRenderer(mRenderer);

        // requsetRender 메소드가 호출될 때만 화면을 그리게 설정
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                mRenderer.setAngle(
                        mRenderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}
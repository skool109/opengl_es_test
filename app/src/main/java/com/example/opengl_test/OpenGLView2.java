package com.example.opengl_test;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class OpenGLView2 extends GLSurfaceView {

    private final OpenGLRenderer2 mRenderer2;

    public OpenGLView2(Context context) {
        super(context);

        // OpenGL ES 2.0 context 생성
        setEGLContextClientVersion(2);

        mRenderer2 = new OpenGLRenderer2();

        // GLSurfaceView에 객체를 그리는 renderer 설정
        setRenderer(mRenderer2);

        // requsetRender 메소드가 호출될 때만 화면을 그리게 설정
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
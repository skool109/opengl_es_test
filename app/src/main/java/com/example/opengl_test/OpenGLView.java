package com.example.opengl_test;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

class OpenglView extends GLSurfaceView {

    private final OpenGLRenderer mRenderer;

    public OpenglView(Context context){
        super(context);

        // OpenGL ES 2.0 context 생성
        setEGLContextClientVersion(2);

        mRenderer = new OpenGLRenderer();

        // GLSurfaceView에 객체를 그리는 renderer 설정
        setRenderer(mRenderer);

        // requsetRender 메소드가 호출될 때만 화면을 그리게 설정
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}

/*
public class OpenGLView extends GLSurfaceView {
    public OpenGLView(Context context) {
        super(context);
        init();
    }

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        setRenderer(new OpenGLRenderer());
    }
}
*/
package com.example.opengl_test;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle; // 0512

    public static int loadShader(int type, String shaderCode) {
        // 빈 쉐이더를 생성하고 그 인덱스를 할당.
        int shader = GLES20.glCreateShader(type);

        // *컴파일 결과를 받을 공간을 생성.
        IntBuffer compiled = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        String shaderType;

        // *컴파일 결과를 출력하기 위해 쉐이더를 구분.
        if(type == GLES20.GL_VERTEX_SHADER)
            shaderType = "Vertex";
        else if(type == GLES20.GL_FRAGMENT_SHADER)
            shaderType = "Fragment";
        else
            shaderType = "Unknown";

        // 빈 쉐이더에 소스코드를 할당.
        GLES20.glShaderSource(shader, shaderCode);
        // 쉐이더에 저장 된 소스코드를 컴파일
        GLES20.glCompileShader(shader);

        // *컴파일 결과 오류가 발생했는지를 확인.
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled);
        // *컴파일 에러가 발생했을 경우 이를 출력.
        if(compiled.get(0) == 0) {
            GLES20.glGetShaderiv(shader,GLES20.GL_INFO_LOG_LENGTH,compiled);
            if (compiled.get(0) > 1){
                Log.e("Shader", shaderType + " shader: " + GLES20.glGetShaderInfoLog(shader));
            }
            GLES20.glDeleteShader(shader);
            Log.e("Shader", shaderType + " shader compile error.");
        }

        // 완성된 쉐이더의 인덱스를 리턴.
        return shader;
    }

    // GLSurfaceView가 생성되었을 때 한번 호출되는 메소드
    // OpenGL 환경 설정, 그래픽 객체 초기화 등 처리 시 사용
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // color buffer 클리어 시 사용할 색 지정
        // red, green, blue, alpha 순 (0~1)
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
    }

    /*
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
     */

    /*
    public volatile float mAngle;

    public float getAngle() {
        return mAngle;
    }
    public void setAngle(float angle) {
        mAngle = angle;
    }
    */

    // GLSurfaceView가 다시 그려질 때마다 호출되는 메소드
    public void onDrawFrame(GL10 unused) {
        // 위에서 설정한 glClearcolor에서 설정한 값으로 color buffer 클리어
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        /*
        //카메라 위치 설정
        Matrix.setLookAtM(mViewMatrix, 0,0,0,-3,0f,0f,0f,0f,30f,0f);

        //Projection과 camera view의 값을 곱하여 mMVPMatrix 변수에 저장
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
         */
        /*
        float[] scratch = new float[16];
        float[] mRotationMatrix = new float[16];

        //회전 matrix 정의
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        */
        mTriangle.draw();
    }

    // GLSurfaceView 크기 변경, 디바이스 화면 방향 전환으로 geometry가 바뀔 때 호출되는 메소드
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // viewport 설정
        // specifies the affine transformation of x and y from
        // normalized device coordinates to window coordinates
        // viewport rectangle의 왼쪽 아래를 (0,0)으로 지정하고
        // viewport의 width와 height를 지정합니다.
        GLES20.glViewport(0, 0, width, height);

        /*
        // GLSurfaceView의 너비, 높이 사이의 비율을 계산
        float ratio = (float) width / height;
        // Projection matrix 정의
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1,1,3,7);
         */

    }
}
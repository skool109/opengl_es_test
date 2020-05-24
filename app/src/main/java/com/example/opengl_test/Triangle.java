package com.example.opengl_test;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {


    private final String vertexShaderCode =
            "#version 300 es                            \n" +
                    "layout (location = 0) in vec4 vPosition;   \n" +
                    "void main() {                              \n" +
                    "   gl_Position = vPosition;                \n" +
                    "}                                          \n";
        /*
        private final String vertexShaderCode =
            "#version 300 es                            \n" +
                    "uniform mat4 uMVPMatrix;                   \n" +
                    "layout (location = 0) in vec4 vPosition;   \n" +
                    "void main() {                              \n" +
                    "   gl_Position = uMVPMatrix * vPosition;   \n" +
                    "}                                          \n";

         */


    private final String fragmentShaderCode =
            "#version 300 es                            \n" +
                    "precision mediump float;                   \n" +
                    "uniform vec4 vColor;                       \n" +
                    "out vec4 fragColor;                        \n" +
                    "void main() {                              \n" +
                    "  fragColor = vColor;                      \n" +
                    "}                                          \n";

    private int mMVPMatrixHandle;
    private FloatBuffer vertexBuffer;

    static final int COORDS_PER_VERTEX = 3; // x, y, z
    static float triangleCoords[] = {
            0.0f,  0.622008459f, 0.0f, // 상단 vertex
            -0.5f, -0.311004243f, 0.0f, // 왼쪽 아래 vertex
            0.5f, -0.311004243f, 0.0f  // 오른쪽 아래 vertex
    };

    // color 설정
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    private final int mProgram;

    public Triangle() {
        // 좌표 배열의 수에 해당하는 만큼 버퍼를 할당
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);

        // 디바이스 하드웨어에서 사용하는 byte order를 사용.
        bb.order(ByteOrder.nativeOrder());

        // ByteBuffer를 통해 floating point 버퍼를 생성.
        vertexBuffer = bb.asFloatBuffer();
        // 이 FloatBuffer에 좌표를 추가.
        vertexBuffer.put(triangleCoords);
        // 버퍼 상의 첫 번째 좌표를 읽을 수 있도록 설정.
        vertexBuffer.position(0);

        // 쉐이더 로드 및 컴파일.
        int vertexShader = OpenGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = OpenGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        // 프로그램 생성.
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    public void draw() {
        // 쉐이더가 포함 된 프로그램을 사용.
        GLES20.glUseProgram(mProgram);

        // 프로그램에 포함 된 쉐이더에서 vPosition 변수에 접근할 수 있는 핸들을 가져온다.
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        // 해당 값에 대한 Vertex Attribute Array를 활성화한다.
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        // 활성화된 Vertex Attribute Array가 정점 데이터를 가리키도록 한다. (그래픽 장치로 복사)
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        // vColor 변수에 접근할 수 있는 핸들을 가져온다.
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        // 해당 핸들을 이용해 해당 uniform 변수에 값을 할당한다.
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        /*
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
         */

        // 위에서 설정한 값들을 바탕으로 렌더링을 수행한다.
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, vertexCount);
        GLES20.glLineWidth(33);

        // 활성화된 Vertex Attribute Array를 비활성화한다.
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
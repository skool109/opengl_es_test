package com.example.opengl_test;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class human {

    private final String vertexShaderCode =
            "#version 300 es                            \n" +
                    "layout (location = 0) in vec4 vPosition;   \n" +
                    "void main() {                              \n" +
                    "   gl_Position = vPosition;                \n" +
                    "}                                          \n";
    private final String fragmentShaderCode =
            "#version 300 es                            \n" +
                    "precision mediump float;                   \n" +
                    "uniform vec4 vColor;                       \n" +
                    "out vec4 fragColor;                        \n" +
                    "void main() {                              \n" +
                    "  fragColor = vColor;                      \n" +
                    "}                                          \n";

    private FloatBuffer vertexBuffer;
    static final int COORDS_PER_VERTEX = 3; // x, y, z
    static float humanCoords[] = {
            -0.066401f/2,	1.393949f/2,	-0.503021f/2,	-0.120006f/2,	1.320214f/2,	-0.365971f/2,	0.065459f/2,	0.072163f/2,	-0.125363f/2,	-0.070888f/2,	0.936261f/2,	-0.245004f/2,	-0.047209f/2,	1.535351f/2,	-0.519255f/2,	0.135612f/2,	1.078532f/2,	-0.306298f/2,	-0.000819f/2,	1.577427f/2,	-0.376945f/2,	-0.16587f/2,	1.018801f/2,	-0.205747f/2,	0.034677f/2,	1.050965f/2,	-0.245848f/2,	0.052401f/2,	0.045432f/2,	-0.194183f/2,	0.023195f/2,	0.492076f/2,	-0.208718f/2,	-0.019507f/2,	0.954656f/2,	-0.457861f/2,	0.045104f/2,	1.364244f/2,	-0.40253f/2,	0.038786f/2,	0.678973f/2,	-0.257076f/2,	0.065351f/2,	0.252602f/2,	-0.157002f/2,	-0.045689f/2,	0.038324f/2,	-0.017196f/2,	0.113846f/2,	1.214315f/2,	-0.316705f/2,	-0.085058f/2,	1.080172f/2,	-0.233369f/2,	-0.05843f/2,	1.022239f/2,	-0.199346f/2,	-0.090091f/2,	0.069736f/2,	-0.61866f/2,	-0.272698f/2,	0.937662f/2,	-0.385341f/2,	-0.160581f/2,	1.249747f/2,	-0.593401f/2,	-0.183778f/2,	1.530805f/2,	-0.48384f/2,	-0.350027f/2,	1.044747f/2,	-0.519323f/2,	-0.189448f/2,	1.569434f/2,	-0.361967f/2,	-0.271697f/2,	1.155192f/2,	-0.240534f/2,	-0.323495f/2,	1.068098f/2,	-0.414076f/2,	-0.021457f/2,	0.052257f/2,	-0.603815f/2,	-0.145289f/2,	0.485886f/2,	-0.570478f/2,	-0.079798f/2,	0.959991f/2,	-0.496151f/2,	-0.266503f/2,	1.337483f/2,	-0.506233f/2,	-0.191248f/2,	0.684511f/2,	-0.553608f/2,	-0.119107f/2,	0.244812f/2,	-0.615281f/2,	-0.212192f/2,	0.033595f/2,	-0.537982f/2,	-0.329492f/2,	1.153766f/2,	-0.496786f/2,	-0.264729f/2,	1.141896f/2,	-0.34241f/2,	-0.292702f/2,	1.08652f/2,	-0.32646f/2,	-0.158132f/2,	1.179897f/2,	-0.307596f/2,	-0.040093f/2,	1.175988f/2,	-0.539558f/2,	-0.098054f/2,	1.552796f/2,	0.686255f/2,	0.448355f/2,	0.720703f/2,	2.524288f/2

    };

    // color 설정
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    private final int mProgram;

    public human() {
        // 좌표 배열의 수에 해당하는 만큼 버퍼를 할당
        ByteBuffer bb = ByteBuffer.allocateDirect(humanCoords.length * 4);

        // 디바이스 하드웨어에서 사용하는 byte order를 사용.
        bb.order(ByteOrder.nativeOrder());

        // ByteBuffer를 통해 floating point 버퍼를 생성.
        vertexBuffer = bb.asFloatBuffer();
        // 이 FloatBuffer에 좌표를 추가.
        vertexBuffer.put(humanCoords);
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

    private final int vertexCount = humanCoords.length / COORDS_PER_VERTEX;
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

        // 위에서 설정한 값들을 바탕으로 렌더링을 수행한다.
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, vertexCount);
        GLES20.glLineWidth(33);

        // 활성화된 Vertex Attribute Array를 비활성화한다.
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}


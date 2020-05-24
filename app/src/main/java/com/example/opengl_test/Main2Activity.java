package com.example.opengl_test;

import androidx.appcompat.app.AppCompatActivity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    private GLSurfaceView mGLView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLView2 = new OpenGLView2(this);
        setContentView(mGLView2);
    }
}

package com.example.opengl_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class HomeActivity extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // 파일 업로드 버튼 - 추후 작업 요소 : 서버전송
        ImageButton btn_upload = findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "파일을 선택하여 업로드합니다.", Toast.LENGTH_LONG).show(); // 출력문구

                Intent fileopen = new Intent(Intent.ACTION_GET_CONTENT); // 파일 선택 기능
                fileopen.setType("*/*");
                startActivityForResult(Intent.createChooser(fileopen, "open"), FILE_SELECT_CODE);
            }
        });

        // 보정전 데이터 시각화 버튼 - 현재 : 삼각형 객체
        ImageButton btn_before = findViewById(R.id.btn_before);
        btn_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "보정전 데이터를 시각화합니다.", Toast.LENGTH_LONG).show(); // 출력문구
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); // opengl 화면으로 전환
                startActivity(intent);
            }
        });

        // 보정후 데이터 시각화 버튼 - 현재 : 사람 객체
        ImageButton btn_after = findViewById(R.id.btn_after);
        btn_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "보정후 데이터를 시각화합니다.", Toast.LENGTH_LONG).show(); // 출력문구
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class); // opengl 화면으로 전환
                startActivity(intent);
            }
        });

        Button btn_test = findViewById(R.id.btn_test); // csv 파일 읽기 확인
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("test.csv")));
                    String[] nextLine;
                    // 선언
                    human mhuman = new human();
                    while ((nextLine = reader.readNext()) != null) {   // 2
                        for (int i = 0; i < nextLine.length; i++) {
                            // humanCoords에 데이터 넣기
                            mhuman.humanCoords.add(Float.parseFloat(nextLine[i])/2);
                        }
                        // nextLine[] is an array of values from the line

                    }
                } catch (IOException e) {

                }
            }
        });
    }

    @Override // 파일선택기능 확인
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final TextView text_test= findViewById(R.id.text_test);

        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    text_test.setText(uri.getPath());
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

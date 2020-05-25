package com.example.opengl_test;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 파일 업로드 버튼 - 추후 작업 요소 : 파일선택가능, 서버전송
        ImageButton btn_upload = findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "파일을 선택하여 업로드합니다.", Toast.LENGTH_LONG).show(); // 출력문구
            }
        });

        // 보정전 데이터 시각화 버튼
        ImageButton btn_before = findViewById(R.id.btn_before);
        btn_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "보정전 데이터를 시각화합니다.", Toast.LENGTH_LONG).show(); // 출력문구
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); // opengl 화면으로 전환
                startActivity(intent);
            }
        });

        // 보정후 데이터 시각화 버튼
        ImageButton btn_after = findViewById(R.id.btn_after);
        btn_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "보정후 데이터를 시각화합니다.", Toast.LENGTH_LONG).show(); // 출력문구
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class); // opengl 화면으로 전환
                startActivity(intent);
            }
        });

        final TextView text_test= findViewById(R.id.text_test);
        Button btn_test = findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("test.csv")));
                    String[] nextLine;
                    while ((nextLine = reader.readNext()) != null) {
                        // nextLine[] is an array of values from the line
                        System.out.println(nextLine[0] + " , " + nextLine[1] + " , " + nextLine[2]);
                        text_test.setText("CSV file read complete");
                    }
                } catch (IOException e) {

                }
            }
        });
    }
}

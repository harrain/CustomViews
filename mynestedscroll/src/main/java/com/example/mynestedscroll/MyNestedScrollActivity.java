package com.example.mynestedscroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyNestedScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynestedscroll);//不要和其他布局文件重名，比如特别容易混的activity_main
    }
}

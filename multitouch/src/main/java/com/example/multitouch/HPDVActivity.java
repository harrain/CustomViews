package com.example.multitouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HPDVActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(new HandledPicDragView(this));
    }
}

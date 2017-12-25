package com.example.bezier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TwoBezierShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TwoBezierView(this));
    }
}

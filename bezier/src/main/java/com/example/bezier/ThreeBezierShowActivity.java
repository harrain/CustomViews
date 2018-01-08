package com.example.bezier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

public class ThreeBezierShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_bezier_show);

        final ThreeBezierView tbv = (ThreeBezierView) findViewById(R.id.threebv);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radiogroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.cb1) tbv.setMode(true);
                else if (checkedId == R.id.cb2) tbv.setMode(false);

            }
        });
    }
}

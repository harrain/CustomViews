package com.example.matrix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;


public class MatrixMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_main);

        final PolyView poly = (PolyView) findViewById(R.id.poly);

        RadioGroup group = (RadioGroup) findViewById(R.id.group);
        assert group != null;
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getCheckedRadioButtonId() == R.id.point0)poly.setTestPoint(0);
                else if (group.getCheckedRadioButtonId() == R.id.point1)poly.setTestPoint(1);
                else if (group.getCheckedRadioButtonId() == R.id.point2)poly.setTestPoint(2);
                else if (group.getCheckedRadioButtonId() == R.id.point3)poly.setTestPoint(3);
                else if (group.getCheckedRadioButtonId() == R.id.point4)poly.setTestPoint(4);
            }
        });
    }
}

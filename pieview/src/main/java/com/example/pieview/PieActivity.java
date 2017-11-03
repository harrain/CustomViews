package com.example.pieview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class PieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PieView pieView = new PieView(this);
        setContentView(pieView);

        ArrayList<PieData> datas = new ArrayList<>();
        PieData pieData = new PieData("sloop", 60);
        PieData pieData2 = new PieData("what", 30);
        PieData pieData3 = new PieData("change", 40);
        PieData pieData4 = new PieData("your", 20);
        PieData pieData5 = new PieData("mind", 20);
        datas.add(pieData);
        datas.add(pieData2);
        datas.add(pieData3);
        datas.add(pieData4);
        datas.add(pieData5);

        pieView.setData(datas);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

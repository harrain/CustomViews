package com.example.bezier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BezierMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_main);
        final List<IntentTitleBean> datas = new ArrayList<>();
        IntentTitleBean bean0 = new IntentTitleBean("二阶贝塞尔曲线",new Intent(this,TwoBezierShowActivity.class));
        IntentTitleBean bean1 = new IntentTitleBean("三阶贝塞尔曲线",new Intent(this,ThreeBezierShowActivity.class));


        datas.add(bean0);
        datas.add(bean1);
        RecyclerView rv= (RecyclerView) findViewById(R.id.bezier_rv);
        LinearLayoutManager ll = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rv.setLayoutManager(ll);rv.addItemDecoration(decoration);
        CommonAdapter adapter = new CommonAdapter<IntentTitleBean>(this,android.R.layout.simple_list_item_1,datas) {
            @Override
            protected void convert(ViewHolder holder, IntentTitleBean o, int position) {
                holder.setText(android.R.id.text1,o.title);
            }


        };
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity(datas.get(position).intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
}

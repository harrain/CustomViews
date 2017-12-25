package com.example.multitouch;

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

public class MultiTouchMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_touch_main);

        final List<IntentTitleBean> beans = new ArrayList<>();
        beans.add(new IntentTitleBean("没有针对多指触控处理版本",new Intent(this,PDVActivity.class)));
        beans.add(new IntentTitleBean("针对多指触控处理后版本",new Intent(this,HPDVActivity.class)));

        RecyclerView rv = (RecyclerView) findViewById(R.id.multitouch_rv);
        LinearLayoutManager ll = new LinearLayoutManager(this);
        rv.setLayoutManager(ll);
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(decoration);

        CommonAdapter adapter = new CommonAdapter<IntentTitleBean>(this,android.R.layout.simple_list_item_1,beans) {
            @Override
            protected void convert(ViewHolder holder, IntentTitleBean o, int position) {
                holder.setText(android.R.id.text1,o.title);
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity(beans.get(position).intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rv.setAdapter(adapter);
    }
}

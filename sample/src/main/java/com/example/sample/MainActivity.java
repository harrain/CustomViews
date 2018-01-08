package com.example.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.avatarbehavior.AvatarProfileActivity;
import com.example.bezier.BezierMainActivity;
import com.example.huazhewan.PlayActivity;
import com.example.matrix.MatrixMainActivity;
import com.example.multitouch.MultiTouchMainActivity;
import com.example.mynestedscroll.MyNestedScrollActivity;
import com.example.pieview.PieActivity;
import com.gastudio.leafloading.LeafLoadingActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<SampleItem> mList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.listview);
        adapter = new ListAdapter(this, mList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(mList.get(position).getIntent());
            }
        });

        initData();
    }

    private void initData() {
        SampleItem item0 = new SampleItem("饼状图", new Intent(this, PieActivity.class));
        mList.add(item0);
        SampleItem item1 = new SampleItem("叶子曲线loading",new Intent(this, LeafLoadingActivity.class));
        mList.add(item1);
        SampleItem nestedScroll = new SampleItem("嵌套滚动NestedScroll机制",new Intent(this,MyNestedScrollActivity.class));
        mList.add(nestedScroll);
        SampleItem avatarBehavior = new SampleItem("自定义Behavior实现头像简介与toolbar联动",new Intent(this, AvatarProfileActivity.class));
        mList.add(avatarBehavior);
        SampleItem bezier = new SampleItem("贝塞尔曲线",new Intent(this, BezierMainActivity.class));
        mList.add(bezier);
        SampleItem matrix = new SampleItem("matrix setPolyToPoly用法",new Intent(this, MatrixMainActivity.class));
        mList.add(matrix);
        SampleItem huazhewan = new SampleItem("滑着玩 控件随手滑动，记录坐标和滑动时间",new Intent(this, PlayActivity.class));
        mList.add(huazhewan);
        SampleItem multitouch = new SampleItem("多点触控 滑动图片bug处理",new Intent(this, MultiTouchMainActivity.class));
        mList.add(multitouch);
        adapter.notifyDataSetChanged();
    }


    private class ListAdapter extends BaseAdapter {
        private ArrayList<SampleItem> sampleItems;
        private Context mContext;

        public ListAdapter(Context context, ArrayList<SampleItem> list) {
            mContext = context;
            sampleItems = list;
        }

        @Override
        public int getCount() {
            return sampleItems == null ? 0 : sampleItems.size();
        }

        @Override
        public Object getItem(int position) {
            return sampleItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MainHolder holder;
            if (convertView == null) {

                convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent,false);

                holder = new MainHolder();
                holder.tv = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);
            }else {
                holder = (MainHolder) convertView.getTag();
            }
            holder.tv.setText(sampleItems.get(position).getSampleName());
            return convertView;
    }

    class MainHolder {
        TextView tv;
    }
}

private class SampleItem {
    String sampleName;
    Intent intent;

    public SampleItem(String sampleName, Intent intent) {
        this.sampleName = sampleName;
        this.intent = intent;
    }

    public String getSampleName() {
        return sampleName;
    }

    public Intent getIntent() {
        return intent;
    }
}
}

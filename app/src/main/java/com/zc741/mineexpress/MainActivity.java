package com.zc741.mineexpress;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zc741.mineexpress.Utils.ToastUtils;

public class MainActivity extends AppCompatActivity {
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("选择快递");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(Color.parseColor("#00BCD4"));

        //显示布局
        initView();
    }

    private void initView() {
        final int expressImages[] = new int[]{
                R.mipmap.sf,
                R.mipmap.st,
                R.mipmap.yt,
                R.mipmap.yd,
                R.mipmap.tt,
                R.mipmap.ems,
                R.mipmap.zt,
                R.mipmap.ht,
        };
        final String expressText[] = new String[]{
                "顺丰快递", "申通快递", "圆通快递", "韵达快递", "天天快递", "EMS", "中通快递", "百世汇通"
        };
        final String expressNo[] = new String[]{
                "sf", "st", "yt", "yd", "tt", "ems", "zt", "ht"
        };

        final ListView listViewExpressName = (ListView) findViewById(R.id.listViewExpressName);
        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return expressImages.length;
            }

            @Override
            public Object getItem(int position) {
                return expressImages[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.express_style_listview, parent, false);

                    holder = new ViewHolder();
                    holder.express_image = (ImageView) convertView.findViewById(R.id.express_image);
                    holder.express_text = (TextView) convertView.findViewById(R.id.express_text);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.express_image.setImageResource(expressImages[position]);
                holder.express_text.setText(expressText[position]);

                assert listViewExpressName != null;
                listViewExpressName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), ExpressQueryActivity.class);
                        intent.putExtra("express_image", expressImages[position]);
                        intent.putExtra("express_text", expressText[position]);
                        intent.putExtra("express_No", expressNo[position]);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, 0);

                    }
                });

                return convertView;
            }

            class ViewHolder {
                ImageView express_image;
                TextView express_text;
            }
        };
        listViewExpressName.setAdapter(baseAdapter);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - exitTime >= 2000) {
            ToastUtils.toast(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT);
            exitTime = currentTime;
        } else {
            finish();
            overridePendingTransition(0, R.anim.slide_out_right);
        }
    }
}

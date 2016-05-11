package com.zc741.mineexpress;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zc741.mineexpress.Utils.ToastUtils;
import com.zc741.mineexpress.bean.ExpressInfo;

public class ExpressQueryActivity extends BaseActivity {

    private EditText mExpress_number;

    private String KEY = "a94fc7397be38de19c96a12272b5ef7e";
    private String mExpressNo;
    private String mExpressName;
    private String mExpressNumber;
    private int mLength;
    private ExpressInfo mExpressInfo;
    private String mReson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_query);

        Intent intent = getIntent();
        //快递公司号码
        mExpressNo = intent.getStringExtra("express_No");
        //快递名称
        mExpressName = intent.getStringExtra("express_text");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mExpressName);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(Color.parseColor("#00BCD4"));
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        //Toast.makeText(ExpressQueryActivity.this, "search",Toast.LENGTH_SHORT).show();
                        //拿到数据请求网络
                        //1.拿数据
                        EditText editText = (EditText) findViewById(R.id.express_number);
                        String content = editText.getText().toString();
                        //System.out.println("content=" + content);

                        //判断是否为空
                        if (content.isEmpty()) {
                            ToastUtils.toast(ExpressQueryActivity.this, "订单号为空，请重新输入", Toast.LENGTH_SHORT);
                        } else {
                            getInfos();
                        }
                        break;
                }
                return true;
            }
        });

        showKeyboard();

        //1.先显示之前查询过的运单号码 会取同一个码
        /*if (mExpressNumber == null) {
            //读取单号
            SharedPreferences read = getSharedPreferences("expressNumber", MODE_PRIVATE);
            String number = read.getString("expressNumber", mExpressNumber);
            System.out.println("number =" + number);
            EditText editText = (EditText) findViewById(R.id.express_number);
            editText.setText(number);

        }*/
    }

    //拿用户填写的信息
    private void getInfos() {
        //点击搜索时获取快递单号
        mExpressNumber = mExpress_number.getText().toString();
        //保存单号
        SharedPreferences sharedPreferences = getSharedPreferences("expressNumber", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("expressNumber", mExpressNumber);
        editor.commit();
        //请求网络数据
        getDataFromServer();
    }

    //请求网络数据
    private void getDataFromServer() {
        String url = "http://v.juhe.cn/exp/index?key=a94fc7397be38de19c96a12272b5ef7e&com=" + mExpressNo + "&no=" + mExpressNumber;
        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                //解析json
                parseJson(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                finish();
            }
        });

    }

    //解析json数据
    private void parseJson(String result) {
        Gson gson = new Gson();
        mExpressInfo = gson.fromJson(result, ExpressInfo.class);

        //查询结果
        mReson = mExpressInfo.getReason();
        //返回标识码
        String resultCode = mExpressInfo.getResultcode();
        if (!resultCode.equals("200")) {
            ToastUtils.toast(ExpressQueryActivity.this, "订单号错误，请确认后查询", Toast.LENGTH_SHORT);
        } else {
            mLength = mExpressInfo.getResult().getList().size();//list的数据大小
        }


        //循环取出数据显示在listview上
        initData();
    }

    private void initData() {
        final ListView listview_express = (ListView) findViewById(R.id.listview_express);
        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mLength;
            }

            @Override
            public Object getItem(int position) {
                return mExpressInfo.getResult().getList().get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(ExpressQueryActivity.this).inflate(R.layout.express_data, parent, false);

                    holder = new ViewHolder();
                    holder.datetime = (TextView) convertView.findViewById(R.id.datetime);
                    holder.remark = (TextView) convertView.findViewById(R.id.remark);
                    holder.zone = (TextView) convertView.findViewById(R.id.zone);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.datetime.setText(mExpressInfo.getResult().getList().get(position).getDatetime());
                holder.remark.setText(mExpressInfo.getResult().getList().get(position).getRemark());
                holder.zone.setText(mExpressInfo.getResult().getList().get(position).getZone());

                return convertView;
            }

            class ViewHolder {
                TextView datetime;
                TextView remark;
                TextView zone;
            }
        };
        listview_express.setAdapter(baseAdapter);
    }

    //showKeyBoard
    private void showKeyboard() {
        mExpress_number = (EditText) findViewById(R.id.express_number);
        mExpress_number.setFocusable(true);
        mExpress_number.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) mExpress_number.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

        //enter键监听
        mExpress_number.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String content = mExpress_number.getText().toString();
                    if (content.isEmpty()) {
                        ToastUtils.toast(getApplicationContext(), "订单号为空，请重新输入", Toast.LENGTH_SHORT);
                    } else {
                        getInfos();
                    }
                    return false;
                }
                return false;
            }
        });
    }

    //处理键盘监听 点击除EditText区域之外键盘消失
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideInput(view, ev)) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (im != null) {
                    im.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        //
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShouldHideInput(View view, MotionEvent ev) {

        if (view != null && (view instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前位置
            view.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
                //点击的是输入框 保留点击EditText事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}

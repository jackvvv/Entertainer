package sinia.com.entertainer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.MineAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.LoginBean;
import sinia.com.entertainer.bean.MyReOutBean;
import sinia.com.entertainer.bean.MyReOutLsBean;
import sinia.com.entertainer.bean.PersonInfBean;
import sinia.com.entertainer.bean.UserIdBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;

/**
 * 我的发布
 * type=1
 * Created by byw on 2016/12/8.
 */
public class MyReleaseActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_all, rl_wait, rl_outtime, rl_close;
    private TextView tv_all, tv_wait, tv_outtime, tv_close;
    private View view_1, view_2, view_3, view_4;
    private ListView listview;
    private MineAdapter adapter;
    private List<MyReOutBean> ls = new ArrayList<>();
    private String userId;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String num = "4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release, "我的发布");
        getDoingView().setVisibility(View.GONE);
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        initView();
        getInf(num);
    }


    private void getInf(String type) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("type", type);
        Log.i("tag", Constants.BASE_URL + "myDeliver&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "myDeliver", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                Log.e("tag", s.toString());
                MyReOutLsBean bean = gson.fromJson(s, MyReOutLsBean.class);
                int state = bean.getState();
                int isSuccessful = bean.getIsSuccessful();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    if (0 == state && 0 == isSuccessful) {
                        ls.clear();
                        ls.addAll(bean.getItems());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initView() {
        rl_all = (RelativeLayout) findViewById(R.id.rl_all);
        rl_wait = (RelativeLayout) findViewById(R.id.rl_wait);
        rl_outtime = (RelativeLayout) findViewById(R.id.rl_outtime);
        rl_close = (RelativeLayout) findViewById(R.id.rl_close);
        tv_all = (TextView) findViewById(R.id.tv_city);
        tv_wait = (TextView) findViewById(R.id.tv_wait);
        tv_outtime = (TextView) findViewById(R.id.tv_outtime);
        tv_close = (TextView) findViewById(R.id.tv_close);
        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);
        view_3 = findViewById(R.id.view_3);
        view_4 = findViewById(R.id.view_4);
        listview = (ListView) findViewById(R.id.listview);
        adapter = new MineAdapter(this, ls, handler);
        listview.setAdapter(adapter);
        rl_all.setOnClickListener(this);
        rl_wait.setOnClickListener(this);
        rl_outtime.setOnClickListener(this);
        rl_close.setOnClickListener(this);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2001) {
                String str = (String) msg.obj;
                close(str);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_all:
                tv_all.setTextColor(Color.parseColor("#6f61b3"));
                view_1.setBackgroundColor(Color.parseColor("#6f61b3"));
                tv_wait.setTextColor(Color.parseColor("#313131"));
                view_2.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_outtime.setTextColor(Color.parseColor("#313131"));
                view_3.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_close.setTextColor(Color.parseColor("#313131"));
                view_4.setBackgroundColor(Color.parseColor("#ffffff"));
                num = "4";
                getInf(num);
                break;
            case R.id.rl_wait:
                tv_wait.setTextColor(Color.parseColor("#6f61b3"));
                view_2.setBackgroundColor(Color.parseColor("#6f61b3"));
                tv_all.setTextColor(Color.parseColor("#313131"));
                view_1.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_outtime.setTextColor(Color.parseColor("#313131"));
                view_3.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_close.setTextColor(Color.parseColor("#313131"));
                view_4.setBackgroundColor(Color.parseColor("#ffffff"));
                num = "1";
                getInf(num);
                break;
            case R.id.rl_outtime:
                tv_outtime.setTextColor(Color.parseColor("#6f61b3"));
                view_3.setBackgroundColor(Color.parseColor("#6f61b3"));
                tv_wait.setTextColor(Color.parseColor("#313131"));
                view_2.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_all.setTextColor(Color.parseColor("#313131"));
                view_1.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_close.setTextColor(Color.parseColor("#313131"));
                view_4.setBackgroundColor(Color.parseColor("#ffffff"));
                num = "2";
                getInf(num);
                break;
            case R.id.rl_close:
                tv_close.setTextColor(Color.parseColor("#6f61b3"));
                view_4.setBackgroundColor(Color.parseColor("#6f61b3"));
                tv_wait.setTextColor(Color.parseColor("#313131"));
                view_2.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_outtime.setTextColor(Color.parseColor("#313131"));
                view_3.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_all.setTextColor(Color.parseColor("#313131"));
                view_1.setBackgroundColor(Color.parseColor("#ffffff"));
                num = "3";
                getInf(num);
                break;
        }
    }

    private void close(String id) {
        RequestParams params = new RequestParams();
        params.put("actMarketId", id);
        Log.i("tag", Constants.BASE_URL + "closeMyArtMarket&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "closeMyArtMarket", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                Log.e("tag", s.toString());
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        showToast("关闭成功");
                        getInf(num);
                    } else {
                        showToast("关闭失败");
                    }
                }
            }
        });
    }
}

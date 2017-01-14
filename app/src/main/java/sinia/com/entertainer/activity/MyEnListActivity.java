package sinia.com.entertainer.activity;

import android.graphics.Color;
import android.os.Bundle;
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
import sinia.com.entertainer.adapter.MineAdapter1;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.bean.MyAdBean;
import sinia.com.entertainer.bean.MyAdLsBean;
import sinia.com.entertainer.bean.MyReOutBean;
import sinia.com.entertainer.bean.MyReOutLsBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;

/**
 * 我的报名
 * type=2
 * Created by byw on 2016/12/8.
 */
public class MyEnListActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_all, rl_wait, rl_outtime, rl_close;
    private TextView tv_all, tv_wait, tv_outtime, tv_close;
    private View view_1, view_2, view_3, view_4;
    private ListView listview;
    private MineAdapter1 adapter;
    private List<MyAdBean> ls = new ArrayList<>();
    private String userId;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myenlist, "我的报名");
        getDoingView().setVisibility(View.GONE);
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        initView();
        getInf("4");
    }


    private void getInf(String type) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("type", type);
        Log.i("tag", Constants.BASE_URL + "mySignUp&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "mySignUp", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                Log.e("tag", s.toString());
                MyAdLsBean bean = gson.fromJson(s, MyAdLsBean.class);
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
        adapter = new MineAdapter1(this, ls);
        listview.setAdapter(adapter);
        rl_all.setOnClickListener(this);
        rl_wait.setOnClickListener(this);
        rl_outtime.setOnClickListener(this);
        rl_close.setOnClickListener(this);
    }

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
                getInf("4");
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
                getInf("1");
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
                getInf("2");
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
                getInf("3");
                break;
        }
    }
}

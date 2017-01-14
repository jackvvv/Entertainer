package sinia.com.entertainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.AcHasJoinAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.bean.DetailBean;
import sinia.com.entertainer.bean.DetailSignBean;
import sinia.com.entertainer.bean.SignBean;
import sinia.com.entertainer.bean.SignLsBean;
import sinia.com.entertainer.bean.UserIdBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;

/**
 * 已参与列表
 * Created by byw on 2016/12/26.
 */

public class AcHasJoinActivity extends BaseActivity {
    private ListView listview;
    private AcHasJoinAdapter adapter;
    private String mId, size, outId, userId;
    private List<SignBean> ls = new ArrayList<>();//参加人员
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        mId = getIntent().getStringExtra("mId");
        outId = getIntent().getStringExtra("outId");
        size = getIntent().getStringExtra("size");
        Log.e("tag", size);
        setContentView(R.layout.activity_achasjoin, "已报名 (" + size + ")");
        getDoingView().setVisibility(View.GONE);
        initView();
        getInf();
    }

    //获取已报名信息
    private void getInf() {
        RequestParams params = new RequestParams();
        params.put("actMarketId", mId);
        Log.i("tag", Constants.BASE_URL + "queryAllSignPeople&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "queryAllSignPeople", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    SignLsBean bean = gson.fromJson(s, SignLsBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        ls.clear();
                        ls.addAll(bean.getItems());
                        adapter.notifyDataSetChanged();
                    } else {
                    }
                }
            }
        });
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
        adapter = new AcHasJoinAdapter(this, ls);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AcHasJoinActivity.this, DetailedInformationActivity.class);
                intent.putExtra("did", ls.get(position).getUserId());
                if (outId.equals(userId)) {
                    intent.putExtra("type", "3");
                } else {
                    intent.putExtra("type", "2");
                }
                intent.putExtra("sId", ls.get(position).getSignId());
                startActivity(intent);
            }
        });
    }
}

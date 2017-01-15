package sinia.com.entertainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.ContentAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.ContentBean;
import sinia.com.entertainer.bean.ContentTypeBean;
import sinia.com.entertainer.chat.ChatActivity;
import sinia.com.entertainer.utils.ActivityManager;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;

/**
 * 通讯录界面
 * Created by byw on 2017/1/12.
 */
public class ContentActivity extends BaseActivity {
    @Bind(R.id.rl_search)
    RelativeLayout rl_search;
    @Bind(R.id.listview)
    ListView listview;
    private List<ContentTypeBean> ls = new ArrayList<>();
    private ContentAdapter adapter;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content, "通讯录");
        getDoingView().setVisibility(View.GONE);
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        initView();
        getInf();
    }

    private void initView() {
        adapter = new ContentAdapter(this, ls);
        listview.setAdapter(adapter);
        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳搜索
                Intent intent = new Intent(ContentActivity.this, SearchContentActivity.class);
                startActivity(intent);
            }
        });
    }

    //获取通讯录
    private void getInf() {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("content", "-1");
        Log.i("tag", Constants.BASE_URL + "contactList&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "contactList", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    ContentBean bean = gson.fromJson(s, ContentBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        ls.clear();
                        ls.addAll(bean.getFollowItems());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}

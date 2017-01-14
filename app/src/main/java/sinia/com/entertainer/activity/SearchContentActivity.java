package sinia.com.entertainer.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.ContentAdapter;
import sinia.com.entertainer.adapter.ContentDetailAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.bean.ContentBean;
import sinia.com.entertainer.bean.ContentDetailBean;
import sinia.com.entertainer.bean.ContentTypeBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;

/**
 * 搜索联系人界面
 * Created by byw on 2017/1/12.
 */
public class SearchContentActivity extends BaseActivity {
    @Bind(R.id.et_text)
    EditText et_text;
    @Bind(R.id.tv_chanel)
    TextView tv_chanel;
    @Bind(R.id.mlv)
    ListView mlv;
    private List<ContentTypeBean> ls = new ArrayList<>();
    private AsyncHttpClient client = new AsyncHttpClient();
    private String userId;
    private ContentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchcontent);
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        initView();
    }

    private void initView() {
        adapter = new ContentAdapter(this, ls);
        mlv.setAdapter(adapter);
        tv_chanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Utils.isEmpty(et_text.getText().toString())) {
                    getInf(et_text.getText().toString().trim());
                }
            }
        });
    }

    //获取信息
    private void getInf(String str) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("content",str);
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

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
import sinia.com.entertainer.adapter.VideoAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.bean.VideoDataDetBean;
import sinia.com.entertainer.bean.VideoDataLsBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.Utils;

/**
 * 搜索视频资料
 * Created by byw on 2016/12/19.
 */
public class SearchVideoDataActivity extends BaseActivity {
    @Bind(R.id.tv_chanel)
    TextView tv_chanel;
    @Bind(R.id.et_text)
    EditText et_text;
    private ListView mlv;
    private List<VideoDataDetBean> ls = new ArrayList<>();
    private VideoAdapter adapter;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchvideodata);
        initView();
    }


    //获取视频信息
    private void getInf(String str) {
        RequestParams params = new RequestParams();
        params.put("content", str);
        Log.i("tag", Constants.BASE_URL + "videoList&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "videoList", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    VideoDataLsBean bean = gson.fromJson(s, VideoDataLsBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        ls.clear();
                        ls.addAll(bean.getItems());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    private void initView() {
        mlv = (ListView) findViewById(R.id.mlv);
        adapter = new VideoAdapter(this, ls);
        mlv.setAdapter(adapter);
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
                    getInf(et_text.getText().toString());
                }
            }
        });


        tv_chanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

package sinia.com.entertainer.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.PersonInfBean;
import sinia.com.entertainer.utils.ActivityManager;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;

/**
 * 反馈界面
 * Created by byw on 2016/12/5.
 */
public class FeedbackActivity extends BaseActivity {
    @Bind(R.id.et_con)
    EditText et_con;
    @Bind(R.id.tv_submit)
    TextView tv_submit;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback, "帮助与反馈");
        getDoingView().setVisibility(View.GONE);
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        initView();
    }

    private void initView() {
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isEmpty(et_con.getEditableText().toString().trim())) {
                    showToast("请输入内容");
                } else {
                    submit(et_con.getEditableText().toString().trim());
                }
            }
        });
    }

    private void submit(String str) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("content", str);
        Log.i("tag", Constants.BASE_URL + "addXnAdvice&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "addXnAdvice", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        showToast("提交成功");
                        ActivityManager.getInstance().finishCurrentActivity();
                    }
                }
            }
        });
    }
}

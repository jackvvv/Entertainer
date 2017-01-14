package sinia.com.entertainer.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.CodeBean;
import sinia.com.entertainer.utils.ActivityManager;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;

/**
 * 实名认证
 * Created by byw on 2016/12/7.
 */
public class RealNameActivity extends BaseActivity {
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.et_num)
    EditText et_num;
    @Bind(R.id.img_up)
    ImageView img_up;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String id, name, num, img="-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname, "实名认证");
        getDoingView().setVisibility(View.VISIBLE);
        getDoingView().setText("提交");
        id = MyApplication.getInstance().getLoginBean().getUserId();
        initView();
    }

    private void initView() {
        img_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击上传
            }
        });
    }

    @Override
    public void doing() {
        name = et_name.getText().toString();
        num = et_num.getText().toString();
        if (!Utils.isEmpty(name)) {
            if (!Utils.isEmpty(num)) {
                if (!Utils.isEmpty(img)) {
                    getInf();
                } else {
                    showToast("请上传照片");
                }
            } else {
                showToast("请输入身份证号");
            }
        } else {
            showToast("请输入姓名");
        }
        getInf();
    }

    private void getInf() {
        RequestParams params = new RequestParams();
        params.put("userId", id);
        params.put("realName", name);
        params.put("idCard", num);
        params.put("imageUrl", img);
        Log.i("tag", Constants.BASE_URL + "personIdConfirm&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "personIdConfirm", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        showToast("提交成功等待审核");
                        ActivityManager.getInstance().finishCurrentActivity();
                    } else if (state == 0 && isSuccessful == 2) {
                        showToast("提交失败");
                    } else {
                    }
                }
            }
        });
    }
}

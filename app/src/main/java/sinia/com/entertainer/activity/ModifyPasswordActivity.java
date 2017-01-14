package sinia.com.entertainer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import sinia.com.entertainer.bean.CodeBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;

/**
 * 修改密码界面
 * 界面有问题 修改 输入旧密码
 * Created by byw on 2016/12/5.
 */
public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_code)
    EditText et_code;
    @Bind(R.id.et_pwd)
    EditText et_pwd;
    @Bind(R.id.tv_getcode)
    TextView tv_getcode;
    @Bind(R.id.tv_submit)
    TextView tv_submit;
    @Bind(R.id.tv_phone)
    TextView tv_phone;
    private int j = 60;
    private String gcode = "-1";//返回的验证码
    private String pwd;
    private String code;
    private String phone;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypwd, "修改密码");
        getDoingView().setVisibility(View.GONE);
        phone = MyApplication.getInstance().getLoginBean().getPhone();
        initView();
    }

    private void initView() {
        tv_phone.setText("为了保证账号安全，我们需要验证手机" + phone);
        tv_getcode.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_getcode:
                getCode(phone);
                break;
            case R.id.tv_submit:
                code = et_code.getText().toString().trim();
                pwd = et_pwd.getText().toString().trim();
                if (!Utils.isEmpty(code)) {
                    if (code.equals(gcode)) {
                        if (!Utils.isEmpty(pwd)) {
                            modify();
                        } else {
                            showToast("请输入密码");
                        }
                    } else {
                        showToast("验证码错误");
                    }
                } else {
                    showToast("请输入验证码");
                }

                break;
        }
    }

    private void modify() {
        RequestParams params = new RequestParams();
        params.put("newPwd", pwd);
        params.put("userId", MyApplication.getInstance().getLoginBean().getUserId());
        Log.i("tag", Constants.BASE_URL + "upPassword&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "upPassword", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    CodeBean bean = gson.fromJson(s, CodeBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        showToast("修改成功");
                    } else if (state == 0 && isSuccessful == 1) {
                        showToast("修改失败");
                    }
                }
            }
        });
    }


    private void getCode(String str) {
        RequestParams params = new RequestParams();
        params.put("telephone", str);
        params.put("type", "2");
        Log.i("tag", Constants.BASE_URL + "gainVolidate&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "gainVolidate", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    CodeBean bean = gson.fromJson(s, CodeBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 1) {
                        showToast("该手机号已注册");
                    } else if (state == 0 && isSuccessful == 3) {
                        showToast("验证码获取失败");
                    } else {
                        gcode = bean.getValidateCode();
                        showToast("验证码已发送");
                        tv_getcode.setClickable(false);
                        tv_getcode.setText("重新发送(" + i + ")");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (; j > 0; j--) {
                                    handler.sendEmptyMessage(-9);
                                    if (j <= 0) {
                                        break;
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                handler.sendEmptyMessage(-8);
                            }
                        }).start();
                    }
                }
            }
        });
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -9) {
                tv_getcode.setText("重新发送(" + j + ")");
            } else if (msg.what == -8) {
                tv_getcode.setText("获取验证码");
                tv_getcode.setClickable(true);
                j = 60;
            }
        }
    };
}

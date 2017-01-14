package sinia.com.entertainer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.CodeBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.Utils;

/**
 * 忘记密码
 * Created by byw on 2016/11/30.
 */
public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.et_phone)
    EditText et_phone;
    @Bind(R.id.et_code)
    EditText et_code;
    @Bind(R.id.et_pwd)
    EditText et_pwd;
    @Bind(R.id.et_repwd)
    EditText et_repwd;
    @Bind(R.id.tv_getcode)
    TextView tv_getcode;
    @Bind(R.id.tv_ok)
    TextView tv_ok;
    @Bind(R.id.rl_code)
    RelativeLayout rl_code;
    @Bind(R.id.ll_pwd)
    LinearLayout ll_pwd;
    private AsyncHttpClient client = new AsyncHttpClient();
    private int j = 60;
    private String gcode = "-1";//返回的验证码
    private String pwd;
    private String repwd;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget, "忘记密码");
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        tv_ok.setOnClickListener(this);
        tv_getcode.setOnClickListener(this);
    }

   /* @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(th);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_getcode:
                phone = et_phone.getText().toString();
                if (!Utils.isEmpty(phone)) {
                    if (!Utils.isMobileNO(phone)) {
                        showToast("请输入正确手机号");
                    } else {
                        getCode(phone);
                    }
                } else {
                    showToast("请输入手机号");
                }
                break;
            case R.id.tv_ok:
                if (tv_ok.getText().equals("提交")) {
                    if (!Utils.isEmpty(et_code.getText().toString())) {
                        if ((et_code.getText().toString()).equals(gcode)) {
                            et_phone.setEnabled(false);
                            rl_code.setVisibility(View.GONE);
                            ll_pwd.setVisibility(View.VISIBLE);
                            tv_ok.setText("完成");
                            //  handler.removeCallbacks(th);
                        } else {
                            showToast("验证码不正确");
                        }
                    } else {
                        showToast("请输入验证码");
                    }
                    break;
                }
                if (tv_ok.getText().equals("完成")) {
                    pwd = et_pwd.getText().toString().trim();
                    repwd = et_repwd.getText().toString().trim();
                    if (Utils.isEmpty(pwd) || Utils.isEmpty(repwd)) {
                        showToast("请输入密码");
                    } else {
                        if (pwd.equals(repwd)) {
                            exchange();
                        } else {
                            showToast("两次密码不一致");
                        }
                    }
                    break;
                }
                break;
            default:

                break;
        }
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
                        et_phone.setEnabled(false);
                        gcode = bean.getValidateCode();
                        showToast("验证码已发送");
                        tv_getcode.setClickable(false);
                        tv_getcode.setText("重新发送(" + i + ")");
                        th.start();
                      /*  new Thread(new Runnable() {
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
                        }).start();*/
                    }
                }
            }
        });
    }

    Thread th = new Thread() {
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
    };

    private void exchange() {
        RequestParams params = new RequestParams();
        params.put("telephone", phone);
        params.put("password", pwd);
        Log.i("tag", Constants.BASE_URL + "findPassword&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "findPassword", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        showToast("修改完成");
                        finish();
                    }
                    if (0 == state && 1 == isSuccessful) {
                        showToast("修改失败");
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
                if (tv_getcode != null) {
                    tv_getcode.setText("重新发送(" + j + ")");
                }
            } else if (msg.what == -8) {
                tv_getcode.setText("获取验证码");
                tv_getcode.setClickable(true);
                j = 60;
            }
        }
    };
}

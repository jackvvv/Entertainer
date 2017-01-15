package sinia.com.entertainer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import sinia.com.entertainer.DemoHelper;
import sinia.com.entertainer.R;
import sinia.com.entertainer.actionsheetdialog.ActionSheetDialogUtils;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.CodeBean;
import sinia.com.entertainer.utils.ActivityManager;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.Utils;

/**
 * 注册界面
 * Created by byw on 2016/11/30.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.et_phone)
    EditText et_phone;
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.et_pwd)
    EditText et_pwd;
    @Bind(R.id.et_tuijian)
    EditText et_tuijian;
    @Bind(R.id.et_code)
    EditText et_code;
    @Bind(R.id.tv_sex)
    TextView tv_sex;
    @Bind(R.id.tv_job)
    TextView tv_job;
    @Bind(R.id.tv_getcode)
    TextView tv_getcode;
    @Bind(R.id.tv_ok)
    TextView tv_ok;
    @Bind(R.id.tv_golog)
    TextView tv_golog;
    private String phone, name, sex, job, pwd, code;
    private String vcode = "-1";//邀请码选填
    private String gcode = "-1";//返回的验证码
    private String atype;//返回的职位类型
    private AsyncHttpClient client = new AsyncHttpClient();
    private int j = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register, "注册");
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        tv_sex.setOnClickListener(this);
        tv_job.setOnClickListener(this);
        tv_getcode.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        tv_golog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sex:
                ActionSheetDialogUtils.createSexDialog(RegisterActivity.this, tv_sex);
                break;
            case R.id.tv_job:
                Intent intent = new Intent(RegisterActivity.this, ChoiceJobActivity.class);
                intent.putExtra("type", "1");
                startActivityForResult(intent, 1001);
                break;
            case R.id.tv_getcode:
                if (!Utils.isEmpty(et_phone.getText().toString())) {
                    if (!Utils.isMobileNO(et_phone.getText().toString())) {
                        showToast("请输入正确手机号");
                    } else {
                        getCode(et_phone.getText().toString());
                    }
                } else {
                    showToast("请输入手机号");
                }
                break;
            case R.id.tv_ok:
                initDate();
                break;
            case R.id.tv_golog:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
        }
    }

    private void toRegister() {
        RequestParams params = new RequestParams();
        params.put("userName", name);
        params.put("password", pwd);
        params.put("telephone", phone);
        params.put("type", atype);
        params.put("code", vcode);
        params.put("sex", sex);
        params.put("profession", job);
        Log.i("tag", Constants.BASE_URL + "regist&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "regist", params, new AsyncHttpResponseHandler() {
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
//                        showToast("注册成功");
                        emRegister(phone);
//                        finish();
                    }
                    if (0 == state && 1 == isSuccessful) {
                        showToast("您的手机号已经注册过了");
                    }
                    if (0 == state && 2 == isSuccessful) {
                        showToast("注册失败");
                    }
                }
            }
        });
    }

    private void emRegister(final String phone) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(phone, "111111");
                    DemoHelper.getInstance().setCurrentUserName(phone);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "注册成功",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                } catch (final HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            int errorCode = e.getErrorCode();
                            if (errorCode == EMError.NETWORK_ERROR) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string
                                        .network_anomalies), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string
                                        .User_already_exists), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
                                Toast.makeText(getApplicationContext(), "registration_failed_without_permission",
                                        Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string
                                        .illegal_user_name), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration_failed", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private void getCode(String str) {
        RequestParams params = new RequestParams();
        params.put("telephone", str);
        params.put("type", "1");
        Log.i("tag", Constants.BASE_URL + "gainVolidate&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "gainVolidate", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                Log.i("tag", s.toString());
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    CodeBean bean = gson.fromJson(s, CodeBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 1) {
                        showToast("该手机号已注册");
                    } else if (state == 0 && isSuccessful == 3) {
                        showToast("验证码获取失败");
                    } else {
                        gcode = bean.getValidateCode();
                        showToast("验证码已发送---" + gcode);
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


    private void initDate() {
        phone = et_phone.getText().toString();
        name = et_name.getText().toString();
        job = tv_job.getText().toString();
        pwd = et_pwd.getText().toString();
        code = et_code.getText().toString();
        sex = tv_sex.getText().toString();
        if (!Utils.isEmpty(et_tuijian.getText().toString())) {
            vcode = et_tuijian.getText().toString();
        } else if (Utils.isEmpty(phone)) {
            showToast("请输入手机号");
        } else if (!Utils.isMobileNO(phone)) {
            showToast("请输入正确手机号");
        } else if (Utils.isEmpty(name)) {
            showToast("请输入姓名");
        } else if (Utils.isEmpty(pwd)) {
            showToast("请输入密码");
        } else if (Utils.isEmpty(code)) {
            showToast("请输入验证码");
        } else if (!gcode.equals(code)) {
            showToast("验证码错误");
        } else {
            if (!(tv_sex.getText().toString()).equals("选择性别")) {
                if ((tv_sex.getText().toString()).equals("男")) {
                    sex = "1";
                }
                if ((tv_sex.getText().toString()).equals("女")) {
                    sex = "2";
                }
                if (!job.equals("选择职业")) {
                    toRegister();
                } else {
                    showToast("请选择职业");
                }
            } else {
                showToast("请选择性别");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == 1001) {
                String str = data.getStringExtra("job");
                str = str.replace("[", "");
                str = str.replace("]", "");
                atype = data.getStringExtra("atype");
                tv_job.setText(str);
                tv_job.setTextColor(Color.parseColor("#313131"));
            }
        }

    }
}

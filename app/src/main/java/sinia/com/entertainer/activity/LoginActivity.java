package sinia.com.entertainer.activity;

import android.content.Intent;
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
import sinia.com.entertainer.bean.LoginBean;
import sinia.com.entertainer.bean.PersonInfBean;
import sinia.com.entertainer.bean.UserIdBean;
import sinia.com.entertainer.utils.ActivityManager;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.Utils;

/**
 * 登录界面
 * Created by byw on 2016/11/30.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_phone;
    private EditText et_pwd;
    private TextView tv_login;
    private TextView tv_enroll;
    private TextView tv_forget;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String phone, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login, "登录");
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("tag", "11111111");
    }

    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_enroll = (TextView) findViewById(R.id.tv_enroll);
        tv_login.setOnClickListener(this);
        tv_enroll.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                phone = et_phone.getText().toString();
                pwd = et_pwd.getText().toString();
                if (!Utils.isEmpty(phone)) {
                    if (!Utils.isEmpty(pwd)) {
                        toLogin();
                    } else {
                        showToast("请输入密码 ");
                    }
                } else {
                    showToast("请输入手机号 ");
                }
                break;
            case R.id.tv_enroll:
                startActivityForNoIntent(RegisterActivity.class);
                break;
            case R.id.tv_forget:
                startActivityForNoIntent(ForgetPwdActivity.class);
                break;
            default:
                break;
        }
    }

    private void toLogin() {
        RequestParams params = new RequestParams();
        params.put("telephone", phone);
        params.put("password", pwd);
        Log.i("tag", Constants.BASE_URL + "login&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "login", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    UserIdBean bean = gson.fromJson(s, UserIdBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        getInf(bean.getUserId());
                    } else {
                        showToast("登录失败");
                    }
                }
            }
        });
    }

    private void getInf(final String id) {
        RequestParams params = new RequestParams();
        params.put("userId", id);
        params.put("type", "1");
        params.put("beUserId", "-1");
        Log.i("tag", Constants.BASE_URL + "personInfo&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "personInfo", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    PersonInfBean bean = gson.fromJson(s, PersonInfBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        MyApplication.getInstance().setBooleanValue(
                                "is_login", true);
                        LoginBean lb = new LoginBean();
                        lb.setUserId(id);//用户id
                        lb.setAddress(bean.getAddress());//地址
                        lb.setBirth(bean.getBirth());//生日
                        lb.setBwh(bean.getBwh());//三围
                        lb.setIdConfirm(bean.getIdConfirm());//实名认证？（1.是0.否）
                        lb.setImageUrl(bean.getImageUrl());//头像
                        lb.setUserName(bean.getUserName());//姓名
                        lb.setProfession(bean.getProfession());//角色
                        lb.setvConfirm(bean.getvConfirm());//v认证
                        lb.setSign(bean.getIntroduction());//签名
                        lb.setSex(bean.getSex());//性别（1.男2.女）
                        lb.setTall(bean.getTall());//身高
                        lb.setWeight(bean.getWeight());//体重
                        lb.setPhone(bean.getTelephone());//手机号
                        MyApplication.getInstance().setLoginBean(lb);
                        showToast("登录成功");
                        finish();
                    }
                }
            }
        });
    }

}
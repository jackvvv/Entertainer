package sinia.com.entertainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.utils.ActivityManager;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;

/**
 * 设置界面
 * Created by byw on 2016/12/5.
 */
public class SetActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.rl_pwd)
    RelativeLayout rl_pwd;
    @Bind(R.id.rl_clear)
    RelativeLayout rl_clear;
    @Bind(R.id.rl_help)
    RelativeLayout rl_help;
    @Bind(R.id.rl_about)
    RelativeLayout rl_about;
    @Bind(R.id.tv_size)
    TextView tv_size;
    @Bind(R.id.tv_laytou)
    TextView tv_laytou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set, "设置");
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        rl_pwd.setOnClickListener(this);
        rl_clear.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        rl_help.setOnClickListener(this);
        tv_laytou.setOnClickListener(this);
        try {
            tv_size.setText(Utils.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_pwd:
                intent = new Intent(SetActivity.this, ModifyPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_clear:
                Utils.cleanInternalCache(SetActivity.this);
                showToast("清理完成");
                try {
                    tv_size.setText(Utils.getTotalCacheSize(this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_about:
                intent = new Intent(SetActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_help:
                intent = new Intent(SetActivity.this, FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_laytou:
                MyApplication.getInstance().setBooleanValue(
                        "is_login", false);
                Intent data = new Intent();
                setResult(666, data);
                ActivityManager.getInstance().finishCurrentActivity();
                showToast("退出成功");
                break;
        }
    }
}

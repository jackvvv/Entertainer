package sinia.com.entertainer.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;

/**
 * 关于演艺人
 * Created by byw on 2016/12/5.
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.rl_mark)
    RelativeLayout rl_mark;
    @Bind(R.id.rl_agreement)
    RelativeLayout rl_agreement;
    @Bind(R.id.rl_statement)
    RelativeLayout rl_statement;
    @Bind(R.id.tv_version)
    TextView tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus, "关于演艺人");
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        rl_mark.setOnClickListener(this);
        rl_agreement.setOnClickListener(this);
        rl_statement.setOnClickListener(this);
        tv_version.setText("演艺人 v" + getVersionName(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_mark:
                break;
            case R.id.rl_agreement:
                break;
            case R.id.rl_statement:
                break;
        }
    }


    //版本名
    public String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}

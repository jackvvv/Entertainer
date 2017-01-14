package sinia.com.entertainer.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.AlertDialog;

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

/**
 * 认证界面
 * Created by byw on 2016/12/7.
 */
public class IdentificationActivity extends BaseActivity implements View.OnClickListener {
    private Dialog dialog;
    @Bind(R.id.rl_realname)
    RelativeLayout rl_realname;
    @Bind(R.id.rl_radv)
    RelativeLayout rl_radv;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification, "我要认证");
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        rl_realname.setOnClickListener(this);
        rl_radv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_realname:
                if (MyApplication.getInstance().getLoginBean().getIdConfirm().equals("1")) {
                    showToast("已认证");
                } else {
                    Intent intent = new Intent(IdentificationActivity.this, RealNameActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_radv:
                if (MyApplication.getInstance().getLoginBean().getvConfirm().equals("1")) {
                    showToast("已认证");
                } else {
                    createDialog(IdentificationActivity.this);
                }
                break;
        }
    }

    public void createDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_vip, null);
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setAttributes(lp);
        dialog.setContentView(view, lp);
        TextView tv_contact = (TextView) view.findViewById(R.id.tv_contact);
        TextView tv_know = (TextView) view.findViewById(R.id.tv_know);
        tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showToast("联系成功");
            }
        });
        tv_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}

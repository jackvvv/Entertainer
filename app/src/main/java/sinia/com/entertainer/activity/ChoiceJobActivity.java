package sinia.com.entertainer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.utils.ActivityManager;

import android.view.View.OnClickListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 选择职业
 * Created by byw on 2016/11/30.
 */
public class ChoiceJobActivity extends BaseActivity implements OnClickListener {
    //-------演员类
    private TextView tv_host, tv_msing, tv_fsing, tv_ds, tv_team, tv_model, tv_magic, tv_beauty, tv_joker, tv_kongfu,
            tv_dj, tv_other, tv_edit;
    //-------企业类
    private TextView tv_night, tv_manager, tv_campany, tv_marry, tv_culture;

    private Map<String, String> amap = new HashMap<>();
    private Map<String, String> cmap = new HashMap<>();

    private String type;//1.注册 2.修改

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choicejob, "选择职业");
        getDoingView().setVisibility(View.VISIBLE);
        getDoingView().setText("确定");
        type = getIntent().getStringExtra("type");
        initView();
    }

    @Override
    public void doing() {
        if (type.equals("1")) {
            if (amap.size() == 0 && cmap.size() == 0) {
                showToast("请先选择职业");
            } else {
                Intent data = new Intent();
                if (amap.size() != 0) {
                    data.putExtra("job", amap.keySet().toString());
                    data.putExtra("atype","1");
                }
                if (cmap.size() != 0) {
                    data.putExtra("job", cmap.keySet().toString());
                    data.putExtra("atype","2");
                }
                setResult(1001, data);
                ActivityManager.getInstance().finishCurrentActivity();
            }
        }
        if (type.equals("2")) {
            //修改信息，调用保存接口
            if (amap.size() == 0 && cmap.size() == 0) {
                showToast("请先选择职业");
            } else {
                Intent data = new Intent();
                if (amap.size() != 0) {
                    data.putExtra("job", amap.keySet().toString());
                }
                if (cmap.size() != 0) {
                    data.putExtra("job", cmap.keySet().toString());
                }
                setResult(1001, data);
                ActivityManager.getInstance().finishCurrentActivity();
            }
        }
    }

    private void initView() {
        //-------演员类
        tv_host = (TextView) findViewById(R.id.tv_host);
        tv_msing = (TextView) findViewById(R.id.tv_msing);
        tv_fsing = (TextView) findViewById(R.id.tv_fsing);
        tv_ds = (TextView) findViewById(R.id.tv_ds);
        tv_model = (TextView) findViewById(R.id.tv_model);
        tv_magic = (TextView) findViewById(R.id.tv_magic);
        tv_beauty = (TextView) findViewById(R.id.tv_beauty);
        tv_joker = (TextView) findViewById(R.id.tv_joker);
        tv_kongfu = (TextView) findViewById(R.id.tv_kongfu);
        tv_dj = (TextView) findViewById(R.id.tv_dj);
        tv_other = (TextView) findViewById(R.id.tv_other);
        tv_edit = (TextView) findViewById(R.id.tv_edit);
        tv_team = (TextView) findViewById(R.id.tv_team);
        tv_host.setOnClickListener(this);
        tv_msing.setOnClickListener(this);
        tv_fsing.setOnClickListener(this);
        tv_ds.setOnClickListener(this);
        tv_model.setOnClickListener(this);
        tv_magic.setOnClickListener(this);
        tv_beauty.setOnClickListener(this);
        tv_joker.setOnClickListener(this);
        tv_kongfu.setOnClickListener(this);
        tv_dj.setOnClickListener(this);
        tv_other.setOnClickListener(this);
        tv_edit.setOnClickListener(this);
        tv_team.setOnClickListener(this);
        //-------企业类
        tv_night = (TextView) findViewById(R.id.tv_night);
        tv_manager = (TextView) findViewById(R.id.tv_manager);
        tv_campany = (TextView) findViewById(R.id.tv_campany);
        tv_marry = (TextView) findViewById(R.id.tv_marry);
        tv_culture = (TextView) findViewById(R.id.tv_culture);
        tv_night.setOnClickListener(this);
        tv_manager.setOnClickListener(this);
        tv_campany.setOnClickListener(this);
        tv_marry.setOnClickListener(this);
        tv_culture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //-------演员类
            case R.id.tv_host:
                actor(tv_host, tv_host.getText().toString());
                break;
            case R.id.tv_msing:
                actor(tv_msing, tv_msing.getText().toString());
                break;
            case R.id.tv_fsing:
                actor(tv_fsing, tv_fsing.getText().toString());
                break;
            case R.id.tv_ds:
                actor(tv_ds, tv_ds.getText().toString());
                break;
            case R.id.tv_model:
                actor(tv_model, tv_model.getText().toString());
                break;
            case R.id.tv_magic:
                actor(tv_magic, tv_magic.getText().toString());
                break;
            case R.id.tv_beauty:
                actor(tv_beauty, tv_beauty.getText().toString());
                break;
            case R.id.tv_joker:
                actor(tv_joker, tv_joker.getText().toString());
                break;
            case R.id.tv_kongfu:
                actor(tv_kongfu, tv_kongfu.getText().toString());
                break;
            case R.id.tv_dj:
                actor(tv_dj, tv_dj.getText().toString());
                break;
            case R.id.tv_other:
                actor(tv_other, tv_other.getText().toString());
                break;
            case R.id.tv_edit:
                actor(tv_edit, tv_edit.getText().toString());
                break;
            case R.id.tv_team:
                actor(tv_team, tv_team.getText().toString());
                break;
            //-------企业类
            case R.id.tv_night:
                campany(tv_night, tv_night.getText().toString());
                break;
            case R.id.tv_manager:
                campany(tv_manager, tv_manager.getText().toString());
                break;
            case R.id.tv_campany:
                campany(tv_campany, tv_campany.getText().toString());
                break;
            case R.id.tv_marry:
                campany(tv_marry, tv_marry.getText().toString());
                break;
            case R.id.tv_culture:
                campany(tv_culture, tv_culture.getText().toString());
                break;
        }
    }

    private void actor(TextView tv, String str) {
        if (cmap.size() == 0) {
            if (amap.containsKey(str)) {
                amap.remove(str);
                tv.setTextColor(Color.parseColor("#313131"));
                tv.setBackgroundResource(R.drawable.lay_sharp3);

            } else if (amap.size() < 2) {
                amap.put(str, "");
                tv.setTextColor(Color.parseColor("#ce171a"));
                tv.setBackgroundResource(R.drawable.lay_sharp4);
            }
            if (amap.size() == 0) {
                getDoingView().setText("确定");
            } else {
                getDoingView().setText("确定(" + amap.size() + ")个");
            }
        }
    }

    private void campany(TextView tv, String str) {
        if (amap.size() == 0) {
            if (cmap.containsKey(str)) {
                cmap.remove(str);
                tv.setTextColor(Color.parseColor("#313131"));
                tv.setBackgroundResource(R.drawable.lay_sharp3);
            } else if (cmap.size() < 1) {
                cmap.put(str, "");
                tv.setTextColor(Color.parseColor("#ce171a"));
                tv.setBackgroundResource(R.drawable.lay_sharp4);
            }
            if (cmap.size() == 0) {
                getDoingView().setText("确定");
            } else {
                getDoingView().setText("确定(" + cmap.size() + ")个");
            }
        }
    }
}

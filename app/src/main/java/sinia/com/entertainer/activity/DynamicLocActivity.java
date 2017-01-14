package sinia.com.entertainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.DynamicLocAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.utils.ActivityManager;

/**
 * 发布动态定位
 * Created by byw on 2016/12/14.
 */
public class DynamicLocActivity extends BaseActivity {
    private DynamicLocAdapter adapter;
    private List<String> locls;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.rl_no)
    RelativeLayout rl_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamicloc, "所在位置");
        getDoingView().setVisibility(View.GONE);
        initData();
        initView();
    }

    private void initData() {
        locls = new ArrayList<String>();
        locls.add("北京市");
        locls.add("天津市");
        locls.add("上海市");
        locls.add("重庆市");
        locls.add("河北省");
        locls.add("山西省");
        locls.add("辽宁省");
        locls.add("吉林省");
        locls.add("黑龙江省");
        locls.add("江苏省");
        locls.add("浙江省");
        locls.add("安徽省");
        locls.add("福建省");
        locls.add("台湾省");
        locls.add("江西省");
        locls.add("山东省");
        locls.add("河南省");
        locls.add("湖北省");
        locls.add("湖南省");
        locls.add("广东省");
        locls.add("海南省");
        locls.add("四川省");
        locls.add("贵州省");
        locls.add("云南省");
        locls.add("陕西省");
        locls.add("甘肃省");
        locls.add("青海省");
        locls.add("内蒙古自治区");
        locls.add("广西壮族自治区");
        locls.add("西藏自治区");
        locls.add("宁夏回族自治区");
        locls.add("新疆维吾尔自治区");
        locls.add("香港特别行政区");
        locls.add("澳门特别行政区");
        locls.add("国外");
    }

    private void initView() {
        adapter = new DynamicLocAdapter(this, locls);
        listview.setAdapter(adapter);
        rl_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("loc", "no");
                setResult(1002, intent);
                ActivityManager.getInstance().finishCurrentActivity();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("loc", locls.get(position));
                setResult(1002, intent);
                ActivityManager.getInstance().finishCurrentActivity();
            }
        });
    }
}

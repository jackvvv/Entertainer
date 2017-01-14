package sinia.com.entertainer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.MyDownloadAdapter;
import sinia.com.entertainer.base.BaseActivity;

/**
 * 我的下载
 * Created by byw on 2016/12/9.
 */
public class MyDownloadActivity extends BaseActivity {

    @Bind(R.id.listview)
    ListView listview;
    private List<String> ls;
    private MyDownloadAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download, "我的下载");
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        adapter = new MyDownloadAdapter(this, ls);
        listview.setAdapter(adapter);
    }
}

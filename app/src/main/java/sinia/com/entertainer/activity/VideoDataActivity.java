package sinia.com.entertainer.activity;

import android.app.AlertDialog;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.VideoAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.VideoDataDetBean;
import sinia.com.entertainer.bean.VideoDataLsBean;
import sinia.com.entertainer.utils.ActivityManager;
import sinia.com.entertainer.utils.Constants;

/**
 * 视频资料
 * Created by byw on 2016/12/12.
 */
public class VideoDataActivity extends BaseActivity {
    private List<VideoDataDetBean> ls = new ArrayList<>();
    private VideoAdapter adapter;
    @Bind(R.id.listview)
    ListView listview;
    private AsyncHttpClient client = new AsyncHttpClient();
    @Bind(R.id.rl_out)
    RelativeLayout rl_out;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videodata, "视频资料");
        getDoingView().setVisibility(View.GONE);
        getImg_pic().setVisibility(View.VISIBLE);
        getImg_pic().setImageResource(R.drawable.searchb);
        initView();
        getInf();
    }

    @Override
    public void doing() {
        Intent intent = new Intent(VideoDataActivity.this, SearchVideoDataActivity.class);
        startActivity(intent);
    }

    //获取视频信息
    private void getInf() {
        RequestParams params = new RequestParams();
        params.put("content", "-1");
        Log.i("tag", Constants.BASE_URL + "videoList&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "videoList", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    VideoDataLsBean bean = gson.fromJson(s, VideoDataLsBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        ls.clear();
                        ls.addAll(bean.getItems());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    private void initView() {
        adapter = new VideoAdapter(this, ls);
        listview.setAdapter(adapter);
        rl_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createConDialog(VideoDataActivity.this);
            }
        });
    }

    public void createConDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_out, null);
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setAttributes(lp);
        dialog.setContentView(view, lp);
        TextView tv_contact = (TextView) view.findViewById(R.id.tv_contact);
        TextView tv_know = (TextView) view.findViewById(R.id.tv_know);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_detail = (TextView) view.findViewById(R.id.tv_detail);
        tv_title.setText("发布视频");
        tv_detail.setText("发布视频需联系我们!");
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

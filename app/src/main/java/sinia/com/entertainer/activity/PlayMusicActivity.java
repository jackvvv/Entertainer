package sinia.com.entertainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.DataDisAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.MusicDetBean;
import sinia.com.entertainer.bean.MusicDetComBean;
import sinia.com.entertainer.utils.ActivityManager;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;

/**
 * 播放音乐
 * Created by byw on 2016/12/13.
 */
public class PlayMusicActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.img_img)
    ImageView img_img;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_sing)
    TextView tv_sing;
    @Bind(R.id.tv_num)
    TextView tv_num;
    @Bind(R.id.tv_re)
    TextView tv_re;
    @Bind(R.id.rl_good)
    RelativeLayout rl_good;
    @Bind(R.id.img_collect)
    ImageView img_collect;
    @Bind(R.id.img_load)
    ImageView img_load;
    @Bind(R.id.img_share)
    ImageView img_share;
    @Bind(R.id.img_good)
    ImageView img_good;
    @Bind(R.id.img_play)
    ImageView img_play;
    @Bind(R.id.img_send)
    ImageView img_send;
    @Bind(R.id.et_dis)
    EditText et_dis;
    private List<MusicDetComBean> ls = new ArrayList<>();
    private DataDisAdapter adapter;
    private String userId, mId, kind;
    private AsyncHttpClient client = new AsyncHttpClient();
    private MusicDetBean bean;
    private String beComId = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmusic, "播放");
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        mId = getIntent().getStringExtra("id");
        kind = getIntent().getStringExtra("kind");
        Log.i("tag", "kind=" + kind);
        if (kind.equals("4")) {
            getDoingView().setVisibility(View.VISIBLE);
            getDoingView().setText("伴奏");
        } else {
            getDoingView().setVisibility(View.GONE);
        }
        initView();
        getInf();
    }

    @Override
    public void doing() {
        Intent intent = new Intent(PlayMusicActivity.this, AccompanimentActivity.class);
        intent.putExtra("mId", mId);
        startActivity(intent);
    }

    //获取歌曲详情
    private void getInf() {
        RequestParams params = new RequestParams();
        params.put("musicDataId", mId);
        params.put("userId", userId);
        Log.i("tag", Constants.BASE_URL + "musicDataDetail&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "musicDataDetail", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    bean = gson.fromJson(s, MusicDetBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        ls.clear();
                        ls.addAll(bean.getComItems());
                        adapter.notifyDataSetChanged();
                        initData();
                    }
                }
            }
        });
    }

    private void commit(String str) {
        RequestParams params = new RequestParams();
        params.put("type", "2");
        params.put("userId", userId);
        params.put("content", str);
        params.put("circleId", "-1");
        params.put("musicDataId", mId);
        params.put("videoId", "-1");
        params.put("beComId", beComId);
        Log.i("tag", Constants.BASE_URL + "makeComment&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "makeComment", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        showToast("评论成功");
                        et_dis.setText("");
                        tv_re.setText("");
                        getInf();
                    } else {
                        showToast("评论失败");
                    }
                }
            }
        });
    }

    private void initView() {
        adapter = new DataDisAdapter(this, ls);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message msg = new Message();
                msg.what = 10001;
                msg.arg1 = position;
                handler.sendMessage(msg);

            }
        });
        img_send.setOnClickListener(this);
        rl_good.setOnClickListener(this);
        img_collect.setOnClickListener(this);
        img_load.setOnClickListener(this);
        img_share.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_send:
                if (Utils.isEmpty(et_dis.getText().toString().trim())) {
                    showToast("请输入评论");
                } else {
                    commit(et_dis.getText().toString().trim());
                }
                break;
            case R.id.rl_good:
                if (bean.getZanStatus().equals("2")) {
                    showToast("已点赞");
                } else {
                    cGood(mId);
                }
                break;
            case R.id.img_collect:
                if (bean.getCollectionStatus().equals("2")) {
                    showToast("已收藏");
                } else {
                    cCollect(mId);
                }
                break;
            case R.id.img_load:
                break;
            case R.id.img_share:
                break;
        }
    }


    //收藏
    private void cCollect(String str) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("type", "1");
        params.put("circleId", "-1");
        params.put("musicDataId", str);
        params.put("vedioId", "-1");
        Log.i("tag", Constants.BASE_URL + "makeCollection&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "makeCollection", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, String s) {
                        super.onSuccess(i, s);
                        Gson gson = new Gson();
                        if (s.contains("isSuccessful")
                                && s.contains("state")) {
                            Log.e("tag", s.toString());
                            JsonBean bean = gson.fromJson(s, JsonBean.class);
                            int state = bean.getState();
                            int isSuccessful = bean.getIsSuccessful();
                            if (state == 0 && isSuccessful == 0) {
                                getInf();
                                showToast("收藏成功");
                            } else {
                                showToast("收藏失败");
                            }
                        }
                    }
                }

        );
    }

    //点赞
    private void cGood(String str) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("type", "2");
        params.put("circleId", "-1");
        params.put("musicDataId", str);
        params.put("videoId", "-1");
        Log.i("tag", Constants.BASE_URL + "makeZan&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "makeZan", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, String s) {
                        super.onSuccess(i, s);
                        Gson gson = new Gson();
                        if (s.contains("isSuccessful")
                                && s.contains("state")) {
                            Log.e("tag", s.toString());
                            JsonBean bean = gson.fromJson(s, JsonBean.class);
                            int state = bean.getState();
                            int isSuccessful = bean.getIsSuccessful();
                            if (state == 0 && isSuccessful == 0) {
                                getInf();
                                showToast("点赞成功");
                            } else {
                                showToast("点赞失败");
                            }
                        }
                    }
                }

        );
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 10001) {
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                int ww = display.getWidth() / 4;
                int position = msg.arg1;
                tv_re.setText("@" + ls.get(position).getComName());
                if (tv_re.getWidth() > ww) {
                    tv_re.setMaxWidth(ww);
                    tv_re.setWidth(ww);
                }
                beComId = ls.get(position).getUserId();
            }
        }
    };

    private void initData() {
        Glide.with(this).load(bean.getImageUrl()).placeholder(R.drawable.moren1).crossFade()
                .into(img_img);
        tv_name.setText(bean.getName());
        tv_sing.setText(bean.getAuthor());
        tv_num.setText(bean.getLikeNum());
    }

}

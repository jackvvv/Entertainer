package sinia.com.entertainer.activity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.DataDisAdapter;
import sinia.com.entertainer.base.BaseActivity;
import io.vov.vitamio.widget.MediaController;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.MusicDetBean;
import sinia.com.entertainer.bean.MusicDetComBean;
import sinia.com.entertainer.bean.VideoDataDetBean;
import sinia.com.entertainer.bean.VideoDetBean;
import sinia.com.entertainer.utils.ActivityManager;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;
import sinia.com.entertainer.view.MyListView;

/**
 * 播放视频
 * Created by byw on 2017/1/10.
 */
public class PlayVideoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.videoview)
    VideoView videoview;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String userId, vId;
    private VideoDetBean bean;
    @Bind(R.id.tv_count)
    TextView tv_count;
    @Bind(R.id.tv_share)
    TextView tv_share;
    @Bind(R.id.tv_collect)
    TextView tv_collect;
    @Bind(R.id.tv_good)
    TextView tv_good;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.tv_re)
    TextView tv_re;
    @Bind(R.id.img_head)
    ImageView img_head;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.img_v)
    ImageView img_v;
    @Bind(R.id.img_realname)
    ImageView img_realname;
    @Bind(R.id.img_send)
    ImageView img_send;
    @Bind(R.id.et_dis)
    EditText et_dis;
    private String zanNum;//1未点赞 2已点赞
    private String collectionNum;//1未收藏 2已收藏
    private List<MusicDetComBean> ls = new ArrayList<>();
    private DataDisAdapter adapter;
    private String beComId = "-1";
    private MyListView mylistview;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playvideo);
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        vId = getIntent().getStringExtra("vId");
        Log.e("tag", vId);
        initView();
        getInf();
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

    //获取视频资料详情
    private void getInf() {
        RequestParams params = new RequestParams();
        params.put("vedioId", vId);
        params.put("userId", userId);
        Log.i("tag", Constants.BASE_URL + "videoDetail&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "videoDetail", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    bean = gson.fromJson(s, VideoDetBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        //  initData();
                    }
                }
            }
        });
    }

    private void initView() {
        mylistview = (MyListView) findViewById(R.id.mylistview);
        adapter = new DataDisAdapter(this, ls);
        mylistview.setAdapter(adapter);
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message msg = new Message();
                msg.what = 10001;
                msg.arg1 = position;
                handler.sendMessage(msg);

            }
        });
    }

    private void initData() {
        Uri uri = Uri.parse(bean.getVideoUrl());
        videoview.setVideoURI(uri);
        videoview.setMediaController(new MediaController(this));
        MediaController mMediaController = new MediaController(this);//实例化控制器
        mMediaController.show(3000);//控制器显示5s后自动隐藏
        videoview.setMediaController(mMediaController);//绑定控制器
        videoview.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//设置播放画质 高画质
        videoview.requestFocus();//取得焦点
        videoview.start();

        tv_count.setText(bean.getNumber());
        tv_name.setText(bean.getUserName());
        tv_time.setText(bean.getCreateTime());
        if (bean.getvConfirm().equals("1")) {
            img_v.setVisibility(View.VISIBLE);
        } else {
            img_v.setVisibility(View.INVISIBLE);
        }
        if (bean.getIdConfirm().equals("2")) {
            img_realname.setVisibility(View.VISIBLE);
        } else {
            img_realname.setVisibility(View.INVISIBLE);
        }
        Glide.with(this).load(bean.getUserImage()).placeholder(R.drawable.moren).crossFade()
                .into(img_head);
        zanNum = bean.getZanNum();
        collectionNum = bean.getCollectionNum();
        if (zanNum.equals("1")) {
            Drawable dd = getResources().getDrawable(R.drawable.pvgood);
            dd.setBounds(0, 0, dd.getMinimumWidth(), dd.getMinimumHeight());
            tv_good.setCompoundDrawables(null, dd, null, null);
        } else {
            Drawable dd = getResources().getDrawable(R.drawable.pvgooded);
            dd.setBounds(0, 0, dd.getMinimumWidth(), dd.getMinimumHeight());
            tv_good.setCompoundDrawables(null, dd, null, null);
        }
        if (collectionNum.equals("1")) {
            Drawable dd = getResources().getDrawable(R.drawable.pvcollect);
            dd.setBounds(0, 0, dd.getMinimumWidth(), dd.getMinimumHeight());
            tv_collect.setCompoundDrawables(null, dd, null, null);
        } else {
            Drawable dd = getResources().getDrawable(R.drawable.pvcollected);
            dd.setBounds(0, 0, dd.getMinimumWidth(), dd.getMinimumHeight());
            tv_collect.setCompoundDrawables(null, dd, null, null);
        }
        ls.clear();
        ls.addAll(bean.getItems());
        adapter.notifyDataSetChanged();

        img_back.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        tv_good.setOnClickListener(this);
        tv_collect.setOnClickListener(this);
        img_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_share:
                showToast("分享");
                break;
            case R.id.tv_good:
                if (zanNum.equals("2")) {
                    showToast("已点赞");
                } else {
                    //点赞接口
                    cGood(vId);
                }
                break;
            case R.id.tv_collect:
                if (collectionNum.equals("2")) {
                    showToast("已收藏");
                } else {
                    //收藏接口
                    cCollect(vId);
                }
            case R.id.img_send:
                if (Utils.isEmpty(et_dis.getText().toString().trim())) {
                    showToast("请输入评论");
                } else {
                    commit(et_dis.getText().toString().trim());
                }
                break;
        }
    }

    private void commit(String str) {
        RequestParams params = new RequestParams();
        params.put("type", "3");
        params.put("userId", userId);
        params.put("content", str);
        params.put("circleId", "-1");
        params.put("musicDataId", "-1");
        params.put("videoId", vId);
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
                    Log.e("tag", s.toString());
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

    //点赞
    private void cGood(String str) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("type", "3");
        params.put("circleId", "-1");
        params.put("musicDataId", "-1");
        params.put("videoId", str);
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

    //收藏
    private void cCollect(String str) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("type", "2");
        params.put("circleId", "-1");
        params.put("musicDataId", "-1");
        params.put("vedioId", str);
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


}

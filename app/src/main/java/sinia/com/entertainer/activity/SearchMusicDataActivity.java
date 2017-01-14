package sinia.com.entertainer.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.MusicDataAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.MusicDataBean;
import sinia.com.entertainer.bean.MusicDataDetBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;

/**
 * 音乐搜索界面
 * Created by byw on 2016/12/19.
 */
public class SearchMusicDataActivity extends BaseActivity {
    private TextView tv_chanel;
    private EditText et_text;
    private ListView mlv;
    private MusicDataAdapter adapter;//音乐
    private List<MusicDataDetBean> items = new ArrayList<>();//资料列表
    private PopupWindow popWindow;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String userId;
    private String mId;
    private Integer zanStatus, collectionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmusicdata);
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        initView();
    }

    private void initView() {
        tv_chanel = (TextView) findViewById(R.id.tv_chanel);
        et_text = (EditText) findViewById(R.id.et_text);
        mlv = (ListView) findViewById(R.id.mlv);
        adapter = new MusicDataAdapter(this, items, handler, "1");
        adapter.kind = "伴奏";
        mlv.setAdapter(adapter);
        tv_chanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(et_text.getText().toString());
            }
        });
    }

    private void search(String str) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("content", str);
        Log.i("tag", Constants.BASE_URL + "queryMusicDataList&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "queryMusicDataList", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, String s) {
                        super.onSuccess(i, s);
                        Gson gson = new Gson();
                        if (s.contains("isSuccessful")
                                && s.contains("state")) {
                            Log.e("tag", s.toString());
                            MusicDataBean bean = gson.fromJson(s, MusicDataBean.class);
                            int state = bean.getState();
                            int isSuccessful = bean.getIsSuccessful();
                            if (state == 0 && isSuccessful == 0) {
                                items.clear();
                                items.addAll(bean.getItems());
                                adapter.notifyDataSetChanged();
                            } else {
                            }
                        }
                    }
                }

        );
    }

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
                                search(et_text.getText().toString());
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
        params.put("type", "1");
        params.put("circleId", "-1");
        params.put("musicId", str);
        params.put("vedioId", "-1");
        Log.i("tag", Constants.BASE_URL + "makeLike&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "makeLike", params, new AsyncHttpResponseHandler() {
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
                                search(et_text.getText().toString());
                                showToast("收藏成功");
                            } else {
                                showToast("收藏失败");
                            }
                        }
                    }
                }

        );
    }

    //回调
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1001) {
                if (msg.what == 1001) {
                    mId = (String) msg.obj;
                    zanStatus = msg.arg1;
                    collectionStatus = msg.arg2;
                    createDialog(SearchMusicDataActivity.this);
                }

            }

        }
    };

    //选择框
    public void createDialog(Context context) {

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout v = (LinearLayout) layoutInflater.inflate(
                R.layout.layout_musicdata, null);

        popWindow = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
                .WRAP_CONTENT);
        popWindow.setAnimationStyle(R.style.popwin_anim_style1);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.5f;
        this.getWindow().setAttributes(lp);

        TextView tv_name = (TextView) v.findViewById(R.id.tv_name);
        TextView tv_good = (TextView) v.findViewById(R.id.tv_good);
        TextView tv_collect = (TextView) v.findViewById(R.id.tv_collect);
        TextView tv_download = (TextView) v.findViewById(R.id.tv_download);
        TextView tv_dis = (TextView) v.findViewById(R.id.tv_dis);
        TextView tv_share = (TextView) v.findViewById(R.id.tv_share);

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        tv_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zanStatus == 1) {
                    popWindow.dismiss();
                    cGood(mId);
                } else {
                    showToast("已赞过该歌曲");
                }
            }
        });
        tv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionStatus == 1) {
                    popWindow.dismiss();
                    cCollect(mId);
                } else {
                    showToast("已收藏该歌曲");
                }

            }
        });
    }
}

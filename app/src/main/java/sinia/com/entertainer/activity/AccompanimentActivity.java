package sinia.com.entertainer.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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

import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.MusicDataAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.bean.MusicDataBean;
import sinia.com.entertainer.bean.MusicDataDetBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;

/**
 * 伴奏界面
 * Created by byw on 2017/1/3.
 */
public class AccompanimentActivity extends BaseActivity implements View.OnClickListener {
    private String userId, mId;
    private ImageView img_back, img_select;
    private TextView tv_all, tv_over, tv_title;
    private LinearLayout ll_choice;
    private MusicDataAdapter adapter;//音乐
    private List<MusicDataDetBean> items = new ArrayList<>();//资料列表
    private ListView listview;
    private PopupWindow popWindow;
    private AsyncHttpClient client = new AsyncHttpClient();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompaniment);
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        mId = getIntent().getStringExtra("mId");
        initView();
        adapter.kind = "伴奏";
        getInf();
    }

    //获取伴奏列表
    private void getInf() {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("musicDataId", mId);
        Log.i("tag", Constants.BASE_URL + "banMusicList&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "banMusicList", params, new AsyncHttpResponseHandler() {
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

    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_select = (ImageView) findViewById(R.id.img_select);
        tv_all = (TextView) findViewById(R.id.tv_all);
        tv_over = (TextView) findViewById(R.id.tv_over);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_choice = (LinearLayout) findViewById(R.id.ll_choice);
        adapter = new MusicDataAdapter(this, items, handler, "1");
        listview.setAdapter(adapter);
    }

    //回调
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1001) {
                createDialog(AccompanimentActivity.this);
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_select:
                img_select.setVisibility(View.GONE);
                img_back.setVisibility(View.GONE);
                tv_all.setVisibility(View.VISIBLE);
                tv_over.setVisibility(View.VISIBLE);
                ll_choice.setVisibility(View.VISIBLE);
                adapter.type = "2";
                break;
            case R.id.tv_over:
                img_select.setVisibility(View.VISIBLE);
                img_back.setVisibility(View.VISIBLE);
                tv_all.setVisibility(View.GONE);
                tv_over.setVisibility(View.GONE);
                ll_choice.setVisibility(View.GONE);
                adapter.type = "1";
                break;
            case R.id.tv_all:
                break;
        }
    }

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
    }
}

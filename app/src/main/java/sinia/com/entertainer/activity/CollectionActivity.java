package sinia.com.entertainer.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.ArtAdapter;
import sinia.com.entertainer.adapter.MusicAdapter;
import sinia.com.entertainer.adapter.VideoAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.bean.VideoDataDetBean;
import sinia.com.entertainer.utils.city.CityPicker;

/**
 * 收藏界面
 * Created by byw on 2016/12/7.
 */
public class CollectionActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.listview)
    ListView listview;
    private TextView tv_music, tv_video, tv_art;
    private RelativeLayout rl_music, rl_video, rl_art;
    private View view_1, view_2, view_3;
    private ArtAdapter aat;//演艺圈
    private MusicAdapter mat;//音乐
    private VideoAdapter vat;//视频
    private List<String> ls;
    private PopupWindow popWindow;
    private List<VideoDataDetBean> vls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection, "我的收藏");
        getDoingView().setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        tv_music = (TextView) findViewById(R.id.tv_music);
        tv_video = (TextView) findViewById(R.id.tv_video);
        tv_art = (TextView) findViewById(R.id.tv_art);
        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);
        view_3 = findViewById(R.id.view_3);
        rl_music = (RelativeLayout) findViewById(R.id.rl_music);
        rl_video = (RelativeLayout) findViewById(R.id.rl_video);
        rl_art = (RelativeLayout) findViewById(R.id.rl_art);
        rl_music.setOnClickListener(this);
        rl_video.setOnClickListener(this);
        rl_art.setOnClickListener(this);
        aat = new ArtAdapter(this, ls);
        mat = new MusicAdapter(this, ls, handler);
        vat = new VideoAdapter(this, vls);
        listview.setAdapter(mat);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_music:
                tv_music.setTextColor(Color.parseColor("#6f61b3"));
                view_1.setBackgroundColor(Color.parseColor("#6f61b3"));
                tv_video.setTextColor(Color.parseColor("#313131"));
                view_2.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_art.setTextColor(Color.parseColor("#313131"));
                view_3.setBackgroundColor(Color.parseColor("#ffffff"));
                listview.setAdapter(mat);
                break;
            case R.id.rl_video:
                tv_music.setTextColor(Color.parseColor("#313131"));
                view_1.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_video.setTextColor(Color.parseColor("#6f61b3"));
                view_2.setBackgroundColor(Color.parseColor("#6f61b3"));
                tv_art.setTextColor(Color.parseColor("#313131"));
                view_3.setBackgroundColor(Color.parseColor("#ffffff"));
                listview.setAdapter(vat);
                break;
            case R.id.rl_art:
                tv_music.setTextColor(Color.parseColor("#313131"));
                view_1.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_video.setTextColor(Color.parseColor("#313131"));
                view_2.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_art.setTextColor(Color.parseColor("#6f61b3"));
                view_3.setBackgroundColor(Color.parseColor("#6f61b3"));
                listview.setAdapter(aat);
                break;
        }
    }

    //回调
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1001) {
                createDialog(CollectionActivity.this);
            }

        }
    };


    //选择框
    public void createDialog(Context context) {

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout v = (LinearLayout) layoutInflater.inflate(
                R.layout.layout_music, null);

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
        TextView tv_delete = (TextView) v.findViewById(R.id.tv_delete);
        TextView tv_download = (TextView) v.findViewById(R.id.tv_download);
        TextView tv_dis = (TextView) v.findViewById(R.id.tv_dis);

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

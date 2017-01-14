package sinia.com.entertainer.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.MyArtAdapter;
import sinia.com.entertainer.adapter.MyWorkAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.bean.PersonInfBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;
import sinia.com.entertainer.view.MyScrollView;


/**
 * 我的资料
 * Created by byw on 2016/12/9.
 */
public class MineActivity extends BaseActivity implements View.OnClickListener {

    private ListView listview;
    private GridView gridview;
    private LinearLayout ll_person, ll_select;
    private TextView tv_job2, tv_job1, tv_person, tv_art, tv_introduce, tv_address, tv_work, title, tv_time,
            tv_name, tv_sex, tv_hight, tv_weight, tv_three;
    private RelativeLayout rl_person, rl_art, rl_work;
    private View view_1, view_2, view_3;
    private List<String> ls;
    private MyArtAdapter ladapter;
    private MyWorkAdapter wadapter;
    private MyScrollView sv;
    private RelativeLayout rl_title;
    private ImageView back, img_pic;
    private int ny = 1;
    private int oy = 1;
    private View view;
    private String userId;
    private PersonInfBean bean;
    private AsyncHttpClient client = new AsyncHttpClient();
    private ImageView img_head, img_redv, img_realname, img_bg;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        getInf();
    }

    //获取个人信息
    private void getInf() {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("type", "1");
        params.put("beUserId", "-1");
        Log.i("tag", Constants.BASE_URL + "personInfo&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "personInfo", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    bean = gson.fromJson(s, PersonInfBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        initView();
                    }
                }
            }
        });
    }


    private void initView() {
        sv = (MyScrollView) findViewById(R.id.sv);
        view = findViewById(R.id.view);
        back = (ImageView) findViewById(R.id.back);
        img_pic = (ImageView) findViewById(R.id.img_pic);
        img_head = (ImageView) findViewById(R.id.img_head);
        img_redv = (ImageView) findViewById(R.id.img_redv);
        img_realname = (ImageView) findViewById(R.id.img_realname);
        img_bg = (ImageView) findViewById(R.id.img_bg);
        listview = (ListView) findViewById(R.id.listview);
        gridview = (GridView) findViewById(R.id.gridview);
        ll_person = (LinearLayout) findViewById(R.id.ll_person);
        ll_select = (LinearLayout) findViewById(R.id.ll_select);
        tv_person = (TextView) findViewById(R.id.tv_person);
        tv_art = (TextView) findViewById(R.id.tv_art);
        tv_work = (TextView) findViewById(R.id.tv_work);
        title = (TextView) findViewById(R.id.title);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_hight = (TextView) findViewById(R.id.tv_hight);
        tv_weight = (TextView) findViewById(R.id.tv_weight);
        tv_three = (TextView) findViewById(R.id.tv_three);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_introduce = (TextView) findViewById(R.id.tv_introduce);
        tv_job2 = (TextView) findViewById(R.id.tv_job2);
        tv_job1 = (TextView) findViewById(R.id.tv_job1);
        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);
        view_3 = findViewById(R.id.view_3);
        rl_person = (RelativeLayout) findViewById(R.id.rl_person);
        rl_art = (RelativeLayout) findViewById(R.id.rl_art);
        rl_work = (RelativeLayout) findViewById(R.id.rl_work);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        rl_person.setOnClickListener(this);
        rl_art.setOnClickListener(this);
        rl_work.setOnClickListener(this);
        back.setOnClickListener(this);
        ladapter = new MyArtAdapter(this, ls);
        listview.setAdapter(ladapter);
        wadapter = new MyWorkAdapter(this, ls);
        gridview.setAdapter(wadapter);
        sv.setOnScollChangedListener(new MyScrollView.OnScollChangedListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
                Message msg = new Message();
                msg.what = 1101;
                msg.arg1 = y;
                handler.sendMessage(msg);
            }
        });
        //背景虚化
        if (Utils.isEmpty(bean.getImageUrl())) {
            img_bg.setImageResource(R.drawable.moren1);
        } else {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    bmp = returnBitMap(bean.getImageUrl());
                    Message mas = new Message();
                    mas.what = 1111;
                    handler.sendMessage(mas);
                }
            }).start();
        }
        //头像
        Glide.with(this).load(bean.getImageUrl()).placeholder(R.drawable.moren).crossFade()
                .into(img_head);
        //标题
        if (Utils.isEmpty(bean.getUserName())) {
            title.setText("");
        } else {
            title.setText(bean.getUserName());
        }
        //加V
        if ((bean.getvConfirm()).equals("1")) {
            img_redv.setVisibility(View.VISIBLE);
        } else {
            img_redv.setVisibility(View.GONE);
        }
        //实名
        if ((bean.getIdConfirm()).equals("1")) {
            img_realname.setVisibility(View.VISIBLE);
        } else {
            img_realname.setVisibility(View.GONE);
        }
        //姓名
        if (Utils.isEmpty(bean.getUserName())) {
            tv_name.setText("");
        } else {
            tv_name.setText(bean.getUserName());
        }
        //性别
        if (Utils.isEmpty(bean.getSex())) {
            tv_sex.setText("");
        } else {
            if (bean.getSex().equals("1")) {
                tv_sex.setText("男");
            } else {
                tv_sex.setText("女");
            }
        }
        //身高
        if (Utils.isEmpty(bean.getTall())) {
            tv_hight.setText("");
        } else {
            tv_hight.setText(bean.getTall());
        }
        //体重
        if (Utils.isEmpty(bean.getWeight())) {
            tv_weight.setText("");
        } else {
            tv_weight.setText(bean.getWeight());
        }
        //三围
        if (Utils.isEmpty(bean.getBwh()) || bean.getBwh().equals("-1")) {
            tv_three.setText("");
        } else {
            tv_three.setText(bean.getBwh());
        }
        //生日
        if (Utils.isEmpty(bean.getBirth())) {
            tv_time.setText("");
        } else {
            tv_time.setText(bean.getBirth());
        }
        //地址
        if (Utils.isEmpty(bean.getAddress())) {
            tv_address.setText("");
        } else {
            tv_address.setText(bean.getAddress());
        }
        //介绍
        if (Utils.isEmpty(bean.getIntroduction()) || bean.getIntroduction().equals("-1")) {
            tv_introduce.setText("");
        } else {
            tv_introduce.setText(bean.getIntroduction());
        }
        if (Utils.isEmpty(bean.getProfession())) {
            tv_job1.setVisibility(View.GONE);
            tv_job2.setVisibility(View.GONE);
        } else {
            if (bean.getProfession().contains(",")) {
                String[] s = bean.getProfession().split(",");
                tv_job1.setText(s[0]);
                tv_job2.setText(s[1]);
                tv_job2.setVisibility(View.VISIBLE);
                tv_job1.setVisibility(View.VISIBLE);
            } else {
                tv_job1.setVisibility(View.VISIBLE);
                tv_job1.setText(bean.getProfession());
                tv_job2.setVisibility(View.GONE);
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1101) {
                ny = msg.arg1;
                if (ny > oy && ny < 900 && ny > 600) {
                    rl_title.setBackgroundColor(Color.parseColor("#ffffff"));
                    title.setTextColor(Color.parseColor("#313131"));
                    img_pic.setImageResource(R.drawable.bshare);
                    back.setImageResource(R.drawable.back);
                    view.setVisibility(View.VISIBLE);
                }
                if (ny < oy && ny < 900 && ny > 600) {
                    rl_title.setBackgroundColor(Color.parseColor("#00000000"));
                    title.setTextColor(Color.parseColor("#ffffff"));
                    img_pic.setImageResource(R.drawable.share);
                    back.setImageResource(R.drawable.wback);
                    view.setVisibility(View.INVISIBLE);
                }
                oy = ny;
            }
            if (msg.what == 1111) {
                img_bg.setImageBitmap(fastblur(bmp, 10));
            }
            ;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_person:
                tv_person.setTextColor(Color.parseColor("#d73232"));
                view_1.setBackgroundColor(Color.parseColor("#d73232"));
                tv_art.setTextColor(Color.parseColor("#313131"));
                view_2.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_work.setTextColor(Color.parseColor("#313131"));
                view_3.setBackgroundColor(Color.parseColor("#ffffff"));
                ll_person.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
                gridview.setVisibility(View.GONE);
                break;
            case R.id.rl_art:
                tv_person.setTextColor(Color.parseColor("#313131"));
                view_1.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_art.setTextColor(Color.parseColor("#d73232"));
                view_2.setBackgroundColor(Color.parseColor("#d73232"));
                tv_work.setTextColor(Color.parseColor("#313131"));
                view_3.setBackgroundColor(Color.parseColor("#ffffff"));
                listview.setVisibility(View.VISIBLE);
//                sv.scrollTo(0, 0);
                ll_person.setVisibility(View.GONE);
                gridview.setVisibility(View.GONE);
                break;
            case R.id.rl_work:
                tv_person.setTextColor(Color.parseColor("#313131"));
                view_1.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_art.setTextColor(Color.parseColor("#313131"));
                view_2.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_work.setTextColor(Color.parseColor("#d73232"));
                view_3.setBackgroundColor(Color.parseColor("#d73232"));
                gridview.setVisibility(View.VISIBLE);
//                sv.scrollTo(0, 0);
                ll_person.setVisibility(View.GONE);
                listview.setVisibility(View.GONE);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    //获得图片
    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //虚化图片
    public Bitmap fastblur(Bitmap sentBitmap, int radius) {


        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }
}

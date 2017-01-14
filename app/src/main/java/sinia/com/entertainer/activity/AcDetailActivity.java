package sinia.com.entertainer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.ArtPhoteAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.DetailBean;
import sinia.com.entertainer.bean.DetailImgBean;
import sinia.com.entertainer.bean.DetailSignBean;
import sinia.com.entertainer.utils.ActivityManager;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;
import sinia.com.entertainer.view.MyScrollView;

import static android.R.attr.id;

/**
 * action,acter详情界面
 * Created by byw on 2016/12/23.
 */
public class AcDetailActivity extends BaseActivity implements View.OnClickListener {
    //----大框架
    private ImageView back;
    private ImageView img_pic;
    private TextView title;
    private MyScrollView sv;
    private View view;
    private RelativeLayout rl_title, rl_hasjoin;
    private int ny = 1;
    private int oy = 1;
    private GridView mgv;
    private ArtPhoteAdapter adapter;
    private ImageView img_1, img_2, img_3, img_4, img_5, img_6, img_7, img_bg, img_head, img_redv, img_realname;
    private DetailBean bean;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String type, mId;
    private List<DetailSignBean> signItems;//参加人员
    private TextView tv_btn, tv_name, tv_outtime, tv_overtime, tv_loc, tv_sex, tv_caiyi1, tv_caiyi2, tv_con, tv_innum;
    private String userId;
    private String myType;//是否是自己发布的场所1.是2.否
    private String inType;//是否报名1.是2.否
    private String closeType;//是否关闭1.是0.否
    private String onclick;//tv_btn点击判断
    private String outId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acdetail);
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        // type = getIntent().getStringExtra("type");
        mId = getIntent().getStringExtra("mId");
        initView();
        getInf();
    }

    //获取详情
    private void getInf() {
        RequestParams params = new RequestParams();
        //params.put("type", type);
        params.put("actMarketId", mId);
        params.put("userId", userId);
        Log.i("tag", Constants.BASE_URL + "spaceDetail&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "spaceDetail", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    bean = gson.fromJson(s, DetailBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        initData();
                    }
                }
            }
        });
    }

    private void close(String id) {
        RequestParams params = new RequestParams();
        params.put("actMarketId", id);
        Log.i("tag", Constants.BASE_URL + "closeMyArtMarket&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "closeMyArtMarket", params, new AsyncHttpResponseHandler() {
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
                        showToast("关闭成功");
                        finish();
                    } else {
                        showToast("关闭失败");
                    }
                }
            }
        });
    }

    //取消报名
    private void cancelSign() {
        RequestParams params = new RequestParams();
        params.put("actMarketId", mId);
        params.put("userId", userId);
        Log.i("tag", Constants.BASE_URL + "cancelSign&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "cancelSign", params, new AsyncHttpResponseHandler() {
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
                        showToast("取消成功");
                        finish();
                    } else {
                        showToast("取消失败");
                    }
                }
            }
        });
    }

    //报名
    private void signUpMarket() {
        RequestParams params = new RequestParams();
        params.put("actMarketId", mId);
        params.put("userId", userId);
        Log.i("tag", Constants.BASE_URL + "signUpMarket&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "signUpMarket", params, new AsyncHttpResponseHandler() {
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
                        showToast("报名成功");
                        finish();
                    } else {
                        showToast("报名失败");
                    }
                }
            }
        });
    }


    private void initData() {
        outId = bean.getUserId();//发布人Id
        inType = bean.getSignStatus();
        closeType = bean.getCloseType();
        if (userId.equals(bean.getUserId())) {
            myType = "1";//是自己发布
            if (closeType.equals("2")) {
                //未关闭
                onclick = "1";
                tv_btn.setText("关闭场所");
                tv_btn.setCompoundDrawables(null, null, null, null);
            } else {
                //已关闭
                onclick = "2";
                tv_btn.setText("场所已关闭");
                tv_btn.setTextColor(Color.parseColor("#c7c7c7"));
                tv_btn.setCompoundDrawables(null, null, null, null);
            }
        } else {
            myType = "2";//不是自己发布
            if (inType.equals("1")) {
                //报名
                onclick = "3";
                tv_btn.setText("取消报名");
                tv_btn.setTextColor(Color.parseColor("#d73232"));
                Drawable dd = getResources().getDrawable(R.drawable.acclose);
                dd.setBounds(0, 0, dd.getMinimumWidth(), dd.getMinimumHeight());
                tv_btn.setCompoundDrawables(dd, null, null, null);
            } else {
                //没报名
                onclick = "4";
                tv_btn.setText("我要报名");
                tv_btn.setTextColor(Color.parseColor("#6f61b3"));
                Drawable dd = getResources().getDrawable(R.drawable.acjoin);
                dd.setBounds(0, 0, dd.getMinimumWidth(), dd.getMinimumHeight());
                tv_btn.setCompoundDrawables(dd, null, null, null);
            }
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < bean.getImageItems().size(); i++) {
            list.add(bean.getImageItems().get(i).getMarketImage().toString());
        }
        adapter = new ArtPhoteAdapter(this, list);
        mgv.setAdapter(adapter);
        signItems = bean.getSignItems();
        title.setText(bean.getSpaceName());
        //图片问题，解决好了删掉注释
        // Glide.with(this).load(bean.getImageItems().get(0)).placeholder(R.drawable.moren1).crossFade()
        //       .into(img_bg);
        Glide.with(this).load(bean.getUpImage()).placeholder(R.drawable.moren).crossFade()
                .into(img_head);
        if (bean.getIdConfirm().equals("1")) {
            img_realname.setVisibility(View.VISIBLE);
        } else {
            img_realname.setVisibility(View.GONE);
        }
        if (bean.getvConfirm().equals("1")) {
            img_redv.setVisibility(View.VISIBLE);
        } else {
            img_redv.setVisibility(View.GONE);
        }
        tv_name.setText(bean.getUpUserName());
        tv_outtime.setText(bean.getMarketTime());
        tv_overtime.setText(bean.getAllowTime());
        tv_loc.setText(bean.getActPlace());
        if (bean.getSex().equals("1")) {
            tv_sex.setText("男");
        } else if (bean.getSex().equals("2")) {
            tv_sex.setText("女");
        } else if (bean.getSex().equals("3")) {
            tv_sex.setText("不限");
        }
        if (Utils.isEmpty(bean.getReActor())) {
            tv_caiyi1.setVisibility(View.GONE);
            tv_caiyi2.setVisibility(View.GONE);
        } else {
            if (bean.getReActor().contains(",")) {
                String[] s = bean.getReActor().split(",");
                tv_caiyi1.setText(s[0]);
                tv_caiyi2.setText(s[1]);
                tv_caiyi2.setVisibility(View.VISIBLE);
                tv_caiyi1.setVisibility(View.VISIBLE);
            } else {
                tv_caiyi1.setVisibility(View.VISIBLE);
                tv_caiyi1.setText(bean.getReActor());
                tv_caiyi2.setVisibility(View.GONE);
            }
        }
        tv_innum.setText(signItems.size() + "人");
        tv_con.setText(bean.getSpaceInfo());
        if (signItems.size() <= 7) {
            switch (signItems.size()) {
                case 0:
                    img_1.setVisibility(View.INVISIBLE);
                    img_2.setVisibility(View.INVISIBLE);
                    img_3.setVisibility(View.INVISIBLE);
                    img_4.setVisibility(View.INVISIBLE);
                    img_5.setVisibility(View.INVISIBLE);
                    img_6.setVisibility(View.INVISIBLE);
                    img_7.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    img_1.setVisibility(View.VISIBLE);
                    img_2.setVisibility(View.INVISIBLE);
                    img_3.setVisibility(View.INVISIBLE);
                    img_4.setVisibility(View.INVISIBLE);
                    img_5.setVisibility(View.INVISIBLE);
                    img_6.setVisibility(View.INVISIBLE);
                    img_7.setVisibility(View.INVISIBLE);
                    Glide.with(this).load(signItems.get(0).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_1);
                    break;
                case 2:
                    img_1.setVisibility(View.VISIBLE);
                    img_2.setVisibility(View.VISIBLE);
                    img_3.setVisibility(View.INVISIBLE);
                    img_4.setVisibility(View.INVISIBLE);
                    img_5.setVisibility(View.INVISIBLE);
                    img_6.setVisibility(View.INVISIBLE);
                    img_7.setVisibility(View.INVISIBLE);
                    Glide.with(this).load(signItems.get(0).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_1);
                    Glide.with(this).load(signItems.get(1).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_2);
                    break;
                case 3:
                    img_1.setVisibility(View.VISIBLE);
                    img_2.setVisibility(View.VISIBLE);
                    img_3.setVisibility(View.VISIBLE);
                    img_4.setVisibility(View.INVISIBLE);
                    img_5.setVisibility(View.INVISIBLE);
                    img_6.setVisibility(View.INVISIBLE);
                    img_7.setVisibility(View.INVISIBLE);
                    Glide.with(this).load(signItems.get(0).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_1);
                    Glide.with(this).load(signItems.get(1).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_2);
                    Glide.with(this).load(signItems.get(2).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_3);
                    break;
                case 4:
                    img_1.setVisibility(View.VISIBLE);
                    img_2.setVisibility(View.VISIBLE);
                    img_3.setVisibility(View.VISIBLE);
                    img_4.setVisibility(View.VISIBLE);
                    img_5.setVisibility(View.INVISIBLE);
                    img_6.setVisibility(View.INVISIBLE);
                    img_7.setVisibility(View.INVISIBLE);
                    Glide.with(this).load(signItems.get(0).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_1);
                    Glide.with(this).load(signItems.get(1).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_2);
                    Glide.with(this).load(signItems.get(2).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_3);
                    Glide.with(this).load(signItems.get(3).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_4);
                    break;
                case 5:
                    img_1.setVisibility(View.VISIBLE);
                    img_2.setVisibility(View.VISIBLE);
                    img_3.setVisibility(View.VISIBLE);
                    img_4.setVisibility(View.VISIBLE);
                    img_5.setVisibility(View.VISIBLE);
                    img_6.setVisibility(View.INVISIBLE);
                    img_7.setVisibility(View.INVISIBLE);
                    Glide.with(this).load(signItems.get(0).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_1);
                    Glide.with(this).load(signItems.get(1).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_2);
                    Glide.with(this).load(signItems.get(2).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_3);
                    Glide.with(this).load(signItems.get(3).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_4);
                    Glide.with(this).load(signItems.get(4).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_5);
                    break;
                case 6:
                    img_1.setVisibility(View.VISIBLE);
                    img_2.setVisibility(View.VISIBLE);
                    img_3.setVisibility(View.VISIBLE);
                    img_4.setVisibility(View.VISIBLE);
                    img_5.setVisibility(View.VISIBLE);
                    img_6.setVisibility(View.VISIBLE);
                    img_7.setVisibility(View.INVISIBLE);
                    Glide.with(this).load(signItems.get(0).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_1);
                    Glide.with(this).load(signItems.get(1).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_2);
                    Glide.with(this).load(signItems.get(2).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_3);
                    Glide.with(this).load(signItems.get(3).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_4);
                    Glide.with(this).load(signItems.get(4).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_5);
                    Glide.with(this).load(signItems.get(5).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_6);
                    break;
                case 7:
                    img_1.setVisibility(View.VISIBLE);
                    img_2.setVisibility(View.VISIBLE);
                    img_3.setVisibility(View.VISIBLE);
                    img_4.setVisibility(View.VISIBLE);
                    img_5.setVisibility(View.VISIBLE);
                    img_6.setVisibility(View.VISIBLE);
                    img_7.setVisibility(View.VISIBLE);
                    Glide.with(this).load(signItems.get(0).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_1);
                    Glide.with(this).load(signItems.get(1).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_2);
                    Glide.with(this).load(signItems.get(2).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_3);
                    Glide.with(this).load(signItems.get(3).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_4);
                    Glide.with(this).load(signItems.get(4).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_5);
                    Glide.with(this).load(signItems.get(5).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_6);
                    Glide.with(this).load(signItems.get(6).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                            .into(img_7);
                    break;
            }
        } else {
            img_1.setVisibility(View.VISIBLE);
            img_2.setVisibility(View.VISIBLE);
            img_3.setVisibility(View.VISIBLE);
            img_4.setVisibility(View.VISIBLE);
            img_5.setVisibility(View.VISIBLE);
            img_6.setVisibility(View.VISIBLE);
            img_7.setVisibility(View.VISIBLE);
            Glide.with(this).load(signItems.get(0).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                    .into(img_1);
            Glide.with(this).load(signItems.get(1).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                    .into(img_2);
            Glide.with(this).load(signItems.get(2).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                    .into(img_3);
            Glide.with(this).load(signItems.get(3).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                    .into(img_4);
            Glide.with(this).load(signItems.get(4).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                    .into(img_5);
            Glide.with(this).load(signItems.get(5).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                    .into(img_6);
            Glide.with(this).load(signItems.get(6).getSignUserImage()).placeholder(R.drawable.moren).crossFade()
                    .into(img_7);
        }
        sv.scrollTo(0, 0);
    }

    private void initView() {
        //----大框架
        sv = (MyScrollView) findViewById(R.id.sv);
        view = findViewById(R.id.view);
        back = (ImageView) findViewById(R.id.back);
        img_pic = (ImageView) findViewById(R.id.img_pic);
        img_1 = (ImageView) findViewById(R.id.img_1);
        img_2 = (ImageView) findViewById(R.id.img_2);
        img_3 = (ImageView) findViewById(R.id.img_3);
        img_4 = (ImageView) findViewById(R.id.img_4);
        img_5 = (ImageView) findViewById(R.id.img_5);
        img_6 = (ImageView) findViewById(R.id.img_6);
        img_7 = (ImageView) findViewById(R.id.img_7);
        img_bg = (ImageView) findViewById(R.id.img_bg);
        img_redv = (ImageView) findViewById(R.id.img_redv);
        img_realname = (ImageView) findViewById(R.id.img_realname);
        img_head = (ImageView) findViewById(R.id.img_head);
        title = (TextView) findViewById(R.id.title);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_outtime = (TextView) findViewById(R.id.tv_outtime);
        tv_overtime = (TextView) findViewById(R.id.tv_overtime);
        tv_loc = (TextView) findViewById(R.id.tv_loc);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_caiyi1 = (TextView) findViewById(R.id.tv_caiyi1);
        tv_caiyi2 = (TextView) findViewById(R.id.tv_caiyi2);
        tv_innum = (TextView) findViewById(R.id.tv_innum);
        tv_con = (TextView) findViewById(R.id.tv_con);
        tv_btn = (TextView) findViewById(R.id.tv_btn);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        rl_hasjoin = (RelativeLayout) findViewById(R.id.rl_hasjoin);
        mgv = (GridView) findViewById(R.id.mgv);
        sv.setOnScollChangedListener(new MyScrollView.OnScollChangedListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
                Message msg = new Message();
                msg.what = 1102;
                msg.arg1 = y;
                handler.sendMessage(msg);
            }
        });
        back.setOnClickListener(this);
        rl_hasjoin.setOnClickListener(this);
        img_1.setOnClickListener(this);
        img_2.setOnClickListener(this);
        img_3.setOnClickListener(this);
        img_4.setOnClickListener(this);
        img_5.setOnClickListener(this);
        img_6.setOnClickListener(this);
        img_7.setOnClickListener(this);
        tv_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_hasjoin:
                Intent intent = new Intent(AcDetailActivity.this, AcHasJoinActivity.class);
                intent.putExtra("mId", mId);
                intent.putExtra("outId", outId);
                intent.putExtra("size", signItems.size() + "");
                startActivity(intent);
                break;
            case R.id.img_1:
                toDetaile(signItems.get(0).getSignUserId(), signItems.get(0).getSignId());
                break;
            case R.id.img_2:
                toDetaile(signItems.get(1).getSignUserId(), signItems.get(1).getSignId());
                break;
            case R.id.img_3:
                toDetaile(signItems.get(2).getSignUserId(), signItems.get(2).getSignId());
                break;
            case R.id.img_4:
                toDetaile(signItems.get(3).getSignUserId(), signItems.get(3).getSignId());
                break;
            case R.id.img_5:
                toDetaile(signItems.get(4).getSignUserId(), signItems.get(4).getSignId());
                break;
            case R.id.img_6:
                toDetaile(signItems.get(5).getSignUserId(), signItems.get(5).getSignId());
                break;
            case R.id.img_7:
                toDetaile(signItems.get(6).getSignUserId(), signItems.get(6).getSignId());
                break;
            case R.id.tv_btn:
                if (onclick.equals("1")) {
                    //关闭场所
                    close(mId);
                }
                if (onclick.equals("2")) {
                    //已关闭
                    showToast("该场所已关闭");
                }
                if (onclick.equals("3")) {
                    //取消报名
                    cancelSign();
                }
                if (onclick.equals("4")) {
                    //我要报名
                    signUpMarket();
                }
                break;
        }
    }


    private void toDetaile(String str, String str1) {
        Intent intent = new Intent(AcDetailActivity.this, DetailedInformationActivity.class);
        intent.putExtra("did", str);
        if (outId.equals(userId)) {
            intent.putExtra("type", "3");
        } else {
            intent.putExtra("type", "2");
        }
        intent.putExtra("sId", str1);
        startActivity(intent);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1102) {
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
        }
    };
}

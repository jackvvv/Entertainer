package sinia.com.entertainer.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.MusicDataAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.HotTopBean;
import sinia.com.entertainer.bean.MusicDataBean;
import sinia.com.entertainer.bean.MusicDataDetBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.view.slideview.SlideShowView1;

/**
 * 音乐资料
 * Created by byw on 2016/12/12.
 */
public class MusicDataActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_male, tv_female, tv_other, tv_original, tv_out;
    private RelativeLayout rl_male, rl_female, rl_other, rl_original;
    private View view_1, view_2, view_3, view_4;
    private MusicDataAdapter adapter;//音乐
    private PopupWindow popWindow;
    private ListView listview;
    private View headView;
    private Dialog dialog;
    //----------标题栏按钮
    private ImageView img_back, img_search, img_select;
    private TextView tv_all, tv_over;
    private LinearLayout ll_choice;
    private SlideShowView1 ssv;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String userId;
    private List<MusicDataDetBean> items = new ArrayList<>();//资料列表
    private List<HotTopBean> imageItems;//首页轮播
    private String mId;
    private Integer zanStatus, collectionStatus;
    private String nowLoc;
    //操作map
    private Map<String, String> map = new HashMap<>();
    //最下方三个按钮
    private TextView tv_cgood, tv_ccollect, tv_cload;//点赞，收藏，下载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicdata);
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        initView();
        nowLoc = "1";
        adapter.kind = nowLoc;
        getInf(nowLoc);
    }

    private void getInf(String str) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("type", str);
        Log.i("tag", Constants.BASE_URL + "musicDataList&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "musicDataList", params, new AsyncHttpResponseHandler() {
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
                                imageItems = bean.getImageItems();
                                initDate();
                            } else {
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
                                getInf(nowLoc);
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
                                getInf(nowLoc);
                                showToast("收藏成功");
                            } else {
                                showToast("收藏失败");
                            }
                        }
                    }
                }

        );
    }

    private void initDate() {
        List<String> ls = new ArrayList<String>();
        for (int i = 0; i < imageItems.size(); i++) {
            ls.add(imageItems.get(i).getImageUrl());
        }
        ssv.setImagePath(ls);
        ssv.startPlay();
        ssv.setOnItemClickListener(new SlideShowView1.ViewPagerItemCLickListener() {
            @Override
            public void onItemClick(View pager, int position) {
                Intent intent = new Intent(MusicDataActivity.this, WebViewActivity.class);
                intent.putExtra("link", imageItems.get(position).getRealPath());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        //--------headview----------
        headView = LayoutInflater.from(this).inflate(
                R.layout.head_music, null);
        ssv = (SlideShowView1) headView.findViewById(R.id.ssv);
        tv_male = (TextView) headView.findViewById(R.id.tv_male);
        tv_female = (TextView) headView.findViewById(R.id.tv_female);
        tv_other = (TextView) headView.findViewById(R.id.tv_other);
        tv_original = (TextView) headView.findViewById(R.id.tv_original);
        view_1 = headView.findViewById(R.id.view_1);
        view_2 = headView.findViewById(R.id.view_2);
        view_3 = headView.findViewById(R.id.view_3);
        view_4 = headView.findViewById(R.id.view_4);
        rl_male = (RelativeLayout) headView.findViewById(R.id.rl_male);
        rl_female = (RelativeLayout) headView.findViewById(R.id.rl_female);
        rl_other = (RelativeLayout) headView.findViewById(R.id.rl_other);
        rl_original = (RelativeLayout) headView.findViewById(R.id.rl_original);
        //-------------------
        tv_out = (TextView) findViewById(R.id.tv_out);
        listview = (ListView) findViewById(R.id.listview);
        listview.addHeaderView(headView);
        adapter = new MusicDataAdapter(this, items, handler, "1");
        listview.setAdapter(adapter);
        //-------标题栏
        img_back = (ImageView) findViewById(R.id.img_back);
        img_search = (ImageView) findViewById(R.id.img_search);
        img_select = (ImageView) findViewById(R.id.img_select);
        tv_all = (TextView) findViewById(R.id.tv_city);
        tv_over = (TextView) findViewById(R.id.tv_over);
        ll_choice = (LinearLayout) findViewById(R.id.ll_choice);
        //最下方按钮
        tv_cgood = (TextView) findViewById(R.id.tv_cgood);
        tv_ccollect = (TextView) findViewById(R.id.tv_ccollect);
        tv_cload = (TextView) findViewById(R.id.tv_cload);
        //------------点击事件
        rl_male.setOnClickListener(this);
        rl_female.setOnClickListener(this);
        rl_other.setOnClickListener(this);
        rl_original.setOnClickListener(this);
        tv_out.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_search.setOnClickListener(this);
        img_select.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        tv_over.setOnClickListener(this);
        tv_cgood.setOnClickListener(this);
        tv_ccollect.setOnClickListener(this);
        tv_cload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_male:
                tv_male.setTextColor(Color.parseColor("#6f61b3"));
                view_1.setBackgroundColor(Color.parseColor("#6f61b3"));
                tv_female.setTextColor(Color.parseColor("#313131"));
                view_2.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_other.setTextColor(Color.parseColor("#313131"));
                view_3.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_original.setTextColor(Color.parseColor("#313131"));
                view_4.setBackgroundColor(Color.parseColor("#ffffff"));
                nowLoc = "1";
                adapter.kind = nowLoc;
                getInf(nowLoc);
                break;
            case R.id.rl_female:
                tv_male.setTextColor(Color.parseColor("#313131"));
                view_1.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_female.setTextColor(Color.parseColor("#6f61b3"));
                view_2.setBackgroundColor(Color.parseColor("#6f61b3"));
                tv_other.setTextColor(Color.parseColor("#313131"));
                view_3.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_original.setTextColor(Color.parseColor("#313131"));
                view_4.setBackgroundColor(Color.parseColor("#ffffff"));
                nowLoc = "2";
                adapter.kind = nowLoc;
                getInf(nowLoc);
                break;
            case R.id.rl_other:
                tv_male.setTextColor(Color.parseColor("#313131"));
                view_1.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_female.setTextColor(Color.parseColor("#313131"));
                view_2.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_other.setTextColor(Color.parseColor("#6f61b3"));
                view_3.setBackgroundColor(Color.parseColor("#6f61b3"));
                tv_original.setTextColor(Color.parseColor("#313131"));
                view_4.setBackgroundColor(Color.parseColor("#ffffff"));
                nowLoc = "3";
                adapter.kind = nowLoc;
                getInf(nowLoc);
                break;
            case R.id.rl_original:
                tv_male.setTextColor(Color.parseColor("#313131"));
                view_1.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_female.setTextColor(Color.parseColor("#313131"));
                view_2.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_other.setTextColor(Color.parseColor("#313131"));
                view_3.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_original.setTextColor(Color.parseColor("#6f61b3"));
                view_4.setBackgroundColor(Color.parseColor("#6f61b3"));
                nowLoc = "4";
                adapter.kind = nowLoc;
                getInf(nowLoc);
                break;
            case R.id.tv_out:
                createConDialog(MusicDataActivity.this);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_search:
                Intent intent = new Intent(MusicDataActivity.this, SearchMusicDataActivity.class);
                startActivity(intent);
                break;
            case R.id.img_select:
                img_back.setVisibility(View.GONE);
                img_search.setVisibility(View.GONE);
                img_select.setVisibility(View.GONE);
                tv_all.setVisibility(View.VISIBLE);
                tv_over.setVisibility(View.VISIBLE);
                tv_out.setVisibility(View.GONE);
                ll_choice.setVisibility(View.VISIBLE);
                adapter.type = "2";
                adapter.notifyDataSetChanged();
                rl_male.setClickable(false);
                rl_female.setClickable(false);
                rl_other.setClickable(false);
                rl_original.setClickable(false);
                listview.setOnItemClickListener(oicl);
                break;
            case R.id.tv_city:
                //全选
                Drawable dd = getResources().getDrawable(R.drawable.mselsected);
                dd.setBounds(0, 0, dd.getMinimumWidth(), dd.getMinimumHeight());
                tv_all.setCompoundDrawables(dd, null, null, null);
                Message msg = new Message();
                msg.what = 1002;
                handler.sendMessage(msg);
                break;
            case R.id.tv_over:
                img_back.setVisibility(View.VISIBLE);
                img_search.setVisibility(View.VISIBLE);
                img_select.setVisibility(View.VISIBLE);
                tv_all.setVisibility(View.GONE);
                tv_over.setVisibility(View.GONE);
                tv_out.setVisibility(View.VISIBLE);
                ll_choice.setVisibility(View.GONE);
                adapter.type = "1";
                map.clear();
                adapter.notifyDataSetChanged();
                rl_male.setOnClickListener(this);
                rl_female.setOnClickListener(this);
                rl_other.setOnClickListener(this);
                rl_original.setOnClickListener(this);
                listview.setOnItemClickListener(null);
                break;
            case R.id.tv_cgood:
                //选择点赞
                Message msg1 = new Message();
                msg1.what = 1003;
                handler.sendMessage(msg1);
                break;
            case R.id.tv_ccollect:
                Message msg2 = new Message();
                msg2.what = 1004;
                handler.sendMessage(msg2);
                break;
            case R.id.tv_cload:
                break;
        }
    }

    OnItemClickListener oicl = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (map.containsKey(items.get(position - 1).getMusicDataId())) {
                //取消
                map.remove(items.get(position - 1).getMusicDataId());
                items.get(position - 1).setChoosed("1");
                adapter.notifyDataSetChanged();
                Drawable dd = getResources().getDrawable(R.drawable.mselect);
                dd.setBounds(0, 0, dd.getMinimumWidth(), dd.getMinimumHeight());
                tv_all.setCompoundDrawables(dd, null, null, null);
            } else {
                //选中
                map.put(items.get(position - 1).getMusicDataId(), "");
                items.get(position - 1).setChoosed("2");
                adapter.notifyDataSetChanged();
                if (map.size() == items.size()) {
                    Drawable dd = getResources().getDrawable(R.drawable.mselsected);
                    dd.setBounds(0, 0, dd.getMinimumWidth(), dd.getMinimumHeight());
                    tv_all.setCompoundDrawables(dd, null, null, null);
                }
            }
        }
    };

    //回调
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1001) {
                mId = (String) msg.obj;
                zanStatus = msg.arg1;
                collectionStatus = msg.arg2;
                createDialog(MusicDataActivity.this);
            }
            if (msg.what == 1002) {
                //全选
                for (int i = 0; i < items.size(); i++) {
                    map.put(items.get(i).getMusicDataId(), "");
                    items.get(i).setChoosed("2");
                }
                adapter.notifyDataSetChanged();
            }
            if (msg.what == 1003) {
                //选中多个点赞
                if (map.size() == 0) {
                    showToast("请选择");
                } else {
                    StringBuffer str = new StringBuffer();
                    Set<String> set = map.keySet();
                    for (String s : set) {
                        str.append(s + ",");
                    }
                    str = str.deleteCharAt(str.length() - 1);
                    cGood(str.toString());
                }
            }
            if (msg.what == 1004) {
                //选中多个收藏
                if (map.size() == 0) {
                    showToast("请选择");
                } else {
                    StringBuffer str = new StringBuffer();
                    Set<String> set = map.keySet();
                    for (String s : set) {
                        str.append(s + ",");
                    }
                    str = str.deleteCharAt(str.length() - 1);
                    cCollect(str.toString());
                }
            }

        }
    };

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
        tv_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicDataActivity.this, PlayMusicActivity.class);
                intent.putExtra("id", mId);
                intent.putExtra("kind", nowLoc);
                startActivity(intent);
                popWindow.dismiss();
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

package sinia.com.entertainer.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.ActorPlaceAdapter;
import sinia.com.entertainer.adapter.SortLocAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.bean.AcDetailBean;
import sinia.com.entertainer.bean.AcDetailLsBean;
import sinia.com.entertainer.utils.Constants;

import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 找场所界面
 * Created by byw on 2016/12/14.
 */
public class ActionActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.ll_condition)
    LinearLayout ll_condition;
    @Bind(R.id.tv_city)
    TextView tv_city;
    @Bind(R.id.tv_shop)
    TextView tv_shop;
    @Bind(R.id.tv_sort)
    TextView tv_sort;
    @Bind(R.id.tv_screen)
    TextView tv_screen;
    @Bind(R.id.img_city)
    ImageView img_city;
    @Bind(R.id.img_shop)
    ImageView img_shop;
    @Bind(R.id.img_sort)
    ImageView img_sort;
    @Bind(R.id.img_screen)
    ImageView img_screen;
    @Bind(R.id.listview)
    ListView listview;

    private PopupWindow locPop;
    private SortLocAdapter locadapter;
    private List<String> locls;
    private String locIsShow = "1";//1.未打开，2打开
    private Map<String, String> amap = new HashMap<>();
    private Map<String, String> cmap = new HashMap<>();//操作的时候使用
    private String sort = "1";//排序1.智能，2.距离，3.最新，4.截止
    private String sex = "3";//性别1.男 2女 3不限
    private String sex1 = "3";//操作时候使用
    private String real = "1";//认证1.不限，2.实名，3.vip
    private String real1 = "1";//操作时候使用
    private ActorPlaceAdapter adapter;
    private List<AcDetailBean> items = new ArrayList<>();
    private AsyncHttpClient client = new AsyncHttpClient();
    private String loc = "-1";//地址
    private String shop = "-1";//商家

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action, "找场所");
        getDoingView().setVisibility(View.GONE);
        getImg_pic().setImageResource(R.drawable.searchb);
        getImg_pic().setVisibility(View.VISIBLE);
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInf();
    }

    private void getInf() {
        RequestParams params = new RequestParams();
        params.put("type", "1");//1.场所2.演员
        params.put("place", loc);//地址
        params.put("profession", "-1");//角色
        params.put("reActor", shop);//场所
        params.put("smartType", sort);//排序
        params.put("latitude", "32.05000");//纬度
        params.put("longitude", "118.78333");//经度
        params.put("sex", sex);//性别1.男 2女 3不限
        params.put("confirmType", real);//1.不限 2实名认证 3.红v
        Log.i("tag", Constants.BASE_URL + "actMarketMakeOrder&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "actMarketMakeOrder", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    AcDetailLsBean bean = gson.fromJson(s, AcDetailLsBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        items.clear();
                        items.addAll(bean.getItems());
                        adapter.notifyDataSetChanged();
                    } else {
                    }
                }
            }
        });
    }

    @Override
    public void doing() {
        Intent intent = new Intent(ActionActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private void initView() {
        adapter = new ActorPlaceAdapter(this, items);
        adapter.type = "1";
        listview.setAdapter(adapter);
        tv_city.setOnClickListener(this);
        tv_shop.setOnClickListener(this);
        tv_sort.setOnClickListener(this);
        tv_screen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_city:
                //地点
                if (locIsShow.equals("2")) {

                    locPop.dismiss();
                    break;
                }
                if (locIsShow.equals("1")) {
                    tv_city.setTextColor(Color.parseColor("#d73232"));
                    img_city.setImageResource(R.drawable.findup);
                    tv_shop.setTextColor(Color.parseColor("#313131"));
                    img_shop.setImageResource(R.drawable.finddown);
                    tv_sort.setTextColor(Color.parseColor("#313131"));
                    img_sort.setImageResource(R.drawable.finddown);
                    tv_screen.setTextColor(Color.parseColor("#313131"));
                    img_screen.setImageResource(R.drawable.finddown);
                    showLocWindow();
                    break;
                }

            case R.id.tv_shop:
                //商家
                tv_city.setTextColor(Color.parseColor("#313131"));
                img_city.setImageResource(R.drawable.finddown);
                tv_shop.setTextColor(Color.parseColor("#d73232"));
                img_shop.setImageResource(R.drawable.findup);
                tv_sort.setTextColor(Color.parseColor("#313131"));
                img_sort.setImageResource(R.drawable.finddown);
                tv_screen.setTextColor(Color.parseColor("#313131"));
                img_screen.setImageResource(R.drawable.finddown);
                showShopWin();
                break;
            case R.id.tv_sort:
                //排序
                tv_city.setTextColor(Color.parseColor("#313131"));
                img_city.setImageResource(R.drawable.finddown);
                tv_shop.setTextColor(Color.parseColor("#313131"));
                img_shop.setImageResource(R.drawable.finddown);
                tv_sort.setTextColor(Color.parseColor("#d73232"));
                img_sort.setImageResource(R.drawable.findup);
                tv_screen.setTextColor(Color.parseColor("#313131"));
                img_screen.setImageResource(R.drawable.finddown);
                showSortPop();
                break;
            case R.id.tv_screen:
                //筛选
                tv_city.setTextColor(Color.parseColor("#313131"));
                img_city.setImageResource(R.drawable.finddown);
                tv_shop.setTextColor(Color.parseColor("#313131"));
                img_shop.setImageResource(R.drawable.finddown);
                tv_sort.setTextColor(Color.parseColor("#313131"));
                img_sort.setImageResource(R.drawable.finddown);
                tv_screen.setTextColor(Color.parseColor("#d73232"));
                img_screen.setImageResource(R.drawable.findup);
                showScreenPop();
                break;
        }
    }

    private void initData() {
        locls = new ArrayList<String>();
        locls.add("北京市");
        locls.add("天津市");
        locls.add("上海市");
        locls.add("重庆市");
        locls.add("河北省");
        locls.add("山西省");
        locls.add("辽宁省");
        locls.add("吉林省");
        locls.add("黑龙江省");
        locls.add("江苏省");
        locls.add("浙江省");
        locls.add("安徽省");
        locls.add("福建省");
        locls.add("台湾省");
        locls.add("江西省");
        locls.add("山东省");
        locls.add("河南省");
        locls.add("湖北省");
        locls.add("湖南省");
        locls.add("广东省");
        locls.add("海南省");
        locls.add("四川省");
        locls.add("贵州省");
        locls.add("云南省");
        locls.add("陕西省");
        locls.add("甘肃省");
        locls.add("青海省");
        locls.add("内蒙古自治区");
        locls.add("广西壮族自治区");
        locls.add("西藏自治区");
        locls.add("宁夏回族自治区");
        locls.add("新疆维吾尔自治区");
        locls.add("香港特别行政区");
        locls.add("澳门特别行政区");
        locls.add("国外");
    }

    //筛选地区
    private void showLocWindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_loclist,
                null);
        locIsShow = "2";
        locPop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        locPop.setAnimationStyle(R.style.ActionSheetDialogAnimation2);
        locPop.setFocusable(true);
        locPop.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        locPop.setBackgroundDrawable(dw);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        locPop.setHeight(display.getHeight() / 2);
        locPop.showAsDropDown(ll_condition, 0, 0);
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();

        lp.alpha = 1f;
        getWindow().setAttributes(lp);

        TextView tv_all = (TextView) view.findViewById(R.id.tv_all);
        ListView listview = (ListView) view.findViewById(R.id.listview);
        locadapter = new SortLocAdapter(ActionActivity.this, locls);
        listview.setAdapter(locadapter);
        locPop.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow()
                        .getAttributes();
                locIsShow = "1";
                lp.alpha = 1f;
                tv_city.setTextColor(Color.parseColor("#313131"));
                img_city.setImageResource(R.drawable.finddown);
                getWindow().setAttributes(lp);
                if (loc.equals("全国")) {
                    loc = "-1";
                }
                getInf();
            }
        });
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "全国";
                tv_city.setText(loc);
                locPop.dismiss();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loc = locls.get(position).toString();
                tv_city.setText(loc);
                locPop.dismiss();
            }
        });
    }

    //筛选商家类型
    private void showShopWin() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_shop,
                null);
        locPop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        locPop.setAnimationStyle(R.style.ActionSheetDialogAnimation2);
        locPop.setFocusable(true);
        locPop.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        locPop.setBackgroundDrawable(dw);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        // locPop.setHeight(display.getHeight() / 2);
        locPop.showAsDropDown(ll_condition, 0, 0);
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();

        lp.alpha = 1f;
        getWindow().setAttributes(lp);

        final TextView tv_all = (TextView) view.findViewById(R.id.tv_all);
        final TextView tv_yule = (TextView) view.findViewById(R.id.tv_yule);
        final TextView tv_chuanmei = (TextView) view.findViewById(R.id.tv_chuanmei);
        final TextView tv_hunqing = (TextView) view.findViewById(R.id.tv_hunqing);
        final TextView tv_other = (TextView) view.findViewById(R.id.tv_other);
        final TextView tv_rechoice = (TextView) view.findViewById(R.id.tv_rechoice);
        final TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        cmap.clear();
        cmap.putAll(amap);

        if (amap.size() == 0) {
            tv_all.setTextColor(Color.parseColor("#ce171a"));
            tv_all.setBackgroundResource(R.drawable.lay_sharp4);
        } else {
            tv_all.setTextColor(Color.parseColor("#313131"));
            tv_all.setBackgroundResource(R.drawable.lay_sharp);
        }
        if (amap.keySet().contains("娱乐场所")) {
            tv_yule.setTextColor(Color.parseColor("#ce171a"));
            tv_yule.setBackgroundResource(R.drawable.lay_sharp4);
        }
        if (amap.keySet().contains("传媒公司")) {
            tv_chuanmei.setTextColor(Color.parseColor("#ce171a"));
            tv_chuanmei.setBackgroundResource(R.drawable.lay_sharp4);
        }
        if (amap.keySet().contains("婚庆公司")) {
            tv_hunqing.setTextColor(Color.parseColor("#ce171a"));
            tv_hunqing.setBackgroundResource(R.drawable.lay_sharp4);
        }
        if (amap.keySet().contains("其他")) {
            tv_other.setTextColor(Color.parseColor("#ce171a"));
            tv_other.setBackgroundResource(R.drawable.lay_sharp4);
        }


        locPop.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow()
                        .getAttributes();
                locIsShow = "1";
                lp.alpha = 1f;
                tv_shop.setTextColor(Color.parseColor("#313131"));
                img_shop.setImageResource(R.drawable.finddown);

                if (amap.size() == 0) {
                    shop = "全部商家";
                    tv_shop.setText(shop);
                } else {
                    StringBuffer str = new StringBuffer();
                    Set<String> set = amap.keySet();
                    for (String s : set) {
                        str.append(s + "/");
                    }
                    str = str.deleteCharAt(str.length() - 1);
                    shop = str.toString();
                    tv_shop.setText(shop);
                }
                getWindow().setAttributes(lp);
                if (shop.equals("全部商家")) {
                    shop = "-1";
                }
                getInf();
            }
        });
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //全部
                cmap.clear();
                tv_all.setTextColor(Color.parseColor("#ce171a"));
                tv_all.setBackgroundResource(R.drawable.lay_sharp4);
                tv_chuanmei.setTextColor(Color.parseColor("#313131"));
                tv_chuanmei.setBackgroundResource(R.drawable.lay_sharp);
                tv_yule.setTextColor(Color.parseColor("#313131"));
                tv_yule.setBackgroundResource(R.drawable.lay_sharp);
                tv_hunqing.setTextColor(Color.parseColor("#313131"));
                tv_hunqing.setBackgroundResource(R.drawable.lay_sharp);
                tv_other.setTextColor(Color.parseColor("#313131"));
                tv_other.setBackgroundResource(R.drawable.lay_sharp);
            }
        });
        tv_rechoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重置
                cmap.clear();
                tv_all.setTextColor(Color.parseColor("#313131"));
                tv_all.setBackgroundResource(R.drawable.lay_sharp);
                tv_chuanmei.setTextColor(Color.parseColor("#313131"));
                tv_chuanmei.setBackgroundResource(R.drawable.lay_sharp);
                tv_yule.setTextColor(Color.parseColor("#313131"));
                tv_yule.setBackgroundResource(R.drawable.lay_sharp);
                tv_hunqing.setTextColor(Color.parseColor("#313131"));
                tv_hunqing.setBackgroundResource(R.drawable.lay_sharp);
                tv_other.setTextColor(Color.parseColor("#313131"));
                tv_other.setBackgroundResource(R.drawable.lay_sharp);
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定
                amap.clear();
                amap.putAll(cmap);
                locPop.dismiss();
            }
        });
        tv_yule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //娱乐
                actor(tv_yule, tv_yule.getText().toString());
                tv_all.setTextColor(Color.parseColor("#313131"));
                tv_all.setBackgroundResource(R.drawable.lay_sharp);
            }
        });
        tv_chuanmei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传媒
                actor(tv_chuanmei, tv_chuanmei.getText().toString());
                tv_all.setTextColor(Color.parseColor("#313131"));
                tv_all.setBackgroundResource(R.drawable.lay_sharp);
            }
        });
        tv_hunqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //婚庆
                actor(tv_hunqing, tv_hunqing.getText().toString());
                tv_all.setTextColor(Color.parseColor("#313131"));
                tv_all.setBackgroundResource(R.drawable.lay_sharp);
            }
        });
        tv_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //其他
                actor(tv_other, tv_other.getText().toString());
                tv_all.setTextColor(Color.parseColor("#313131"));
                tv_all.setBackgroundResource(R.drawable.lay_sharp);
            }
        });
    }

    //选择方法，最多选俩
    private void actor(TextView tv, String str) {
        if (cmap.containsKey(str)) {
            cmap.remove(str);
            tv.setTextColor(Color.parseColor("#313131"));
            tv.setBackgroundResource(R.drawable.lay_sharp);

        } else if (cmap.size() < 2) {
            cmap.put(str, "");
            tv.setTextColor(Color.parseColor("#ce171a"));
            tv.setBackgroundResource(R.drawable.lay_sharp4);
        }

    }

    //排序
    private void showSortPop() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_sort,
                null);
        locPop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        locPop.setAnimationStyle(R.style.ActionSheetDialogAnimation2);
        locPop.setFocusable(true);
        locPop.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        locPop.setBackgroundDrawable(dw);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        //locPop.setHeight(display.getHeight() / 2);
        locPop.showAsDropDown(ll_condition, 0, 0);
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();

        lp.alpha = 1f;
        getWindow().setAttributes(lp);

        TextView tv_s1 = (TextView) view.findViewById(R.id.tv_s1);
        TextView tv_s2 = (TextView) view.findViewById(R.id.tv_s2);
        TextView tv_s3 = (TextView) view.findViewById(R.id.tv_s3);
        TextView tv_s4 = (TextView) view.findViewById(R.id.tv_s4);
        ImageView img_s1 = (ImageView) view.findViewById(R.id.img_s1);
        ImageView img_s2 = (ImageView) view.findViewById(R.id.img_s2);
        ImageView img_s3 = (ImageView) view.findViewById(R.id.img_s3);
        ImageView img_s4 = (ImageView) view.findViewById(R.id.img_s4);

        if (sort.equals("1")) {
            tv_s1.setTextColor(Color.parseColor("#d73232"));
            img_s1.setImageResource(R.drawable.s1d);
        }
        if (sort.equals("2")) {
            tv_s2.setTextColor(Color.parseColor("#d73232"));
            img_s2.setImageResource(R.drawable.s1d);
        }
        if (sort.equals("3")) {
            tv_s3.setTextColor(Color.parseColor("#d73232"));
            img_s3.setImageResource(R.drawable.s1d);
        }
        if (sort.equals("4")) {
            tv_s4.setTextColor(Color.parseColor("#d73232"));
            img_s4.setImageResource(R.drawable.s1d);
        }

        locPop.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow()
                        .getAttributes();
                locIsShow = "1";
                lp.alpha = 1f;
                tv_sort.setTextColor(Color.parseColor("#313131"));
                img_sort.setImageResource(R.drawable.finddown);
                getWindow().setAttributes(lp);
                getInf();
            }
        });
        tv_s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = "1";
                tv_sort.setText("智能排序");
                locPop.dismiss();
            }
        });
        tv_s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = "2";
                tv_sort.setText("离我最近");
                locPop.dismiss();
            }
        });
        tv_s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = "3";
                tv_sort.setText("最新发布");
                locPop.dismiss();
            }
        });
        tv_s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = "4";
                tv_sort.setText("最新截止");
                locPop.dismiss();
            }
        });
    }

    //筛选
    private void showScreenPop() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_screenpop,
                null);
        locPop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        locPop.setAnimationStyle(R.style.ActionSheetDialogAnimation2);
        locPop.setFocusable(true);
        locPop.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        locPop.setBackgroundDrawable(dw);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        //  locPop.setHeight(display.getHeight() / 2);
        locPop.showAsDropDown(ll_condition, 0, 0);
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        final TextView tv_rechoice = (TextView) view.findViewById(R.id.tv_rechoice);
        final TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        final TextView tv_male = (TextView) view.findViewById(R.id.tv_male);
        final TextView tv_female = (TextView) view.findViewById(R.id.tv_female);
        final TextView tv_no = (TextView) view.findViewById(R.id.tv_no);
        final TextView tv_no1 = (TextView) view.findViewById(R.id.tv_no1);
        final TextView tv_realname = (TextView) view.findViewById(R.id.tv_realname);
        final TextView tv_vip = (TextView) view.findViewById(R.id.tv_vip);

        // String sex="0";//性别0.不限制，1.男，2.女
        // String real="0";//认证0.不限，1.实名，2.vip
        sex1 = sex;
        real1 = real;
        switch (sex) {
            case "3":
                tv_no.setTextColor(Color.parseColor("#d73232"));
                tv_no.setBackgroundResource(R.drawable.lay_sharp4);
                tv_male.setTextColor(Color.parseColor("#313131"));
                tv_male.setBackgroundResource(R.drawable.lay_sharp);
                tv_female.setTextColor(Color.parseColor("#313131"));
                tv_female.setBackgroundResource(R.drawable.lay_sharp);
                break;
            case "1":
                tv_no.setTextColor(Color.parseColor("#313131"));
                tv_no.setBackgroundResource(R.drawable.lay_sharp);
                tv_male.setTextColor(Color.parseColor("#d73232"));
                tv_male.setBackgroundResource(R.drawable.lay_sharp4);
                tv_female.setTextColor(Color.parseColor("#313131"));
                tv_female.setBackgroundResource(R.drawable.lay_sharp);
                break;
            case "2":
                tv_no.setTextColor(Color.parseColor("#313131"));
                tv_no.setBackgroundResource(R.drawable.lay_sharp);
                tv_male.setTextColor(Color.parseColor("#313131"));
                tv_male.setBackgroundResource(R.drawable.lay_sharp);
                tv_female.setTextColor(Color.parseColor("#d73232"));
                tv_female.setBackgroundResource(R.drawable.lay_sharp4);
                break;
        }
        switch (real) {
            case "1":
                tv_no1.setTextColor(Color.parseColor("#d73232"));
                tv_no1.setBackgroundResource(R.drawable.lay_sharp4);
                tv_realname.setTextColor(Color.parseColor("#313131"));
                tv_realname.setBackgroundResource(R.drawable.lay_sharp);
                tv_vip.setTextColor(Color.parseColor("#313131"));
                tv_vip.setBackgroundResource(R.drawable.lay_sharp);
                break;
            case "2":
                tv_no1.setTextColor(Color.parseColor("#313131"));
                tv_no1.setBackgroundResource(R.drawable.lay_sharp);
                tv_realname.setTextColor(Color.parseColor("#d73232"));
                tv_realname.setBackgroundResource(R.drawable.lay_sharp4);
                tv_vip.setTextColor(Color.parseColor("#313131"));
                tv_vip.setBackgroundResource(R.drawable.lay_sharp);
                break;
            case "3":
                tv_no1.setTextColor(Color.parseColor("#313131"));
                tv_no1.setBackgroundResource(R.drawable.lay_sharp);
                tv_realname.setTextColor(Color.parseColor("#313131"));
                tv_realname.setBackgroundResource(R.drawable.lay_sharp);
                tv_vip.setTextColor(Color.parseColor("#d73232"));
                tv_vip.setBackgroundResource(R.drawable.lay_sharp4);
                break;
        }
        locPop.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow()
                        .getAttributes();
                locIsShow = "1";
                lp.alpha = 1f;
                tv_screen.setTextColor(Color.parseColor("#313131"));
                img_screen.setImageResource(R.drawable.finddown);
                getWindow().setAttributes(lp);
            }
        });
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex1 = "3";
                tv_no.setTextColor(Color.parseColor("#d73232"));
                tv_no.setBackgroundResource(R.drawable.lay_sharp4);
                tv_male.setTextColor(Color.parseColor("#313131"));
                tv_male.setBackgroundResource(R.drawable.lay_sharp);
                tv_female.setTextColor(Color.parseColor("#313131"));
                tv_female.setBackgroundResource(R.drawable.lay_sharp);
            }
        });
        tv_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex1 = "1";
                tv_no.setTextColor(Color.parseColor("#313131"));
                tv_no.setBackgroundResource(R.drawable.lay_sharp);
                tv_male.setTextColor(Color.parseColor("#d73232"));
                tv_male.setBackgroundResource(R.drawable.lay_sharp4);
                tv_female.setTextColor(Color.parseColor("#313131"));
                tv_female.setBackgroundResource(R.drawable.lay_sharp);
            }
        });
        tv_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex1 = "2";
                tv_no.setTextColor(Color.parseColor("#313131"));
                tv_no.setBackgroundResource(R.drawable.lay_sharp);
                tv_male.setTextColor(Color.parseColor("#313131"));
                tv_male.setBackgroundResource(R.drawable.lay_sharp);
                tv_female.setTextColor(Color.parseColor("#d73232"));
                tv_female.setBackgroundResource(R.drawable.lay_sharp4);
            }
        });
        tv_no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                real1 = "1";
                tv_no1.setTextColor(Color.parseColor("#d73232"));
                tv_no1.setBackgroundResource(R.drawable.lay_sharp4);
                tv_realname.setTextColor(Color.parseColor("#313131"));
                tv_realname.setBackgroundResource(R.drawable.lay_sharp);
                tv_vip.setTextColor(Color.parseColor("#313131"));
                tv_vip.setBackgroundResource(R.drawable.lay_sharp);
            }
        });
        tv_realname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                real1 = "2";
                tv_no1.setTextColor(Color.parseColor("#313131"));
                tv_no1.setBackgroundResource(R.drawable.lay_sharp);
                tv_realname.setTextColor(Color.parseColor("#d73232"));
                tv_realname.setBackgroundResource(R.drawable.lay_sharp4);
                tv_vip.setTextColor(Color.parseColor("#313131"));
                tv_vip.setBackgroundResource(R.drawable.lay_sharp);
            }
        });
        tv_vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                real1 = "3";
                tv_no1.setTextColor(Color.parseColor("#313131"));
                tv_no1.setBackgroundResource(R.drawable.lay_sharp);
                tv_realname.setTextColor(Color.parseColor("#313131"));
                tv_realname.setBackgroundResource(R.drawable.lay_sharp);
                tv_vip.setTextColor(Color.parseColor("#d73232"));
                tv_vip.setBackgroundResource(R.drawable.lay_sharp4);
            }
        });
        tv_rechoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex1 = "3";
                real1 = "1";
                tv_no1.setTextColor(Color.parseColor("#d73232"));
                tv_no1.setBackgroundResource(R.drawable.lay_sharp4);
                tv_realname.setTextColor(Color.parseColor("#313131"));
                tv_realname.setBackgroundResource(R.drawable.lay_sharp);
                tv_vip.setTextColor(Color.parseColor("#313131"));
                tv_vip.setBackgroundResource(R.drawable.lay_sharp);
                tv_no.setTextColor(Color.parseColor("#d73232"));
                tv_no.setBackgroundResource(R.drawable.lay_sharp4);
                tv_male.setTextColor(Color.parseColor("#313131"));
                tv_male.setBackgroundResource(R.drawable.lay_sharp);
                tv_female.setTextColor(Color.parseColor("#313131"));
                tv_female.setBackgroundResource(R.drawable.lay_sharp);
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = sex1;
                real = real1;
                String str = "";
                String str1 = "";
                //-----------
                if (sex.equals("3")) {
                    str = "";
                }
                if (sex.equals("1")) {
                    str = "男";
                }
                if (sex.equals("2")) {
                    str = "女";
                }
                //--------------
                if (real.equals("1")) {
                    str1 = "";
                }
                if (real.equals("2")) {
                    str1 = "实名";
                }
                if (real.equals("3")) {
                    str1 = "vip";
                }
                if ((!sex.equals("3")) && (!real.equals("1"))) {
                    tv_screen.setText(str + "/" + str1);
                }
                if ((!sex.equals("3")) && (real.equals("1"))) {
                    tv_screen.setText(str);
                }
                if ((sex.equals("3")) && (!real.equals("1"))) {
                    tv_screen.setText(str1);
                }
                getInf();
                locPop.dismiss();
            }
        });
    }
}
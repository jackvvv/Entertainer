package sinia.com.entertainer.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.actionsheetdialog.ActionSheetDialog;
import sinia.com.entertainer.adapter.PhoteAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;
import sinia.com.entertainer.utils.city.CityPicker;
import sinia.com.entertainer.utils.time.TimePickerView;

/**
 * 发布演员界面
 * Created by byw on 2016/12/2.
 */
public class RecruitActivity extends BaseActivity implements View.OnClickListener {
    private PhoteAdapter adapter;
    @Bind(R.id.gridview)
    GridView gridview;
    @Bind(R.id.img_addvideo)
    ImageView img_addvideo;
    //名字
    @Bind(R.id.et_name)
    EditText et_name;
    //自我介绍
    @Bind(R.id.et_con)
    EditText et_con;
    //时间
    @Bind(R.id.rl_time)
    RelativeLayout rl_time;
    @Bind(R.id.tv_time)
    TextView tv_time;
    //性别
    @Bind(R.id.rl_sex)
    RelativeLayout rl_sex;
    @Bind(R.id.tv_sex)
    TextView tv_sex;
    //地址
    @Bind(R.id.rl_loc)
    RelativeLayout rl_loc;
    @Bind(R.id.tv_hopeloc)
    TextView tv_hopeloc;

    private List<String> ls;
    private AsyncHttpClient client = new AsyncHttpClient();
    private PopupWindow popWindow;
    private String name;//名字
    private String time;//时间
    private String sex;//性别
    private String address;//地址
    private String intro;//自我介绍
    TimePickerView pvTime;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit, "招演员");
        getDoingView().setVisibility(View.VISIBLE);
        getDoingView().setText("发布");
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        initView();
    }

    @Override
    public void doing() {
        name = et_name.getText().toString();
        intro = et_con.getText().toString();
        if (Utils.isEmpty(name)) {
            showToast("请输入标题");
        } else if (Utils.isEmpty(time)) {
            showToast("请选择截止时间");
        } else if (Utils.isEmpty(sex)) {
            showToast("请选择性别要求");
        } else if (Utils.isEmpty(address)) {
            showToast("请选择工作地址");
        } else if (Utils.isEmpty(intro)) {
            showToast("请填写自我介绍");
        } else {
            deliver();
        }
    }

    //发布接口
    private void deliver() {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("actorTitle", name);
        params.put("allowTime", time);
        params.put("introduction", intro);
        params.put("actPlace", address);
        params.put("sex", sex);
        params.put("imageUrl", "-1");
        params.put("vedioUrl", "-1");
        params.put("latitude", "32");
        params.put("longitude", "118");
        params.put("videoImageUrl", "-1");
        Log.i("tag", Constants.BASE_URL + "deliverActMarket&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "deliverActMarket", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        showToast("发布成功");
                        finish();
                    } else {
                        showToast("发布失败");
                    }
                }
            }
        });
    }


    private void initView() {
        adapter = new PhoteAdapter(this, ls);
        gridview.setAdapter(adapter);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        img_addvideo.setMaxHeight(display.getWidth() / 3);
        img_addvideo.setMaxWidth(display.getWidth() / 3);
        rl_time.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_loc.setOnClickListener(this);
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                time = getTime(date);
                tv_time.setText(time);
            }
        });
    }

    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_time:
                pvTime.show();
                break;
            case R.id.rl_sex:
                choiceSex();
                break;
            case R.id.rl_loc:
                createAddressDialog(RecruitActivity.this);
                break;
        }
    }

    //选择地址
    public void createAddressDialog(Context context) {

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout v = (LinearLayout) layoutInflater.inflate(
                R.layout.activity_pickview1, null);

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
        final CityPicker cityPicker = (CityPicker) v
                .findViewById(R.id.citypicker);
        TextView cancelTextView = (TextView) v.findViewById(R.id.cancel_layout);
        TextView sureTextView = (TextView) v.findViewById(R.id.sure_layout);
        cancelTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
            }
        });
        sureTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (cityPicker.getCity().equals("县") || cityPicker.getCity().equals("市") || cityPicker.getCity()
                        .equals("市辖区")) {
                    address = cityPicker.getProvince();
                } else {
                    address = cityPicker.getProvince() + cityPicker.getCity();
                }
                tv_hopeloc.setText(address);
                popWindow.dismiss();
            }
        });
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }


    //选择性别
    private void choiceSex() {
        new ActionSheetDialog(RecruitActivity.this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("男", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                sex = "1";
                                tv_sex.setText("男");
                            }
                        })
                .addSheetItem("女", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                sex = "2";
                                tv_sex.setText("女");
                            }
                        })
                .addSheetItem("不限", ActionSheetDialog.SheetItemColor.BLACK,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                sex = "3";
                                tv_sex.setText("不限");
                            }
                        }).show();
    }
}

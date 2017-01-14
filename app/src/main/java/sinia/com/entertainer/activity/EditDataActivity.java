package sinia.com.entertainer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import sinia.com.entertainer.R;
import sinia.com.entertainer.actionsheetdialog.ActionSheetDialog;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.PersonInfBean;
import sinia.com.entertainer.bean.TokenBean;
import sinia.com.entertainer.utils.CacheUtils;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;
import sinia.com.entertainer.utils.city.CityPicker;
import sinia.com.entertainer.utils.time.TimePickerView;

/**
 * 编辑资料
 * 头像暂穿-1
 * Created by byw on 2016/12/5.
 */
public class EditDataActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.rl_head)
    RelativeLayout rl_head;
    @Bind(R.id.rl_name)
    RelativeLayout rl_name;
    @Bind(R.id.rl_sex)
    RelativeLayout rl_sex;
    @Bind(R.id.rl_loc)
    RelativeLayout rl_loc;
    @Bind(R.id.rl_player)
    RelativeLayout rl_player;
    @Bind(R.id.rl_height)
    RelativeLayout rl_height;
    @Bind(R.id.rl_weight)
    RelativeLayout rl_weight;
    @Bind(R.id.rl_figure)
    RelativeLayout rl_figure;
    @Bind(R.id.rl_birthday)
    RelativeLayout rl_birthday;
    @Bind(R.id.rl_introduce)
    RelativeLayout rl_introduce;
    @Bind(R.id.img_head)
    ImageView img_head;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_sex)
    TextView tv_sex;
    @Bind(R.id.tv_loc)
    TextView tv_loc;
    @Bind(R.id.tv_job)
    TextView tv_job;
    @Bind(R.id.tv_height)
    TextView tv_height;
    @Bind(R.id.tv_weight)
    TextView tv_weight;
    @Bind(R.id.tv_figure)
    TextView tv_figure;
    @Bind(R.id.tv_birthday)
    TextView tv_birthday;
    @Bind(R.id.tv_introduce)
    TextView tv_introduce;
    TimePickerView pvTime;
    private PopupWindow popWindow;
    private AsyncHttpClient client = new AsyncHttpClient();
    private PersonInfBean bean;
    private String userId;
    private String tall;//身高
    private String weight;//体重
    private String t1, t2, t3;//三围
    private String figure = "-1";//三围总体,选填
    private String sign = "-1";//自我介绍,选填
    private String name;//姓名
    private String job;//角色
    private String sex;//性别
    private String address;//地址
    private String brith;//生日
    private String imgurl = "-1";//头像
    private String imgPath, dateTime;
    private String imgUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata, "编辑资料");
        getDoingView().setVisibility(View.VISIBLE);
        getDoingView().setText("完成");
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        getInf();
        initView();
    }

    private void initDate() {
        Glide.with(this).load(bean.getImageUrl()).placeholder(R.drawable.moren).crossFade()
                .into(img_head);
        if (Utils.isEmpty(bean.getUserName())) {
            tv_name.setText("");
        } else {
            name = bean.getUserName();
            tv_name.setText(name);
        }
        if (bean.getSex().equals("1")) {
            sex = "1";
            tv_sex.setText("男");
        } else if (bean.getSex().equals("2")) {
            sex = "2";
            tv_sex.setText("女");
        } else {
            tv_sex.setText("");
        }
        if (Utils.isEmpty(bean.getAddress())) {
            tv_loc.setText("");
        } else {
            address = bean.getAddress();
            tv_loc.setText(address);
        }
        if (Utils.isEmpty(bean.getProfession())) {
            tv_job.setText("");
        } else {
            job = bean.getProfession();
            tv_job.setText(job);
        }
        if (Utils.isEmpty(bean.getTall())) {
            tv_height.setText("");
        } else {
            tall = bean.getTall();
            tv_height.setText(tall + " cm");
        }
        if (Utils.isEmpty(bean.getWeight())) {
            tv_weight.setText("");
        } else {
            weight = bean.getWeight();
            tv_weight.setText(weight + " kg");
        }
        if (Utils.isEmpty(bean.getBwh()) || bean.getBwh().equals("-1")) {
            tv_figure.setText("");
        } else {
            figure = bean.getBwh();
            tv_figure.setText(figure);
        }
        if (Utils.isEmpty(bean.getBirth())) {
            tv_birthday.setText("");
        } else {
            brith = bean.getBirth();
            tv_birthday.setText(brith);
        }
        if (Utils.isEmpty(bean.getIntroduction()) || bean.getIntroduction().equals("-1")) {
            tv_introduce.setText("未填写");
        } else {
            sign = bean.getIntroduction();
            tv_introduce.setText("");
        }
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
                        initDate();
                    }
                }
            }
        });
    }

    @Override
    public void doing() {
        if (Utils.isEmpty(name)) {
            showToast("请填写姓名");
        } else if (Utils.isEmpty(sex)) {
            showToast("请选择性别");
        } else if (Utils.isEmpty(address)) {
            showToast("请选择地址");
        } else if (Utils.isEmpty(job)) {
            showToast("请选择职业");
        } else if (Utils.isEmpty(tall)) {
            showToast("请选择身高");
        } else if (Utils.isEmpty(weight)) {
            showToast("请选择体重");
        } else if (Utils.isEmpty(brith)) {
            showToast("请选择生日");
        } else if (Utils.isEmpty(sign)) {
            showToast("请填写个人介绍");
        } else {
            saveInf();
        }
    }

    //保存个人信息
    private void saveInf() {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("userName", name);
        params.put("sex", sex);
        params.put("address", address);
        params.put("profession", job);
        params.put("tall", tall);
        params.put("weight", weight);
        params.put("bwh", figure);
        params.put("birth", brith);
        params.put("introduction", sign);
        params.put("imageUrl", imgurl);
        Log.i("tag", Constants.BASE_URL + "updatePersonInfo&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "updatePersonInfo", params, new AsyncHttpResponseHandler() {
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
                        showToast("保存成功");
                        finish();
                    }
                }
            }
        });
    }

    private void initView() {
        rl_head.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_loc.setOnClickListener(this);
        rl_player.setOnClickListener(this);
        rl_height.setOnClickListener(this);
        rl_weight.setOnClickListener(this);
        rl_figure.setOnClickListener(this);
        rl_birthday.setOnClickListener(this);
        rl_introduce.setOnClickListener(this);
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                brith = getTime(date);
                tv_birthday.setText(brith);
            }
        });
    }

    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_head:
                selectHeadImage();
                break;
            case R.id.rl_name:
                //名字
                intent = new Intent(EditDataActivity.this, EditNameActivity.class);
                intent.putExtra("name", tv_name.getText().toString());
                startActivityForResult(intent, 1001);
                break;
            case R.id.rl_sex:
                //性别
                choiceSex();
                break;
            case R.id.rl_loc:
                //地址
                createAddressDialog(EditDataActivity.this);
                break;
            case R.id.rl_player:
                //角色
                intent = new Intent(EditDataActivity.this, ChoiceJobActivity.class);
                intent.putExtra("type", "2");
                startActivityForResult(intent, 1001);
                break;
            case R.id.rl_height:
                choiceTail();
                break;
            case R.id.rl_weight:
                choiceWeight();
                break;
            case R.id.rl_figure:
                choiceThree();
                break;
            case R.id.rl_birthday:
                pvTime.show();
                break;
            case R.id.rl_introduce:
                //自我介绍
                intent = new Intent(EditDataActivity.this, EditIntroduceActivity.class);
                intent.putExtra("sign", sign);
                startActivityForResult(intent, 1001);
                break;
        }
    }

    //选择职业返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == 1001) {
                String str = data.getStringExtra("job");
                str = str.replace("[", "");
                str = str.replace("]", "");
                job = str;
                tv_job.setText(job);
                tv_job.setTextColor(Color.parseColor("#313131"));
            }
            if (resultCode == 1002) {
                name = data.getStringExtra("nn");
                tv_name.setText(name);
            }
            if (resultCode == 1003) {
                String str = data.getStringExtra("sign");
                if (Utils.isEmpty(str)) {
                    tv_introduce.setText("未填写");
                } else {
                    sign = str;
                    tv_introduce.setText("");
                }
            }
        }
        if (resultCode == -1) {
            switch (requestCode) {
                case 1:
                    String files = CacheUtils.getCacheDirectory(this,
                            true, "icon") + dateTime + "avatar.jpg";
                    File file = new File(files);
                    if (file.exists() && file.length() > 0) {
                        Uri uri = Uri.fromFile(file);
                        startPhotoZoom(uri);
                    }
                    break;
                case 2:
                    if (data == null) {
                        return;
                    }
                    startPhotoZoom(data.getData());
                    break;
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = extras.getParcelable("data");
                            imgPath = saveToSdCard(bitmap);
                            img_head.setImageBitmap(bitmap);
                            getToken();
                        }
                    }
                    break;
            }
        }

    }

    private void getToken() {
        RequestParams params = new RequestParams();
        Log.i("tag", Constants.BASE_URL + "upToken&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "upToken", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", s.toString());
                    TokenBean bean = gson.fromJson(s, TokenBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        updateIcon(bean.getToken());
                    }
                }
            }
        });
    }

    private void updateIcon(String str) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(imgPath, dateTime, str, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                Log.e("tag", "上传图片成功");
                imgurl = key;
            }
        }, null);
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
        cancelTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
            }
        });
        sureTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (cityPicker.getCity().equals("县") || cityPicker.getCity().equals("市") || cityPicker.getCity()
                        .equals("市辖区")) {
                    address = cityPicker.getProvince();
                } else {
                    address = cityPicker.getProvince() + cityPicker.getCity();
                }
                tv_loc.setText(address);
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

    //选择身高
    private void choiceTail() {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout v = (LinearLayout) layoutInflater.inflate(
                R.layout.item_tallpick, null);
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

        NumberPicker pick = (NumberPicker) v.findViewById(R.id.pick);
        pick.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pick.setMaxValue(230);
        pick.setMinValue(130);
        pick.setValue(170);
        TextView cancelTextView = (TextView) v.findViewById(R.id.cancel_layout);
        TextView sureTextView = (TextView) v.findViewById(R.id.sure_layout);
        pick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tall = newVal + "cm";
            }
        });
        cancelTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        sureTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isEmpty(tall)) {
                    tall = "170cm";
                }
                tv_height.setText(tall);
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

    //选择体重
    private void choiceWeight() {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout v = (LinearLayout) layoutInflater.inflate(
                R.layout.item_tallpick, null);
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
        NumberPicker pick = (NumberPicker) v.findViewById(R.id.pick);
        pick.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pick.setMaxValue(150);
        pick.setMinValue(30);
        pick.setValue(70);
        TextView cancelTextView = (TextView) v.findViewById(R.id.cancel_layout);
        TextView sureTextView = (TextView) v.findViewById(R.id.sure_layout);
        pick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                weight = newVal + "kg";
            }
        });
        cancelTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        sureTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isEmpty(weight)) {
                    weight = "70kg";
                }
                tv_weight.setText(weight);
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

    private void choiceThree() {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout v = (LinearLayout) layoutInflater.inflate(
                R.layout.item_threepick, null);
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
        NumberPicker pick1 = (NumberPicker) v.findViewById(R.id.pick1);
        NumberPicker pick2 = (NumberPicker) v.findViewById(R.id.pick2);
        NumberPicker pick3 = (NumberPicker) v.findViewById(R.id.pick3);
        pick1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pick1.setMaxValue(150);
        pick1.setMinValue(50);
        pick1.setValue(80);
        pick2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pick2.setMaxValue(150);
        pick2.setMinValue(50);
        pick2.setValue(80);
        pick3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pick3.setMaxValue(150);
        pick3.setMinValue(50);
        pick3.setValue(80);
        TextView cancelTextView = (TextView) v.findViewById(R.id.cancel_layout);
        TextView sureTextView = (TextView) v.findViewById(R.id.sure_layout);
        pick1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                t1 = newVal + "cm";
            }
        });
        pick2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                t2 = newVal + "cm";
            }
        });
        pick3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                t3 = newVal + "cm";
            }
        });
        cancelTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        sureTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isEmpty(t1)) {
                    t1 = "80cm";
                }
                if (Utils.isEmpty(t2)) {
                    t2 = "80cm";
                }
                if (Utils.isEmpty(t3)) {
                    t3 = "80cm";
                }
                figure = t1 + "-" + t2 + "-" + t3;
                tv_figure.setText(figure);
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
        new ActionSheetDialog(EditDataActivity.this)
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
                        }).show();
    }

    //========================头像
    private void selectHeadImage() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Date date1 = new Date(System
                                        .currentTimeMillis());
                                dateTime = date1.getTime() + "";
                                getAvataFromCamera();
                            }
                        })
                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Date date1 = new Date(System
                                        .currentTimeMillis());
                                dateTime = date1.getTime() + "";
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, 2);
                            }
                        }).show();
    }

    protected void getAvataFromCamera() {
        File f = new File(CacheUtils.getCacheDirectory(this, true,
                "icon") + dateTime + "avatar.jpg");
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(camera, 1);
    }


    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils
                .getCacheDirectory(this, true, "icon")
                + dateTime
                + "_11.jpg";
        File file = new File(files);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)) {
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "ture");
        // aspectX aspectY 是宽高的比例
        // intent.putExtra("aspectX", 0);
        // intent.putExtra("aspectY", 0);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        //   intent.putExtra("scale", true);// 黑边
        //  intent.putExtra("scaleUpIfNeeded", true);// 黑边
        intent.putExtra("return-data", true);// 选择返回数据
        startActivityForResult(intent, 3);
    }

}

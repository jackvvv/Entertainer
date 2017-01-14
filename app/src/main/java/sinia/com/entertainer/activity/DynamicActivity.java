package sinia.com.entertainer.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore.Video.Thumbnails;
import android.provider.MediaStore.Video.VideoColumns;
import android.provider.MediaStore.Video.Thumbnails;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import android.media.MediaMetadataRetriever;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.actionsheetdialog.ActionSheetDialog;
import sinia.com.entertainer.adapter.DynamicAdapter;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.TokenBean;
import sinia.com.entertainer.utils.CacheUtils;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;

import static android.R.attr.width;


/**
 * 发布动态界面
 * Created by byw on 2016/12/2.
 */
public class DynamicActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_con)
    EditText et_con;
    @Bind(R.id.img_addphoto)
    ImageView img_addphoto;
    @Bind(R.id.img_addvideo)
    ImageView img_addvideo;
    @Bind(R.id.img_no)
    ImageView img_no;
    @Bind(R.id.img_tongbu)
    ImageView img_tongbu;
    @Bind(R.id.rl_loc)
    RelativeLayout rl_loc;
    @Bind(R.id.rl_tongbu)
    RelativeLayout rl_tongbu;
    @Bind(R.id.tv_loc)
    TextView tv_loc;
    //视频
    @Bind(R.id.rl_video)
    RelativeLayout rl_video;
    @Bind(R.id.img_video)
    ImageView img_video;
    @Bind(R.id.img_vdelete)
    ImageView img_vdelete;
    private GridView mygv;
    private String type = "1";
    private String loc = "-1";
    private List<String> imgLs = new ArrayList<>();
    private DynamicAdapter adapter;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String userId;
    private String content;//内容
    private String imageUrl;//图片
    private String vedioUrl = "-1";//视频
    private String videoImageUrl = "-1";//视频图片
    private String imgPath, dateTime;
    private int pos;
    private List<Bitmap> bitLs = new ArrayList<>();
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic, "发布动态");
        getDoingView().setVisibility(View.VISIBLE);
        getDoingView().setText("发布");
        userId = MyApplication.getInstance().getLoginBean().getUserId();
        initView();
    }


    @Override
    public void doing() {
        content = et_con.getText().toString();
        if (imgLs.size() == 0) {
            imageUrl = "-1";
        } else {
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < imgLs.size(); i++) {
                str.append(imgLs.get(i).toString() + ",");
            }
            imageUrl = (str.deleteCharAt(str.length() - 1)).toString();
        }
        if (Utils.isEmpty(content)) {
            showToast("请输入内容");
        } else {
            deliver();
        }
    }

    private void deliver() {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("content", content);
        params.put("imageUrl", imageUrl);
        params.put("vedioUrl", vedioUrl);
        params.put("address", loc);
        params.put("videoImageUrl", videoImageUrl);
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
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (state == 0 && isSuccessful == 0) {
                        showToast("发布成功");
                        finish();
                    }
                }
            }
        });
    }

    private void initView() {
        mygv = (GridView) findViewById(R.id.mygv);
        adapter = new DynamicAdapter(bitLs, this, handler);
        mygv.setAdapter(adapter);
        rl_loc.setOnClickListener(this);
        rl_tongbu.setOnClickListener(this);
        img_addphoto.setOnClickListener(this);
        img_addvideo.setOnClickListener(this);
        img_vdelete.setOnClickListener(this);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1234) {
                if (bitLs.size() < 9) {
                    //添加图片
                    selectHeadImage();
                } else {
                    showToast("最多添加九张");
                }
            }
            if (msg.what == 2234) {
                //删除图片
                pos = msg.arg1;
                bitLs.remove(pos);
                imgLs.remove(pos);
                adapter.notifyDataSetChanged();
                if (bitLs.size() == 0) {
                    img_addphoto.setVisibility(View.VISIBLE);
                    img_addvideo.setVisibility(View.VISIBLE);
                    img_no.setVisibility(View.INVISIBLE);
                    mygv.setVisibility(View.GONE);
                    rl_video.setVisibility(View.GONE);
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_loc:
                Intent intent = new Intent(DynamicActivity.this, DynamicLocActivity.class);
                startActivityForResult(intent, 1002);
                break;
            case R.id.rl_tongbu:
                if (type.equals("1")) {
                    //开启
                    img_tongbu.setImageResource(R.drawable.tbon);
                    type = "1";
                    break;
                }
                if (type.equals("2")) {
                    //关闭
                    img_tongbu.setImageResource(R.drawable.tboff);
                    type = "2";
                    break;
                }
            case R.id.img_addphoto:
                selectHeadImage();
                break;
            case R.id.img_addvideo:
                selectVideo();
                break;
            case R.id.img_vdelete:
                img_addphoto.setVisibility(View.VISIBLE);
                img_addvideo.setVisibility(View.VISIBLE);
                img_no.setVisibility(View.INVISIBLE);
                mygv.setVisibility(View.GONE);
                rl_video.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002) {
            if (resultCode == 1002) {
                String str = data.getStringExtra("loc");
                if (str.equals("no")) {
                    tv_loc.setText("");
                    loc = "-1";
                } else {
                    tv_loc.setText(str);
                    loc = str;
                }
            }
        }
        //照片
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
                            bitLs.add(bitmap);
                            // img_head.setImageBitmap(bitmap);
                            getToken();
                        }
                    }
                    break;
            }

            if (requestCode == 666) {
                //显示
                img_addphoto.setVisibility(View.GONE);
                img_addvideo.setVisibility(View.GONE);
                img_no.setVisibility(View.GONE);
                mygv.setVisibility(View.GONE);
                rl_video.setVisibility(View.VISIBLE);
                Uri uri = data.getData();
                File file = getFileByUri(uri);
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
                try {
//                mmr.setDataSource(file.getAbsolutePath());
                    mmr.setDataSource(DynamicActivity.this, uri);
                } catch (Exception e) {
                    Log.e("tag", "e=" + e.toString());
                }

                bitmap = mmr.getFrameAtTime(-1);//获得视频第一帧的Bitmap对象
                String duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
                //时长(毫秒)
                int int_duration = Integer.parseInt(duration);
                if (int_duration > 60000) {
                    Toast.makeText(getApplicationContext(), "视频时长超过60秒请重新选择", Toast.LENGTH_SHORT).show();
                }
                bitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
                img_video.setImageBitmap(bitmap);
            }
            if (requestCode == 777) {
                //显示
                img_addphoto.setVisibility(View.GONE);
                img_addvideo.setVisibility(View.GONE);
                img_no.setVisibility(View.GONE);
                mygv.setVisibility(View.GONE);
                rl_video.setVisibility(View.VISIBLE);
                Uri uri = data.getData();
                Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(VideoColumns._ID));
                    String filePath = cursor.getString(cursor.getColumnIndex(VideoColumns.DATA));
                    //bitmap = Thumbnails.getThumbnail(getContentResolver(), id, Thumbnails.MICRO_KIND, null);
                    // ThumbnailUtils类2.2以上可用
                    bitmap = ThumbnailUtils.createVideoThumbnail(filePath,
                            Thumbnails.MICRO_KIND);
                    img_video.setImageBitmap(bitmap);
                    Log.e("tag", "filepath==" + filePath);
                    File file = new File(filePath);
                    cursor.close();
                }
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
                Log.e("tag", "上传成功");
                mygv.setVisibility(View.VISIBLE);
                img_addphoto.setVisibility(View.GONE);
                img_addvideo.setVisibility(View.GONE);
                img_no.setVisibility(View.GONE);
                rl_video.setVisibility(View.GONE);
                imgLs.add(key);
                adapter.notifyDataSetChanged();
            }
        }, null);
    }


    //========================视频
    private void selectVideo() {
        Log.e("tag", "打开");
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍摄选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //拍摄;
                                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);

                                startActivityForResult(intent, 777);
                            }
                        })
                .addSheetItem("从手机中选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //选择视频
                               /* Intent intent = new Intent(DynamicActivity.this, ChoiceVideoActivity.class);
                                startActivityForResult(intent, 666);*/
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("video/*");
                                startActivityForResult(intent, 666);
                            }
                        }).show();
    }

    //========================图片
    private void selectHeadImage() {
        Log.e("tag", "打开");
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

    public File getFileByUri(Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{Images.ImageColumns._ID, Images.ImageColumns.DATA}, buff.toString(), null,
                        null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else {
            Log.i("tag", "Uri Scheme:" + uri.getScheme());
        }
        return null;
    }
}

package sinia.com.entertainer.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.activity.PlayVideoActivity;
import sinia.com.entertainer.activity.SearchMusicDataActivity;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.VideoDataDetBean;
import sinia.com.entertainer.bean.VideoDataLsBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * Created by byw on 2016/12/8.
 */
public class VideoAdapter extends BaseAdapter {

    private Context context;
    private List<VideoDataDetBean> ls;
    private AsyncHttpClient client = new AsyncHttpClient();

    public VideoAdapter(Context context, List<VideoDataDetBean> ls) {
        this.context = context;
        this.ls = ls;
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public Object getItem(int position) {
        return ls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_video, null);
        }
        ImageView img_logo = ViewHolder.get(convertView, R.id.img_logo);
        TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
        TextView tv_con = ViewHolder.get(convertView, R.id.tv_con);
        TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
        TextView tv_count = ViewHolder.get(convertView, R.id.tv_count);
        TextView tv_dis = ViewHolder.get(convertView, R.id.tv_dis);
        final VideoDataDetBean bean = ls.get(position);
        if (bean != null) {
            Glide.with(context).load(bean.getImageUrl()).placeholder(R.drawable.moren1).crossFade()
                    .into(img_logo);
            tv_name.setText(bean.getTitle());
            tv_con.setText(bean.getContent());
            tv_count.setText(bean.getNumber());
            tv_time.setText(bean.getCreateTime());
            tv_dis.setText(bean.getCommentNum());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toDetail(bean.getVideoId());
                }
            });
        }
        return convertView;
    }

    //播放数+1
    private void toDetail(final String str) {
        RequestParams params = new RequestParams();
        params.put("vedioId", str);
        Log.i("tag", Constants.BASE_URL + "videoAction&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "videoAction", params, new AsyncHttpResponseHandler() {
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
                        //跳转详情
                        Intent intent = new Intent(context, PlayVideoActivity.class);
                        intent.putExtra("vId", str);
                        context.startActivity(intent);
                    }
                }
            }
        });
    }
}

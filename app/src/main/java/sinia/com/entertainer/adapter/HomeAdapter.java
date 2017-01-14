package sinia.com.entertainer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.activity.AcDetailActivity;
import sinia.com.entertainer.activity.PlayMusicActivity;
import sinia.com.entertainer.activity.PlayVideoActivity;
import sinia.com.entertainer.bean.HotItemBean;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * Created by byw on 2016/12/2.
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<HotItemBean> ls;

    public HomeAdapter(Context context, List<HotItemBean> ls) {
        this.context = context;
        this.ls = ls;
    }

    @Override
    public int getCount() {
        if (ls.size() <= 8) {
            return ls.size();
        } else {
            return 8;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_home, null);
        }
        final HotItemBean bean = ls.get(position);
        ImageView img_img = ViewHolder.get(convertView, R.id.img_img);
        TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
        TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
        Glide.with(context).load(bean.getImageUrl()).placeholder(R.drawable.moren1).crossFade()
                .into(img_img);
        tv_title.setText(bean.getTitle());
        tv_time.setText(bean.getCreateTime());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = bean.getType();
                Intent intent;
                //1:晒艺圈信息详情2:找场所找演员详情3音乐播放详情4:视频播放详情
                if (type.equals("1")) {
                    //晒艺圈信息详情,界面没写
                }
                if (type.equals("2")) {
                    //找场所找演员详情
                    intent = new Intent(context, AcDetailActivity.class);
                    intent.putExtra("mId", bean.getActMarketId());
                    context.startActivity(intent);
                }
                if (type.equals("3")) {
                    intent = new Intent(context, PlayMusicActivity.class);
                    intent.putExtra("mId", bean.getMusicDataId());
                    intent.putExtra("kind", bean.getMusicType());
                    context.startActivity(intent);
                }
                if (type.equals("4")) {
                    intent = new Intent(context, PlayVideoActivity.class);
                    intent.putExtra("mId", bean.getVideoId());
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }
}

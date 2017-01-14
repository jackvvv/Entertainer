package sinia.com.entertainer.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.bean.MusicDetComBean;
import sinia.com.entertainer.utils.Utils;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * 音乐、视频资料评论
 * Created by byw on 2016/12/13.
 */
public class DataDisAdapter extends BaseAdapter {

    private Context context;
    private List<MusicDetComBean> ls;

    public DataDisAdapter(Context context, List<MusicDetComBean> ls) {
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
                    R.layout.item_datadis, null);
        }
        final MusicDetComBean bean = ls.get(position);
        ImageView img_head = ViewHolder.get(convertView, R.id.img_head);
        TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
        TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
        TextView tv_con = ViewHolder.get(convertView, R.id.tv_con);
        Glide.with(context).load(bean.getComImage()).placeholder(R.drawable.moren).crossFade()
                .into(img_head);
        tv_name.setText(bean.getComName());
        tv_time.setText(bean.getComTime());
        if (bean.getType().equals("2")) {
            tv_con.setText(bean.getComment());
        } else {
            String html = "<html><body><span ><font color=\"#d73232\">" + "@" + bean.getReName() + " "
                    + "</span>" + bean.getComment() + "</body></html>";
            tv_con.setText(Html.fromHtml(html));
        }

        return convertView;
    }
}

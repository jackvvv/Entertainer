package sinia.com.entertainer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.bean.ContentDetailBean;
import sinia.com.entertainer.bean.ContentTypeBean;
import sinia.com.entertainer.utils.Utils;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * Created by byw on 2017/1/12.
 */
public class ContentDetailAdapter extends BaseAdapter {
    private List<ContentDetailBean> ls;
    private Context context;

    public ContentDetailAdapter(Context context, List<ContentDetailBean> ls) {
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
                    R.layout.item_contentdetail, null);
        }
        ContentDetailBean bean = ls.get(position);
        ImageView img_head = ViewHolder.get(convertView, R.id.img_head);
        ImageView img_redv = ViewHolder.get(convertView, R.id.img_redv);
        TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
        TextView tv_job1 = ViewHolder.get(convertView, R.id.tv_job1);
        TextView tv_job2 = ViewHolder.get(convertView, R.id.tv_job2);
        tv_name.setText(bean.getName());
        Glide.with(context).load(bean.getImageUrl()).placeholder(R.drawable.moren).crossFade()
                .into(img_head);
        if (bean.getvConfirm().equals("")) {
            img_redv.setVisibility(View.GONE);
        } else {
            img_redv.setVisibility(View.VISIBLE);
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
        return convertView;
    }
}

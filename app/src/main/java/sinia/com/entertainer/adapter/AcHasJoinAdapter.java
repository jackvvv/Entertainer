package sinia.com.entertainer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.bean.DetailSignBean;
import sinia.com.entertainer.bean.SignBean;
import sinia.com.entertainer.utils.Utils;
import sinia.com.entertainer.utils.ViewHolder;


/**
 * 已参加
 * Created by byw on 2016/12/26.
 */

public class AcHasJoinAdapter extends BaseAdapter {


    private Context context;
    private List<SignBean> ls;
    public String outId;
    public AcHasJoinAdapter(Context context, List<SignBean> ls) {
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
                    R.layout.item_achasjoin, null);
        }
        ImageView img_head = ViewHolder.get(convertView, R.id.img_head);
        TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
        TextView tv_job1 = ViewHolder.get(convertView, R.id.tv_job1);
        TextView tv_job2 = ViewHolder.get(convertView, R.id.tv_job2);
        SignBean bean = ls.get(position);
        tv_name.setText(bean.getUserName());
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
        Glide.with(context).load(bean.getUserImg()).placeholder(R.drawable.moren).crossFade()
                .into(img_head);
        return convertView;
    }
}

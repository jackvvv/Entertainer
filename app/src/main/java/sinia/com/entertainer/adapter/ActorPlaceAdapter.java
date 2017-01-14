package sinia.com.entertainer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.activity.AcDetailActivity;
import sinia.com.entertainer.bean.AcDetailBean;
import sinia.com.entertainer.utils.Utils;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * Created by byw on 2016/12/19.
 */
public class ActorPlaceAdapter extends BaseAdapter {
    private Context context;
    private List<AcDetailBean> ls;
    public String type;

    public ActorPlaceAdapter(Context context, List<AcDetailBean> ls) {
        this.context = context;
        this.ls = ls;
    }

    @Override
    public int getCount() {
        return ls.size();
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
                    R.layout.item_artplace, null);
        }
        final AcDetailBean bean = ls.get(position);
        ImageView img_head = ViewHolder.get(convertView, R.id.img_head);
        ImageView img_img = ViewHolder.get(convertView, R.id.img_img);
        ImageView img_redv = ViewHolder.get(convertView, R.id.img_redv);
        ImageView img_rz = ViewHolder.get(convertView, R.id.img_rz);
        TextView tv_con = ViewHolder.get(convertView, R.id.tv_con);
        TextView tv_job1 = ViewHolder.get(convertView, R.id.tv_job1);
        TextView tv_job2 = ViewHolder.get(convertView, R.id.tv_job2);
        TextView tv_job3 = ViewHolder.get(convertView, R.id.tv_job3);
        TextView tv_loc = ViewHolder.get(convertView, R.id.tv_loc);
        TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
        TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
        tv_job3.setVisibility(View.GONE);
        if (bean != null) {
            tv_con.setText(bean.getActorTitle());
            tv_loc.setText(bean.getActPlace());
            tv_name.setText(bean.getUserName());
            tv_time.setText(bean.getMarketTime());
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
            Glide.with(context).load(bean.getImageUrl()).placeholder(R.drawable.moren1).crossFade()
                    .into(img_head);
            Glide.with(context).load(bean.getUserImage()).placeholder(R.drawable.moren).crossFade()
                    .into(img_img);

            if (bean.getIdConfirm().equals("2")) {
                img_rz.setVisibility(View.VISIBLE);
            } else {
                img_rz.setVisibility(View.GONE);
            }
            if (bean.getvConfirm().equals("1")) {
                img_redv.setVisibility(View.VISIBLE);
            } else {
                img_redv.setVisibility(View.GONE);
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AcDetailActivity.class);
                intent.putExtra("mId", bean.getMarketId());
                intent.putExtra("type", type);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

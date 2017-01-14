package sinia.com.entertainer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.bean.MyAdBean;
import sinia.com.entertainer.bean.MyReOutBean;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * 接口需返回状态
 * Created by byw on 2016/12/8.
 */
public class MineAdapter1 extends BaseAdapter {


    private Context context;
    private List<MyAdBean> ls;
    private AsyncHttpClient client = new AsyncHttpClient();

    public MineAdapter1(Context context, List<MyAdBean> ls) {
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
                    R.layout.item_mine, null);
        }
        final MyAdBean bean = ls.get(position);
        TextView tv_title = ViewHolder.get(convertView, R.id.tv_title);
        TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
        TextView tv_num = ViewHolder.get(convertView, R.id.tv_num);
        TextView tv_allnum = ViewHolder.get(convertView, R.id.tv_allnum);
        TextView tv_job1 = ViewHolder.get(convertView, R.id.tv_job1);
        TextView tv_job2 = ViewHolder.get(convertView, R.id.tv_job2);
        TextView tv_sex = ViewHolder.get(convertView, R.id.tv_sex);
        TextView tv_endtime = ViewHolder.get(convertView, R.id.tv_endtime);
        TextView tv_loc = ViewHolder.get(convertView, R.id.tv_loc);
        TextView tv_status = ViewHolder.get(convertView, R.id.tv_status);
        TextView tv_btn = ViewHolder.get(convertView, R.id.tv_btn);
        tv_btn.setText("电话联系");
        tv_title.setText(bean.getActPlace());
        tv_time.setText(bean.getMarketTime());
        tv_num.setText(bean.getSignNum());
        tv_allnum.setText("已接受" + bean.getAgreeNum() + "人");
        if (bean.getReActor().contains(",")) {
            String[] s = bean.getReActor().split(",");
            tv_job1.setText(s[0]);
            tv_job2.setText(s[1]);
            tv_job2.setVisibility(View.VISIBLE);
            tv_job1.setVisibility(View.VISIBLE);
        } else {
            tv_job1.setVisibility(View.VISIBLE);
            tv_job1.setText(bean.getReActor());
            tv_job2.setVisibility(View.GONE);
        }
        if (bean.getSex().equals("1")) {
            tv_sex.setText("男");
        }
        if (bean.getSex().equals("2")) {
            tv_sex.setText("女");
        }
        if (bean.getSex().equals("3")) {
            tv_sex.setText("不限男女");
        }
        tv_endtime.setText(bean.getAllowTime());
        tv_loc.setText(bean.getActPlace());
        if (bean.getStatus().equals("1")) {
            tv_status.setText("待处理");
        }
        if (bean.getStatus().equals("2")) {
            tv_status.setText("已过期");
            tv_btn.setClickable(false);
            tv_btn.setBackgroundResource(R.drawable.lay_sharp);
            tv_btn.setTextColor(Color.parseColor("#b6b6b6"));
        }
        if (bean.getStatus().equals("3")) {
            tv_status.setText("已关闭");
            tv_btn.setClickable(false);
            tv_btn.setBackgroundResource(R.drawable.lay_sharp);
            tv_btn.setTextColor(Color.parseColor("#b6b6b6"));
        }
        tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getStatus().equals("1")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + bean.getPhone()));
                    context.startActivity(intent);
                }

            }
        });
        return convertView;
    }
}

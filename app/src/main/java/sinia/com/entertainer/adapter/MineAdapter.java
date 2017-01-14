package sinia.com.entertainer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.base.JsonBean;
import sinia.com.entertainer.bean.MyReOutBean;
import sinia.com.entertainer.bean.PersonInfBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * 接口需返回状态
 * Created by byw on 2016/12/8.
 */
public class MineAdapter extends BaseAdapter {


    private Context context;
    private List<MyReOutBean> ls;
    private Handler handler;

    public MineAdapter(Context context, List<MyReOutBean> ls, Handler handler) {
        this.context = context;
        this.ls = ls;
        this.handler = handler;
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
        final MyReOutBean bean = ls.get(position);
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
        tv_title.setText(bean.getSpaceName());
        tv_time.setText(bean.getCreateTime());
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
        tv_endtime.setText(bean.getAllowTime());
        tv_loc.setText(bean.getActPlace());
        if (bean.getSex().equals("1")) {
            tv_sex.setText("男");
        }
        if (bean.getSex().equals("2")) {
            tv_sex.setText("女");
        }
        if (bean.getSex().equals("3")) {
            tv_sex.setText("不限男女");
        }
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
                    Message msg = new Message();
                    msg.what = 2001;
                    msg.obj = bean.getMarketId();
                    handler.sendMessage(msg);
                }
            }
        });
        return convertView;
    }

}

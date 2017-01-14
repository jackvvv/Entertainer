package sinia.com.entertainer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.activity.PlayMusicActivity;
import sinia.com.entertainer.bean.MusicDataDetBean;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * Created by byw on 2016/12/8.
 */
public class MusicDataAdapter extends BaseAdapter {

    private Context context;
    private List<MusicDataDetBean> ls;
    private Handler handle;
    public String type;//判断是否全选
    public String kind;

    public MusicDataAdapter(Context context, List<MusicDataDetBean> ls, Handler handle, String type) {
        this.context = context;
        this.ls = ls;
        this.handle = handle;
        this.type = type;
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
                    R.layout.item_musicdata, null);
        }
        ImageView img_more = ViewHolder.get(convertView, R.id.img_more);
        ImageView img_play = ViewHolder.get(convertView, R.id.img_play);
        ImageView img_choice = ViewHolder.get(convertView, R.id.img_choice);
        ImageView img_type = ViewHolder.get(convertView, R.id.img_type);
        TextView tv_singname = ViewHolder.get(convertView, R.id.tv_singname);
        TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
        TextView tv_type = ViewHolder.get(convertView, R.id.tv_type);
        TextView tv_con = ViewHolder.get(convertView, R.id.tv_con);
        final MusicDataDetBean bean = ls.get(position);
        tv_singname.setText(bean.getName());
        tv_name.setText(bean.getAuthor());
        if (bean.getSongType().equals("1")) {
            tv_type.setText(" - 翻唱");
        } else {
            tv_type.setText(" - 原唱");
        }
        tv_con.setText(bean.getSourceDet());
        if (kind.equals("伴奏")) {
            img_type.setVisibility(View.GONE);
        } else {
            if (bean.getHotType().equals("1")) {
                img_type.setImageResource(R.drawable.mhot);
            } else if (bean.getNewType().equals("1")) {
                img_type.setImageResource(R.drawable.mnew);
            } else {
                img_type.setVisibility(View.GONE);
            }
        }
        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("1")) {
                    Message msg = new Message();
                    msg.what = 1001;
                    msg.arg1 = Integer.parseInt(bean.getZanStatus());
                    msg.arg2 = Integer.parseInt(bean.getCollectionStatus());
                    msg.obj = bean.getMusicDataId();
                    handle.sendMessage(msg);
                }
            }
        });
        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("1")) {
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    intent.putExtra("id", bean.getMusicDataId());
                    intent.putExtra("kind", kind);
                    context.startActivity(intent);
                }
            }
        });
        if (type.equals("1")) {
            img_choice.setVisibility(View.GONE);
        }
        if (type.equals("2")) {
            img_choice.setVisibility(View.VISIBLE);
            if (bean.getChoosed().equals("1")) {
                img_choice.setImageResource(R.drawable.mselect);
            } else {
                img_choice.setImageResource(R.drawable.mselsected);
            }
        }
        return convertView;
    }
}

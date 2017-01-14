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

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.activity.PlayMusicActivity;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * Created by byw on 2016/12/8.
 */
public class MusicAdapter extends BaseAdapter {

    private Context context;
    private List<String> ls;
    private Handler handle;

    public MusicAdapter(Context context, List<String> ls, Handler handle) {
        this.context = context;
        this.ls = ls;
        this.handle = handle;
    }

    @Override
    public int getCount() {
        return 10;
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
                    R.layout.item_music, null);
        }
        ImageView img_more = ViewHolder.get(convertView, R.id.img_more);
        ImageView img_play = ViewHolder.get(convertView, R.id.img_play);
        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 1001;
                handle.sendMessage(msg);
            }
        });
        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayMusicActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

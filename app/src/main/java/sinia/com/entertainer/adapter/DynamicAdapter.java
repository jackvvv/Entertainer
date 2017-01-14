package sinia.com.entertainer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * Created by byw on 2017/1/12.
 */
public class DynamicAdapter extends BaseAdapter {
    private List<Bitmap> ls;
    private Context context;
    private Handler handler;

    public DynamicAdapter(List<Bitmap> ls, Context context, Handler handler) {
        this.ls = ls;
        this.context = context;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return ls.size() + 1;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_dynamic, null);
        }
        ImageView img = ViewHolder.get(convertView, R.id.img_img);
        ImageView img_delete = ViewHolder.get(convertView, R.id.img_delete);
        if (position < ls.size()) {
            // Glide.with(context).load("http://ojlsvioci.bkt.clouddn.com/" + ls.get(position)).placeholder(R.drawable
            //        .moren1).crossFade()
            //        .into(img);
            img.setImageBitmap(ls.get(position));
        } else {
            img.setImageResource(R.drawable.addphoto);
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == ls.size()) {
                    Message msg = new Message();
                    msg.what = 1234;
                    handler.sendMessage(msg);
                }
            }
        });
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != ls.size()) {
                    Message msg = new Message();
                    msg.what = 2234;
                    msg.arg1 = position;
                    handler.sendMessage(msg);
                }
            }
        });


        return convertView;
    }
}

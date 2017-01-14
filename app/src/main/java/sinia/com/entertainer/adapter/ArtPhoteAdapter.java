package sinia.com.entertainer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.activity.PhotoDetailActivity;
import sinia.com.entertainer.bean.DetailImgBean;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * 演艺圈列表照片
 * Created by byw on 2016/12/1.
 */
public class ArtPhoteAdapter extends BaseAdapter {
    private Context context;

    private List<String> ls;

    public ArtPhoteAdapter(Context context, List<String> ls) {
        this.context = context;
        this.ls = ls;
    }

    @Override
    public int getCount() {
        return 9;
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
                    R.layout.item_artphoto, null);
        }
        ImageView img = ViewHolder.get(convertView, R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoDetailActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

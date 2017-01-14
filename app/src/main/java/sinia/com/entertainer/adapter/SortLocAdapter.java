package sinia.com.entertainer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * 筛选地区
 * Created by byw on 2016/12/14.
 */
public class SortLocAdapter extends BaseAdapter {

    private Context context;
    private List<String> ls;

    public SortLocAdapter(Context context, List<String> ls) {
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
                    R.layout.item_sortloc, null);
        }
        TextView tv_loc = ViewHolder.get(convertView, R.id.tv_loc);
        tv_loc.setText(ls.get(position));
        return convertView;
    }
}

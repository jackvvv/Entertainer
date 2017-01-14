package sinia.com.entertainer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.utils.ActivityManager;

/**
 * Created by byw on 2016/12/14.
 */
public class DynamicLocAdapter extends BaseAdapter {

    private Context context;
    private List<String> ls;

    public DynamicLocAdapter(Context context, List<String> ls) {
        this.context = context;
        this.ls = ls;
    }

    @Override
    public int getCount() {
        return 6;
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
                    R.layout.item_locchoice, null);
        }
        return convertView;
    }
}


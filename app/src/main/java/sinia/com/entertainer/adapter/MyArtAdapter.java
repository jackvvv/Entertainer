package sinia.com.entertainer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * Created by byw on 2016/12/12.
 */
public class MyArtAdapter extends BaseAdapter {
    private ArtPhoteAdapter adapter;
    private Context context;
    private List<String> ls;


    public MyArtAdapter(Context context, List<String> ls) {
        this.context = context;
        this.ls = ls;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_myart, null);
        }
        GridView mgv = ViewHolder.get(convertView, R.id.mgv);
        adapter = new ArtPhoteAdapter(context, ls);
        mgv.setAdapter(adapter);
        return convertView;
    }
}

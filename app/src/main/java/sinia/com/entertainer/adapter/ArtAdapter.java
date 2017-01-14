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
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.utils.ViewHolder;

/**
 * Created by byw on 2016/12/1.
 */
public class ArtAdapter extends BaseAdapter {

    private Context context;
    private List<String> ls;

    private ArtPhoteAdapter adapter;
    private ArtDisAdapter adapter1;

    public ArtAdapter(Context context, List<String> ls) {
        this.context = context;
        this.ls = ls;
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
                    R.layout.item_art, null);
        }
        GridView mgv = ViewHolder.get(convertView, R.id.mgv);
        ListView mlv = ViewHolder.get(convertView, R.id.mlv);
        adapter = new ArtPhoteAdapter(context, ls);
        mgv.setAdapter(adapter);
        adapter1 = new ArtDisAdapter(context, ls);
        mlv.setAdapter(adapter1);
        return convertView;
    }
}

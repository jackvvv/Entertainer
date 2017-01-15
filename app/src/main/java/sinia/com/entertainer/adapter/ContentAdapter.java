package sinia.com.entertainer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.activity.ContentActivity;
import sinia.com.entertainer.bean.ContentTypeBean;
import sinia.com.entertainer.chat.ChatActivity;
import sinia.com.entertainer.utils.ViewHolder;

import static sinia.com.entertainer.R.id.listview;

/**
 * Created by byw on 2017/1/12.
 */
public class ContentAdapter extends BaseAdapter {
    private List<ContentTypeBean> ls;
    private Context context;
    private ContentDetailAdapter adapter;

    public ContentAdapter(Context context, List<ContentTypeBean> ls) {
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
                    R.layout.item_content, null);
        }
        final ContentTypeBean bean = ls.get(position);
        TextView tv_type = ViewHolder.get(convertView, R.id.tv_type);
        ListView dls = ViewHolder.get(convertView, R.id.dls);
        tv_type.setText(bean.getType());
        adapter = new ContentDetailAdapter(context, bean.getItems());
        dls.setAdapter(adapter);

        dls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userId = bean.getItems().get(position).getTelephone();
                String username = bean.getItems().get(position).getName();
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("username", username);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

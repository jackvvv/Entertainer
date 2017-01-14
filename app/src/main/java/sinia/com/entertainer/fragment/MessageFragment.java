package sinia.com.entertainer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.activity.ContentActivity;
import sinia.com.entertainer.adapter.MsgAdapter;
import sinia.com.entertainer.base.BaseFragment;

/**
 * 消息界面
 * Created by byw on 2016/11/29.
 */
public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private View headView;
    private TextView tv_list;

    private TextView tv_sysnum;
    private TextView tv_strnum;
    private RelativeLayout rl_system;
    private RelativeLayout rl_stranger;

    private ListView mlistview;

    private List<String> ls;
    private MsgAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_msg, null);
        headView = LayoutInflater.from(getActivity()).inflate(
                R.layout.head_view, null);
        initView();
        return rootView;
    }

    private void initView() {
        mlistview = (ListView) rootView.findViewById(R.id.mlistview);
        tv_list = (TextView) rootView.findViewById(R.id.tv_list);
        mlistview.addHeaderView(headView);
        tv_sysnum = (TextView) headView.findViewById(R.id.tv_sysnum);
        tv_strnum = (TextView) headView.findViewById(R.id.tv_strnum);
        rl_system = (RelativeLayout) headView.findViewById(R.id.rl_system);
        rl_stranger = (RelativeLayout) headView.findViewById(R.id.rl_stranger);
        adapter = new MsgAdapter(getActivity(), ls);
        mlistview.setAdapter(adapter);
        tv_list.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_list:
                intent = new Intent(getActivity(), ContentActivity.class);
                startActivity(intent);
                break;
        }
    }
}

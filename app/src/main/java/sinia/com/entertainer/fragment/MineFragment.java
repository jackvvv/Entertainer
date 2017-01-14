package sinia.com.entertainer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import sinia.com.entertainer.R;
import sinia.com.entertainer.activity.CollectionActivity;
import sinia.com.entertainer.activity.EditDataActivity;
import sinia.com.entertainer.activity.IdentificationActivity;
import sinia.com.entertainer.activity.LoginActivity;
import sinia.com.entertainer.activity.MainActivity;
import sinia.com.entertainer.activity.MineActivity;
import sinia.com.entertainer.activity.MyDownloadActivity;
import sinia.com.entertainer.activity.MyEnListActivity;
import sinia.com.entertainer.activity.MyReleaseActivity;
import sinia.com.entertainer.activity.SetActivity;
import sinia.com.entertainer.base.BaseFragment;
import sinia.com.entertainer.bean.LoginBean;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.Utils;

/**
 * Created by byw on 2016/11/29.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private RelativeLayout rl_mine, rl_edit, rl_identification, rl_collect, rl_download, rl_release, rl_enlist, rl_set;
    private ImageView img_head;
    private TextView tv_name, tv_job1, tv_job2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        initView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getInstance().getBoolValue("is_login")) {
            rl_mine.setOnClickListener(this);
            rl_edit.setOnClickListener(this);
            rl_identification.setOnClickListener(this);
            rl_collect.setOnClickListener(this);
            rl_download.setOnClickListener(this);
            rl_release.setOnClickListener(this);
            rl_enlist.setOnClickListener(this);
            rl_set.setOnClickListener(this);
        } else {
            rl_mine.setOnClickListener(oc);
            rl_edit.setOnClickListener(oc);
            rl_identification.setOnClickListener(oc);
            rl_collect.setOnClickListener(oc);
            rl_download.setOnClickListener(oc);
            rl_release.setOnClickListener(oc);
            rl_enlist.setOnClickListener(oc);
            rl_set.setOnClickListener(oc);
            img_head.setImageResource(R.drawable.moren);
            tv_name.setText("");
            tv_job1.setVisibility(View.INVISIBLE);
            tv_job2.setVisibility(View.INVISIBLE);
        }
    }

    private void initView() {
        img_head = (ImageView) rootView.findViewById(R.id.img_head);
        tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        tv_job1 = (TextView) rootView.findViewById(R.id.tv_job1);
        tv_job2 = (TextView) rootView.findViewById(R.id.tv_job2);
        rl_mine = (RelativeLayout) rootView.findViewById(R.id.rl_mine);
        rl_edit = (RelativeLayout) rootView.findViewById(R.id.rl_edit);
        rl_identification = (RelativeLayout) rootView.findViewById(R.id.rl_identification);
        rl_collect = (RelativeLayout) rootView.findViewById(R.id.rl_collect);
        rl_download = (RelativeLayout) rootView.findViewById(R.id.rl_download);
        rl_release = (RelativeLayout) rootView.findViewById(R.id.rl_release);
        rl_enlist = (RelativeLayout) rootView.findViewById(R.id.rl_enlist);
        rl_set = (RelativeLayout) rootView.findViewById(R.id.rl_set);
        tv_name.setText(MyApplication.getInstance().getLoginBean().getUserName());
        if (Utils.isEmpty(MyApplication.getInstance().getLoginBean().getProfession())) {
            tv_job1.setVisibility(View.GONE);
            tv_job2.setVisibility(View.GONE);
        } else {
            if (MyApplication.getInstance().getLoginBean().getProfession().contains(",")) {
                String[] s = MyApplication.getInstance().getLoginBean().getProfession().split(",");
                tv_job1.setText(s[0]);
                tv_job2.setText(s[1]);
                tv_job2.setVisibility(View.VISIBLE);
                tv_job1.setVisibility(View.VISIBLE);
            } else {
                tv_job1.setVisibility(View.VISIBLE);
                tv_job1.setText(MyApplication.getInstance().getLoginBean().getProfession());
                tv_job2.setVisibility(View.GONE);
            }
        }
        Glide.with(this).load(MyApplication.getInstance().getLoginBean().getImageUrl()).placeholder(R.drawable.moren)
                .crossFade()
                .into(img_head);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_mine:
                intent = new Intent(getActivity(), MineActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_edit:
                intent = new Intent(getActivity(), EditDataActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_identification:
                intent = new Intent(getActivity(), IdentificationActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_collect:
                intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_download:
                intent = new Intent(getActivity(), MyDownloadActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_release:
                intent = new Intent(getActivity(), MyReleaseActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_enlist:
                intent = new Intent(getActivity(), MyEnListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_set:
                intent = new Intent(getActivity(), SetActivity.class);
                startActivityForResult(intent, 666);
                break;
        }
    }

    View.OnClickListener oc = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666) {
            if (resultCode == 666) {
                img_head.setImageResource(R.drawable.moren);
                tv_name.setText("");
                tv_job1.setVisibility(View.INVISIBLE);
                tv_job2.setVisibility(View.INVISIBLE);
            }
        }
    }
}

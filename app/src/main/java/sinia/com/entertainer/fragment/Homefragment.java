package sinia.com.entertainer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import sinia.com.entertainer.R;
import sinia.com.entertainer.activity.AcDetailActivity;
import sinia.com.entertainer.activity.ActionActivity;
import sinia.com.entertainer.activity.ActorActivity;
import sinia.com.entertainer.activity.DetailedInformationActivity;
import sinia.com.entertainer.activity.MusicDataActivity;
import sinia.com.entertainer.activity.SearchActivity;
import sinia.com.entertainer.activity.VideoDataActivity;
import sinia.com.entertainer.activity.WebViewActivity;
import sinia.com.entertainer.adapter.HomeAdapter;
import sinia.com.entertainer.base.BaseFragment;
import sinia.com.entertainer.bean.HotActorBean;
import sinia.com.entertainer.bean.HotBean;
import sinia.com.entertainer.bean.HotItemBean;
import sinia.com.entertainer.bean.HotPlaceBean;
import sinia.com.entertainer.bean.HotTopBean;
import sinia.com.entertainer.bean.UserIdBean;
import sinia.com.entertainer.utils.Constants;
import sinia.com.entertainer.view.slideview.SlideShowView;

/**
 * Created by byw on 2016/11/29.
 */
public class Homefragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private SlideShowView mslv;
    private ScrollView sv;
    private GridView mgv;
    private HomeAdapter adapter;
    private ImageView img_search;
    private TextView tv_music, tv_video;
    private RelativeLayout rl_action, rl_actor;
    //演员
    private RelativeLayout rl_al, rl_a2, rl_a3, rl_a4, rl_a5;
    private ImageView img_a1, img_a2, img_a3, img_a4, img_a5;
    private TextView tv_aname1, tv_aname2, tv_aname3, tv_aname4, tv_aname5;
    //场所
    private RelativeLayout rl_locl, rl_loc2, rl_loc3, rl_loc4, rl_loc5;
    private ImageView img_loc1, img_loc2, img_loc3, img_loc4, img_loc5;
    private TextView tv_lname1, tv_lname2, tv_lname3, tv_lname4, tv_lname5;
    private AsyncHttpClient client = new AsyncHttpClient();
    //数据
    private HotBean bean;
    private List<HotActorBean> actorItems;//演员
    private List<HotPlaceBean> placeItems;//剧院
    private List<HotTopBean> topItems;//首页轮播
    private List<HotItemBean> items = new ArrayList<>();//热点广场

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        initView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getInf();
    }

    //获取首页数据
    private void getInf() {
        RequestParams params = new RequestParams();
        Log.i("tag", Constants.BASE_URL + "hotList&" + params);
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "hotList", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    Log.e("tag", "首页");
                    bean = gson.fromJson(s, HotBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        initDate();
                    } else {

                    }
                }
            }
        });
    }


    private void initView() {
        sv = (ScrollView) rootView.findViewById(R.id.sv);
        tv_music = (TextView) rootView.findViewById(R.id.tv_music);
        tv_video = (TextView) rootView.findViewById(R.id.tv_video);
        mslv = (SlideShowView) rootView.findViewById(R.id.mslv);
        rl_action = (RelativeLayout) rootView.findViewById(R.id.rl_action);
        rl_actor = (RelativeLayout) rootView.findViewById(R.id.rl_actor);
        mgv = (GridView) rootView.findViewById(R.id.mgv);
        adapter = new HomeAdapter(getActivity(), items);
        mgv.setAdapter(adapter);
        img_search = (ImageView) rootView.findViewById(R.id.img_search);

        //两行头像
        rl_al = (RelativeLayout) rootView.findViewById(R.id.rl_al);
        rl_a2 = (RelativeLayout) rootView.findViewById(R.id.rl_a2);
        rl_a3 = (RelativeLayout) rootView.findViewById(R.id.rl_a3);
        rl_a4 = (RelativeLayout) rootView.findViewById(R.id.rl_a4);
        rl_a5 = (RelativeLayout) rootView.findViewById(R.id.rl_a5);
        rl_locl = (RelativeLayout) rootView.findViewById(R.id.rl_locl);
        img_a1 = (ImageView) rootView.findViewById(R.id.img_a1);
        img_a2 = (ImageView) rootView.findViewById(R.id.img_a2);
        img_a3 = (ImageView) rootView.findViewById(R.id.img_a3);
        img_a4 = (ImageView) rootView.findViewById(R.id.img_a4);
        img_a5 = (ImageView) rootView.findViewById(R.id.img_a5);
        tv_aname1 = (TextView) rootView.findViewById(R.id.tv_aname1);
        tv_aname2 = (TextView) rootView.findViewById(R.id.tv_aname2);
        tv_aname3 = (TextView) rootView.findViewById(R.id.tv_aname3);
        tv_aname4 = (TextView) rootView.findViewById(R.id.tv_aname4);
        tv_aname5 = (TextView) rootView.findViewById(R.id.tv_aname5);
        rl_locl = (RelativeLayout) rootView.findViewById(R.id.rl_locl);
        rl_loc2 = (RelativeLayout) rootView.findViewById(R.id.rl_loc2);
        rl_loc3 = (RelativeLayout) rootView.findViewById(R.id.rl_loc3);
        rl_loc4 = (RelativeLayout) rootView.findViewById(R.id.rl_loc4);
        rl_loc5 = (RelativeLayout) rootView.findViewById(R.id.rl_loc5);
        img_loc1 = (ImageView) rootView.findViewById(R.id.img_loc1);
        img_loc2 = (ImageView) rootView.findViewById(R.id.img_loc2);
        img_loc3 = (ImageView) rootView.findViewById(R.id.img_loc3);
        img_loc4 = (ImageView) rootView.findViewById(R.id.img_loc4);
        img_loc5 = (ImageView) rootView.findViewById(R.id.img_loc5);
        tv_lname1 = (TextView) rootView.findViewById(R.id.tv_lname1);
        tv_lname2 = (TextView) rootView.findViewById(R.id.tv_lname2);
        tv_lname3 = (TextView) rootView.findViewById(R.id.tv_lname3);
        tv_lname4 = (TextView) rootView.findViewById(R.id.tv_lname4);
        tv_lname5 = (TextView) rootView.findViewById(R.id.tv_lname5);
    }

    private void initDate() {
        actorItems = bean.getActorItems();
        placeItems = bean.getPlaceItems();
        topItems = bean.getTopItems();
        items.clear();
        items.addAll(bean.getItems());
        adapter.notifyDataSetChanged();
        sv.scrollTo(0, 0);
        //点击
        img_search.setOnClickListener(this);
        tv_music.setOnClickListener(this);
        tv_video.setOnClickListener(this);
        rl_action.setOnClickListener(this);
        rl_actor.setOnClickListener(this);
        //轮播
        List<String> ls = new ArrayList<String>();
        for (int i = 0; i < topItems.size(); i++) {
            ls.add(topItems.get(i).getImageUrl());
        }
        mslv.setImagePath(ls);
        mslv.startPlay();
        mslv.setOnItemClickListener(new SlideShowView.ViewPagerItemCLickListener() {
            @Override
            public void onItemClick(View pager, int position) {

                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("link", topItems.get(position).getRealPath());
                getActivity().startActivity(intent);
            }
        });
        //演员
        switch (actorItems.size()) {
            case 0:
                rl_al.setVisibility(View.INVISIBLE);
                rl_a2.setVisibility(View.INVISIBLE);
                rl_a3.setVisibility(View.INVISIBLE);
                rl_a4.setVisibility(View.INVISIBLE);
                rl_a5.setVisibility(View.INVISIBLE);
                break;
            case 1:
                rl_al.setVisibility(View.VISIBLE);
                tv_aname1.setText(actorItems.get(0).getName());
                Glide.with(this).load(actorItems.get(0).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a1);
                rl_a2.setVisibility(View.INVISIBLE);
                rl_a3.setVisibility(View.INVISIBLE);
                rl_a4.setVisibility(View.INVISIBLE);
                rl_a5.setVisibility(View.INVISIBLE);
                break;
            case 2:
                rl_al.setVisibility(View.VISIBLE);
                tv_aname1.setText(actorItems.get(0).getName());
                Glide.with(this).load(actorItems.get(0).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a1);
                rl_a2.setVisibility(View.VISIBLE);
                tv_aname2.setText(actorItems.get(1).getName());
                Glide.with(this).load(actorItems.get(1).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a2);
                rl_a3.setVisibility(View.INVISIBLE);
                rl_a4.setVisibility(View.INVISIBLE);
                rl_a5.setVisibility(View.INVISIBLE);
                break;
            case 3:
                rl_al.setVisibility(View.VISIBLE);
                tv_aname1.setText(actorItems.get(0).getName());
                Glide.with(this).load(actorItems.get(0).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a1);
                rl_a2.setVisibility(View.VISIBLE);
                tv_aname2.setText(actorItems.get(1).getName());
                Glide.with(this).load(actorItems.get(1).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a2);
                rl_a3.setVisibility(View.VISIBLE);
                tv_aname3.setText(actorItems.get(2).getName());
                Glide.with(this).load(actorItems.get(2).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a3);
                rl_a4.setVisibility(View.INVISIBLE);
                rl_a5.setVisibility(View.INVISIBLE);
                break;
            case 4:
                rl_al.setVisibility(View.VISIBLE);
                tv_aname1.setText(actorItems.get(0).getName());
                Glide.with(this).load(actorItems.get(0).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a1);
                rl_a2.setVisibility(View.VISIBLE);
                tv_aname2.setText(actorItems.get(1).getName());
                Glide.with(this).load(actorItems.get(1).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a2);
                rl_a3.setVisibility(View.VISIBLE);
                tv_aname3.setText(actorItems.get(2).getName());
                Glide.with(this).load(actorItems.get(2).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a3);
                rl_a4.setVisibility(View.VISIBLE);
                tv_aname4.setText(actorItems.get(3).getName());
                Glide.with(this).load(actorItems.get(3).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a4);
                rl_a5.setVisibility(View.INVISIBLE);
                break;
            case 5:
                rl_al.setVisibility(View.VISIBLE);
                tv_aname1.setText(actorItems.get(0).getName());
                Glide.with(this).load(actorItems.get(0).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a1);
                rl_a2.setVisibility(View.VISIBLE);
                tv_aname2.setText(actorItems.get(1).getName());
                Glide.with(this).load(actorItems.get(1).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a2);
                rl_a3.setVisibility(View.VISIBLE);
                tv_aname3.setText(actorItems.get(2).getName());
                Glide.with(this).load(actorItems.get(2).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a3);
                rl_a4.setVisibility(View.VISIBLE);
                tv_aname4.setText(actorItems.get(3).getName());
                Glide.with(this).load(actorItems.get(3).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a4);
                rl_a5.setVisibility(View.VISIBLE);
                tv_aname5.setText(actorItems.get(4).getName());
                Glide.with(this).load(actorItems.get(4).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_a5);
                break;
        }
        //场所
        switch (placeItems.size()) {
            case 0:
                rl_locl.setVisibility(View.INVISIBLE);
                rl_loc2.setVisibility(View.INVISIBLE);
                rl_loc3.setVisibility(View.INVISIBLE);
                rl_loc4.setVisibility(View.INVISIBLE);
                rl_loc5.setVisibility(View.INVISIBLE);
                break;
            case 1:
                rl_locl.setVisibility(View.VISIBLE);
                tv_lname1.setText(placeItems.get(0).getName());
                Glide.with(this).load(placeItems.get(0).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc1);
                rl_loc2.setVisibility(View.INVISIBLE);
                rl_loc3.setVisibility(View.INVISIBLE);
                rl_loc4.setVisibility(View.INVISIBLE);
                rl_loc5.setVisibility(View.INVISIBLE);
                break;
            case 2:
                rl_locl.setVisibility(View.VISIBLE);
                tv_lname1.setText(placeItems.get(0).getName());
                Glide.with(this).load(placeItems.get(0).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc1);
                rl_loc2.setVisibility(View.VISIBLE);
                tv_lname2.setText(placeItems.get(1).getName());
                Glide.with(this).load(placeItems.get(1).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc2);
                rl_loc3.setVisibility(View.INVISIBLE);
                rl_loc4.setVisibility(View.INVISIBLE);
                rl_loc5.setVisibility(View.INVISIBLE);
                break;
            case 3:
                rl_locl.setVisibility(View.VISIBLE);
                tv_lname1.setText(placeItems.get(0).getName());
                Glide.with(this).load(placeItems.get(0).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc1);
                rl_loc2.setVisibility(View.VISIBLE);
                tv_lname2.setText(placeItems.get(1).getName());
                Glide.with(this).load(placeItems.get(1).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc2);
                rl_loc3.setVisibility(View.VISIBLE);
                tv_lname3.setText(placeItems.get(2).getName());
                Glide.with(this).load(placeItems.get(2).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc3);
                rl_loc4.setVisibility(View.INVISIBLE);
                rl_loc5.setVisibility(View.INVISIBLE);
                break;
            case 4:
                rl_locl.setVisibility(View.VISIBLE);
                tv_lname1.setText(placeItems.get(0).getName());
                Glide.with(this).load(placeItems.get(0).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc1);
                rl_loc2.setVisibility(View.VISIBLE);
                tv_lname2.setText(placeItems.get(1).getName());
                Glide.with(this).load(placeItems.get(1).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc2);
                rl_loc3.setVisibility(View.VISIBLE);
                tv_lname3.setText(placeItems.get(2).getName());
                Glide.with(this).load(placeItems.get(2).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc3);
                rl_loc4.setVisibility(View.VISIBLE);
                tv_lname4.setText(placeItems.get(3).getName());
                Glide.with(this).load(placeItems.get(3).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc4);
                rl_loc5.setVisibility(View.INVISIBLE);
                break;
            case 5:
                rl_locl.setVisibility(View.VISIBLE);
                tv_lname1.setText(placeItems.get(0).getName());
                Glide.with(this).load(placeItems.get(0).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc1);
                rl_loc2.setVisibility(View.VISIBLE);
                tv_lname2.setText(placeItems.get(1).getName());
                Glide.with(this).load(placeItems.get(1).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc2);
                rl_loc3.setVisibility(View.VISIBLE);
                tv_lname3.setText(placeItems.get(2).getName());
                Glide.with(this).load(placeItems.get(2).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc3);
                rl_loc4.setVisibility(View.VISIBLE);
                tv_lname4.setText(placeItems.get(3).getName());
                Glide.with(this).load(placeItems.get(3).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc4);
                rl_loc5.setVisibility(View.VISIBLE);
                tv_lname5.setText(placeItems.get(4).getName());
                Glide.with(this).load(placeItems.get(4).getImageUrl()).placeholder(R.drawable.moren).crossFade()
                        .into(img_loc5);
                break;
        }

        img_a1.setOnClickListener(this);
        img_a2.setOnClickListener(this);
        img_a3.setOnClickListener(this);
        img_a4.setOnClickListener(this);
        img_a5.setOnClickListener(this);
        img_loc1.setOnClickListener(this);
        img_loc2.setOnClickListener(this);
        img_loc3.setOnClickListener(this);
        img_loc4.setOnClickListener(this);
        img_loc5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_search:
                //搜索
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_video:
                //跳转视频资料
                intent = new Intent(getActivity(), VideoDataActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_music:
                //跳转音乐资料
                intent = new Intent(getActivity(), MusicDataActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_action:
                //接演出
                intent = new Intent(getActivity(), ActionActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_actor:
                //招演员
                intent = new Intent(getActivity(), ActorActivity.class);
                startActivity(intent);
                break;
            case R.id.img_a1:
                toDetaile(actorItems.get(0).getUserId());
                break;
            case R.id.img_a2:
                toDetaile(actorItems.get(1).getUserId());
                break;
            case R.id.img_a3:
                toDetaile(actorItems.get(2).getUserId());
                break;
            case R.id.img_a4:
                toDetaile(actorItems.get(3).getUserId());
                break;
            case R.id.img_a5:
                toDetaile(actorItems.get(4).getUserId());
                break;
            case R.id.img_loc1:
                toDetaile(placeItems.get(0).getUserId());
                break;
            case R.id.img_loc2:
                toDetaile(placeItems.get(1).getUserId());
                break;
            case R.id.img_loc3:
                toDetaile(placeItems.get(2).getUserId());
                break;
            case R.id.img_loc4:
                toDetaile(placeItems.get(3).getUserId());
                break;
            case R.id.img_loc5:
                toDetaile(placeItems.get(4).getUserId());
                break;
        }
    }


    private void toDetaile(String str) {
        Intent intent = new Intent(getActivity(), DetailedInformationActivity.class);
        intent.putExtra("did",str);
        intent.putExtra("type", "2");
        startActivity(intent);
    }
}
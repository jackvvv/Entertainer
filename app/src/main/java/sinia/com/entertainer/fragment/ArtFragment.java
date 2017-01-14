package sinia.com.entertainer.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.ArtAdapter;
import sinia.com.entertainer.base.BaseFragment;

/**
 * 演艺圈
 * Created by byw on 2016/11/29.
 */
public class ArtFragment extends BaseFragment {
    private View rootView;
    private ListView mlistview;
    private ImageView img_screen;
    private ArtAdapter adapter;
    private List<String> ls;
    private PopupWindow popWindow;
    private RelativeLayout rl_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_art, null);
        initView();
        return rootView;
    }

    private void initView() {
        rl_title = (RelativeLayout) rootView.findViewById(R.id.rl_title);
        img_screen = (ImageView) rootView.findViewById(R.id.img_screen);
        mlistview = (ListView) rootView.findViewById(R.id.mlistview);
        adapter = new ArtAdapter(getActivity(), ls);
        mlistview.setAdapter(adapter);
        img_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPup();
            }
        });
    }


    private void showPup() {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout view = (LinearLayout) layoutInflater.inflate(
                R.layout.layout_screen, null);
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
                .WRAP_CONTENT);
        //  popWindow.setAnimationStyle(R.style.popwin_anim_style);
        popWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation2);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAsDropDown(rl_title, 0, 0);
        // popWindow.showAtLocation(rl_title, Gravity.NO_GRAVITY, 0,rl_title.getHeight()+getResources()
        // .getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android")));
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }


}

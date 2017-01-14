package sinia.com.entertainer.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.adapter.SearchAdapter;
import sinia.com.entertainer.adapter.SearchListAdapter;
import sinia.com.entertainer.base.BaseActivity;

/**
 * 搜索界面
 * Created by byw on 2016/12/2.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private PopupWindow popWindow;
    private GridView hotgv;
    private GridView hisgv;
    private SearchAdapter adapter;
    private List<String> ls;
    private TextView tv_choice, tv_chanel;
    private LinearLayout ll_search;
    private ListView mlistview;
    private EditText et_search;
    private SearchListAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    private void initView() {
        et_search = (EditText) findViewById(R.id.et_search);
        hotgv = (GridView) findViewById(R.id.hotgv);
        hisgv = (GridView) findViewById(R.id.hisgv);
        adapter = new SearchAdapter(this, ls);
        hotgv.setAdapter(adapter);
        hisgv.setAdapter(adapter);
        tv_choice = (TextView) findViewById(R.id.tv_choice);
        tv_chanel = (TextView) findViewById(R.id.tv_chanel);
        tv_choice.setOnClickListener(this);
        tv_chanel.setOnClickListener(this);
        ll_search = (LinearLayout) findViewById(R.id.ll_search);
        mlistview = (ListView) findViewById(R.id.mlistview);
        adapter1 = new SearchListAdapter(this, ls);
        mlistview.setAdapter(adapter1);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mlistview.setVisibility(View.VISIBLE);
                ll_search.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_choice:
                showPup();
                break;
            case R.id.tv_chanel:
                finish();
                break;
        }
    }

    private void showPup() {
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout view = (LinearLayout) layoutInflater.inflate(
                R.layout.layout_blackbg, null);
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
                .WRAP_CONTENT);

        TextView tv_loc = (TextView) view.findViewById(R.id.tv_loc);
        TextView tv_actor = (TextView) view.findViewById(R.id.tv_actor);

        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, tv_choice.getHeight() + getResources()
                .getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android")));
        tv_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_choice.setText("场所");
                popWindow.dismiss();
            }
        });
        tv_actor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_choice.setText("演员");
                popWindow.dismiss();
            }
        });

    }
}

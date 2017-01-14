package sinia.com.entertainer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;

/**
 * 选择视频
 * Created by byw on 2017/1/13.
 */
public class ChoiceVideoActivity extends BaseActivity {
    @Bind(R.id.mygv)
    GridView mygv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choicevideo, "选择视频");
        getDoingView().setVisibility(View.GONE);
    }
}

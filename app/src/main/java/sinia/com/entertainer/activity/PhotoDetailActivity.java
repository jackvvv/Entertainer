package sinia.com.entertainer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;

/**
 * 照片详情
 * Created by byw on 2016/12/1.
 */
public class PhotoDetailActivity extends BaseActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photodetail);
        img=(ImageView)findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

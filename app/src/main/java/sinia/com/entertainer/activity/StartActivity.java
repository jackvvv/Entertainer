package sinia.com.entertainer.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;


import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;


/***
 * 欢迎页
 *
 * @author byw
 */
public class StartActivity extends BaseActivity {
    AlphaAnimation aa;

    private LinearLayout startLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startLayout = (LinearLayout) this.findViewById(R.id.start_layout);
        aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(2000);
        startLayout.startAnimation(aa);

        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this,
                        MainActivity.class);
                startActivity(intent);
                StartActivity.this.finish();
            }
        };
        timer.schedule(tt, 2000);
    }
}

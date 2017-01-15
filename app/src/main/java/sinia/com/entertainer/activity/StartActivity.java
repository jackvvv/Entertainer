package sinia.com.entertainer.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;


import com.hyphenate.chat.EMClient;

import sinia.com.entertainer.DemoHelper;
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
    private static final int sleepTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startLayout = (LinearLayout) this.findViewById(R.id.start_layout);
        aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(1500);
        startLayout.startAnimation(aa);

//        Timer timer = new Timer();
//        TimerTask tt = new TimerTask() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(StartActivity.this,
//                        MainActivity.class);
//                startActivity(intent);
//                StartActivity.this.finish();
//            }
//        };
//        timer.schedule(tt, 2000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (DemoHelper.getInstance().isLoggedIn()) {
                    // auto login mode, make sure all group and conversation is loaed before enter the main screen
                    long start = System.currentTimeMillis();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    EMClient.getInstance().groupManager().loadAllGroups();
                    long costTime = System.currentTimeMillis() - start;
                    //wait
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    Intent intent = new Intent(StartActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    StartActivity.this.finish();
                } else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }).start();
    }
}

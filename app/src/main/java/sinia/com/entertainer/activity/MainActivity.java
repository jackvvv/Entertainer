package sinia.com.entertainer.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.animation.Animator.AnimatorListener;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.support.v4.app.Fragment;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentManager;
import android.widget.TextView;
import android.os.Handler;

import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.fragment.ArtFragment;
import sinia.com.entertainer.fragment.Homefragment;
import sinia.com.entertainer.fragment.MessageFragment;
import sinia.com.entertainer.fragment.MineFragment;
import sinia.com.entertainer.utils.ActivityManager;
import sinia.com.entertainer.utils.KickBackAnimator;
import sinia.com.entertainer.utils.MyApplication;
import sinia.com.entertainer.utils.SystemBarTintManager;

/***
 * 主页
 *
 * @author byw
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    // @InjectView(R.id.center_btn)
    private ImageView center_btn;
    // 定义PopupWindow
    private PopupWindow popWindow;

    private Handler mHandler = new Handler();

    private RelativeLayout layout_home, layout_msg, layout_art,
            layout_mine;
    private View v;

    private ImageView image_home, image_msg, image_art, image_mine;

    private SystemBarTintManager mTintManager;

    private LayoutInflater inflater;
    public static FragmentTabHost fragmentTabHost;
    private View view1, view2, view3, view4;
    private long exitTime = 0;
    private Homefragment hf;
    private ArtFragment af;
    private MessageFragment msf;
    private MineFragment mf;

    private ViewPager vp;
    private List<Fragment> pagers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public ViewPager getVp() {
        return vp;
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        hf = new Homefragment();
        msf = new MessageFragment();
        af = new ArtFragment();
        mf = new MineFragment();
        pagers = new ArrayList<Fragment>();
        pagers.add(hf);
        pagers.add(msf);
        pagers.add(af);
        pagers.add(mf);
        ViewAdapter adapter = new ViewAdapter(this.getSupportFragmentManager());
        vp.setAdapter(adapter);
        vp.setCurrentItem(0);
        layout_home = (RelativeLayout) findViewById(R.id.layout_home);
        layout_msg = (RelativeLayout) findViewById(R.id.layout_msg);
        layout_art = (RelativeLayout) findViewById(R.id.layout_art);
        layout_mine = (RelativeLayout) findViewById(R.id.layout_mine);
        center_btn = (ImageView) findViewById(R.id.center_btn);
        v = findViewById(R.id.v);
        image_home = (ImageView) findViewById(R.id.image_home);
        image_msg = (ImageView) findViewById(R.id.image_msg);
        image_art = (ImageView) findViewById(R.id.image_art);
        image_mine = (ImageView) findViewById(R.id.image_mine);

        image_home.setSelected(true);
        image_msg.setSelected(false);
        image_art.setSelected(false);
        image_mine.setSelected(false);

        center_btn.setOnClickListener(this);
        layout_home.setOnClickListener(this);
        layout_msg.setOnClickListener(this);
        layout_art.setOnClickListener(this);
        layout_mine.setOnClickListener(this);

        final View activityRootView = findViewById(R.id.activityRoot);
        // 给该layout设置监听，监听其布局发生变化事件
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        // 比较Activity根布局与当前布局的大小
                        int heightDiff = activityRootView.getRootView()
                                .getHeight() - activityRootView.getHeight();
                        if (heightDiff > 100) {
                            // 大小超过100时，一般为显示虚拟键盘事件
                            // fragmentTabHost.setVisibility(View.GONE);
                        } else {
                            // 大小小于100时，为不显示虚拟键盘或虚拟键盘隐藏
                        }
                    }
                });
    }

    private class ViewAdapter extends FragmentPagerAdapter {

        public ViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return pagers.get(arg0);
        }

        @Override
        public int getCount() {
            return pagers.size();
        }
    }


    @Override
    public void onClick(View vv) {
        switch (vv.getId()) {
            case R.id.layout_home:
                image_home.setSelected(true);
                image_msg.setSelected(false);
                image_art.setSelected(false);
                image_mine.setSelected(false);
                vp.setCurrentItem(0);
                break;
            case R.id.layout_msg:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    image_home.setSelected(false);
                    image_msg.setSelected(true);
                    image_art.setSelected(false);
                    image_mine.setSelected(false);
                    vp.setCurrentItem(1);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.layout_art:
                image_home.setSelected(false);
                image_msg.setSelected(false);
                image_art.setSelected(true);
                image_mine.setSelected(false);
                vp.setCurrentItem(2);
                break;
            case R.id.layout_mine:
                // if (MyApplication.getInstance().getBoolValue("is_login")) {
                image_home.setSelected(false);
                image_msg.setSelected(false);
                image_art.setSelected(false);
                image_mine.setSelected(true);
                vp.setCurrentItem(3);
                // } else {
                //     Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                //    startActivity(intent);
                //  }

                break;
            case R.id.center_btn:
                showPup();
                break;
        }
    }


    private void showPup() {
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout view = (RelativeLayout) layoutInflater.inflate(
                R.layout.layout_center_popwindow, null);
        popWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        showAnimation(view);
        RelativeLayout rl = (RelativeLayout) popWindow.getContentView();
        TextView tv_dongtai = (TextView) rl.findViewById(R.id.tv_dongtai);
        TextView tv_play = (TextView) rl.findViewById(R.id.tv_play);
        TextView tv_place = (TextView) rl.findViewById(R.id.tv_place);
        ImageView img_center = (ImageView) rl.findViewById(R.id.img_center);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
                closeAnimation(view, popWindow);
            }
        });
        img_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        tv_dongtai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布动态
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    Intent intent = new Intent(MainActivity.this, DynamicActivity.class);
                    startActivity(intent);
                    popWindow.dismiss();

                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    popWindow.dismiss();
                }


            }
        });
        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //找演员
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    Intent intent = new Intent(MainActivity.this, RecruitActivity.class);
                    startActivity(intent);
                    popWindow.dismiss();

                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    popWindow.dismiss();
                }

            }
        });
        tv_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //接场所
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    Intent intent = new Intent(MainActivity.this, PlaceActivity.class);
                    startActivity(intent);
                    popWindow.dismiss();

                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    popWindow.dismiss();
                }

            }
        });
    }

    public void showAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            child.setVisibility(View.INVISIBLE);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child,
                            "translationY", 600, 0);
                    fadeAnim.setDuration(300);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(150);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                }
            }, i * 50);
        }

    }

    public void closeAnimation(ViewGroup layout, final PopupWindow p) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child,
                            "translationY", 0, 600);
                    fadeAnim.setDuration(200);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(100);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                    fadeAnim.addListener(new AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            child.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }
                    });
                }
            }, (layout.getChildCount() - i - 1) * 30);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast("再按一次退出应用");
            exitTime = System.currentTimeMillis();
        } else {
            ActivityManager.getInstance().finishAllActivity();
        }
    }
}

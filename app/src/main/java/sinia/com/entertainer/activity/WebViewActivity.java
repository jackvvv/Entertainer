package sinia.com.entertainer.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;


/***
 * 网页界面
 * @author byw
 */
public class WebViewActivity extends BaseActivity {
    private String link;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview, "详情");
        getDoingView().setVisibility(View.GONE);
        link = getIntent().getStringExtra("link");
        initView();
    }
    //生命周期，重新加载网页
    @Override
    protected void onRestart() {
        super.onRestart();
        webView.loadUrl(link);
    }

    //初始化
    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        //支持缩放

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);

        //去掉按钮
        webView.getSettings().setDisplayZoomControls(false);
        // 解决webview的适配屏幕问题
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url); // 在当前的webview中跳转到新的url
                return false;
            }
        });
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.loadUrl(link);
    }

    //结束时加载空白页，防止锁屏还继续放声音
    @Override
    protected void onStop() {
        super.onStop();
        webView.loadUrl("about:blank");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}

package cn.fredye.hybriddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class CommonWebViewActivity extends AppCompatActivity {
    public static final String TAG = "CommonWebViewActivity";
    private CommonWebView webView;
    private ProgressBar progressBar;
    private TextView tvTitle;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_web_view);
        initView();
    }

    private void initView() {
        webView = (CommonWebView) findViewById(R.id.webView);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        progressBar = (ProgressBar) findViewById(R.id.progress) ;
        imageView = (ImageView) findViewById(R.id.iv_back);
        CommonWebSettings settings = new CommonWebSettings(webView);
        settings.initWebSettings();
        webView.setWebViewClient(new CommonWebViewClient(progressBar));
        webView.setWebChromeClient(new CommonWebChromeClient(tvTitle, progressBar));
        webView.loadUrl("file:///android_asset/hybrid-web.html");
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", "Hello");
                map.put("age", 11);
                webView.sendToJavaScript(map);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}

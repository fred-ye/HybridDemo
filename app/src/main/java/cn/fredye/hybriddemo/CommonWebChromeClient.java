package cn.fredye.hybriddemo;
import android.net.Uri;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by fred on 2017/11/23.
 */

public class CommonWebChromeClient extends WebChromeClient{
    private TextView tvTitle;
    private ProgressBar progressBar;

    public CommonWebChromeClient(TextView textView, ProgressBar progressBar) {
        this.tvTitle = textView;
        this.progressBar = progressBar;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        tvTitle.setText(title);
        super.onReceivedTitle(view, title);

    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (newProgress ==100) {
            progressBar.setProgress(newProgress);
            progressBar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            }, 100);

        } else {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress <= 10 ? 10 : newProgress);
        }
    }
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        Uri uri = Uri.parse(message);
        //如果是调nativeAPI.
        if ("native".equals(uri.getScheme())) {
            result.confirm("call natvie api success");
            return true;
        }
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }
}

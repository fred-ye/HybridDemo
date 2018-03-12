package cn.fredye.hybriddemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by fred on 2018/3/5.
 */

public class CommonWebView extends WebView{
    public CommonWebView(Context context) {
        super(context);
    }
    public final String TO_JAVASCRIPT_PREFIX = "javascript:HybridAPI.sendToJavaScript('%s')";

    public HybridAPI hybridAPI;

    @SuppressLint("JavascriptInterface")
    public CommonWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.setWebContentsDebuggingEnabled(true);
        }
        hybridAPI = new HybridAPI();
        this.addJavascriptInterface(hybridAPI, "HybridAPI");
    }


    public void sendToJavaScript(Map<String, Object> message) {
        String str = new Gson().toJson(message);
        final String jsCommand = String.format(TO_JAVASCRIPT_PREFIX, escapeString(str));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            evaluateJavascript(jsCommand, null);
        } else {
            loadUrl(jsCommand);
        }

    }
    private String escapeString(String javascript) {
        String result;
        result = javascript.replace("\\", "\\\\");
        result = result.replace("\"", "\\\"");
        result = result.replace("\'", "\\\'");
        result = result.replace("\n", "\\n");
        result = result.replace("\r", "\\r");
        result = result.replace("\f", "\\f");
        return result;
    }

}

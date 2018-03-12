package cn.fredye.hybriddemo;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import cn.fredye.hybriddemo.CommonWebView;
import cn.fredye.hybriddemo.util.Constants;


/**
 * Created by fred on 2018/3/5.
 */

public class CommonWebSettings {
    public static final String USERAGENT = "Mozilla/5.0 (Linux; U; "
            + "Android " + Build.VERSION.RELEASE + "; en-us; " + Build.MODEL
            + " Build/FRF91) AppleWebKit/533.1 "
            + "(KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

    protected WebSettings settings;
    private WebView webView;

    public CommonWebSettings(CommonWebView webView) {
        settings = webView.getSettings();
        this.webView = webView;
    }
    public void initWebSettings() {
        removeJavascriptInterfaces(webView);
        settings.setSaveFormData(false);
        settings.setJavaScriptEnabled(true);
        settings.setNeedInitialFocus(false);
        settings.setSupportMultipleWindows(true);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setUseWideViewPort(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setUserAgentString(USERAGENT);
        settings.setDefaultTextEncodingName(Constants.UTF8);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(webView.getContext().getDir(Constants.CACHE, 0).getPath());
        settings.setDatabasePath(webView.getContext().getDir(Constants.DATABASE, 0).getPath());
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(false);
        //不保存密码
        settings.setSavePassword(false);
    }
    private void removeJavascriptInterfaces(WebView webView) {
        try {
            if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 17) {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
                webView.removeJavascriptInterface("accessibility");
                webView.removeJavascriptInterface("accessibilityTraversal");
            }
        } catch (Throwable tr) {
            tr.printStackTrace();
        }
    }
}

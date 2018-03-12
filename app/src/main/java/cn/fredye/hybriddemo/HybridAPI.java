package cn.fredye.hybriddemo;


import android.util.Log;
import android.webkit.JavascriptInterface;

public class HybridAPI {
    public static final String TAG = "HybridAPI";

    @JavascriptInterface
    public void sendToNative(final String message) {
        Log.i(TAG, "get data from js------------>" + message);

    }
}



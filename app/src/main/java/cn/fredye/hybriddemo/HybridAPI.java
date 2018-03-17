package cn.fredye.hybriddemo;


import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.fredye.hybriddemo.util.DataUtil;

public class HybridAPI {
    public static final String TAG = "HybridAPI";

    private CommonWebView webView;
    private Map<String, INativeCallback> nativeCallbackMap;
    public Handler uiHandler;


    public HybridAPI(Context context, CommonWebView webView) {
        this.nativeCallbackMap = new HashMap<>();
        uiHandler = new Handler(context.getMainLooper());
        this.webView = webView;
    }

    @JavascriptInterface
    public void sendToNative(final String message) {
        JSONObject object = DataUtil.str2JSONObject(message);
        if (object == null) {
            return;
        }
        final String callbackId = DataUtil.getStrInJSONObject(object, "id");
        final String method = DataUtil.getStrInJSONObject(object, "method");
        final String params = DataUtil.getStrInJSONObject(object, "params");
        final String data = DataUtil.getStrInJSONObject(object, "data");
        final String error = DataUtil.getStrInJSONObject(object, "error");

        final Map<String, Object> paramsMap = DataUtil.jsonStr2Map(params);
        //调native方法，做分发
        if (!TextUtils.isEmpty(method)) {
            uiHandler.post(new Runnable() {

                @Override
                public void run() {
                    final INativeCallback callback = new INativeCallback() {
                        @Override
                        public void call(Map<String, Object> error, Map<String, Object> result) {
                            Map<String, Object> responseData = new HashMap<>();
                            responseData.put("error", error);
                            responseData.put("data", result);
                            responseData.put("id", callbackId);
                            webView.sendToJavaScript(responseData);

                        }


                    };
                    handleNativeAPI(method, paramsMap, callback);
                }
            });

        } else {
            //没有方法名，表明是从native到前端
            //前端执行后不回调native
            if (TextUtils.isEmpty(callbackId)) {
                return;
            }
            // 前端执行后，再回调native.
            System.out.println("callbackId:" + callbackId);
            final INativeCallback callback = webView.callbackMap.get(callbackId);
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.call(DataUtil.jsonStr2Map(error), DataUtil.jsonStr2Map(data));
                    webView.callbackMap.remove(callbackId);
                }
            });

        }

    }

    private void handleNativeAPI(String method, Map<String, Object> params, INativeCallback callback) {
        if ("getDeviceInfo".equals(method)) {

        } else if ("getUserInfo".equals(method)) {

        } else if ("login".equals(method)) {

        } else if ("logout".equals(method)) {

        }
    }
}



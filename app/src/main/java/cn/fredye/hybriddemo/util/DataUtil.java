package cn.fredye.hybriddemo.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by fred on 2018/3/7.
 */

public class DataUtil {
    public static JSONObject str2JSONObject(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static String getStrInJSONObject(JSONObject obj, String key) {
        if (obj != null && obj.has(key)) {
            try {
                return obj.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static boolean getBooleanInJSONObject(JSONObject obj, String key) {
        if (obj != null && obj.has(key)) {
            try {
                return obj.getBoolean(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static Map<String, Object> jsonStr2Map(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return new Gson().fromJson(json, Map.class);
    }

    public static String map2Json(Map<String, Object> map) {
        if (map != null) {
            return new Gson().toJson(map);
        }
        return null;
    }
}

package cn.fredye.hybriddemo;

import java.util.Map;

/**
 * Created by fred on 2017/11/21.
 */

public interface INativeCallback {
    void call(Map<String, Object> error, Map<String, Object> response);
}

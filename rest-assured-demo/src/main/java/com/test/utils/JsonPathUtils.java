package com.test.utils;

import com.jayway.jsonpath.JsonPath;

/**
 * @author jingLv
 * @date 2020-08-03 1:21 下午
 */
public class JsonPathUtils {
    /**
     * 需要解析的json串
     */
    private final String json;

    /**
     * jsonPath的公式
     */
    private final String pattern;

    public JsonPathUtils(String json, String pattern) {
        this.json = json;
        this.pattern = pattern;
    }

    public Object readJson() {
        return JsonPath.read(this.json, this.pattern);
    }
}

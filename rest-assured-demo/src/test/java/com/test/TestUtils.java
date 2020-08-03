package com.test;

import cn.hutool.core.codec.Base64;
import org.junit.jupiter.api.Test;

/**
 * @author jingLv
 * @date 2020-08-03 11:12 上午
 */
public class TestUtils {

    @Test
    void testBase64Encode() {
        String str = "{\n" +
                "  \"books\": [\n" +
                "    {\n" +
                "      \"name\": \"西游记\",\n" +
                "      \"price\": 100\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"三国演绎\",\n" +
                "      \"price\": 20\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"水浒传\",\n" +
                "      \"price\": 33\n" +
                "    }\n" +
                "  ],\n" +
                "  \"store\": \"木子书店\"\n" +
                "}";
        String encode = Base64.encode(str);
        System.out.println(encode);

        String body = "ewogICJib29rcyI6IFsKICAgIHsKICAgICAgIm5hbWUiOiAi6KW/5ri46K6wIiwKICAgICAgInByaWNlIjogMTAwCiAgICB9LAogICAgewogICAgICAibmFtZSI6ICLkuInlm73mvJTnu44iLAogICAgICAicHJpY2UiOiAyMAogICAgfSwKICAgIHsKICAgICAgIm5hbWUiOiAi5rC05rWS5LygIiwKICAgICAgInByaWNlIjogMzMKICAgIH0KICBdLAogICJzdG9yZSI6ICLmnKjlrZDkuablupciCn0=";
        String decodeStr = Base64.decodeStr(body);
        System.out.println(decodeStr);
    }
}

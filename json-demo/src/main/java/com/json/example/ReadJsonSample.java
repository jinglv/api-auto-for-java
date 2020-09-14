package com.json.example;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * @author jingLv
 * @date 2020/08/21
 */
public class ReadJsonSample {

    /**
     * 从文件读取json
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/apple/WorkProject/api-auto-for-java/json-demo/src/main/resources/User.json");
        String content = FileUtils.readFileToString(file);
        JSONObject jsonObject = new JSONObject(content);
        System.out.println("name:" + jsonObject.getString("name"));
        System.out.println("age:" + jsonObject.getInt("age"));
        System.out.println("has_boyfriend:" + jsonObject.getBoolean("has_boyfriend"));
        JSONArray majorArray = jsonObject.getJSONArray("major");
        for (int i = 0; i < majorArray.length(); i++) {
            String m = (String) majorArray.get(i);
            System.out.println("major-" + (i + 1) + m);
        }
    }
}

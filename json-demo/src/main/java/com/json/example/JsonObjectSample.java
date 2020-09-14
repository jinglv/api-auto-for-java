package com.json.example;

import com.json.example.bean.User;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jingLv
 * @date 2020/08/19
 */
public class JsonObjectSample {

    public static void main(String[] args) {
        jsonObject();
        createJsonByMap();
        createJsonByBean();
    }

    /*    {
            "name": "刘滋滋",
                "age": 18,
                "birthday": "1990-01-01",
                "school": "蓝翔",
                "major": [
            "理发",
                    "挖掘机"
      ],
            "has_boyfriend": false,
                "car": null,
                "house": null,
                "comment": "这是一个注释"
        }
        */

    /**
     * 通过JSONObject，构建json对象
     */
    private static void jsonObject() {
        JSONObject liuzizi = new JSONObject();
        Object nullObject = null;
        liuzizi.put("name", "刘滋滋");
        liuzizi.put("age", 18);
        liuzizi.put("birthday", "1990-01-01");
        liuzizi.put("school", "蓝翔");
        liuzizi.put("major", new String[]{"理发", "挖掘机"});
        liuzizi.put("has_boyfriend", false);
        // null会有二义性
        liuzizi.put("car", nullObject);
        liuzizi.put("house", nullObject);
        liuzizi.put("comment", "这是一个注释");
        System.out.println(liuzizi.toString());
    }

    /**
     * 通过HashMap，构建json对象
     */
    private static void createJsonByMap() {
        Map<String, Object> liuzizi = new HashMap<String, Object>();
        Object nullObject = null;
        liuzizi.put("name", "刘滋滋");
        liuzizi.put("age", 18);
        liuzizi.put("birthday", "1990-01-01");
        liuzizi.put("school", "蓝翔");
        liuzizi.put("major", new String[]{"理发", "挖掘机"});
        liuzizi.put("has_boyfriend", false);
        // null会有二义性
        liuzizi.put("car", nullObject);
        liuzizi.put("house", nullObject);
        liuzizi.put("comment", "这是一个注释");

        System.out.println(new JSONObject(liuzizi).toString());
    }

    /**
     * 通过JavaBean，构建json对象
     */
    public static void createJsonByBean() {
        List<String> majors = new ArrayList<>();
        majors.add("理发");
        majors.add("挖掘机");
        User user = new User();
        user.setName("刘滋滋");
        user.setAge(18);
        user.setBirthday("1990-01-01");
        user.setSchool("蓝翔");
        user.setMajor(majors);
        user.setHasBoyfriend(false);
        user.setCar(null);
        user.setHouse(null);
        user.setComment("这是一个注释");
        System.out.println(new JSONObject(user));
    }
}

package com.test.utils;


import org.junit.jupiter.api.Test;

/**
 * @author jingLv
 * @date 2020/08/03
 */
class JsonPathUtilsTest {
    private static String jsonStr = "{\n" +
            "\t\"store\": {\n" +
            "\t\t\"book\": [{\n" +
            "\t\t\t\"category\": \"reference\",\n" +
            "\t\t\t\"author\": \"Nigel Rees\",\n" +
            "\t\t\t\"title\": \"Sayings of the Century\",\n" +
            "\t\t\t\"price\": 8.95\n" +
            "\t\t}, {\n" +
            "\t\t\t\"category\": \"fiction\",\n" +
            "\t\t\t\"author\": \"Evelyn Waugh\",\n" +
            "\t\t\t\"title\": \"Sword of Honour\",\n" +
            "\t\t\t\"price\": 12.99\n" +
            "\t\t}, {\n" +
            "\t\t\t\"category\": \"fiction\",\n" +
            "\t\t\t\"author\": \"Herman Melville\",\n" +
            "\t\t\t\"title\": \"Moby Dick\",\n" +
            "\t\t\t\"isbn\": \"0-553-21311-3\",\n" +
            "\t\t\t\"price\": 8.99\n" +
            "\t\t}, {\n" +
            "\t\t\t\"category\": \"fiction\",\n" +
            "\t\t\t\"author\": \"J. R. R. Tolkien\",\n" +
            "\t\t\t\"title\": \"The Lord of the Rings\",\n" +
            "\t\t\t\"isbn\": \"0-395-19395-8\",\n" +
            "\t\t\t\"price\": 22.99\n" +
            "\t\t}],\n" +
            "\t\t\"bicycle\": {\n" +
            "\t\t\t\"color\": \"red\",\n" +
            "\t\t\t\"price\": 19.95\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}\n";

    @Test
    void testReadJson() {
        //1. 获取json中store下book下的所有title值
        Object titlesForBook = new JsonPathUtils(jsonStr, "$.store.book[*].title").readJson();
        System.out.println("获取所有的title：" + titlesForBook);

        //2. 获取json中所有的title值
        Object titles = new JsonPathUtils(jsonStr, "$..title").readJson();
        System.out.println("获取所有的title：" + titles);
    }
}
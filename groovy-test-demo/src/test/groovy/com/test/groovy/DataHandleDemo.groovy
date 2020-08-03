package com.test.groovy

import org.junit.Test

/**
 *
 *
 * @author jingLv* @date 2020-08-03 5:06 下午
 *
 */
class DataHandleDemo {

    /**
     *  map 和 list 的 each 方法使用
     */
    @Test
    void testEach() {
        //数组的each方法，就是一个闭包
        println("--------List---------")
        def firstList = ["zhangsan", 1, 2, "lisi"]
        firstList.each { println(it) }

        println("--------list求和---------")

        //采用each巧妙计算数组的和
        def secondList = [1, 3, 5, 7]
        def a = 0
        secondList.each { a = a + it }
        println(a)

        println("--------Map---------")
        def map = ["name": "Tom", "age": 50]
        map.each { key, value -> println(key + "------" + value) }
    }

    /**
     * list的find方法使用
     */
    @Test
    void testFind() {
        def firstList = [1, 3, 5, 7, 9]
        def result = firstList.find { it -> it > 5 }
        println(result) //7
        result = firstList.findAll { it -> it > 5 }
        println(result) //[7, 9]
    }
}

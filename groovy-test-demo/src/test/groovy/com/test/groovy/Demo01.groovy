package com.test.groovy

import org.junit.Test

/**
 *
 *
 * @author jingLv* @date 2020-07-30 5:16 下午
 *
 */

class Demo01 {
    def add(a, b, c = 1) { //方法的参数可以不指定数据类型，参数可以指定默认值，例如C的默认值是1
        a + b + c //无retrun关键字，代码结尾处无需添加；
    }

    @Test
    void testAdd() {
        println add(2, 2)  //因为c有默认值，所有调用add方法的时候，可以不传入第三个变量值

        def c = add 1, 1, 3 //调用方法时，方法的（）可以取消掉
        println c
    }
}

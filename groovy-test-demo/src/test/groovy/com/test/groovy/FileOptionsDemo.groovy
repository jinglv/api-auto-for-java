package com.test.groovy

import com.test.file.FileService
import spock.lang.Specification

/**
 *
 *
 * @author jingLv* @date 2020-08-05 11:15 上午
 *
 */
class FileOptionsDemo extends Specification {
    FileService fileService

    def setup() {
        fileService = new FileService()
    }

    /**
     * 读取txt文件
     *
     * @return
     */
    def "create and read txt file"() {
        given: "create txt file"
        def file = fileService.createFile("./src/test/resources/test2.txt")
        when: "write some context to the file"
        //支持<<写入文件
        file << "name,age,address\n"
        file << "Tom,100,Beijing\n"
        then: "print file content"
        //读取txt文件内容
        def lines = file.readLines();
        lines.each { println it }
        and: "delete file"
        file.delete()
    }

    /**
     * 读取yml文件
     *
     * @return
     */
    def "read yml file"() {
        given: "read yml file"
        when: "get the yml data"
        def configs = fileService.getConfigs("./src/main/resources/config.yml") //这里请写入自己创建的文件路径
        then: "print data"
        println(configs.dev.db.url) //打印的值与config.yml文件中的值相同则说明成功获取到了yml文件内容
        println(configs.dev.db.user)
        println(configs.dev.db.password)
    }

    /**
     * 读取csv文件
     * @return
     */
    def "read csv file"() {
        given: "read csv file"
        when: "get the csv file data"
        def csvContent = fileService.getCsvFileContent("./src/main/resources/data.csv", ",") //这里请写入自己创建的文件路径
        then: "println the data"
        csvContent.each { it -> println(it.name + ":" + it.age + ":" + it.address) }
        //这里使用了groovy自带的处理数据集闭包each{}，打印csv文件中的所有name、age、address列内容
        //打印的值与csv文件内容一致则说明获取到了csv文件内容
    }

    def "read json file"() {
        given: ""
        when: "get json file data"
        def jsonContent = fileService.getCollectionFromJsonFile('./src/main/resources/test.json') //这里请写入自己创建的文件路径
        then: "println the data"
        println(jsonContent.pipelineName) //打印的值与json文件内容相同则说明正确获取到文件内容
        println(jsonContent.sonar.coverage)
        def stage = jsonContent.stages.find { it -> it.name == "stage2" }  //通过find方法查找json文件中stages对象下name等于“stage2”的对象
        println(stage.id)
        println(stage.duration)
    }

    def "read xml file"() {
        given: ""
        when: "read xml file"
        def xmlContent = fileService.getCollectionFromXMLFile("./src/main/resources/test.xml") //这里请写入自己创建的文件路径
        then: "println the data"
        xmlContent.person.each { println it.name + ":" + it.age }
        println xmlContent.person.find { it -> it.name == "DAVE" }.age  //通过find方法查找XML文件中name等于“DAVE”的person对象，然后获取该对象下age的值
    }
}

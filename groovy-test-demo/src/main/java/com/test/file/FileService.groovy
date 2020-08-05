package com.test.file

import com.xlson.groovycsv.CsvParser
import groovy.json.JsonSlurper
import org.yaml.snakeyaml.Yaml

/**
 *
 *
 * @author jingLv* @date 2020-08-05 11:07 上午
 *
 */
class FileService {
    JsonSlurper jsonSlurper
    XmlSlurper xmlSlurper
    CsvParser csvParser

    FileService() {
        jsonSlurper = new JsonSlurper()
        xmlSlurper = new XmlSlurper()
        csvParser = new CsvParser()
    }

    // 如果不存在则创建，如果已存在则直接返回存在的文件对象
    static def createFile(path) {
        new File(path)
    }

    //这里通过引入snackyaml包来解析yaml文件内容
    private static def yml(String text) {
        new Yaml().load(text)
    }

    // 获取yml文件
    static def getConfigs(String ymlFilePath) {
        def configs = yml(createFile(ymlFilePath).text)
    }

    // 将xml文件内容转换为groovy中数据集合
    // xmlSlurper是groovy自带的一款强大的解析xml文件的工具
    def getCollectionFromXMLFile(String xmlFilePath) {
        xmlSlurper.parse(createFile(xmlFilePath))
    }

    // 将json文件内容转换为groovy中数据集合
    // JsonSlurper是groovy自带的一款强大的解析json文件的工具
    def getCollectionFromJsonFile(String jsonFilePath) {
        jsonSlurper.parse(createFile(jsonFilePath))
    }

    // 获取csv文件内容，并支持不同类型的分隔符
    // 这里通过引入groovycsv包使用CsvParser
    def getCsvFileContent(String csvFilePath, separator) {
        csvParser.parse(new FileReader(createFile(csvFilePath)), separator: separator)
    }
}

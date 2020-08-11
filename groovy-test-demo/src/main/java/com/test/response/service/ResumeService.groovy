package com.test.response.service

import groovy.json.JsonSlurper

/**
 *
 *
 * @author jingLv* @date 2020/08/10
 *
 */
class ResumeService {
    JsonSlurper jsonSlurper

    ResumeService() {
        jsonSlurper = new JsonSlurper()
    }

    def getPersonByCountry(String res, country) {
        def resumeDetails = jsonSlurper.parseText(res) //将String类型的json字符串转换为数据对象，转换为数据对象后才能轻松获取对应的属性值
        resumeDetails.birthPlace.country == country ? resumeDetails.name : "no person"
        //获取接口response body中的contry信息，如果与传入的country一致则返回该值，否则返回“no person”
    }

    def getContactPhone(String res) {
        def resumeDetails = jsonSlurper.parseText(res)
        resumeDetails.contacts.size > 0 ? resumeDetails.contacts[0].phone : "no contact"
        //这里做了空保护，先判断contacts.size()>0再获取phone信息，在实际项目中为了保证接口测试在流水线上稳定运行，在平时编写脚本时一定要注意进行空保护，否则某些情况下可能就报空指针异常了
    }

    /**
     * 查找候选人简历中是否具备某项编程语言能力
     * @param res
     * @param language
     */
    void printIfPersonWithSpecialSkill(String res, language) {
        def resumeDetails = jsonSlurper.parseText(res)
        if (resumeDetails.skills.tech.size() > 0) {
            def programmingSkill = resumeDetails.skills.tech.find { it -> it.language == language } //使用了find方法
            println("--programmingSkill:${programmingSkill.language}--level:${programmingSkill.level}")
            //使用了groovy中“”中可以带参数的特性
        }
    }

    /**
     * 遍历打印简历中所有的项目经验信息
     * @param res
     */
    void printWorkingDetails(String res) {
        def resumeDetails = jsonSlurper.parseText(res)
        if (resumeDetails.working.workingProject.size > 0) {
            resumeDetails.working.workingProject.each { it -> println("--projectName:${it.projectName}--jobTitle:${it.jobTitle}--responseibility:${it.responsibility}") }
            // 使用each遍历，且使用了groovy中“”中可以带参数的方式打印获取到的内容
        }
    }
}

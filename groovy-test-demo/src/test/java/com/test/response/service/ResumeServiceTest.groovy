package com.test.response.service

import com.test.response.client.ResumeClient
import spock.lang.Specification

/**
 *
 *
 * @author jingLv* @date 2020/08/10
 */
class ResumeServiceTest extends Specification {
    ResumeClient resumeClient
    ResumeService resumeService

    def setup() {
        resumeClient = new ResumeClient()
        resumeService = new ResumeService()
    }

    def "get person from different country"() {
        given: "no given"
        when: "call the get resume api"
        def res = resumeClient.getResumeDetails()
        then: "println out the person name from different country"
        println(resumeService.getPersonByCountry(res, country))
        where:
        country | placeHolder
        "China" | ""
        "USA"   | ""
    }

    def "get contact from resume"() {
        given: "no given"
        when: "call the get resume api"
        def res = resumeClient.getResumeDetails()
        then: "println out contacts info"
        println(resumeService.getContactPhone(res))
    }

    def "get all working experience"() {
        given: "no given"
        when: "call the get resume api"
        def res = resumeClient.getResumeDetails()
        then: "println out contacts info"
        resumeService.printWorkingDetails(res)
    }

    def "println language skill if person with it"() {
        given: "no given"
        when: "call the get resume api"
        def res = resumeClient.getResumeDetails()
        then: "println out contacts info"
        resumeService.printIfPersonWithSpecialSkill(res, language)
        where:
        language | placeHolder
        "Java"   | ""
    }
}

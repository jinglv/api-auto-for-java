package com.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * WireMock启动
 *
 * @author jingLv
 * @date 2020/07/01
 */
public class WireMockApplication {

    /**
     * 定义mock服务的端口
     */
    private static final int PORT = 9090;

    /**
     * 定义mock服务的地址
     */
    private static final String LOCALHOST = "localhost";

    /**
     * 定义mapping文件目录
     * <p>
     * resources/mock
     */
    private static final String MOCK_DIR = "mock";

    /**
     * main方法中的内容为启动mock服务的固定写法
     *
     * @param args
     */
    public static void main(String[] args) {
        final WireMockConfiguration config = wireMockConfig()
                .port(PORT)
                .usingFilesUnderClasspath(MOCK_DIR);
        final WireMockServer wireMockServer = new WireMockServer(config);
        wireMockServer.start();
        WireMock.configureFor(LOCALHOST, PORT);
    }
}

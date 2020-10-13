package com.demo.utils;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

/**
 * @author jingLv
 * @date 2020/08/18
 */
public class OauthClientUtils {

    private OauthClientUtils() {
    }

    public static String getApiToken(String clientId, String clientSecret) throws OAuthProblemException {
        String accessToken = null;
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation("http://uaa-test.renmaitech.com/uaa/oauth/token")
                    .setGrantType(GrantType.CLIENT_CREDENTIALS)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .buildQueryMessage();

            //去服务端请求access_token，并返回响应
            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request, OAuth.HttpMethod.POST);
            //获取服务端返回过来的access_token
            accessToken = oAuthResponse.getAccessToken();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        return accessToken;
    }
}


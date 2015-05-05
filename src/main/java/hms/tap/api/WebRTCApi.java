/*
*  (C) Copyright 2010-2015 hSenid Mobile Solutions (Pvt) Limited.
*  All Rights Reserved.
*
*  These materials are unpublished, proprietary, confidential source code of
*  hSenid Mobile Solutions (Pvt) Limited and constitute a TRADE SECRET
*  of hSenid Mobile Solutions (Pvt) Limited.
*
*  hSenid Mobile Solutions (Pvt) Limited retains all title to and intellectual
*  property rights in these materials.
*/

package hms.tap.api;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

public class WebRTCApi {

    private RestTemplate restTemplate;
    private RestTemplateFixer restTemplateFixer;

    public WebRTCApi() {
        restTemplate = new RestTemplate();
        restTemplateFixer = new RestTemplateFixer();
        restTemplateFixer.setRestTemplate(restTemplate);
        restTemplateFixer.init();
    }

    public Map<String, Object> createComponent(String createComponentUrl, String appId, String appPassword,
                                               String externalRefNo, String mobileNumber) {
        ProvisionComponentRequest provisionComponentRequest = new ProvisionComponentRequest();
        provisionComponentRequest.setApplicationId(appId);
        provisionComponentRequest.setPassword(appPassword);
        provisionComponentRequest.setExternalRefNo(externalRefNo);
        provisionComponentRequest.setSubscriberId(mobileNumber);
        return restTemplate.postForObject(createComponentUrl, provisionComponentRequest, Map.class);
    }

    public Map<String, Object> requestScript(String requestScriptUrl, String appId, String appPassword, String externalRefNo) {
        final String scriptUrl =  createScriptUrl(requestScriptUrl, appId, appPassword, externalRefNo);
        return restTemplate.getForObject(scriptUrl, Map.class);
    }

    public Map<String, Object> revokeComponent(String revokeComponentUrl, String appId, String appPassword,
                                               String externalRefNo) {
        RevokeComponentRequest revokeComponentRequest = new RevokeComponentRequest();
        revokeComponentRequest.setApplicationId(appId);
        revokeComponentRequest.setPassword(appPassword);
        revokeComponentRequest.setExternalRefNo(externalRefNo);
        return restTemplate.postForObject(revokeComponentUrl, revokeComponentRequest, Map.class);
    }

    private String createScriptUrl(String requestScriptUrl, String appId, String appPassword, String adId) {
        StringBuilder scriptBuilder = new StringBuilder();
        scriptBuilder.append(requestScriptUrl).append("/app/").append(appId)
                .append("/password/").append(appPassword).append("/ref/").append(adId);
        return scriptBuilder.toString();
    }

}

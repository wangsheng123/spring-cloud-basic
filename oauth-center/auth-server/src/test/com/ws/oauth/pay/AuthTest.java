package com.ws.oauth.pay;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AuthTest {
    @Test
    public void tokenTest() {
        RestTemplate restTemplate = new RestTemplate();
        /*
         *  client_id
         *  client_secret
         * */

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("client_id", "testtwo4");
        headers.set("client_secret", "123456");
        HttpEntity httpEntity = new HttpEntity<>(null, headers);
        JSONObject body = restTemplate.postForEntity("http://127.0.0.1:8000/oauth/client/token", httpEntity, JSONObject.class).getBody();
        log.info("body:{}", body);
    }

    @Test
    public void queryByToken() {
        //token c6273e4d-fbb1-493c-9114-255dc9fad264
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();
        map.add("access_token","c6273e4d-fbb1-493c-9114-255dc9fad264");
        JSONObject body = restTemplate.postForEntity("http://127.0.0.1:8000/oauth/get/token", map, JSONObject.class).getBody();
        log.info("body:{}", body);
    }
}

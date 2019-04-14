package com.kgcorner.vachan.services.pexels;

/*
Description : Request interceptor for feign client
Author: kumar
Created on : 14/4/19
*/

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PexelRequestInterceptor implements RequestInterceptor {

    @Value("${pexel.api.key}")
    private String apiKey;
    private static final String AUTHORIZATION = "Authorization";


    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(AUTHORIZATION, apiKey);
        requestTemplate.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 " +
            "(KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
    }
}
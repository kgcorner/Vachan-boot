package com.kgcorner.vachan.services.pexels;

import feign.RequestTemplate;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;

/*
Description : <Write is class Description>
Author: kumar
Created on : 15/4/19
*/

public class PexelRequestInterceptorTest {

    private static final String MOCKED_KEY = "mocked key";
    private static final String AUTHORIZATION = "Authorization";
    @Test
    public void apply() {
        PexelRequestInterceptor interceptor = new PexelRequestInterceptor();
        try {
            MemberModifier.field(PexelRequestInterceptor.class, "apiKey").set(interceptor, MOCKED_KEY);
            RequestTemplate template = new RequestTemplate();
            interceptor.apply(template);
            Assert.assertNotNull(template.headers());
            Assert.assertNotNull(template.headers().get(AUTHORIZATION));
            Assert.assertNotNull(template.headers().get(AUTHORIZATION).equals(MOCKED_KEY));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
package com.kgcorner.vachan.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kgcorner.vachan.data.Quote;
import com.kgcorner.vachan.data.QuoteDB;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


/*
Description : Contains test for QuotesServiceImpl
Author: kumar
Created on : 14/4/19
*/
//@PrepareForTest(QuoteDB.class)
public class QuotesServiceImplTest {

    private QuotesServiceImpl service = null;
    private QuoteDB db = null;
    private static final String quotesJson = "[\n" +
        "  {\n" +
        "    \"tags\": [\n" +
        "      \"Inspiration\"\n" +
        "    ],\n" +
        "    \"author\": \"Thomas Edison\",\n" +
        "    \"quote\": \"Genius is one percent inspiration and ninety-nine percent perspiration.\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"tags\": [\n" +
        "\n" +
        "    ],\n" +
        "    \"author\": \"Yogi Berra\",\n" +
        "    \"quote\": \"You can observe a lot just by watching.\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"tags\": [\n" +
        "\n" +
        "    ],\n" +
        "    \"author\": \"Abraham Lincoln\",\n" +
        "    \"quote\": \"A house divided against itself cannot stand.\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"tags\": [\n" +
        "\n" +
        "    ],\n" +
        "    \"author\": \"Johann Wolfgang von Goethe\",\n" +
        "    \"quote\": \"Difficulties increase the nearer we get to the goal.\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"tags\": [\n" +
        "\n" +
        "    ],\n" +
        "    \"author\": \"Byron Pulsifer\",\n" +
        "    \"quote\": \"Fate is in your hands and no one elses\"\n" +
        "  }\n" +
        "]";
    private List<Quote> quotesList = null;



    @Before
    public void init() throws IllegalAccessException {
        service = new QuotesServiceImpl();
        db = PowerMockito.mock(QuoteDB.class);
        Type quotesListType = new TypeToken<List<Quote>>(){}.getType();
        quotesList = new Gson().fromJson(quotesJson, quotesListType);
        MemberModifier.field(QuotesServiceImpl.class, "db").set(service, db);
    }

    @Test
    public void getQuotes() {
        PowerMockito.when(db.getQuotes(0,20)).thenReturn(quotesList);
        List<Quote> returnedQuote = service.getQuotes(1);
        Assert.assertNotNull("Returned quote's list is null", returnedQuote);
        Assert.assertTrue("Returned quotes are not matching", returnedQuote.containsAll(quotesList));
    }

    @Test
    public void getQuotesWithTopic() {
        List<Quote> expectedResult = quotesList.subList(0,0);
        PowerMockito.when(db.getQuotesFromTopic("Inspiration",0,20))
            .thenReturn(expectedResult);
        List<Quote> returnedQuote = service.getQuotes("Inspiration", 1);
        Assert.assertNotNull("Returned quote's list is null", returnedQuote);
        Assert.assertTrue("Returned quotes are not matching", returnedQuote.containsAll(expectedResult));
    }

    @Test
    public void getQuotesWithEmptyTopic() {
        List<Quote> returnedQuote = service.getQuotes("", 1);
        Assert.assertNotNull("Returned quote's list is null", returnedQuote);
        Assert.assertEquals("Returned quotes are not matching", 0, returnedQuote.size());
    }

    @Test
    public void getQuotesWithNullTopic() {
        List<Quote> returnedQuote = service.getQuotes(null, 1);
        Assert.assertNotNull("Returned quote's list is null", returnedQuote);
        Assert.assertEquals("Returned quotes are not matching", 0, returnedQuote.size());
    }

    @Test
    public void getQuotesWithMultipleTopic() {
        List<Quote> expectedResult1 = quotesList.subList(0,0);
        List<Quote> expectedResult2 = quotesList.subList(1,1);
        PowerMockito.when(db.getQuotesFromTopic("Inspiration",0,20))
            .thenReturn(expectedResult1);
        PowerMockito.when(db.getQuotesFromTopic("Spiritual",0,20))
            .thenReturn(expectedResult2);
        List<Quote> expectedResult = new ArrayList<>();
        expectedResult.addAll(expectedResult1);
        expectedResult.addAll(expectedResult2);
        List<Quote> returnedQuote = service.getQuotes("Inspiration,Spiritual", 1);
        Assert.assertNotNull("Returned quote's list is null", returnedQuote);
        Assert.assertTrue("Returned quotes are not matching", returnedQuote.containsAll(expectedResult));
    }
}
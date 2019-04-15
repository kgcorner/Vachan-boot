package com.kgcorner.vachan.data;

/*
Description : <Write is class Description>
Author: kumar
Created on : 14/4/19
*/

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.junit4.SpringRunner;
import org.powermock.api.support.membermodification.MemberModifier;
import java.lang.reflect.Type;
import java.util.*;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class QuoteDbTest {

    private static QuoteDB quoteDB;

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



    @BeforeClass
    public static void init() throws IllegalAccessException {
        quoteDB = QuoteDB.getInstance();
        Type quotesListType = new TypeToken<List<Quote>>(){}.getType();
        List<Quote> quotesList = new Gson().fromJson(quotesJson, quotesListType);
        Map<String, List<Integer>> topicQuoteMapper = new HashMap<>();
        List<Integer> index = new ArrayList<>();
        index.add(0);
        topicQuoteMapper.put("Inspiration", index);

        MemberModifier.field(QuoteDB.class, "quotes").set(null, quotesList);
        MemberModifier.field(QuoteDB.class, "topicQuoteMapper").set(null, topicQuoteMapper);
    }

    @Test
    public void testGetQuote() throws IllegalAccessException {
        List<Quote> quotes = quoteDB.getQuotes(0,2);
        Assert.assertNotNull("Quotes are null", quotes);
        Assert.assertTrue("Quotes count are not matching", quotes.size() <= 2);
        Assert.assertEquals("Quotes ise not matching","Genius is one percent " +
            "inspiration and ninety-nine percent perspiration.", quotes.get(0).getQuote());
        Assert.assertEquals("Author ise not matching","Thomas Edison", quotes.get(0).getAuthor());
    }

    @Test
    public void testGetQuoteForBeyondLimit() throws IllegalAccessException {
        List<Quote> quotes = quoteDB.getQuotes(50,2);
        Assert.assertNotNull("Quotes are null", quotes);
        Assert.assertTrue("Quotes count are not matching", quotes.size() == 0);
    }

    @Test
    public void testGetQuoteWithNegativeOffset() throws IllegalAccessException {
        Exception thrownException = null;
        try {
            List<Quote> quotes = quoteDB.getQuotes(-1, 2);
        } catch (Exception x) {
            thrownException = x;
        }

        Assert.assertNotNull("No exception were thrown for negative offset", thrownException);
        Assert.assertTrue("Unexpected exception thrown for negative offset",
            thrownException instanceof IllegalArgumentException );
    }

    @Test
    public void testGetQuoteWithZeroMaxItem() throws IllegalAccessException {
        Exception thrownException = null;
        try {
            List<Quote> quotes = quoteDB.getQuotes(1, 0);
        } catch (Exception x) {
            thrownException = x;
        }

        Assert.assertNotNull("No exception were thrown for zero count", thrownException);
        Assert.assertTrue("Unexpected exception thrown for zero count",
            thrownException instanceof IllegalArgumentException );
    }

    @Test
    public void testGetQuotesForTopic() throws IllegalAccessException {
        Type quotesListType = new TypeToken<List<Quote>>(){}.getType();
        List<Quote> quotesList = new Gson().fromJson(quotesJson, quotesListType);
        Map<String, List<Integer>> topicQuoteMapper = new HashMap<>();
        List<Integer> index = new ArrayList<>();
        index.add(0);
        topicQuoteMapper.put("Inspiration", index);

        MemberModifier.field(QuoteDB.class, "quotes").set(null, quotesList);
        MemberModifier.field(QuoteDB.class, "topicQuoteMapper").set(null, topicQuoteMapper);
        List<Quote> quotes = quoteDB.getQuotesFromTopic("Inspirational", 0, 2);
        Assert.assertNotNull("Quotes are null", quotes);
        Assert.assertTrue("Quotes count are not matching", quotes.size() <= 2);
    }

    @Test
    public void testGetQuotesForTopicForBeyondLimit() throws IllegalAccessException {
        List<Quote> quotes = quoteDB.getQuotesFromTopic("Inspirational", 50, 2);
        Assert.assertNotNull("Quotes are null", quotes);
        Assert.assertTrue("Quotes count are not matching", quotes.size() == 0);
    }

    @Test
    public void testGetQuotesForTopicWithEmptyTopic() throws IllegalAccessException {
        Exception thrownException = null;
        try {
            List<Quote> quotes = quoteDB.getQuotesFromTopic("", 0, 2);
        } catch(Exception x) {
            thrownException = x;
        }
        Assert.assertNotNull("No exception were thrown empty topic", thrownException);
        Assert.assertTrue("Unexpected exception thrown for empty topic",
            thrownException instanceof IllegalArgumentException );
    }

    @Test
    public void testGetQuotesForTopicWithUnExpectedTopic() throws IllegalAccessException {
        List<Quote> quotes = quoteDB.getQuotesFromTopic("un-matchable", 0, 2);
        Assert.assertNotNull("Quotes are null", quotes);
        Assert.assertTrue("Quotes count are not matching for un-matching ", quotes.size() == 0);
    }

    @Test
    public void testGetQuotesForTopicWithNullTopic() throws IllegalAccessException {
        Exception thrownException = null;
        try {
            List<Quote> quotes = quoteDB.getQuotesFromTopic(null, 0, 2);
        } catch (Exception x) {
            thrownException = x;
        }

        Assert.assertNotNull("No exception were thrown null topic", thrownException);
        Assert.assertTrue("Unexpected exception thrown for null topic",
            thrownException instanceof IllegalArgumentException );
    }

    @Test
    public void testGetQuotesForTopicWithNegativeOffset() throws IllegalAccessException {
        Exception thrownException = null;
        try {
            List<Quote> quotes = quoteDB.getQuotesFromTopic("hello", -1, 2);
        } catch (Exception x) {
            thrownException = x;
        }

        Assert.assertNotNull("No exception were thrown for negative offset", thrownException);
        Assert.assertTrue("Unexpected exception thrown for negative offset",
            thrownException instanceof IllegalArgumentException );
    }

    @Test
    public void testGetQuotesForTopicWithZeroMaxItem() throws IllegalAccessException {
        Exception thrownException = null;
        try {
            List<Quote> quotes = quoteDB.getQuotesFromTopic("hello", 0, 0);
        } catch (Exception x) {
            thrownException = x;
        }
        Assert.assertNotNull("No exception were thrown for zero max item", thrownException);
        Assert.assertTrue("Unexpected exception thrown for zero max item",
            thrownException instanceof IllegalArgumentException );
    }

    @Test
    public void testPopulateQuote() {
        try {
            Whitebox.invokeMethod(QuoteDB.class, "populateQuotes");
            List<Quote> quotes = quoteDB.getQuotesFromTopic("Inspiration", 0, 5);
            Assert.assertNotNull("Quotes are null for topic inspiration", quotes);
            Assert.assertEquals("Quotes count is not matching", 1, quotes.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPopulateQuoteWithUnexpectedTopic() {
        try {
            Whitebox.invokeMethod(QuoteDB.class, "populateQuotes");
            List<Quote> quotes = quoteDB.getQuotesFromTopic("not matchable", 0, 5);
            Assert.assertNotNull("Quotes are null for topic inspiration", quotes);
            Assert.assertEquals("Quotes count is not matching",  0, quotes.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
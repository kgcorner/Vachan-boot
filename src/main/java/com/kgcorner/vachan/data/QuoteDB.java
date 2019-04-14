package com.kgcorner.vachan.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import com.kgcorner.vachan.util.Strings;
import org.apache.log4j.Logger;

public class QuoteDB {
    private static QuoteDB instance;
    private static  List<Quote> quotes;
    private static Map<String, List<Integer>> topicQuoteMapper = new HashMap<>();

    private static final Logger LOGGER = Logger.getLogger(QuoteDB.class);

    private QuoteDB(){}

    public static QuoteDB getInstance() {
        if(instance == null) {
            instance = new QuoteDB();
            populateQuotes();
        }
        return instance;
    }

    public List<Quote> getQuotes(int offset, int count) {
        if(offset <0 || count < 1)
            throw new IllegalArgumentException("Offset can't be less than 0 and Max item cant be less than 1");
        List<Quote> subQuotes = new ArrayList<>();
        if(quotes.size() < offset+count) {
            return subQuotes;
        }

        for (int i = offset; i < count + offset; i++) {
            if(quotes.size() <= i)
                break;
            subQuotes.add(quotes.get(i));
        }
        return subQuotes;
    }

    public List<Quote> getQuotesFromTopic(String topic, int offset, int maxItem) {

        if(Strings.isNullOrEmpty(topic)) {
            throw new IllegalArgumentException("Topic can't be null");
        }
        if(offset <0 || maxItem < 1)
            throw new IllegalArgumentException("Offset can't be less than 0 and Max item cant be less than 1");
        List<Integer> indexes = topicQuoteMapper.get(topic.toLowerCase());
        if(indexes == null)
            return Collections.emptyList();

        List<Quote> mappedQuotes = new ArrayList<>();
        for(Integer index : indexes) {
            if(mappedQuotes.size() <= maxItem)
                mappedQuotes.add(quotes.get(index));
            else
                break;
        }
        return mappedQuotes;
    }

    private static void populateQuotes() {
        quotes = readQuotes();
        LOGGER.debug("Quotes are loaded");
        LOGGER.debug("Indexing quotes");
        topicQuoteMapper = mapQuotesWithTopics(quotes);
        LOGGER.debug("quotes are indexed");
    }

    private static List<Quote> readQuotes() {
        try(FileReader fileReader = new FileReader(QuoteDB.class.getResource("/quotes.json").getFile())) {
            Type quoteListType = new TypeToken<List<Quote>>() {
            }.getType();
            return new Gson().fromJson(fileReader, quoteListType);
        } catch (IOException x) {
            LOGGER.error(x.getMessage(), x);
        }
        return Collections.emptyList();
    }

    private static Map<String, List<Integer>>  mapQuotesWithTopics(List<Quote> quotes) {
        if(quotes == null)
            return Collections.emptyMap();
        Map<String, List<Integer>> topicQuoteMapper = new HashMap<>();
        for (int i = 0; i < quotes.size(); i++) {
            Quote quote = quotes.get(i);
            for(String tag : quote.getTags()) {
                if(topicQuoteMapper.containsKey(tag.toLowerCase())) {
                    topicQuoteMapper.get(tag.toLowerCase()).add(i);
                } else {
                    List<Integer> indexes = new ArrayList<>();
                    indexes.add(i);
                    topicQuoteMapper.put(tag.toLowerCase(), indexes);
                }
            }
        }
        return topicQuoteMapper;
    }
}

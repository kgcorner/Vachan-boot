package com.kgcorner.vachan.vachan.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kgcorner.vachan.vachan.dataparser.ParseQuotes;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;

import org.apache.log4j.Logger;

public class QuoteDB {
    private static QuoteDB instance;
    private static  List<Quote> quotes;
    private static Map<String, List<Integer>> topicQuoteMapper = new HashMap<>();

    private static final Logger LOGGER = Logger.getLogger(QuoteDB.class);


    public static QuoteDB getInstance() {
        if(instance == null) {
            instance = new QuoteDB();
            try(FileReader fileReader = new FileReader(ParseQuotes.class.getResource("/quotes.json").getFile())) {
                Type quoteListType = new TypeToken<List<Quote>>() {}.getType();
                quotes = new Gson().fromJson(fileReader, quoteListType);
                LOGGER.debug("Quotes are loaded");
                LOGGER.debug("Indexing quotes");
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
                LOGGER.debug("quotes are indexed");
            } catch (IOException x) {
                x.printStackTrace();
            }
        }
        return instance;
    }

    public List<Quote> getQuotes(int offset, int count) {
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
        List<Integer> indexes = topicQuoteMapper.get(topic.toLowerCase());
        if(indexes == null)
            return Collections.emptyList();

        List<Quote> mappedQuotes = new ArrayList<>();
        for(Integer index : indexes) {
            mappedQuotes.add(quotes.get(index));
        }
        mappedQuotes = mappedQuotes.subList(offset, offset + maxItem);
        return mappedQuotes;
    }
}

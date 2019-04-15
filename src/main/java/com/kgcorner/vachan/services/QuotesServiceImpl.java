package com.kgcorner.vachan.services;

import com.kgcorner.vachan.data.Quote;
import com.kgcorner.vachan.data.QuoteDB;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Quotes Service provides facilities for fetching quotes in different ways
 */
@Service
public class QuotesServiceImpl implements QuotesService {

    private static final int MAX_QUOTES_PER_PAGE = 20;
    private QuoteDB db;

    QuotesServiceImpl() {
        db = QuoteDB.getInstance();
    }

    /**
     * Get Quotes for  given page
     * @param page
     * @return
     */
    @Override
    public List<Quote> getQuotes(int page) {
        int offset = (page-1) * MAX_QUOTES_PER_PAGE;
        return db.getQuotes(offset, MAX_QUOTES_PER_PAGE);
    }

    /**
     * Get quotes for given topics
     * @param topic
     * @param page
     * @return
     */
    @Override
    public List<Quote> getQuotes(String topic, int page) {
        int offset = (page-1) * MAX_QUOTES_PER_PAGE;
        if(topic == null || topic.trim().length() ==0) {
            return Collections.emptyList();
        }
        if(topic.contains(",")) {
            String[] topics = topic.split(",");
            Set<Quote> quotes = new HashSet<>();
            for(String t : topics) {
                quotes.addAll(db.getQuotesFromTopic(t, offset, MAX_QUOTES_PER_PAGE));
            }
            return new ArrayList<>(quotes);
        }
        else {
            return db.getQuotesFromTopic(topic, offset, MAX_QUOTES_PER_PAGE);
        }
    }
}

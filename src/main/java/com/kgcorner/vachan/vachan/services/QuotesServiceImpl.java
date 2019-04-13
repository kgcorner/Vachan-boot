package com.kgcorner.vachan.vachan.services;

import com.kgcorner.vachan.vachan.data.Quote;
import com.kgcorner.vachan.vachan.data.QuoteDB;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuotesServiceImpl implements QuotesService {

    private static final int MAX_QUOTES_PER_PAGE = 20;
    private QuoteDB db;
    QuotesServiceImpl() {
        db = QuoteDB.getInstance();
    }

    @Override
    public List<Quote> getQuotes(int page) {
        int offset = page * MAX_QUOTES_PER_PAGE;
        return db.getQuotes(offset, MAX_QUOTES_PER_PAGE);
    }

    @Override
    public List<Quote> getQuotes(String topic, int page) {
        int offset = page * MAX_QUOTES_PER_PAGE;
        if(topic == null || topic.trim().length() ==0) {
            Collections.emptyList();
        }
        if(topic.contains(",")) {
            String[] topics = topic.split(",");
            Set<Quote> quotes = new HashSet<Quote>();
            for(String t : topics) {
                quotes.addAll(db.getQuotesFromTopic(t, offset, MAX_QUOTES_PER_PAGE));
            }
        }
        else {
            return db.getQuotesFromTopic(topic, offset, MAX_QUOTES_PER_PAGE);
        }
        return null;
    }
}

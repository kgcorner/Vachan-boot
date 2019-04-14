package com.kgcorner.vachan.services;

import com.kgcorner.vachan.data.Quote;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuotesService {
    List<Quote> getQuotes(int page);
    List<Quote> getQuotes(String quote, int page);
}

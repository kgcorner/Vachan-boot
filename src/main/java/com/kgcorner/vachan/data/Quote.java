package com.kgcorner.vachan.data;


import java.util.List;

public class Quote {
    private String quote;
    private String author;
    private List<String> tags;

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Quote)) {
            return false;
        }
        Quote quoteObject = (Quote) obj;
        return this.quote.equalsIgnoreCase(quoteObject.quote);
    }

    @Override
    public int hashCode() {
        return quote.hashCode();
    }
}

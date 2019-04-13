package com.kgcorner.vachan.vachan.dataparser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class ParseQuotes {

    public static void main(String[] args) {
        List<QuoteWithTag> newQuotes= createQuotesWithTags(args[0]);
        String json = new Gson().toJson(newQuotes);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File("nextQuotes.json")));
            bufferedWriter.write(json, 0 , json.length());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*if(args.length == 2) {
            String path = args[0];
            String separator = args[1];
            try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line = reader.readLine();
                List<Quote> quotes = new ArrayList<>();
                while(line != null && line.trim().length() > 0) {
                    *//*String[] parts = line.split(Pattern.quote(separator));
                    if(parts.length > 2) {
                        parts = new String[2];
                        parts[0] = line.substring(0, line.lastIndexOf("-"));
                        parts[1] = line.substring(line.lastIndexOf("-")+1);
                    }
                    if(parts.length ==2) {
                        String quoteT = parts[0].length() > parts[1].length() ? parts[0] : parts[1];
                        String author = parts[0].length() < parts[1].length() ? parts[0] : parts[1];
                        Quote quote = new Quote(author,quoteT);
                        quotes.add(quote);
                    } else {
                        System.out.println(line);
                    }*//*
                    Quote quote = new Quote("Anonymous", line);
                    quotes.add(quote);
                    line = reader.readLine();
                }
                System.out.println("****************************************");
                String jsonQuote = new Gson().toJson(quotes);
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("nextQuotes.json")));
                bufferedWriter.write(jsonQuote, 0 , jsonQuote.length());
                bufferedWriter.close();
            } catch (IOException x) {
                x.printStackTrace();
            }
        }*/
    }

    private static List<QuoteWithTag> createQuotesWithTags(String tags) {
        Set<String> tagSet = new HashSet<>(Arrays.asList(tags.split(",")));
        List<QuoteWithTag> quotesWithTags = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(ParseQuotes.class.getResource("/quotes.json").getFile());
            Type quoteListType = new TypeToken<List<Quote>>(){}.getType();
            List<Quote> quotes = new Gson().fromJson(fileReader, quoteListType);
            for(Quote quote : quotes) {
                List<String> tagToInclude = new ArrayList<>();
                for(String tag : tagSet) {
                    if(quote.getQuote().toLowerCase().contains(tag.toLowerCase())) {
                        tagToInclude.add(tag);
                    }
                }
                QuoteWithTag quoteWithTag = new QuoteWithTag(quote.getAuthor(), quote.getQuote(), tagToInclude);
                quotesWithTags.add(quoteWithTag);
            }
        } catch(IOException x) {
            x.printStackTrace();
        }
        return quotesWithTags;
    }

    static class Quote {
        private String author;
        private String quote;

        public Quote(String author, String quote) {
            this.author = author;
            this.quote = quote;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }

        public String getAuthor() {
            return author;
        }

        public String getQuote() {
            return quote;
        }
    }

    static class QuoteWithTag extends Quote{
        private List<String> tags;

        public QuoteWithTag(String author, String quote, List<String> tags) {
            super(author, quote);
            this.tags = tags;
        }

        public List<String> getTags() {
            return tags;
        }
    }
}

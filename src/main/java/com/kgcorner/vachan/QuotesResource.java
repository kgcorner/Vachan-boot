package com.kgcorner.vachan;

import com.kgcorner.vachan.data.Image;
import com.kgcorner.vachan.data.Quote;
import com.kgcorner.vachan.services.QuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kgcorner.vachan.services.ImageService;
import java.util.Collections;
import java.util.List;

@RestController
public class QuotesResource {

    @Autowired
    private QuotesService service;

    @Autowired
    private ImageService imageService;


    @GetMapping("/quotes/{page}")
    public List<Quote> getQuotes(@PathVariable("page") int page) {
        if(page == 0) {
            return Collections.emptyList();
        }
        return service.getQuotes(page);
    }

    @GetMapping("/quotes")
    public List<Quote> getQuotes(@RequestParam("topic") String topic
    ,@RequestParam(value= "page", required = false, defaultValue = "0") int page) {
        if(topic == null || topic.trim().length() == 0) {
            return getQuotes(1);
        }
        return service.getQuotes(topic, page);
    }

    @GetMapping("/images")
    public List<Image> getImages(@RequestParam("topic") String topic
        , @RequestParam(value= "page", required = false, defaultValue = "0") int page) {
        if(topic == null || topic.trim().length() == 0) {
            return imageService.getImages(page);
        }
        return imageService.getImages(topic, page);
    }
}

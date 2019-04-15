package com.kgcorner.vachan.services.pexels;

import com.google.gson.annotations.SerializedName;
import com.kgcorner.vachan.data.Image;
import com.kgcorner.vachan.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class PexelsImageService implements ImageService {

    @Autowired
    private PexelsClient client;
    private static final int MAX_IMAGES_PER_PAGE = 20;
    private static final String PEXELS = "Pexels";


    @Override
    public List<Image> getImages(int page) {
        PexelsResponse response = client.getPhotos(MAX_IMAGES_PER_PAGE, page);
        List<Image> images = new ArrayList<>();
        for(Photo photo : response.getPhotos()) {
            Image image = new Image(PEXELS, photo.getPhotographer(),
                    photo.getSrc().getLarge(), photo.getPhotographerUrl());
            images.add(image);
        }
        return images;
    }

    @Override
    public List<Image> getImages(String topic, int page) {
        PexelsResponse response = client.getPhotos(topic, MAX_IMAGES_PER_PAGE, page);
        List<Image> images = new ArrayList<>();
        for(Photo photo : response.getPhotos()) {
            Image image = new Image(PEXELS, photo.getPhotographer(),
                photo.getSrc().getLarge(), photo.getPhotographerUrl());
            images.add(image);
        }
        return images;
    }

    @FeignClient(name = "data", url = "${pexel.search.uri}")
    private interface PexelsClient {

        @RequestMapping(method = RequestMethod.GET)
        PexelsResponse getPhotos(@RequestParam("query") String query,
                           @RequestParam("per_page") int perPage,
                           @RequestParam("page") int page);

        @RequestMapping(method = RequestMethod.GET)
        PexelsResponse getPhotos(@RequestParam("per_page") int perPage,
                                 @RequestParam("page") int page);
    }

    private static class PexelsResponse {
        private List<Photo> photos;

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }
    }

    private static class Photo {
        private String url;
        private String photographer;
        @SerializedName("photographer_url")
        private String photographerUrl;
        private ImageSrc src;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPhotographer() {
            return photographer;
        }

        public void setPhotographer(String photographer) {
            this.photographer = photographer;
        }

        public String getPhotographerUrl() {
            return photographerUrl;
        }

        public void setPhotographerUrl(String photographerUrl) {
            this.photographerUrl = photographerUrl;
        }

        public ImageSrc getSrc() {
            return src;
        }

        public void setSrc(ImageSrc src) {
            this.src = src;
        }
    }

    private static class ImageSrc {
        private String large;

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }
}

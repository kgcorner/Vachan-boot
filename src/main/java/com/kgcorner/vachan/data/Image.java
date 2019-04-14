package com.kgcorner.vachan.data;

public class Image {
    private final String source;
    private final String photographer;
    private final String photographerUrl;
    private final String imageUrl;

    public Image(String source, String photographer, String imageUrl, String photographerUrl) {
        this.source = source;
        this.photographer = photographer;
        this.imageUrl = imageUrl;
        this.photographerUrl = photographerUrl;
    }

    public String getSource() {
        return source;
    }

    public String getPhotographer() {
        return photographer;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public String getPhotographerUrl() {
        return photographerUrl;
    }

}

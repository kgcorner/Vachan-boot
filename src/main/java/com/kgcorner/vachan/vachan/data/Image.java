package com.kgcorner.vachan.vachan.data;

public class Image {
    private String source;
    private String photographer;
    private String photographerUrl;
    private String imageUrl;

    public Image(String source, String photographer, String imageUrl, String photographerUrl) {
        this.source = source;
        this.photographer = photographer;
        this.imageUrl = imageUrl;
        this.photographerUrl = photographerUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

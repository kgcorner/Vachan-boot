package com.kgcorner.vachan.services;

import com.kgcorner.vachan.data.Image;

import java.util.List;

public interface ImageService {
    public List<Image> getImages(int page);
    public List<Image> getImages(String topic, int page);
}

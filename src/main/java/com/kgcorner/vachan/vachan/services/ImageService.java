package com.kgcorner.vachan.vachan.services;

import com.kgcorner.vachan.vachan.data.Image;

import java.util.List;

public interface ImageService {
    public List<Image> getImages(int page);
    public List<Image> getImages(String topic, int page);
}

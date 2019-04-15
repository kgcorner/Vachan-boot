package com.kgcorner.vachan.data;

/*
Description : Represents a topic for quote
Author: kumar
Created on : 15/4/19
*/

import java.util.List;

public class Topic {
    private String name;
    private String imagePath;
    private List<String> tags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
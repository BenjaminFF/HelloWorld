package com.example.benja.helloworld.IdeaRecycler;

/**
 * Created by benja on 2018/3/13.
 */

public class IdeaItem {
    private int ImageId;

    private String title,subtitle;

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}

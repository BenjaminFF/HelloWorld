package com.example.benja.helloworld.RecyclerViews;

/**
 * Created by benja on 2018/3/13.
 */

public class IdeaItem {
    private int ImageId;

    private String title;

    public IdeaItem(int imageId, String title) {
        ImageId = imageId;
        this.title = title;
    }

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
}

package com.raysmond.blog.models.support;

/**
 * Created by Raysmond on 9/26/15.
 */
public enum PostType {
    PAGE("Page"),
    POST("Post");

    private String name;

    PostType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

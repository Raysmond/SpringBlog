package com.raysmond.blog.models.support;

/**
 * @author Raysmond
 */
public enum PostStatus {
    DRAFT("Draft"),
    PUBLISHED("Published");

    private String name;

    PostStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return name();
    }

    @Override
    public String toString() {
        return getName();
    }
}

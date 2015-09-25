package com.raysmond.blog.models.support;

/**
 * Created by Raysmond on 9/25/15.
 */
public enum PostFormat {
    HTML("Html", "html"),
    MARKDOWN("Markdown","markdown");

    private String displayName;
    private String value;

    PostFormat(String displayName, String value){
        this.displayName = displayName;
        this.value = value;
    }

    public String getDisplayName(){
        return displayName;
    }

    public String getValue(){
        return value;
    }

    public String getId() {
        return name();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
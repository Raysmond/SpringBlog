package com.raysmond.blog.models;

import com.raysmond.blog.forms.PostForm;
import com.raysmond.blog.models.support.PostFormat;
import com.raysmond.blog.models.support.PostStatus;
import com.raysmond.blog.models.support.PostType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Raysmond on 9/25/15.
 */
@Entity
@Table(name = "posts")
public class Post extends BaseModel{
    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String title;

    @Type(type="text")
    private String content;

    @Type(type = "text")
    private String renderedContent;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostFormat postFormat;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Column(nullable = false)
    private Date createdAt = new Date();

    @PrePersist
    public void beforeCreate(){
        if (postStatus == null)
            postStatus = PostStatus.DRAFT;

        if (postFormat == null)
            postFormat = PostFormat.HTML;

        if (createdAt == null)
            createdAt = new Date();

        if (postType == null)
            postType = PostType.POST;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    public PostFormat getPostFormat() {
        return postFormat;
    }

    public void setPostFormat(PostFormat postFormat) {
        this.postFormat = postFormat;
    }

    public String getRenderedContent() {
        if (this.postFormat == PostFormat.MARKDOWN)
            return renderedContent;

        return getContent();
    }

    public void setRenderedContent(String renderedContent) {
        this.renderedContent = renderedContent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }
}

package com.raysmond.blog.forms;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.support.PostFormat;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Raysmond on 9/25/15.
 */
public class PostForm {
    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotNull
    private PostFormat postFormat;

    public PostForm(){

    }

    public PostForm(Post post){
        title = post.getTitle();
        content = post.getContent();
        postFormat = post.getPostFormat();
    }

    public Post toPost(){
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setPostFormat(postFormat);
        return post;
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

    public PostFormat getPostFormat() {
        return postFormat;
    }

    public void setPostFormat(PostFormat postFormat) {
        this.postFormat = postFormat;
    }
}

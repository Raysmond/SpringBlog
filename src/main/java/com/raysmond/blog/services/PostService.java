package com.raysmond.blog.services;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.User;
import com.raysmond.blog.models.support.PostFormat;
import com.raysmond.blog.models.support.PostStatus;
import com.raysmond.blog.models.support.PostType;
import com.raysmond.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Raysmond on 9/26/15.
 */
@Service
public class PostService {
    @Autowired
    private PostRepository posts;

    @Autowired
    private MarkdownService markdown;

    public Post createPage(User user, String title, String content, PostFormat format, PostStatus status){
        return _createPost(user,title,content,format, status, PostType.PAGE);
    }

    public Post createPost(User user, String title, String content, PostFormat format, PostStatus status){
        return _createPost(user,title,content,format, status, PostType.POST);
    }

    public Post _createPost(User user, String title, String content, PostFormat format, PostStatus status, PostType type){
        Post post = new Post();
        post.setUser(user);
        post.setTitle(title);
        post.setContent(content);
        post.setPostFormat(format);
        post.setPostStatus(status);
        post.setPostType(type);

        return createPost(post);
    }

    public Post createPost(Post post){
        if (post.getPostFormat() == PostFormat.MARKDOWN){
            post.setRenderedContent(markdown.renderToHtml(post.getContent()));
        }
        return posts.save(post);
    }

    public Post updatePost(Post post){
        if (post.getPostFormat() == PostFormat.MARKDOWN){
            post.setRenderedContent(markdown.renderToHtml(post.getContent()));
        }
        return posts.save(post);
    }
}

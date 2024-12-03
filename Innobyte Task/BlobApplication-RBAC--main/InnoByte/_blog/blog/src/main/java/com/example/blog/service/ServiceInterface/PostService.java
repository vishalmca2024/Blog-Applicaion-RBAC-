package com.example.blog.service.ServiceInterface;
import com.example.blog.entity.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post, String username);
    List<Post> getAllPosts();
    Post getPostById(Long id);
    Post updatePost(Long id, Post post, String username);
    void deletePost(Long id, String username);
}


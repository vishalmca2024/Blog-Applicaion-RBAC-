package com.example.blog;

import com.example.blog.entity.Post;
import com.example.blog.reporsitry.PostRepository;
import com.example.blog.service.PostServiceImpl;
import com.example.blog.service.ServiceInterface.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PostServiceTest {

    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = postServiceImpl;
    }

    @Test
    void testCreatePost() {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("Test Content");

        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post createdPost = postService.createPost(post, "testuser");

        assertEquals("Test Post", createdPost.getTitle());
        assertEquals("Test Content", createdPost.getContent());

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testGetPostById() {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("Test Content");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Post foundPost = postService.getPostById(1L);

        assertNotNull(foundPost);
        assertEquals("Test Post", foundPost.getTitle());

        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdatePost() {
        Post existingPost = new Post();
        existingPost.setTitle("Old Title");
        existingPost.setContent("Old Content");

        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setContent("Updated Content");

        when(postRepository.findById(1L)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        Post result = postService.updatePost(1L, updatedPost, "testuser");

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Content", result.getContent());

        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(any(Post.class));
    }
}

package com.example.blog;

import com.example.blog.controller.PostController;
import com.example.blog.entity.Post;
import com.example.blog.service.ServiceInterface.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void testCreatePost() throws Exception {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("Test Content");

        when(postService.createPost(any(Post.class), anyString())).thenReturn(post);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Post\",\"content\":\"Test Content\"}")
                        .param("username", "testuser"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Post"))
                .andExpect(jsonPath("$.content").value("Test Content"));

        verify(postService, times(1)).createPost(any(Post.class), anyString());
    }

    @Test
    void testGetPostById() throws Exception {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("Test Content");

        when(postService.getPostById(1L)).thenReturn(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Post"))
                .andExpect(jsonPath("$.content").value("Test Content"));

        verify(postService, times(1)).getPostById(1L);
    }
}

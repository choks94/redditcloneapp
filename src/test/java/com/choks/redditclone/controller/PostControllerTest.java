package com.choks.redditclone.controller;

import com.choks.redditclone.dto.PostResponse;
import com.choks.redditclone.security.JwtProvider;
import com.choks.redditclone.service.PostService;
import com.choks.redditclone.service.UserDetailsServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {

    @MockBean
    private PostService postService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtProvider jwtProvider;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should list all Posts when making GET request to endpoint - /api/posts")
    public void shouldListAllPosts() throws Exception {

        PostResponse postResponse1 = new PostResponse(1L, "Post Name", "http://url.site",
                "Description", "User 1", "Subreddit Name", 0, 0,
                "1 day ago", false, false);
        PostResponse postResponse2 = new PostResponse(2L, "Post Name 2", "http://url.site2",
                "Description 2", "User 2", "Subreddit Name 2", 0,
                0, "2 days ago", false, false);

        Mockito.when(postService.getAllPosts()).thenReturn(Arrays.asList(postResponse1, postResponse2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].postName", Matchers.is("Post Name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url", Matchers.is("http://url.site")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].postName", Matchers.is("Post Name 2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].url", Matchers.is("http://url.site2")));
    }

}
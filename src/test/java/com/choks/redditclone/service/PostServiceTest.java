package com.choks.redditclone.service;

import com.choks.redditclone.dto.PostRequest;
import com.choks.redditclone.dto.PostResponse;
import com.choks.redditclone.mapper.PostMapper;
import com.choks.redditclone.model.Post;
import com.choks.redditclone.model.Subreddit;
import com.choks.redditclone.model.User;
import com.choks.redditclone.repository.PostRepository;
import com.choks.redditclone.repository.SubredditRepository;
import com.choks.redditclone.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static java.util.Collections.emptyList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private SubredditRepository subredditRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthService authService;
    @Mock
    private PostMapper postMapper;
    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    private PostService postService;
    @BeforeEach
    public void setup() {
        postService = new PostService(subredditRepository, postRepository, authService, postMapper, userRepository);
    }

    @Test
    @DisplayName("Should find post by id")
    void shouldFindPostById() {
        Post post = new Post(123L, "First Post", "http://url.site/", "Test", 0, null, Instant.now(), null);
        PostResponse expectedPostResponse = new PostResponse(123L, "First Post", "http://url.site/",
                "Test", "Test User", "Test Subreddit", 0, 0,
                "1 Hour Ago", false, false);
        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(expectedPostResponse);

        PostResponse actualPostResponse = postService.getPost(123L);

        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
        Assertions.assertThat(actualPostResponse.getPostName()).isEqualTo(expectedPostResponse.getPostName());
    }

    @Test
    @DisplayName("Should save posts")
    public void shouldSavePosts() {
        User currentUser = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
        Subreddit subreddit = new Subreddit(123L, "First Subreddit", "Subreddit Description", emptyList(), Instant.now(), currentUser);
        Post post = new Post(123L, "First Post", "http://url.site/", "Test", 0, null, Instant.now(), null);
        PostRequest postRequest = new PostRequest(null, "First Subreddit", "First Post", "http://url.site", "Test");

        Mockito.when(subredditRepository.findByName("First Subreddit")).thenReturn(Optional.of(subreddit));
        Mockito.when(postMapper.map(postRequest, subreddit, currentUser)).thenReturn(post);
        Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);
        postService.save(postRequest);

        Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

        Assertions.assertThat(postArgumentCaptor.getValue().getPostId()).isEqualTo(123L);
        Assertions.assertThat(postArgumentCaptor.getValue().getPostName()).isEqualTo("First Post");
    }

}
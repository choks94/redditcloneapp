package com.choks.redditclone.repository;

import com.choks.redditclone.BaseTest;
import com.choks.redditclone.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest extends BaseTest {


    @Autowired
    private PostRepository postRepository;

    @Test
    public void shouldSavePost() {
        Post expectedPostObject = new Post(123L, "First Post", "http://url.site/", "Test", 0, null, Instant.now(), null);
        Post actualPostObject = postRepository.save(expectedPostObject);

        assertThat(actualPostObject).usingRecursiveComparison().ignoringFields("postId").isEqualTo(expectedPostObject);
    }
}

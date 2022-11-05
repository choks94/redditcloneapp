package com.choks.redditclone.repository;

import com.choks.redditclone.BaseTest;
import com.choks.redditclone.model.User;
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
public class UserRepositoryTest extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSavePost() {
        User expectedUserObject = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
        User actualUserObject = userRepository.save(expectedUserObject);
        assertThat(actualUserObject).usingRecursiveComparison().ignoringFields("userId").isEqualTo(expectedUserObject);
    }
}

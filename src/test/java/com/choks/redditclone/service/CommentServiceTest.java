package com.choks.redditclone.service;

import com.choks.redditclone.exceptions.SpringRedditException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CommentServiceTest {

    @Test
    @DisplayName("Test Should Not Pass when comment do not contains swear words")
    void shouldNotContainSwearWordsInsideComment() {
        CommentService commentService = new CommentService(null, null, null, null, null, null, null);
//        Assertions.assertFalse(commentService.containsSwearWords("This is a clean comment"));
        assertThat(commentService.containsSwearWords("This is a comment")).isFalse();
    }


    @Test
    @DisplayName("Should Throw Exception when Exception Contains Swear Words")
    public void shouldFailWhenCommentContainsSwearWords() {
        CommentService commentService = new CommentService(null, null, null, null, null, null, null);
//        SpringRedditException exception = assertThrows(SpringRedditException.class, () -> {
//            commentService.containsSwearWords("This is shitty comment");
//        });
//        assertTrue(exception.getMessage().contains("Comments contains unacceptable language"));

        assertThatThrownBy(() -> {
            commentService.containsSwearWords("This is a shitty comment");
        }).isInstanceOf(SpringRedditException.class)
                .hasMessage("Comments contains unacceptable language");
    }
}
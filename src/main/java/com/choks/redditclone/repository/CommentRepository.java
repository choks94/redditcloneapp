package com.choks.redditclone.repository;

import com.choks.redditclone.dto.CommentsDto;
import com.choks.redditclone.model.Comment;
import com.choks.redditclone.model.Post;
import com.choks.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}

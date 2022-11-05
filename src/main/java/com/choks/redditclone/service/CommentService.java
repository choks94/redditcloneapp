package com.choks.redditclone.service;

import com.choks.redditclone.dto.CommentsDto;
import com.choks.redditclone.exceptions.PostNotFoundException;
import com.choks.redditclone.exceptions.SpringRedditException;
import com.choks.redditclone.mapper.CommentMapper;
import com.choks.redditclone.model.Comment;
import com.choks.redditclone.model.NotificationEmail;
import com.choks.redditclone.model.Post;
import com.choks.redditclone.model.User;
import com.choks.redditclone.repository.CommentRepository;
import com.choks.redditclone.repository.PostRepository;
import com.choks.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    @Transactional
    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
            .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);
        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    @Transactional
    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));

        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException(userName));

        return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new SpringRedditException("Comments contains unacceptable language");
        }
        return false;
    }
}

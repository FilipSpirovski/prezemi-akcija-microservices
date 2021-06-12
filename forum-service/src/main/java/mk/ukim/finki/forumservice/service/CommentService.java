package mk.ukim.finki.forumservice.service;

import mk.ukim.finki.forumservice.model.Comment;
import mk.ukim.finki.forumservice.model.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<Comment> findAll();

    List<Comment> findAllByForumId(Long forumId);

    List<Comment> findAllBySubmitterEmail(String submitterEmail);

    Comment findById(Long commentId);

    Comment createComment(String submitterEmail, CommentDto commentDto);

    Comment editComment(Long commentId, CommentDto commentDto);

    Comment likeComment(Long commentId);

    Comment dislikeComment(Long commentId);

    boolean deleteComment(Long commentId);
}

package mk.ukim.finki.forumservice.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.forumservice.model.Comment;
import mk.ukim.finki.forumservice.model.Forum;
import mk.ukim.finki.forumservice.model.dto.CommentDto;
import mk.ukim.finki.forumservice.model.exception.CommentNotFound;
import mk.ukim.finki.forumservice.model.exception.ForumNotFound;
import mk.ukim.finki.forumservice.repository.CommentRepository;
import mk.ukim.finki.forumservice.repository.ForumRepository;
import mk.ukim.finki.forumservice.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ForumRepository forumRepository;
    private final Validator validator;

    @Override
    public List<Comment> findAll() {
        return this.commentRepository.findAll();
    }

    @Override
    public List<Comment> findAllByForumId(Long forumId) {
        return this.commentRepository.findAllByForumId(forumId);
    }

    @Override
    public List<Comment> findAllBySubmitterEmail(String submitterEmail) {
        return this.commentRepository.findAllBySubmitterEmail(submitterEmail);
    }

    @Override
    public Comment findById(Long commentId) {
        return this.commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFound(commentId));
    }

    @Override
    public Comment createComment(CommentDto commentDto, Authentication authentication) throws ConstraintViolationException {
        this.checkDtoForViolations(commentDto);
        Forum existingForum = this.forumRepository.findById(commentDto.getForumId())
                .orElseThrow(() -> new ForumNotFound(commentDto.getForumId()));
        String submitterEmail = (String) authentication.getPrincipal();
        Comment newComment = new Comment(existingForum, submitterEmail, commentDto.getText());

        return this.commentRepository.save(newComment);
    }

    @Override
    public Comment editComment(Long commentId, CommentDto commentDto) throws ConstraintViolationException, CommentNotFound {
        this.checkDtoForViolations(commentDto);
        Comment existingComment = this.findById(commentId);

        existingComment.update(commentDto);

        return this.commentRepository.save(existingComment);
    }

    @Override
    public Comment likeComment(Long commentId) throws CommentNotFound {
        Comment existingComment = this.findById(commentId);

        existingComment.like();

        return this.commentRepository.save(existingComment);
    }

    @Override
    public Comment dislikeComment(Long commentId) throws CommentNotFound {
        Comment existingComment = this.findById(commentId);

        existingComment.dislike();

        return this.commentRepository.save(existingComment);
    }

    @Override
    public boolean deleteComment(Long commentId) throws CommentNotFound {
        Comment existingComment = this.findById(commentId);

        this.commentRepository.delete(existingComment);
        try {
            existingComment = this.findById(commentId);

            return false;
        } catch (CommentNotFound e) {
            return true;
        }
    }

    private void checkDtoForViolations(CommentDto commentDto) {
        var constraintViolations = this.validator.validate(commentDto);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The provided 'Comment' object is not valid.", constraintViolations);
        }
    }
}

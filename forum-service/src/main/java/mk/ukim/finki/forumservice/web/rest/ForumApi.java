package mk.ukim.finki.forumservice.web.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.forumservice.model.Comment;
import mk.ukim.finki.forumservice.model.Forum;
import mk.ukim.finki.forumservice.model.dto.CommentDto;
import mk.ukim.finki.forumservice.model.exception.*;
import mk.ukim.finki.forumservice.service.CommentService;
import mk.ukim.finki.forumservice.service.ForumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/forum")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class ForumApi {

    private final ForumService forumService;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Forum>> getAllForums() {
        List<Forum> forums = this.forumService.findAll();

        return ResponseEntity.ok().body(forums);
    }

    @GetMapping("/{id}")
    public ResponseEntity getForumDetails(@PathVariable Long id) {
        try {
            Forum forum = this.forumService.findById(id);

            return ResponseEntity.status(HttpStatus.FOUND).body(forum);
        } catch (ForumNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/for-initiative/{initiativeId}")
    public ResponseEntity getForumForInitiative(@PathVariable Long initiativeId) {
        try {
            Forum forum = this.forumService.findByInitiative(initiativeId);

            return ResponseEntity.status(HttpStatus.FOUND).body(forum);
        } catch (ForumForInitiativeNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/new/{initiativeId}")
    public ResponseEntity addNewForum(@PathVariable Long initiativeId) {
        try {
            Forum forum = this.forumService.createForum(initiativeId);

            return ResponseEntity.status(HttpStatus.CREATED).body(forum);
        } catch (ForumForInitiativeAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteExistingForum(@PathVariable Long id) {
        try {
            boolean result = this.forumService.deleteForum(id);

            if (result) {
                String message = String.format("The forum with the provided id (%d) was successfully deleted.", id);

                return ResponseEntity.ok().body(message);
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (ForumNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = this.commentService.findAll();

        return ResponseEntity.ok().body(comments);
    }

    @GetMapping("/comments/for-forum/{forumId}")
    public ResponseEntity<List<Comment>> getCommentsForForum(@PathVariable Long forumId) {
        List<Comment> comments = this.commentService.findAllByForumId(forumId);

        return ResponseEntity.ok().body(comments);
    }

    @GetMapping("/comments/submitted-by")
    public ResponseEntity<List<Comment>> getCommentsSubmittedBy(@RequestBody String submitterEmail) {
        List<Comment> comments = this.commentService.findAllBySubmitterEmail(submitterEmail);

        return ResponseEntity.ok().body(comments);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity getCommentDetails(@PathVariable Long id) {
        try {
            Comment comment = this.commentService.findById(id);

            return ResponseEntity.status(HttpStatus.FOUND).body(comment);
        } catch (CommentNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/comments/new")
    public ResponseEntity addNewComment(@RequestBody CommentDto commentDto, Authentication authentication) {
        try {
            Comment comment = this.commentService.createComment(commentDto, authentication);

            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ForumNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/comments/{id}/edit")
    public ResponseEntity updateExistingComment(@PathVariable Long id,
                                                @RequestBody CommentDto commentDto) {

        try {
            Comment comment = this.commentService.editComment(id, commentDto);

            return ResponseEntity.ok().body(comment);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (CommentNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/comments/{id}/like")
    public ResponseEntity likeExistingComment(@PathVariable Long id) {

        try {
            Comment comment = this.commentService.likeComment(id);

            return ResponseEntity.ok().body(comment);
        } catch (CommentNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/comments/{id}/dislike")
    public ResponseEntity dislikeExistingComment(@PathVariable Long id) {

        try {
            Comment comment = this.commentService.dislikeComment(id);

            return ResponseEntity.ok().body(comment);
        } catch (CommentNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/comments/{id}/delete")
    public ResponseEntity deleteExistingComment(@PathVariable Long id) {
        try {
            boolean result = this.commentService.deleteComment(id);

            if (result) {
                String message = String.format("The comment with the provided id (%d) was successfully deleted.", id);

                return ResponseEntity.ok().body(message);
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (CommentNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

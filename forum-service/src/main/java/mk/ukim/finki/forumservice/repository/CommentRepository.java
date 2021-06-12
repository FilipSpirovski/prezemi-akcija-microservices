package mk.ukim.finki.forumservice.repository;

import mk.ukim.finki.forumservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByForumId(Long forumId);

    List<Comment> findAllBySubmitterEmail(String submitterEmail);
}

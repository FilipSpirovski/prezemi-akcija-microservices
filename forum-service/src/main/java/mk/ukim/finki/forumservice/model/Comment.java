package mk.ukim.finki.forumservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.forumservice.model.dto.CommentDto;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Forum forum;

    private String submitterEmail;

    private String text;

    private int likes;

    private int dislikes;

    public Comment(Forum forum, String submitterEmail, String text) {
        this.forum = forum;
        this.submitterEmail = submitterEmail;
        this.text = text;
        this.likes = 0;
        this.dislikes = 0;
    }

    public void update(CommentDto commentDto) {
        this.text = commentDto.getText();
    }

    public void like() {
        this.likes += 1;
    }

    public void dislike() {
        this.likes -= 1;
    }
}

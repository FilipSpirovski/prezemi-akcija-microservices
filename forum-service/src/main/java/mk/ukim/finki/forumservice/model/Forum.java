package mk.ukim.finki.forumservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "forums")
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long initiativeId;

    @OneToMany(mappedBy = "forum")
    private List<Comment> comments;

    public Forum(Long initiativeId) {
        this.initiativeId = initiativeId;
    }
}

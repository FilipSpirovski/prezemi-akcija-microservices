package mk.ukim.finki.forumservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "forums")
public class Forum implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long initiativeId;

    @OneToMany(mappedBy = "forum")
    @JsonManagedReference
    private List<Comment> comments;

    public Forum(Long initiativeId) {
        this.initiativeId = initiativeId;
    }
}

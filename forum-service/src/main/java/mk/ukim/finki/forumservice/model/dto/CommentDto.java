package mk.ukim.finki.forumservice.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {

    @NotNull
    private Long forumId;

    @NotNull
    @NotEmpty
    private String text;
}

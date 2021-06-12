package mk.ukim.finki.initiativesservice.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class InitiativeDto {

    @NotNull
    @NotEmpty
    private String categoryName;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    private String scheduledFor;

    @NotNull
    @NotEmpty
    private String eventTypeName;

    @NotNull
    @NotEmpty
    private String location;
}

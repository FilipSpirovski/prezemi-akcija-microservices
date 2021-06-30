package mk.ukim.finki.initiativesservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.initiativesservice.model.dto.InitiativeDto;
import mk.ukim.finki.initiativesservice.model.utility.Utilities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "initiatives")
public class Initiative implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String initiatorEmail;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private String title;

    private String description;

    private LocalDateTime scheduledFor;

    @Enumerated(value = EnumType.STRING)
    private EventType eventType;

    private String location;

    @
            ElementCollection()
    private List<String> participantEmails;

    public Initiative(String initiatorEmail, InitiativeDto initiativeDto) {
        this.initiatorEmail = initiatorEmail;
        this.category = Category.valueOf(initiativeDto.getCategoryName());
        this.title = initiativeDto.getTitle();
        this.description = initiativeDto.getDescription();
        this.scheduledFor = Utilities.convertFromStringToDateAndTime(initiativeDto.getScheduledFor());
        this.eventType = EventType.valueOf(initiativeDto.getEventTypeName());
        this.location = initiativeDto.getLocation();
    }

    public void update(InitiativeDto initiativeDto) {
        this.category = Category.valueOf(initiativeDto.getCategoryName());
        this.title = initiativeDto.getTitle();
        this.description = initiativeDto.getDescription();
        this.scheduledFor = Utilities.convertFromStringToDateAndTime(initiativeDto.getScheduledFor());
        this.eventType = EventType.valueOf(initiativeDto.getEventTypeName());
        this.location = initiativeDto.getLocation();
    }
}

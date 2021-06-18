package mk.ukim.finki.initiativesservice.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.initiativesservice.model.Initiative;
import mk.ukim.finki.initiativesservice.model.dto.InitiativeDto;
import mk.ukim.finki.initiativesservice.model.exception.InitiativeNotFound;
import mk.ukim.finki.initiativesservice.model.exception.InvalidCategoryName;
import mk.ukim.finki.initiativesservice.model.exception.InvalidEventTypeName;
import mk.ukim.finki.initiativesservice.model.exception.ParticipantNotFound;
import mk.ukim.finki.initiativesservice.service.InitiativeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/initiatives")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class InitiativeApi {

    private final InitiativeService initiativeService;

    @GetMapping
    public ResponseEntity<List<Initiative>> getAllInitiatives() {
        List<Initiative> initiatives = this.initiativeService.findAll();

        return ResponseEntity.ok().body(initiatives);
    }

    @GetMapping("/initiated-by")
    public ResponseEntity<List<Initiative>> getInitiativesInitiatedBy(@RequestParam String initiatorEmail) {
        List<Initiative> initiatives = this.initiativeService.findAllByInitiatorEmail(initiatorEmail);

        return ResponseEntity.ok().body(initiatives);
    }

    @GetMapping("/{category}")
    public ResponseEntity getInitiativesWithCategory(@PathVariable String category) {

        try {
            List<Initiative> initiatives = this.initiativeService.findAllByCategory(category);

            return ResponseEntity.ok().body(initiatives);
        } catch (InvalidCategoryName e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{eventType}")
    public ResponseEntity getInitiativesWithEventType(@PathVariable String eventType) {

        try {
            List<Initiative> initiatives = this.initiativeService.findAllByEventType(eventType);

            return ResponseEntity.ok().body(initiatives);
        } catch (InvalidEventTypeName e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getInitiativeDetails(@PathVariable Long id) {

        try {
            Initiative initiative = this.initiativeService.findById(id);

            return ResponseEntity.status(HttpStatus.FOUND).body(initiative);
        } catch (InitiativeNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/new")
    public ResponseEntity addNewInitiative(@RequestParam String initiatorEmail,
                                           @RequestParam InitiativeDto initiativeDto) {
        try {
            Initiative initiative = this.initiativeService.createInitiative(initiatorEmail, initiativeDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(initiative);
        } catch (ConstraintViolationException | InvalidCategoryName | InvalidEventTypeName e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity updateExistingInitiative(@PathVariable Long id,
                                                   @RequestParam InitiativeDto initiativeDto) {
        try {
            Initiative initiative = this.initiativeService.editInitiative(id, initiativeDto);

            return ResponseEntity.ok().body(initiative);
        } catch (ConstraintViolationException | InvalidCategoryName | InvalidEventTypeName e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InitiativeNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/add-participant")
    public ResponseEntity addParticipantToExistingInitiative(@PathVariable Long id,
                                                             @RequestParam String userEmail) {
        try {
            Initiative initiative = this.initiativeService.addParticipantToInitiative(userEmail, id);

            return ResponseEntity.ok().body(initiative);
        } catch (InitiativeNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/remove-participant")
    public ResponseEntity removeParticipantFromExistingInitiative(@PathVariable Long id,
                                                                  @RequestParam String userEmail) {
        try {
            Initiative initiative = this.initiativeService.removeParticipantFromInitiative(userEmail, id);

            return ResponseEntity.ok().body(initiative);
        } catch (InitiativeNotFound | ParticipantNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteExistingInitiative(@PathVariable Long id) {
        try {
            boolean result = this.initiativeService.deleteInitiative(id);

            if (result) {
                String message = String.format("The initiative with the provided id (%d) was successfully deleted.", id);

                return ResponseEntity.ok().body(message);
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (InitiativeNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

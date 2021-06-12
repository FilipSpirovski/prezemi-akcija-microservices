package mk.ukim.finki.initiativesservice.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.initiativesservice.model.Category;
import mk.ukim.finki.initiativesservice.model.EventType;
import mk.ukim.finki.initiativesservice.model.Initiative;
import mk.ukim.finki.initiativesservice.model.dto.InitiativeDto;
import mk.ukim.finki.initiativesservice.model.exception.InitiativeNotFound;
import mk.ukim.finki.initiativesservice.model.exception.InvalidCategoryName;
import mk.ukim.finki.initiativesservice.model.exception.InvalidEventTypeName;
import mk.ukim.finki.initiativesservice.model.exception.ParticipantNotFound;
import mk.ukim.finki.initiativesservice.repository.InitiativeRepository;
import mk.ukim.finki.initiativesservice.service.InitiativeService;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;

@Service
@AllArgsConstructor
public class InitiativeServiceImpl implements InitiativeService {

    private final InitiativeRepository initiativeRepository;
    private final Validator validator;

    @Override
    public List<Initiative> findAll() {
        return this.initiativeRepository.findAll();
    }

    @Override
    public List<Initiative> findAllByInitiatorEmail(String initiatorEmail) {
        return this.initiativeRepository.findAllByInitiatorEmail(initiatorEmail);
    }

    @Override
    public List<Initiative> findAllByCategory(String categoryName) throws InvalidCategoryName {
        Category category = this.validateCategoryName(categoryName);

        return this.initiativeRepository.findAllByCategory(category);
    }

    @Override
    public List<Initiative> findAllByEventType(String eventTypeName) throws InvalidEventTypeName {
        EventType eventType = this.validateEventTypeName(eventTypeName);

        return this.initiativeRepository.findAllByEventType(eventType);
    }

    @Override
    public Initiative findById(Long initiativeId) {
        return this.initiativeRepository.findById(initiativeId)
                .orElseThrow(() -> new InitiativeNotFound(initiativeId));
    }

    @Override
    public Initiative createInitiative(String initiatorEmail, InitiativeDto initiativeDto) throws
            ConstraintViolationException, InvalidCategoryName, InvalidEventTypeName {
        this.checkDtoForViolations(initiativeDto);
        this.validateCategoryName(initiativeDto.getCategoryName());
        this.validateEventTypeName(initiativeDto.getEventTypeName());
        Initiative newInitiative = new Initiative(initiatorEmail, initiativeDto);

        return this.initiativeRepository.save(newInitiative);
    }

    @Override
    public Initiative editInitiative(Long initiativeId, InitiativeDto initiativeDto) throws
            ConstraintViolationException, InvalidCategoryName, InvalidEventTypeName, InitiativeNotFound {
        this.checkDtoForViolations(initiativeDto);
        this.validateCategoryName(initiativeDto.getCategoryName());
        this.validateEventTypeName(initiativeDto.getEventTypeName());
        Initiative existingInitiative = this.findById(initiativeId);

        existingInitiative.update(initiativeDto);

        return this.initiativeRepository.save(existingInitiative);
    }

    @Override
    public Initiative addParticipantToInitiative(String participantEmail, Long initiativeId) throws InitiativeNotFound {
        Initiative existingInitiative = this.findById(initiativeId);

        existingInitiative.getParticipantEmails()
                .add(participantEmail);

        return this.initiativeRepository.save(existingInitiative);
    }

    @Override
    public Initiative removeParticipantFromInitiative(String participantEmail, Long initiativeId) throws InitiativeNotFound {
        Initiative existingInitiative = this.findById(initiativeId);

        boolean result = existingInitiative.getParticipantEmails()
                .remove(participantEmail);

        if (result) {
            return this.initiativeRepository.save(existingInitiative);
        } else {
            throw new ParticipantNotFound(participantEmail, existingInitiative.getTitle());
        }
    }

    @Override
    public boolean deleteInitiative(Long initiativeId) throws InitiativeNotFound {
        Initiative existingInitiative = this.findById(initiativeId);

        this.initiativeRepository.delete(existingInitiative);
        try {
            existingInitiative = this.findById(initiativeId);

            return false;
        } catch (InitiativeNotFound e) {
            return true;
        }
    }

    private Category validateCategoryName(String categoryName) {
        boolean validCategoryName = false;

        for (Category category : Category.values()) {
            if (category.toString().equals(categoryName)) {
                validCategoryName = true;
                break;
            }
        }

        if (validCategoryName) {
            return Category.valueOf(categoryName);
        } else {
            throw new InvalidCategoryName(categoryName);
        }
    }

    private EventType validateEventTypeName(String eventTypeName) {
        boolean validEventTypeName = false;

        for (EventType eventType : EventType.values()) {
            if (eventType.toString().equals(eventTypeName)) {
                validEventTypeName = true;
                break;
            }
        }

        if (validEventTypeName) {
            return EventType.valueOf(eventTypeName);
        } else {
            throw new InvalidEventTypeName(eventTypeName);
        }
    }

    private void checkDtoForViolations(InitiativeDto initiativeDto) {
        var constraintViolations = this.validator.validate(initiativeDto);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The provided 'Initiative' object is not valid.", constraintViolations);
        }
    }
}

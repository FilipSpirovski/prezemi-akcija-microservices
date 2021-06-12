package mk.ukim.finki.initiativesservice.service;

import mk.ukim.finki.initiativesservice.model.Category;
import mk.ukim.finki.initiativesservice.model.EventType;
import mk.ukim.finki.initiativesservice.model.Initiative;
import mk.ukim.finki.initiativesservice.model.dto.InitiativeDto;

import java.util.List;

public interface InitiativeService {

    List<Initiative> findAll();

    List<Initiative> findAllByInitiatorEmail(String initiatorEmail);

    List<Initiative> findAllByCategory(String categoryName);

    List<Initiative> findAllByEventType(String eventTypeName);

    Initiative findById(Long initiativeId);

    Initiative createInitiative(String initiatorEmail, InitiativeDto initiativeDto);

    Initiative editInitiative(Long initiativeId, InitiativeDto initiativeDto);

    Initiative addParticipantToInitiative(String participantEmail, Long initiativeId);

    Initiative removeParticipantFromInitiative(String participantEmail, Long initiativeId);

    boolean deleteInitiative(Long initiativeId);
}

package mk.ukim.finki.initiativesservice.service;

import mk.ukim.finki.initiativesservice.model.Initiative;
import mk.ukim.finki.initiativesservice.model.dto.InitiativeDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface InitiativeService {

    List<Initiative> findAll();

    List<Initiative> findAllByInitiatorEmail(String initiatorEmail);

    List<Initiative> findAllByCategory(String categoryName);

    List<Initiative> findAllByEventType(String eventTypeName);

    Initiative findById(Long initiativeId);

    Initiative createInitiative(InitiativeDto initiativeDto, Authentication authentication);

    Initiative editInitiative(Long initiativeId, InitiativeDto initiativeDto);

    Initiative addParticipantToInitiative(Long initiativeId, Authentication authentication);

    Initiative removeParticipantFromInitiative(Long initiativeId, Authentication authentication);

    boolean deleteInitiative(Long initiativeId);
}

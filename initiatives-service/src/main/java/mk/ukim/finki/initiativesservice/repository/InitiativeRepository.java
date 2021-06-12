package mk.ukim.finki.initiativesservice.repository;

import mk.ukim.finki.initiativesservice.model.Category;
import mk.ukim.finki.initiativesservice.model.EventType;
import mk.ukim.finki.initiativesservice.model.Initiative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InitiativeRepository extends JpaRepository<Initiative, Long> {

    List<Initiative> findAllByInitiatorEmail(String initiatorEmail);

    List<Initiative> findAllByCategory(Category category);

    List<Initiative> findAllByEventType(EventType eventType);
}

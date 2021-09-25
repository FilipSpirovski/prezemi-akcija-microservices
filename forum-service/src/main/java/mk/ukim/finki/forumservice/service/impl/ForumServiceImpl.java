package mk.ukim.finki.forumservice.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.forumservice.model.Forum;
import mk.ukim.finki.forumservice.model.exception.ForumForInitiativeAlreadyExists;
import mk.ukim.finki.forumservice.model.exception.ForumForInitiativeNotFound;
import mk.ukim.finki.forumservice.model.exception.ForumNotFound;
import mk.ukim.finki.forumservice.model.exception.InitiativeNotFound;
import mk.ukim.finki.forumservice.repository.ForumRepository;
import mk.ukim.finki.forumservice.service.ForumService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@AllArgsConstructor
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;
    private final RestTemplate restTemplate;
    private final String initiativesMicroserviceUrl = "http://initiatives-service/api/initiatives";

    @Override
    public List<Forum> findAll() {
        return this.forumRepository.findAll();
    }

    @Override
    public Forum findById(Long forumId) {
        return this.forumRepository.findById(forumId)
                .orElseThrow(() -> new ForumNotFound(forumId));
    }

    @Override
    public Forum findByInitiative(Long initiativeId) {
        return this.forumRepository.findByInitiativeId(initiativeId)
                .orElseThrow(() -> new ForumForInitiativeNotFound(initiativeId));
    }

    @Override
    public Forum createForum(Long initiativeId) {
        Forum forum;

        try {
            forum = this.findByInitiative(initiativeId);
            throw new ForumForInitiativeAlreadyExists(initiativeId);
        } catch (ForumForInitiativeNotFound e) {
            forum = new Forum(initiativeId);

            return this.forumRepository.save(forum);
        }
    }

    @Override
    public boolean deleteForum(Long forumId) throws ForumNotFound {
        Forum existingForum = this.findById(forumId);

        this.forumRepository.delete(existingForum);
        try {
            existingForum = this.findById(forumId);

            return false;
        } catch (ForumNotFound e) {
            return true;
        }
    }

    private boolean checkIfInitiativeExists(Long initiativeId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(this.initiativesMicroserviceUrl + "/" + initiativeId);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = this.restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        return response.getStatusCode().equals(HttpStatus.FOUND);
    }
}

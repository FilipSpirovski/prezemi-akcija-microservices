package mk.ukim.finki.forumservice.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.forumservice.model.Forum;
import mk.ukim.finki.forumservice.model.exception.ForumForInitiativeAlreadyExists;
import mk.ukim.finki.forumservice.model.exception.ForumForInitiativeNotFound;
import mk.ukim.finki.forumservice.model.exception.ForumNotFound;
import mk.ukim.finki.forumservice.repository.ForumRepository;
import mk.ukim.finki.forumservice.service.ForumService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;

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
}

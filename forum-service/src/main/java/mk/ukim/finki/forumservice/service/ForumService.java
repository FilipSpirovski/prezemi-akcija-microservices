package mk.ukim.finki.forumservice.service;

import mk.ukim.finki.forumservice.model.Forum;

import java.util.List;

public interface ForumService {

    List<Forum> findAll();

    Forum findById(Long forumId);

    Forum findByInitiative(Long initiativeId);

    Forum createForum(Long initiativeId);

    boolean deleteForum(Long forumId);
}

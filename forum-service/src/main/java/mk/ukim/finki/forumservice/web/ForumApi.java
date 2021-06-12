package mk.ukim.finki.forumservice.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.forumservice.service.ForumService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forum")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class ForumApi {

    private final ForumService forumService;
}

package mk.ukim.finki.initiativesservice.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.initiativesservice.service.InitiativeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/initiatives")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class InitiativeApi {

    private final InitiativeService initiativeService;
}

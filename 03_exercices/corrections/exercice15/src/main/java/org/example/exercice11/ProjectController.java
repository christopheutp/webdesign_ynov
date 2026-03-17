package org.example.exercice11;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Project>>> getProjects() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication())
                .flatMap(authentication -> {
                    String username = authentication.getName();
                    Flux<Project> projectFlux = projectService.getProjectsForUser(username);
                    return Mono.just(ResponseEntity.ok(projectFlux));
                });
    }
}

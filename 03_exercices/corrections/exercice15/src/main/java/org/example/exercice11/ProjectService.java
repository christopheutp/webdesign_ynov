package org.example.exercice11;


import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    private final Map<String, List<Project>> projects = new HashMap<>();

    public ProjectService() {
        projects.put("user1",List.of(
                new Project(1L,"Projet A","description du projet"),
                new Project(2L,"Projet 2","description du projet 2")
                ));
        projects.put("toto",List.of(
                new Project(3L,"Projet Z","description du projet Z"),
                new Project(4L,"Projet 42","description du projet 42")
        ));
    }

    public Flux<Project> getProjectsForUser(String username) {
        List<Project> projectList = projects.getOrDefault(username,List.of());
        return Flux.fromIterable(projectList);
    }
}

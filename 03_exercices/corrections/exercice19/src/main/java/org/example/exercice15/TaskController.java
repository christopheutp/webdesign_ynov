package org.example.exercice15;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @GetMapping("/stream")
    public Flux<String> getTaskStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "Task " + (i + 1));
    }
}

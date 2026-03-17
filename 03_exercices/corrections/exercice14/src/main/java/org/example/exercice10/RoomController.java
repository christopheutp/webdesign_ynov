package org.example.exercice10;


import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final Map<Long, Room> rooms = new HashMap<Long, Room>();

    public RoomController() {
        rooms.put(1L, new Room(1L, "Room 1"));
        rooms.put(2L, new Room(2L, "Room 2"));
    }

    @GetMapping
    public Flux<Room> getAllRooms() {
        return Flux.fromIterable(rooms.values());
    }

    @PostMapping("/add")
    public Mono<Room> addRoom(@RequestBody Room room) {
        rooms.put(room.getId(), room);
        return Mono.just(room);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteRoom(@PathVariable Long id) {
        rooms.remove(id);
        return Mono.empty();
    }

}

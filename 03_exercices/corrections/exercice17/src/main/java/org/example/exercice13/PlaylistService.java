package org.example.exercice13;

import reactor.core.publisher.Flux;

import java.util.Map;

public class PlaylistService {

    private static final Map<String, String[]> playlists = Map.of(
            "rock", new String[]{"Bohemian Rhapsody", "Hotel California", "Stairway to Heaven"},
            "pop", new String[]{"Thriller", "Like a Virgin", "Billie Jean"}
    );

    public Flux<String> getPlaylist(String genre) {
        if (!playlists.containsKey(genre)) {
            return Flux.error(new IllegalArgumentException("Genre non supporté : " + genre));
        }
        return Flux.fromArray(playlists.get(genre));
    }
}

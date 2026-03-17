package org.example.exercice13;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class PlaylistServiceTest {

    private final PlaylistService playlistService = new PlaylistService();

    @Test
    void testGetRockPlaylist() {
        StepVerifier.create(playlistService.getPlaylist("rock"))
                .expectNext("Bohemian Rhapsody", "Hotel California", "Stairway to Heaven")
                .verifyComplete();
    }

    @Test
    void testGetPopPlaylist() {
        StepVerifier.create(playlistService.getPlaylist("pop"))
                .expectNext("Thriller", "Like a Virgin", "Billie Jean")
                .verifyComplete();
    }

    @Test
    void testUnsupportedGenre() {
        StepVerifier.create(playlistService.getPlaylist("jazz"))
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals("Genre non supporté : jazz"))
                .verify();
    }
}

package by.clever.reflection.service.impl;

import by.clever.reflection.entity.MusicBand;
import by.clever.reflection.entity.constant.MusicGenre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Setter
@Getter
public class MusicBandTestData {

    @Builder.Default
    private UUID id = UUID.fromString("66caf9ea-6074-4b1c-a1d1-c0dca7095f9f");

    @Builder.Default
    private String name = "LP";

    @Builder.Default
    private MusicGenre genre = MusicGenre.ROCK;

    @Builder.Default
    private LocalDate creationDate = LocalDate.MIN;

    @Builder.Default
    private String workPhoneNumber = "+333127469855";

    @Builder.Default
    private String workEmail = "email@email.me";

    public MusicBand getMusicBand() {

        return new MusicBand(id, name, genre, creationDate, workPhoneNumber, workEmail);
    }
}

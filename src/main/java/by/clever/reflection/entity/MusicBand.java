package by.clever.reflection.entity;

import by.clever.reflection.entity.constant.MusicGenre;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class MusicBand {

    private UUID id;
    private String name;
    private MusicGenre genre;
    private LocalDate creationDate;
    private String workPhoneNumber;
    private String workEmail;
}

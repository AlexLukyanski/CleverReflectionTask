package by.clever.reflection.entity;

import by.clever.reflection.entity.constant.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Main entity for the task
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MusicBand {

    /**
     * Cannot be null
     */
    private UUID id;

    /**
     * Can contain ane symbol and length should be in range from 1 to 30 symbols
     */
    private String name;

    /**
     * Genre from MusicGenre enum
     */
    private MusicGenre genre;

    /**
     * Cannot be in future
     */
    private LocalDate creationDate;

    /**
     * In international format
     */
    private String workPhoneNumber;

    /**
     * Should contain @ symbol
     */
    private String workEmail;
}
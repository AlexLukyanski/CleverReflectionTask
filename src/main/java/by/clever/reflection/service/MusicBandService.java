package by.clever.reflection.service;

import by.clever.reflection.entity.MusicBand;

import java.util.Optional;
import java.util.UUID;

public interface MusicBandService {

    UUID create(MusicBand band);

    Optional<MusicBand> getByID(UUID id);

    void update(MusicBand band);

    void delete(UUID id);
}

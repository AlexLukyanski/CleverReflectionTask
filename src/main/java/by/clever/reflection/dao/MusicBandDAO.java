package by.clever.reflection.dao;

import by.clever.reflection.entity.MusicBand;

import java.util.Optional;
import java.util.UUID;

public interface MusicBandDAO {

    UUID save(MusicBand band);

    Optional<MusicBand> readByID(UUID id);

    UUID update(MusicBand band);

    void delete(UUID id);
}

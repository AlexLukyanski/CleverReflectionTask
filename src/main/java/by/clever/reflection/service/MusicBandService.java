package by.clever.reflection.service;

import by.clever.reflection.dto.MusicBandDTO;

import java.util.UUID;

public interface MusicBandService {

    UUID create(MusicBandDTO bandDTO);

    MusicBandDTO getByID(UUID id);

    UUID update(MusicBandDTO bandDTO);

    void delete(UUID id);
}

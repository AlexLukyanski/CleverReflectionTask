package by.clever.reflection.service.impl;

import by.clever.reflection.entity.MusicBand;
import by.clever.reflection.service.MusicBandService;

import java.util.Optional;
import java.util.UUID;

public class MusicBandServiceImpl implements MusicBandService {
    @Override
    public UUID create(MusicBand band) {
        return null;
    }

    @Override
    public Optional<MusicBand> getByID(UUID id) {
        return Optional.empty();
    }

    @Override
    public void update(MusicBand band) {

    }

    @Override
    public void delete(UUID id) {

    }
}

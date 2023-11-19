package by.clever.reflection.dao.cache.impl;

import by.clever.reflection.dao.cache.Cache;
import by.clever.reflection.entity.MusicBand;
import lombok.Getter;

import java.util.UUID;

@Getter
public final class CacheLFUTestData {

    private Cache<UUID, MusicBand> cache = new CacheLFU<>(15);

    {
        cache.put(UUID.fromString("0d512e9b-7d8d-4a5e-9317-4d355cfdfa1b"), new MusicBand());
        cache.put(UUID.fromString("559b19f5-771c-43bd-b189-743711f8a0c9"), new MusicBand());
        cache.put(UUID.fromString("7f1b53b4-eb6d-458e-9f9f-ec0daf257f8f"), new MusicBand());
    }
}

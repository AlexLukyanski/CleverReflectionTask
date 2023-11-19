package by.clever.reflection.dao.cache.impl;

import by.clever.reflection.dao.cache.Cache;
import by.clever.reflection.entity.MusicBand;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CacheLFUTest {

    @Nested
    class PutMethodTest {

        @Test
        void should_PutValueInCash_when_MethodInvoke() {
            //given
            CacheLFUTestData testData = new CacheLFUTestData();
            Cache<UUID, MusicBand> cache = testData.getCache();

            UUID id = UUID.fromString("c9ebda96-6fe8-469f-a75a-3cb3e29e771e");

            MusicBand expectedBand = new MusicBand();
            expectedBand.setId(id);

            //when
            cache.put(id, expectedBand);
            MusicBand actualBand = cache.get(id).orElseThrow();

            //then
            assertEquals(expectedBand, actualBand);
        }
    }

    @Nested
    class GetMethodTest {
        @Test
        void get() {
            //given
            CacheLFUTestData testData = new CacheLFUTestData();
            Cache<UUID, MusicBand> cache = testData.getCache();
            MusicBand expected = cache.get(UUID.fromString("0d512e9b-7d8d-4a5e-9317-4d355cfdfa1b")).orElseThrow();

            //when
            MusicBand actual = cache.get(UUID.fromString("0d512e9b-7d8d-4a5e-9317-4d355cfdfa1b")).orElseThrow();

            //then
            assertEquals(expected, actual);
        }
    }

    @Nested
    class RemoveMethodTest {

        @Test
        void should_RemoveElement_when_MethodInvoke() {
            //given
            CacheLFUTestData testData = new CacheLFUTestData();
            Cache<UUID, MusicBand> cache = testData.getCache();
            UUID id = UUID.fromString("0d512e9b-7d8d-4a5e-9317-4d355cfdfa1b");
            UUID expected = null;

            //when
            cache.remove(id);
            UUID actual = cache.get(id).get().getId();

            //then
            assertNull(actual);
        }
    }

    @Nested
    class ClearMethodTest {

        @Test
        void shuold_ClearCache_when_MethodInvoke() {
            //given
            CacheLFUTestData testData = new CacheLFUTestData();
            Cache<UUID, MusicBand> cache = testData.getCache();
            UUID id = UUID.fromString("0d512e9b-7d8d-4a5e-9317-4d355cfdfa1b");

            //when
            cache.clear();
            Executable executable = () -> cache.get(id);

            //then
            assertThrows(NullPointerException.class, executable);
        }
    }
}

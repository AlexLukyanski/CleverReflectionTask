package by.clever.reflection.service.impl;


import by.clever.reflection.dao.MusicBandDAO;
import by.clever.reflection.dto.MusicBandDTO;
import by.clever.reflection.entity.MusicBand;
import by.clever.reflection.mapper.MusicBandMapper;
import by.clever.reflection.service.exception.ServiceValidationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MusicBandServiceImplTest {

    @Mock
    private MusicBandMapper mapper;

    @Mock
    private MusicBandDAO musicBandDAO;

    @InjectMocks
    private MusicBandServiceImpl musicBandService;


    @Nested
    class CreateMethodTest {
        @Test
        void should_NotThrowValidationException_when_MusicBandDtoValid() {
            //given
            MusicBand band = MusicBandTestData.builder().build().getMusicBand();
            MusicBandDTO bandDTO = MusicBandDtoTestData.builder().build().getMusicBandDTO();

            when(musicBandDAO.save(band)).thenReturn(band.getId());
            when(mapper.toMusicBand(bandDTO)).thenReturn(band);

            //when
            Executable executable = () -> musicBandService.create(bandDTO);

            //then
            assertDoesNotThrow(executable);
        }

        @Test
        void should_ThrowValidationException_when_MusicBandDtoNotValid() {
            //given
            MusicBand band = MusicBandTestData.builder().id(null).build().getMusicBand();
            MusicBandDTO bandDTO = MusicBandDtoTestData.builder().id(null).build().getMusicBandDTO();

            //when
            Executable executable = () -> musicBandService.create(bandDTO);

            //then
            assertThrows(ServiceValidationException.class, executable);
        }

        @Test
        void should_ReturnID_when_MethodInvoke() {
            //given
            MusicBand band = MusicBandTestData.builder().build().getMusicBand();
            MusicBandDTO bandDTO = MusicBandDtoTestData.builder().build().getMusicBandDTO();
            UUID expected = bandDTO.id();

            when(musicBandDAO.save(band)).thenReturn(band.getId());
            when(mapper.toMusicBand(bandDTO)).thenReturn(band);

            //when
            UUID actual = musicBandService.create(bandDTO);

            //then
            assertEquals(expected, actual);
        }
    }


    @Nested
    class GetByIDMethodTest {

        @Test
        void should_NotThrowValidationException_when_IdValid() {
            //given
            UUID id = UUID.fromString("66caf9ea-6074-4b1c-a1d1-c0dca7095f9f");
            MusicBand band = MusicBandTestData.builder().build().getMusicBand();
            MusicBandDTO bandDTO = MusicBandDtoTestData.builder().build().getMusicBandDTO();

            when(musicBandDAO.readByID(id)).thenReturn(Optional.of(band));
            when(mapper.toMusicBandDTO(band)).thenReturn(bandDTO);

            //when
            Executable executable = () -> musicBandService.getByID(id);

            //then
            assertDoesNotThrow(executable);
        }

        @Test
        void should_ThrowValidationException_when_IdNotValid() {
            //given
            UUID id = null;
            MusicBand band = MusicBandTestData.builder().build().getMusicBand();
            MusicBandDTO bandDTO = MusicBandDtoTestData.builder().build().getMusicBandDTO();

            //when
            Executable executable = () -> musicBandService.getByID(id);

            //then
            assertThrows(ServiceValidationException.class, executable);
        }

    }

    @Nested
    class UpdateMethodTest {

        @Test
        void should_NotThrowValidationException_when_MusicBandDtoValid() {
            //given
            MusicBand band = MusicBandTestData.builder().build().getMusicBand();
            MusicBandDTO bandDTO = MusicBandDtoTestData.builder().build().getMusicBandDTO();

            when(musicBandDAO.update(band)).thenReturn(band.getId());
            when(mapper.toMusicBand(bandDTO)).thenReturn(band);

            //when
            Executable executable = () -> musicBandService.update(bandDTO);

            //then
            assertDoesNotThrow(executable);
        }

        @Test
        void should_ThrowValidationException_when_MusicBandDtoNotValid() {
            //given
            MusicBand band = MusicBandTestData.builder().id(null).build().getMusicBand();
            MusicBandDTO bandDTO = MusicBandDtoTestData.builder().id(null).build().getMusicBandDTO();

            //when
            Executable executable = () -> musicBandService.update(bandDTO);

            //then
            assertThrows(ServiceValidationException.class, executable);
        }

        @Test
        void should_ReturnID_when_MethodInvoke() {
            //given
            MusicBand band = MusicBandTestData.builder().build().getMusicBand();
            MusicBandDTO bandDTO = MusicBandDtoTestData.builder().build().getMusicBandDTO();
            UUID expected = bandDTO.id();

            when(musicBandDAO.update(band)).thenReturn(band.getId());
            when(mapper.toMusicBand(bandDTO)).thenReturn(band);

            //when
            UUID actual = musicBandService.update(bandDTO);

            //then
            assertEquals(expected, actual);
        }
    }

    @Nested
    class DeleteMethodTest {
        @Test
        void should_NotThrowValidationException_when_IdValid() {
            //given
            UUID id = UUID.fromString("66caf9ea-6074-4b1c-a1d1-c0dca7095f9f");

            doNothing().when(musicBandDAO).delete(id);

            //when
            Executable executable = () -> musicBandService.delete(id);

            //then
            assertDoesNotThrow(executable);
        }
    }
}
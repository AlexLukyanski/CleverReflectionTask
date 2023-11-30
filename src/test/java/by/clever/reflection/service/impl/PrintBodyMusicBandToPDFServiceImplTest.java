package by.clever.reflection.service.impl;

import by.clever.reflection.dto.MusicBandDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class PrintBodyMusicBandToPDFServiceImplTest {

    PrintBodyMusicBandToPDFServiceImpl pdfService = new PrintBodyMusicBandToPDFServiceImpl(new PrintTitleMusicBandToPDFServiceImpl());

    @Nested
    class PrintMusicBandToPDFMethodTest {

        @Test
        void should_NotThrowException_when_MethodInvoke() {

            //given
            MusicBandDTO bandDTO = MusicBandDtoTestData.builder().build().getMusicBandDTO();

            //when
            Executable executable = () -> pdfService.printMusicBandToPDF(bandDTO);

            //then
            assertDoesNotThrow(executable);
        }
    }
}

package by.clever.reflection.service.impl;

import by.clever.reflection.dto.MusicBandDTO;
import by.clever.reflection.service.exception.PrintServiceException;
import by.clever.reflection.service.validation.PrintMusicBandToPDFService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class PrintBottomMusicBandToPDFServiceImpl implements PrintMusicBandToPDFService {

    private final PrintMusicBandToPDFService printMusicBandToPDFService;

    @Override
    public void printMusicBandToPDF(MusicBandDTO musicBandDTO) {

        printMusicBandToPDFService.printMusicBandToPDF(musicBandDTO);

        String fileName = "Clevertec_Band_With_Title_And_Body.pdf";
        String outputFileName = "Clevertec_Band_Whole.pdf";
        String stringToPrint = "What am I doing here?";

        try (PdfReader reader = new PdfReader(fileName);
             PdfWriter writer = new PdfWriter(outputFileName);
             PdfDocument pdfDocument = new PdfDocument(reader, writer);
             Document document = new Document(pdfDocument)) {

            Paragraph paragraph = new Paragraph(stringToPrint);
            paragraph.setFixedPosition(250, 100, 150);
            document.add(paragraph);

        } catch (IOException e) {
            throw new PrintServiceException(e);
        }
    }
}

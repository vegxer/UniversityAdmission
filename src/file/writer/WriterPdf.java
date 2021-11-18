package file.writer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

class WriterPdf extends Writer {
    public WriterPdf(String fileName) {
        super.setFilePath(fileName);
    }

    @Override
    public void write(String message) throws IOException, DocumentException {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(super.filePath));
        doc.open();

        BaseFont utf8 = BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(utf8, 12, Font.NORMAL);
        doc.addTitle("Message from university");
        Paragraph paragraph = new Paragraph(message, font);
        doc.add(paragraph);

        doc.close();
    }
}

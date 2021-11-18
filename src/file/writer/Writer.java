package file.writer;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

public abstract class Writer {
    protected String filePath;

    public static Writer createWriter(String fileName) throws IOException {
        if (file.File.getExtension(fileName).equals("txt"))
            return new WriterTxt(fileName);
        else if (file.File.getExtension(fileName).equals("pdf"))
            return new WriterPdf(fileName);
        else
            throw new IllegalArgumentException("Incorrect file extension");
    }

    public abstract void write(String string) throws IOException, DocumentException;


    public void setFilePath(String filePath) {
        if (filePath == null)
            throw new NullPointerException();

        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}

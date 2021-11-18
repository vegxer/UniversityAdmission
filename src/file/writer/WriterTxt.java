package file.writer;

import java.io.FileWriter;
import java.io.IOException;

class WriterTxt extends Writer {
    public WriterTxt(String fileName) {
        super.setFilePath(fileName);
    }

    @Override
    public void write(String message) throws IOException {
        FileWriter writer = new FileWriter(super.filePath, false);
        writer.write(message);
        writer.flush();
        writer.close();
    }
}

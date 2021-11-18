package file.reader.facultiesReader;

import applicant.Applicant;
import file.File;
import file.reader.Reader;
import university.Faculty;

import java.io.IOException;
import java.util.ArrayList;

public final class FacultiesReaderFactory {
    private FacultiesReaderFactory() {}

    public static Reader<ArrayList<Faculty>> create(String filePath) throws IOException {
        if (File.getExtension(filePath).equals("txt"))
            return new TextFacultiesReader(filePath);
        else if (File.getExtension(filePath).equals("xml"))
            return new XmlFacultiesReader(filePath);
        else if (File.getExtension(filePath).equals("json"))
            return new JsonFacultiesReader(filePath);
        else
            throw new IllegalArgumentException("Incorrect file extension");
    }
}

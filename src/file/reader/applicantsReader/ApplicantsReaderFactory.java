package file.reader.applicantsReader;

import applicant.Applicant;
import file.File;
import file.reader.Reader;

import java.io.IOException;
import java.util.ArrayList;

public final class ApplicantsReaderFactory {
    private ApplicantsReaderFactory() {}

    public static Reader<ArrayList<Applicant>> create(String filePath) throws IOException {
        if (File.getExtension(filePath).equals("txt"))
            return new TextApplicantsReader(filePath);
        else if (File.getExtension(filePath).equals("xml"))
            return new XmlApplicantsReader(filePath);
        else if (File.getExtension(filePath).equals("json"))
            return new JsonApplicantsReader(filePath);
        else
            throw new IllegalArgumentException("Incorrect file extension");
    }
}

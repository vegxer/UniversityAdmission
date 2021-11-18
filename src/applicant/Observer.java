package applicant;


import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface Observer {
    void update(String message) throws IOException, DocumentException;
}

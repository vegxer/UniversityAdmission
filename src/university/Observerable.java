package university;

import applicant.Observer;
import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface Observerable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers() throws IOException, DocumentException;
}

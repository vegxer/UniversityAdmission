package university;

import applicant.Applicant;
import applicant.Observer;
import com.itextpdf.text.DocumentException;
import file.reader.applicantsReader.ApplicantsReaderFactory;
import file.reader.facultiesReader.FacultiesReaderFactory;
import org.json.simple.parser.ParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;

public class University implements Observerable {
    private final ArrayList<Observer> applicants;
    private final ArrayList<Faculty> faculties;

    public University(String filePathFaculties, String filePathApplicants)
            throws IOException, XMLStreamException, XMLParseException, ParseException {
        applicants = new ArrayList<>(ApplicantsReaderFactory.create(filePathApplicants).readFile());
        faculties = new ArrayList<>(FacultiesReaderFactory.create(filePathFaculties).readFile());
    }

    @Override
    public void notifyObservers() throws IOException, DocumentException {
        for (Observer applicant : applicants) {
            ArrayList<String> passedFaculties = new ArrayList<>();

            for (Faculty faculty : faculties)
                if (faculty.DoesApplicantPass((Applicant)applicant))
                    passedFaculties.add(faculty.getName());

            applicant.update(createMessage(applicant, passedFaculties));
        }
    }


    @Override
    public void addObserver(Observer observer) {
        applicants.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        applicants.remove(observer);
    }

    public void addFaculty(Faculty faculty) {
        faculties.add(faculty);
    }

    public void removeFaculty(Faculty faculty) {
        faculties.remove(faculty);
    }


    private String createMessage(Observer applicant, ArrayList<String> passedFaculties) {
        String fullName = ((Applicant)applicant).getFullName();

        if (passedFaculties.isEmpty())
            return String.format("Уважаемый(-ая) %s!\nК сожалению, Вы не прошли по баллам ни на один факультет.", fullName);

        String faculties = formatFacultiesList(passedFaculties);
        return String.format("Уважаемый(-ая) %s!\nВы прошли по баллам на следующие факультеты: %s", fullName, faculties);
    }

    private String formatFacultiesList(ArrayList<String> passedFaculties) {
        String faculties = "";

        for (int i = 0; i < passedFaculties.size(); ++i) {
            faculties = faculties.concat(String.format("\n%d) %s", i + 1, passedFaculties.get(i)));
        }

        return faculties;
    }

}

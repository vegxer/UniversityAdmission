package applicant;

import com.itextpdf.text.DocumentException;
import file.writer.Writer;

import java.io.IOException;
import java.util.HashMap;

public class Applicant implements Observer {
    final String SAVE_PATH = "messagesToApplicants/";
    final String PDF_EXTENSION =".pdf";
    final String TXT_EXTENSION =".txt";

    private String name = null, surname = null, patronymic = null;
    private HashMap<String, Integer> subjectsScores;

    public Applicant()
    {
        subjectsScores = new HashMap<>();
    }

    @Override
    public void update(String message) throws IOException, DocumentException {
        saveMessage(message, PDF_EXTENSION);
    }

    private void saveMessage(String message, String extension) throws IOException, DocumentException {
        Writer writer = Writer.createWriter(SAVE_PATH + getFullName() + extension);
        writer.write(message);
    }


    public String getFullName() {
        return getSurname() + " " + getName() + " " + getPatronymic();
    }

    public void addSubject(String subject, int score)
    {
        subjectsScores.put(subject, score);
    }

    public void removeSubject(String subject) {
        subjectsScores.remove(subject);
    }

    public HashMap<String, Integer> getSubjectScores()
    {
        return subjectsScores;
    }

    public void setSubjectsScores(HashMap<String, Integer> subjectsScores)
    {
        this.subjectsScores = subjectsScores;
    }

    public String getName() {
        if (name == null)
            throw new NullPointerException("Name are not initialized");

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        if (name == null)
            throw new NullPointerException("Surname are not initialized");

        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        if (name == null)
            throw new NullPointerException("Patronymic are not initialized");

        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}

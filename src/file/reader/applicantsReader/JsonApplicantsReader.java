package file.reader.applicantsReader;

import applicant.Applicant;
import file.reader.Reader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class JsonApplicantsReader extends Reader<ArrayList<Applicant>> {
    public JsonApplicantsReader(String filePath) {
        super(filePath);
    }

    @Override
    public ArrayList<Applicant> readFile() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonApplicants = (JSONArray)(((JSONObject)parser.parse(new FileReader(filePath))).get("applicants"));
        ArrayList<Applicant> applicants = new ArrayList<>();

        for (Object obj : jsonApplicants) {
            JSONObject jsonApplicant = (JSONObject)obj;
            Applicant applicant = new Applicant();
            applicant.setName((String)jsonApplicant.get("name"));
            applicant.setSurname((String)jsonApplicant.get("surname"));
            applicant.setPatronymic((String)jsonApplicant.get("patronymic"));
            applicant.setSubjectsScores(getSubjectsJson(jsonApplicant));

            applicants.add(applicant);
        }

        if (applicants.isEmpty())
            throw new IllegalArgumentException("Applicants are not found");

        return applicants;
    }

}

package file.reader.applicantsReader;


import applicant.Applicant;
import file.reader.Reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class TextApplicantsReader extends Reader<ArrayList<Applicant>> {
    public TextApplicantsReader(String filePath) {
        super(filePath);
    }

    @Override
    public ArrayList<Applicant> readFile() throws IOException {
        ArrayList<Applicant> applicants = new ArrayList<Applicant>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line, prevLine = "";
        for (int i = -1; (line = reader.readLine()) != null; prevLine = line) {
            if (!line.isEmpty() && prevLine.isEmpty()) {
                ++i;
                String[] fullName = line.split(" ");
                applicants.add(new Applicant());
                applicants.get(i).setName(fullName[1]);
                applicants.get(i).setSurname(fullName[0]);
                applicants.get(i).setPatronymic(fullName[2]);
            }
            else if (!line.isEmpty()) {
                String[] subject = line.split(":");
                applicants.get(i).addSubject(subject[0], Integer.parseInt(subject[1]));
            }
        }
        reader.close();

        if (applicants.isEmpty())
            throw new IllegalArgumentException("Applicants are not found");

        return applicants;
    }
}

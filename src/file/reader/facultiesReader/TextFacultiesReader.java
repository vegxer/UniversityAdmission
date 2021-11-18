package file.reader.facultiesReader;


import file.reader.Reader;
import university.Faculty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class TextFacultiesReader extends Reader<ArrayList<Faculty>> {
    public TextFacultiesReader(String filePath) {
        super(filePath);
    }

    @Override
    public ArrayList<Faculty> readFile() throws IOException {
        ArrayList<Faculty> faculties = new ArrayList<Faculty>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line, prevLine = "";
        for (int i = -1; (line = reader.readLine()) != null; prevLine = line) {
            if (!line.isEmpty() && prevLine.isEmpty()) {
                ++i;
                faculties.add(new Faculty());
                faculties.get(i).setName(line);
            }
            else if (!line.isEmpty()) {
                String[] subject = line.split(":");
                faculties.get(i).addSubject(subject[0], Integer.parseInt(subject[1]));
            }
        }
        reader.close();

        if (faculties.isEmpty())
            throw new IllegalArgumentException("Faculties are no found");

        return faculties;
    }
}

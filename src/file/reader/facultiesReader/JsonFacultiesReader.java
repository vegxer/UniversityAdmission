package file.reader.facultiesReader;

import file.reader.Reader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import university.Faculty;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class JsonFacultiesReader extends Reader<ArrayList<Faculty>> {
    public JsonFacultiesReader(String filePath) {
        super(filePath);
    }

    @Override
    public ArrayList<Faculty> readFile() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonFaculties = (JSONArray)(((JSONObject)parser.parse(new FileReader(filePath))).get("faculties"));
        ArrayList<Faculty> faculties = new ArrayList<>();

        for (Object obj : jsonFaculties) {
            JSONObject jsonFaculty = (JSONObject)obj;
            Faculty faculty = new Faculty();
            faculty.setName((String)jsonFaculty.get("name"));
            faculty.setMinSubjectsScores(getSubjectsJson(jsonFaculty));

            faculties.add(faculty);
        }

        if (faculties.isEmpty())
            throw new IllegalArgumentException("Faculties are not found");

        return faculties;
    }
}

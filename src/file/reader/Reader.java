package file.reader;

import pair.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.util.HashMap;

public abstract class Reader<T> {
    protected String filePath;

    public Reader(String filePath) {
        this.filePath = filePath;
    }

    public abstract T readFile()
            throws IOException, XMLStreamException, XMLParseException, ParseException;


    protected HashMap<String, Integer> getSubjectsXml(XMLEvent xmlEvent, XMLEventReader reader)
            throws XMLStreamException, XMLParseException {
        if (!xmlEvent.asStartElement().getName().getLocalPart().equals("Subjects"))
            throw new IllegalArgumentException();

        HashMap<String, Integer> subjects = new HashMap<>();

        do {
            xmlEvent = reader.nextEvent();

            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                if (tagName.equals("Subject")) {
                    Pair<String, Integer> subject = getSubjectXml(xmlEvent, reader);
                    subjects.put(subject.firstElement, subject.secondElement);
                }
                else
                    throw new XMLParseException("Incorrect tag");
            }
        }
        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("Subjects"));

        if (subjects.isEmpty())
            throw new XMLParseException("Subjects are not found");

        return subjects;
    }

    private Pair<String, Integer> getSubjectXml(XMLEvent xmlEvent, XMLEventReader reader)
            throws XMLStreamException, XMLParseException {
        if (!xmlEvent.asStartElement().getName().getLocalPart().equals("Subject"))
            throw new IllegalArgumentException();

        String name = null;
        int score = -1;

        do {
            xmlEvent = reader.nextEvent();

            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                if (tagName.equals("name"))
                    name = reader.nextEvent().asCharacters().getData();
                else if (tagName.equals("score"))
                    score = Integer.parseInt(reader.nextEvent().asCharacters().getData());
                else
                    throw new XMLParseException("Incorrect tags in Subject tag");
            }
        }
        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("Subject"));

        if (name == null || score == -1)
            throw new XMLParseException("Not enough tags in Subject tag");

        return new Pair<>(name, score);
    }

    protected HashMap<String, Integer> getSubjectsJson(JSONObject jsonApplicant) {
        JSONArray jsonSubjects = (JSONArray)jsonApplicant.get("subjects");
        HashMap<String, Integer> subjects = new HashMap<>();

        for (Object subject : jsonSubjects) {
            JSONObject jsonSubject = (JSONObject)subject;
            subjects.put((String)jsonSubject.get("name"), Integer.parseInt((String)jsonSubject.get("score")));
        }

        if (subjects.isEmpty())
            throw new IllegalArgumentException("Empty array of subjects");

        return subjects;
    }
}

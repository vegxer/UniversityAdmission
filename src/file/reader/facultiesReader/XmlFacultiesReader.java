package file.reader.facultiesReader;


import file.reader.Reader;
import university.Faculty;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

class XmlFacultiesReader extends Reader<ArrayList<Faculty>> {
    public XmlFacultiesReader(String filePath) {
        super(filePath);
    }

    @Override
    public ArrayList<Faculty> readFile() throws IOException, XMLStreamException, XMLParseException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(new FileInputStream(filePath));
        XMLEvent xmlEvent = reader.nextEvent();

        return getFaculties(xmlEvent, reader);
    }

    private ArrayList<Faculty> getFaculties(XMLEvent xmlEvent, XMLEventReader reader)
            throws XMLStreamException, XMLParseException {
        ArrayList<Faculty> faculties = new ArrayList<>();

        if (reader.hasNext()) {
            xmlEvent = reader.nextEvent();

            if (!xmlEvent.isStartElement() || !xmlEvent.asStartElement().getName().getLocalPart().equals("Faculties"))
                throw new XMLParseException("Faculties tag must be first tag");
        }
        else
            throw new XMLParseException("File is empty");

        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("Faculties"))
        {
            xmlEvent = reader.nextEvent();
            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                if (tagName.equals("Faculty"))
                    faculties.add(getFaculty(xmlEvent, reader));
                else
                    throw new XMLParseException("Incorrect tag");
            }
        }

        if (faculties.isEmpty())
            throw new XMLParseException("No faculties found");

        return faculties;
    }

    private Faculty getFaculty(XMLEvent xmlEvent, XMLEventReader reader) throws XMLStreamException, XMLParseException {
        if (!xmlEvent.asStartElement().getName().getLocalPart().equals("Faculty"))
            throw new IllegalArgumentException();

        Faculty faculty = new Faculty();

        do {
            xmlEvent = reader.nextEvent();

            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                switch (tagName) {
                    case "name":
                        faculty.setName(reader.nextEvent().asCharacters().getData());
                        break;
                    case "Subjects":
                        faculty.setMinSubjectsScores(getSubjectsXml(xmlEvent, reader));
                        break;
                    default:
                        throw new XMLParseException("Incorrect tag");
                }
            }
        }
        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("Faculty"));

        if (faculty.getName() == null)
            throw new XMLParseException("Not enough tags in Faculty tag");

        return faculty;
    }
}

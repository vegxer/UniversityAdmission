package file.reader.applicantsReader;


import applicant.Applicant;
import file.reader.Reader;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

class XmlApplicantsReader extends Reader<ArrayList<Applicant>> {
    public XmlApplicantsReader(String filePath) {
        super(filePath);
    }

    @Override
    public ArrayList<Applicant> readFile() throws IOException, XMLStreamException, XMLParseException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(new FileInputStream(filePath));
        XMLEvent xmlEvent = reader.nextEvent();

        return getApplicants(xmlEvent, reader);
    }

    private ArrayList<Applicant> getApplicants(XMLEvent xmlEvent, XMLEventReader reader)
            throws XMLStreamException, XMLParseException {
        ArrayList<Applicant> applicants = new ArrayList<>();

        if (reader.hasNext()) {
            xmlEvent = reader.nextEvent();

            if (!xmlEvent.isStartElement() || !xmlEvent.asStartElement().getName().getLocalPart().equals("Applicants"))
                throw new XMLParseException("Applicants tag must be first tag");
        }
        else
            throw new XMLParseException("File is empty");

        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("Applicants"))
        {
            xmlEvent = reader.nextEvent();
            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                if (tagName.equals("applicant"))
                    applicants.add(getApplicant(xmlEvent, reader));
                else
                    throw new XMLParseException("Incorrect tag");
            }
        }

        if (applicants.isEmpty())
            throw new XMLParseException("No applicants found");

        return applicants;
    }

    private Applicant getApplicant(XMLEvent xmlEvent, XMLEventReader reader)
            throws XMLStreamException, XMLParseException {
        if (!xmlEvent.asStartElement().getName().getLocalPart().equals("applicant"))
            throw new IllegalArgumentException();

        Applicant applicant = new Applicant();

        do {
            xmlEvent = reader.nextEvent();

            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                String tagName = startElement.getName().getLocalPart();

                switch (tagName) {
                    case "surname":
                        applicant.setSurname(reader.nextEvent().asCharacters().getData());
                        break;
                    case "name":
                        applicant.setName(reader.nextEvent().asCharacters().getData());
                        break;
                    case "patronymic":
                        applicant.setPatronymic(reader.nextEvent().asCharacters().getData());
                        break;
                    case "Subjects":
                        applicant.setSubjectsScores(getSubjectsXml(xmlEvent, reader));
                        break;
                    default:
                        throw new XMLParseException("Incorrect tag");
                }
            }
        }
        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("applicant"));

        if (applicant.getName() == null || applicant.getPatronymic() == null || applicant.getSurname() == null)
            throw new XMLParseException("Not enough tags in task1.Applicant tag");

        return applicant;
    }

}

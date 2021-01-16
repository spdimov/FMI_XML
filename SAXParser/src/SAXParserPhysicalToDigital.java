import books.physical.PhysicalBook;
import books.physical.PhysicalBookTags;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SAXParserPhysicalToDigital extends DefaultHandler {

    PhysicalBook bookBeingParsed;
    List<PhysicalBook> books;
    File outputFile;
    FileWriter outputWriter;
    private Map<String, Boolean> tagToIsParsedNow;

    static public void main(String[] args) throws SAXException, IOException {

        XMLReader parser = XMLReaderFactory.createXMLReader();
        InputSource source = new InputSource("resources/physical_book.xml");
        parser.setContentHandler(new SAXParserPhysicalToDigital());

        parser.parse(source);
    }

    @Override
    public void startDocument() {

        outputFile = new File("physical_to_digital.xml");

        try {
            outputFile.createNewFile();
            outputWriter = new FileWriter(outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }


        books = new LinkedList<>();

        try {
            tagToIsParsedNow = createTagsMap();

            outputWriter.write("<?xml version=\"1.0\" standalone=\"no\"?>" + System.lineSeparator());
            outputWriter.write("<!DOCTYPE bookstore SYSTEM \"digital_book_validation.dtd\">" + System.lineSeparator());
            outputWriter.write("<bookstore>" + System.lineSeparator());
            outputWriter.flush();
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes atts) {

        setElementBeingParsed(localName);

        if ("book".equals(localName)) {
            bookBeingParsed = new PhysicalBook();
            int bookNumber = books.size();
            bookBeingParsed.setId("id_" + bookNumber);
        } else if ("author".equals(localName)) {
            bookBeingParsed.setAuthor("not_specified_yet", atts.getValue(0));
        }
    }

    private void setElementBeingParsed(String tag) {
        final boolean BEING_PARSED = true;
        tagToIsParsedNow.put(tag, BEING_PARSED);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        unsetElementBeingParsed(localName);

        if ("book".equals(localName)) {
            books.add(bookBeingParsed);
        }
    }

    private void unsetElementBeingParsed(String tag) {
        final boolean NOT_BEING_PARSED = false;
        tagToIsParsedNow.put(tag, NOT_BEING_PARSED);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String elementData = new String(ch, start, length).trim();
        if (isBeingProcessedNow("title")) {
            bookBeingParsed.setTitle(elementData);
        } else if (isBeingProcessedNow("price")) {
            bookBeingParsed.setPrice(Double.parseDouble(elementData));
        } else if (isBeingProcessedNow("year")) {
            Calendar releaseDate = Calendar.getInstance();
            releaseDate.set(Calendar.YEAR, Integer.parseInt(elementData));
            bookBeingParsed.setReleaseYear(Integer.parseInt(elementData));
        } else if (isBeingProcessedNow("author")) {
            bookBeingParsed.getAuthor().setName(elementData);
        } else if (isBeingProcessedNow("genre")) {
            bookBeingParsed.setGenre(elementData);
        } else if (isBeingProcessedNow("publisher")) {
            bookBeingParsed.setPublisher(elementData);
        } else if (isBeingProcessedNow("pages")) {
            bookBeingParsed.setPagesNumber(Integer.parseInt(elementData));
        } else if (isBeingProcessedNow("language")) {
            bookBeingParsed.setLanguage(elementData);
        } else if (isBeingProcessedNow("rating")) {
            bookBeingParsed.setRating(Double.parseDouble(elementData));
        } else if (isBeingProcessedNow("length")) {
            bookBeingParsed.setLength(Integer.parseInt(elementData));
        } else if (isBeingProcessedNow("width")) {
            bookBeingParsed.setWidth(Integer.parseInt(elementData));
        } else if (isBeingProcessedNow("covers")) {
            bookBeingParsed.setCoversType(elementData);
        }
    }

    private Boolean isBeingProcessedNow(String tag) {
        return tagToIsParsedNow.get(tag);
    }

    @Override
    public void endDocument() {

        try {
            for (PhysicalBook book : books) {
                outputWriter.write(book.parseToDigitalBookXML());

            }
            outputWriter.write("</bookstore>");
            outputWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Boolean> createTagsMap() throws IllegalAccessException {
        final boolean NOT_BEING_PARSED = false;
        Map<String, Boolean> tagToIsParsedNow = new HashMap<>();

        PhysicalBookTags tags = new PhysicalBookTags();
        for (Field field : tags.getClass().getDeclaredFields()) {
            String tag = (String) field.get(this);
            tagToIsParsedNow.put(tag, NOT_BEING_PARSED);
        }

        return tagToIsParsedNow;
    }
}


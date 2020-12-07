package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ContactData {

  public static final String CONTACT = "contact";
  public static final String FIRST_NAME = "first_name";
  public static final String LAST_NAME = "last_name";
  public static final String Address = "address";
  public static final String PHONE_NUMBER = "phone_number";
  public static final String NOTES = "notes";
  private static final String CONTACTS_FILE = "contacts.xml";



  private final ObservableList<Contact> contacts;
  /* for data binding */

  public ContactData() {
    contacts = FXCollections.observableArrayList();
    // *** initialized the contacts list here ***
  }

  // *** Added methods to add/delete/access contacts here ***
  public ObservableList<Contact> getContacts() {
    return contacts;
  }

  public void addContacts(Contact item) {
    contacts.add(item);
  }

  public void deleteContact(Contact item) {
    contacts.remove(item);
  }

  public void loadContacts() {
    try {
      // First, create a new XMLInputFactory
      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      // Setup a new eventReader
      InputStream in = new FileInputStream(CONTACTS_FILE);
      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
      // read the XML document
      Contact contact = null;

      while (eventReader.hasNext()) {
        XMLEvent event = eventReader.nextEvent();

        if (event.isStartElement()) {
          StartElement startElement = event.asStartElement();
          // If we have a contact item, we create a new contact
          if (startElement.getName().getLocalPart().equals(CONTACT)) {
            contact = new Contact();
            continue;
          }

          if (event.isStartElement()) {
            if (event.asStartElement().getName().getLocalPart().equals(FIRST_NAME)) {
              event = eventReader.nextEvent();
              assert contact != null;
              contact.setFirstName(event.asCharacters().getData());
              continue;
            }
          }
          if (event.asStartElement().getName().getLocalPart().equals(LAST_NAME)) {
            event = eventReader.nextEvent();
            assert contact != null;
            contact.setLastName(event.asCharacters().getData());
            continue;
          }
          if (event.isStartElement()) {
            if (event.asStartElement().getName().getLocalPart().equals(Address)) {
              event = eventReader.nextEvent();
              assert contact != null;
              contact.setaddress(event.asCharacters().getData());
              continue;
            }
          }
          if (event.asStartElement().getName().getLocalPart().equals(PHONE_NUMBER)) {
            event = eventReader.nextEvent();
            assert contact != null;
            contact.setPhoneNumber(event.asCharacters().getData());
            continue;
          }

          if (event.asStartElement().getName().getLocalPart().equals(NOTES)) {
            event = eventReader.nextEvent();
            assert contact != null;
            contact.setNotes(event.asCharacters().getData());
            continue;
          }
        }

        // If we reach the end of a contact element, we add it to the list
        if (event.isEndElement()) {
          EndElement endElement = event.asEndElement();
          if (endElement.getName().getLocalPart().equals(CONTACT)) {
            contacts.add(contact);
          }
        }
      }
    } catch (FileNotFoundException e) {
      // e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  public void saveContacts() {

    try {
      // created an XMLOutputFactory
      XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
      // created XMLEventWriter
      XMLEventWriter eventWriter;
      eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(CONTACTS_FILE));
      // created an EventFactory
      XMLEventFactory eventFactory = XMLEventFactory.newInstance();
      XMLEvent end = eventFactory.createDTD("\n");
      // created and wrote Start Tag
      StartDocument startDocument = eventFactory.createStartDocument();
      eventWriter.add(startDocument);
      eventWriter.add(end);

      StartElement contactsStartElement = eventFactory.createStartElement("", "", "contacts");
      eventWriter.add(contactsStartElement);
      eventWriter.add(end);

      for (Contact contact : contacts) {
        contact.saveContact(eventWriter, eventFactory, this);
      }

      eventWriter.add(eventFactory.createEndElement("", "", "contacts"));
      eventWriter.add(end);
      eventWriter.add(eventFactory.createEndDocument());
      eventWriter.close();
    } catch (FileNotFoundException e) {
      System.out.println("Problem with Contacts file: " + e.getMessage());
      e.printStackTrace();
    } catch (XMLStreamException e) {
      System.out.println("Problem writing contact: " + e.getMessage());
      e.printStackTrace();
    }
  }

  void createNode(XMLEventWriter eventWriter, String name, String value)
      throws XMLStreamException {

    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    XMLEvent end = eventFactory.createDTD("\n");
    XMLEvent tab = eventFactory.createDTD("\t");
    // created Start node
    StartElement sElement = eventFactory.createStartElement("", "", name);
    eventWriter.add(tab);
    eventWriter.add(sElement);
    // created Content
    Characters characters = eventFactory.createCharacters(value);
    eventWriter.add(characters);
    // created End node
    EndElement eElement = eventFactory.createEndElement("", "", name);
    eventWriter.add(eElement);
    eventWriter.add(end);
  }
}

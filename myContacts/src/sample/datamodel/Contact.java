package sample.datamodel;
/* Created by Neel Patel on 10/27/19. */
import javafx.beans.property.SimpleStringProperty;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class Contact {
  private final SimpleStringProperty firstName = new SimpleStringProperty("");
  private final SimpleStringProperty lastName = new SimpleStringProperty("");
  private final SimpleStringProperty phoneNumber = new SimpleStringProperty("");
  private final SimpleStringProperty notes = new SimpleStringProperty("");

  public Contact() {}

  public Contact(String firstName, String lastName, String phoneNumber, String notes) {
    this.firstName.set(firstName);
    this.lastName.set(lastName);
    this.phoneNumber.set(phoneNumber);
    this.notes.set(notes);
  }

  public String getFirstName() {
    return firstName.get();
  }

  public void setFirstName(String firstName) {
    this.firstName.set(firstName);
  }

  public String getLastName() {
    return lastName.get();
  }

  public void setLastName(String lastName) {
    this.lastName.set(lastName);
  }

  public String getPhoneNumber() {
    return phoneNumber.get();
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber.set(phoneNumber);
  }

  public String getNotes() {
    return notes.get();
  }

  public void setNotes(String notes) {
    this.notes.set(notes);
  }

  @Override
  public String toString() {
    return "Contact{"
        + "firstName="
        + firstName
        + ", lastName="
        + lastName
        + ", phoneNumber="
        + phoneNumber
        + ", notes="
        + notes
        + '}';
  }

  void saveContact(
      XMLEventWriter eventWriter, XMLEventFactory eventFactory, ContactData contactData)
      throws XMLStreamException {

    XMLEvent end = eventFactory.createDTD("\n");

    // created contact open tag
    StartElement configStartElement = eventFactory.createStartElement("", "", ContactData.CONTACT);
    eventWriter.add(configStartElement);
    eventWriter.add(end);
    // Wrote the different nodes
    contactData.createNode(eventWriter, ContactData.FIRST_NAME, getFirstName());
    contactData.createNode(eventWriter, ContactData.LAST_NAME, getLastName());
    contactData.createNode(eventWriter, ContactData.PHONE_NUMBER, getPhoneNumber());
    contactData.createNode(eventWriter, ContactData.NOTES, getNotes());

    eventWriter.add(eventFactory.createEndElement("", "", ContactData.CONTACT));
    eventWriter.add(end);
  }
}

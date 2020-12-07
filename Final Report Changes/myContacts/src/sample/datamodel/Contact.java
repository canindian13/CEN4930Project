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
  private final SimpleStringProperty address = new SimpleStringProperty("");
  private final SimpleStringProperty phoneNumber = new SimpleStringProperty("");
  private final SimpleStringProperty notes = new SimpleStringProperty("");

  public Contact() {}

  public Contact(String firstName, String lastName,String address, String phoneNumber, String notes) {
    this.firstName.set(firstName);
    this.address.set(address);
    this.lastName.set(lastName);
    this.phoneNumber.set(phoneNumber);
    this.notes.set(notes);
  }

  public static String getAddress() {
    return null;
  }


  @Override
  public String toString() {
    return "Contact{"
        + "firstName="
        + firstName
        + ", lastName="
        + lastName
        +", address="
        + address
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

  public String getNotes() {
    return null;
  }

  public String getPhoneNumber() {
    return null;
  }

  public String getLastName() {
    return null;
  }

  public String getFirstName() {

    return null;
  }

  public void setFirstName(String text) {
  }

  public void setLastName(String text) {
  }

  public void setaddress(String text) {
  }

  public void setPhoneNumber(String text) {
  }

  public void setNotes(String text) {
  }
}

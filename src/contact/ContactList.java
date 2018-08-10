package contact;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

public class ContactList {
    private static ContactList contactList = new ContactList();
    private ObservableList<ContactData> contacts;

    private ContactList(){
        contacts = FXCollections.observableArrayList();
        getContacts();
    }

    public static ContactList getContactList(){
        return contactList;
    }

    private void getContacts(){
        Scanner inFile;
        try {
            inFile = new Scanner(new File("C:\\Users\\ngyen\\IdeaProjects\\ContactList\\src\\contact\\contactData"));
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
            return;
        }

        String[] contactInfo;
        while(inFile.hasNextLine()){
            contactInfo = inFile.nextLine().split(", ");
            // Deleting all the trailing & leading whitespace.
            for(int i = 0; i < contactInfo.length; i++){
                contactInfo[i] = contactInfo[i].trim();
            }

            if(contactInfo.length == 5) {
                ContactData contact = new ContactData(new SimpleStringProperty(contactInfo[0]), new SimpleStringProperty(contactInfo[1]),
                        new SimpleStringProperty(contactInfo[2]), new SimpleStringProperty(contactInfo[3]), new SimpleStringProperty(contactInfo[4]));
                contacts.add(contact);
            }else{
                throw new IllegalStateException("All fields of contact information must be filled up correctly.");
            }
        }

        inFile.close();
    }

    public void storeContact(){
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter("C:\\Users\\ngyen\\IdeaProjects\\ContactList\\src\\contact\\contactData");
            String contact;
            for(ContactData contactData : ContactList.getContactList().getContactsInfo()){
                contact = contactData.getFirstName() + ", " + contactData.getLastName() + ", " + contactData.getPhone()
                        + ", " + contactData.getEmail() + ", " + contactData.getNotes();
                fileWriter.write(contact + "\n");
            }
        } catch (IOException ex){
            ex.printStackTrace();
            return;
        } finally {
            try{
                fileWriter.close();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public SortedList<ContactData> getSortedList(){
        return contacts.sorted(new Comparator<ContactData>() {
            @Override
            public int compare(ContactData o1, ContactData o2) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        });
    }

    public ObservableList<ContactData> getContactsInfo() {
        return contacts;
    }

}

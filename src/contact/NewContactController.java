package contact;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class NewContactController {
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField phoneNum;

    @FXML
    private TextField email;

    @FXML
    private TextField remark;

    public ContactData addNewContact(){
        SimpleStringProperty first = new SimpleStringProperty(firstName.getText().trim());
        SimpleStringProperty last = new SimpleStringProperty(lastName.getText().trim());
        SimpleStringProperty phone = new SimpleStringProperty(phoneNum.getText().trim());
        SimpleStringProperty em = new SimpleStringProperty(email.getText().trim());
        SimpleStringProperty rem = new SimpleStringProperty(remark.getText().trim());
        ContactData contactData = new ContactData(first, last, phone, em, rem);
        ContactList.getContactList().getContactsInfo().add(contactData);
        return contactData;
    }

    public boolean notFillUp(){
        return firstName.getText().trim().isEmpty()|| lastName.getText().trim().isEmpty() || phoneNum.getText().trim().isEmpty()
                || email.getText().trim().isEmpty() || remark.getText().trim().isEmpty();
    }

    public void initialize(){
        firstName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(notFillUp())
                    Controller.dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                else
                    Controller.dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            }
        });

        lastName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(notFillUp())
                    Controller.dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                else
                    Controller.dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            }
        });

        phoneNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(notFillUp())
                    Controller.dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                else
                    Controller.dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            }
        });

        email.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(notFillUp())
                    Controller.dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                else
                    Controller.dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            }
        });

        remark.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(notFillUp())
                    Controller.dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                else
                    Controller.dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            }
        });
    }

    public void setInfo(ContactData contactData){
        firstName.setText(contactData.getFirstName());
        lastName.setText(contactData.getLastName());
        phoneNum.setText(contactData.getPhone());
        email.setText(contactData.getEmail());
        remark.setText(contactData.getNotes());
    }

    public ContactData replaceContact(ContactData replaced){
        ContactData contactData = new ContactData(new SimpleStringProperty(firstName.getText()),
                new SimpleStringProperty(lastName.getText()), new SimpleStringProperty(phoneNum.getText()),
                new SimpleStringProperty(email.getText()), new SimpleStringProperty(remark.getText()));

        ContactList.getContactList().getContactsInfo().remove(replaced);
        ContactList.getContactList().getContactsInfo().add(contactData);
        return contactData;
    }
}

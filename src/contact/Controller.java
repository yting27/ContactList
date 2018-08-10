package contact;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TableView<ContactData> tableView;
    @FXML
    private TableColumn<ContactData, String> firstName;

    @FXML
    private TableColumn<ContactData, String> lastName;

    @FXML
    private TableColumn<ContactData, String> phone;

    @FXML
    private TableColumn<ContactData, String> email;

    @FXML
    private TableColumn<ContactData, String> remark;

    public static Dialog<ButtonType> dialog;

    @FXML
    public void initialize(){
        firstName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ContactData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ContactData, String> param) {
                return param.getValue().firstNameProperty();
            }
        });

        lastName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ContactData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ContactData, String> param) {
                return param.getValue().lastNameProperty();
            }
        });

        phone.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ContactData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ContactData, String> param) {
                return param.getValue().phoneProperty();
            }
        });

        email.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ContactData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ContactData, String> param) {
                return param.getValue().emailProperty();
            }
        });

        remark.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ContactData, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ContactData, String> param) {
                return param.getValue().notesProperty();
            }
        });

        tableView.setItems(ContactList.getContactList().getSortedList());
        tableView.getSelectionModel().selectFirst();

        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        editContact();
                    }
                }
            }
        });
    }

    @FXML
    public void addButtonOnAction(){
        dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addNewContactWindow.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException ex){
            ex.printStackTrace();
            return;
        }

        dialog.setTitle("Add New Contact");
        NewContactController newController = fxmlLoader.getController();
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

        Optional<ButtonType> returnValue = dialog.showAndWait();
        if(returnValue.isPresent() && returnValue.get() == ButtonType.OK){
            ContactData contactData = newController.addNewContact();
            tableView.getSelectionModel().select(contactData);
        }
    }

    @FXML
    public void deleteItem(){
        ContactData deletedItem = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete a contact");
        alert.setHeaderText("Permanently delete a contact");
        alert.setContentText("Are you sure to delete " + deletedItem.getFirstName() + "?");

        Optional<ButtonType> response = alert.showAndWait();
        if(response.isPresent() && (response.get() == ButtonType.OK)){
            ContactList.getContactList().getContactsInfo().remove(deletedItem);
        }
    }

    @FXML
    public void editContact(){
        ContactData selectedItem = tableView.getSelectionModel().getSelectedItem();
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addNewContactWindow.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException ex){
            ex.printStackTrace();
            return;
        }

        dialog.setTitle("Edit contact information");
        NewContactController newController = fxmlLoader.getController();
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        newController.setInfo(selectedItem);

        Optional<ButtonType> returnValue = dialog.showAndWait();
        if(returnValue.isPresent() && returnValue.get() == ButtonType.OK){
            ContactData contactData = newController.replaceContact(selectedItem);
            tableView.getSelectionModel().select(contactData);
        }
    }
}

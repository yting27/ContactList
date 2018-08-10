package contact;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ESCAPE){
                    Platform.exit();
                }
            }
        });
        primaryStage.setTitle("Contact List");
        primaryStage.setScene(new Scene(root, 800, 600));
        // The format of URL is below. (file == C), no need double backslash.
        Image image = new Image("file:/Users/ngyen/IdeaProjects/ContactList/src/contact/phone-book.png");
        primaryStage.getIcons().add(image);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void stop(){
        ContactList.getContactList().storeContact();
    }
}

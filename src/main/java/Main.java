/**
 * IEMLS - Main.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * TODO: Commenta algo
 *
 */
public class Main extends Application {
    private static String TITLE = "IEMLS";


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(ClassLoader.getSystemClassLoader().getResource("Main.fxml"));

        Scene scene = new Scene(root);
        //scene.getStylesheets().add(ClassLoader.getSystemClassLoader().getResource("ruby-highlighting.css").toExternalForm());
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

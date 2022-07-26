package com.example.loginform;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
    static boolean answear;
    public  static boolean display(String title , String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);// this to block user interaction with other windows when you open this window
        window.setTitle(title);
        window.setMinWidth(200);
        Label label1 = new Label(message);
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setOnAction(e -> {
            answear = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answear =  false;
            window.close();
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1,noButton,yesButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); // display this window and before you exit you have to close the window
        return answear;
    }
}



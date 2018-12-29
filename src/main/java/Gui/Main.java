package Gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller control = new Controller();
        primaryStage.setTitle("葫芦娃大战妖精");
        primaryStage.getIcons().add(new Image("icons/Title.png",50,50,true,true));
        primaryStage.setScene(new Scene(control.createContent()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
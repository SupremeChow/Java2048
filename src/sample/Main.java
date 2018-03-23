package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller gameController;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));

        /*
        Parent root = fxmlLoader.load(getClass().getResource("sample.fxml"));
        */
        Parent root = (Parent)fxmlLoader.load();
        primaryStage.setTitle("2048");

        Scene scene = new Scene(root, 800, 800);
        final Controller gameController = fxmlLoader.getController();

        scene.getStylesheets().add(getClass().getResource("2048Style.css").toExternalForm());
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                GridController gridControl = gameController.getGrid();
                boolean moveMade;
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                    try {
                        System.out.println("Pressing UP!");
                        moveMade=gridControl.shiftUp();
                        //gameController.updateGrid();
                        gameController.getGrid().flushMerge();
                        if(moveMade)
                            gameController.iterateTurn();
                    }
                    catch (Exception e)
                    {
                        System.out.println("NOT TIME YET!@");
                    }
                } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                    try{
                        System.out.println("Pressing DOWN!");
                        moveMade=gridControl.shiftDown();
                       // gameController.updateGrid();
                        gameController.getGrid().flushMerge();
                        if(moveMade)
                            gameController.iterateTurn();
                    }

                    catch (Exception e)
                            {
                                    System.out.println("NOT TIME YET!@");
                    }
                } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                    try{
                        System.out.println("Pressing LEFT!");
                        moveMade=gridControl.shiftLeft();
                       // gameController.updateGrid();
                        gameController.getGrid().flushMerge();
                        if(moveMade)
                            gameController.iterateTurn();
                    }

                    catch (Exception e)
                            {
                                    System.out.println("NOT TIME YET!@");
                    }
                } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                    try{
                        System.out.println("Pressing RIGHT!");
                        moveMade=gridControl.shiftRight();
                       // gameController.updateGrid();
                        gameController.getGrid().flushMerge();
                        if(moveMade)
                            gameController.iterateTurn();
                    }

                    catch (Exception e)
                            {
                                    System.out.println("NOT TIME YET!@");
                    }
                }
            }
        });
        primaryStage.setScene(scene);




        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }




}

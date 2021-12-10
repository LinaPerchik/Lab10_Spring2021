/**
 *
 * @author SivasamyA
 */
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.input.*;
import java.util.Random;
import javafx.scene.control.*;
import java.util.*;

public class Lab10 extends Application {
    public static final int SCENE_WIDTH = 1024;
    public static final int SCENE_HEIGHT = 640;
    public static final int MAX_VALUE = Short.MAX_VALUE;
    public static int num = 1;
    public void start(Stage primaryStage)    {
        Pane pane = new Pane();
        MyAVLTreeSetLab10<Integer> myTree = new MyAVLTreeSetLab10();



        Random rand = new Random();

        pane.setOnMouseClicked(evt -> {
            if (evt.getButton() == MouseButton.SECONDARY) {
                int rint = rand.nextInt(MAX_VALUE);

                System.out.print("Adding " + rint + "...");
                boolean result = myTree.add(num);
                //boolean result1 = myTree.add(2);
                //boolean result2 = myTree.add(3);
                //System.out.println(myTree);
                num = num + 1;
            }
        });



        pane.getChildren().add(myTree);

        Scene scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("271 Lab10");
        primaryStage.show();
    }
}
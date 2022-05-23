package pt.isec.pa.apoio_poe.model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainJFX extends Application {
    private final ModelManager model;

    public MainJFX() {
        model = new ModelManager();
    }

    @Override
    public void start(Stage stage) throws Exception {
        RootPane root = new RootPane(model);
        Scene scene = new Scene(root,600,400);
        stage.setScene(scene);
        stage.setTitle("App");
        stage.setMinWidth(400);
        stage.show();

        /*Stage stage2 = new Stage();
        RootPane root2 = new RootPane(model);
        Scene scene2 = new Scene(root2,600,400);
        stage2.setScene(scene2);
        stage2.setTitle("TeoStateJFX2");
        stage2.setMinWidth(400);
        stage2.show();*/
    }
}

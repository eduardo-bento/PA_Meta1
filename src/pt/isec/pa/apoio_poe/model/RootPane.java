package pt.isec.pa.apoio_poe.model;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class RootPane extends BorderPane {
    ModelManager model;

    public RootPane(ModelManager model) {
        this.model = model;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        StackPane stackPane = new StackPane(
                new Configuration(model),new ConfigurationClosed(model),
                new StudentFx(model));
        this.setCenter(stackPane);
        setTop(model.getTopMenu());
    }

    private void registerHandlers() { }

    private void update() { }
}

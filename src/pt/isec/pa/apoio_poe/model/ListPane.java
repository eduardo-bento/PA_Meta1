package pt.isec.pa.apoio_poe.model;

import javafx.scene.control.ListView;
import pt.isec.pa.apoio_poe.model.Student.Student;

import java.util.List;

public class ListPane extends ListView<String> {
    ModelManager model;

    public ListPane(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.getItems().clear();
        this.getItems().addAll(model.querying());
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
    }

    private void createViews() {
        this.getItems().addAll(model.querying());
    }

}

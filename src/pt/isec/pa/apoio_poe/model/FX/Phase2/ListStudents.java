package pt.isec.pa.apoio_poe.model.FX.Phase2;

import javafx.scene.control.ListView;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class ListStudents extends ListView<String> {
    ModelManager model;

    public ListStudents(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.getItems().clear();
        this.getItems().addAll(model.getListOfStudents());
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
    }

    private void createViews() {
        this.getItems().addAll(model.getListOfStudents());
    }
}

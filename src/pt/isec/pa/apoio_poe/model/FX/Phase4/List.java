package pt.isec.pa.apoio_poe.model.FX.Phase4;

import javafx.scene.control.ListView;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class List extends ListView<String> {
    protected ModelManager model;

    public List(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.getItems().clear();
        this.getItems().addAll(model.getAttributionTeacherData());
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
    }

    private void createViews() {
        this.getItems().addAll(model.getAttributionTeacherData());
    }
}

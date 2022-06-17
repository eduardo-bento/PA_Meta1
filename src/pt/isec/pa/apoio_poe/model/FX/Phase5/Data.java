package pt.isec.pa.apoio_poe.model.FX.Phase5;

import javafx.scene.control.ListView;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class Data extends ListView<String> {
    ModelManager model;

    public Data(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.getItems().clear();
        if (model.getAttributionTeacherData() != null){
            this.getItems().addAll(model.getAttributionTeacherData());
        }
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
    }

    private void createViews() {
        this.getItems().addAll(model.getData());
    }
}

package pt.isec.pa.apoio_poe.model.FX;

import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class ListPane extends ListView<Object> {
    protected ModelManager model;
    public ListPane(ModelManager model){
        this.model = model;
        registerHandlers();
    }

    private void update() {
        this.getItems().clear();
        if (model.querying() != null){
            this.getItems().addAll(model.querying());
        }
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_UPDATE, evt -> update());
    }

}

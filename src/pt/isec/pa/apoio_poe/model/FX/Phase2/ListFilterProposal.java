package pt.isec.pa.apoio_poe.model.FX.Phase2;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

import java.util.List;

public class ListFilterProposal extends VBox {
    ModelManager model;
    Label label;
    public ListFilterProposal(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        label.setText(model.getFilterList(List.of(1)));
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
    }

    private void createViews() {
        label = new Label();
    }

}

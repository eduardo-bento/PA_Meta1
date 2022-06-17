package pt.isec.pa.apoio_poe.model.FX.Phase2;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;

import java.util.List;

public class ListFilterProposal extends VBox {
    ModelManager model;
    CheckBox comboBox;
    MyButton button;
    Label label;
    public ListFilterProposal(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        button.setOnAction(event -> {
        label.setText(model.getFilterList(List.of(1)));
        });

    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
    }

    private void createViews() {
        label = new Label();
        button = new MyButton("Insert");

        //this.getChildren().addAll(label, comboBox,button);
    }
}

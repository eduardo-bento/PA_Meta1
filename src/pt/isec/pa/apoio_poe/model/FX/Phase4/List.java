package pt.isec.pa.apoio_poe.model.FX.Phase4;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class List extends VBox {
    private ModelManager model;
    private HBox teachersValues;
    public List(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
    }

    private void update(){
        teachersValues = new HBox(new VBox(new Label("Average"),new Label(String.valueOf(model.getTeachersAverage()))),
                new VBox(new Label("Lowest"),new Label(String.valueOf(model.getTeachersLowest()))),
                new VBox(new Label("Highest"),new Label(String.valueOf(model.getTeachersHighest()))));
    }

    private void createViews() {
        FinalProposalWithoutTeacher finalProposalWithoutTeacher = new FinalProposalWithoutTeacher(model);
        FinalProposalWithTeacher finalProposalWithTeacher = new FinalProposalWithTeacher(model);

        teachersValues = new HBox(new VBox(new Label("Average"),new Label(String.valueOf(model.getTeachersAverage()))),
                new VBox(new Label("Lowest"),new Label(String.valueOf(model.getTeachersLowest()))),
                new VBox(new Label("Highest"),new Label(String.valueOf(model.getTeachersHighest()))));

        teachersValues.setSpacing(10);
        teachersValues.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(20));
        this.getChildren().addAll(new VBox(new Label("Without teachers"),finalProposalWithoutTeacher),new VBox(new Label("With teachers"),finalProposalWithTeacher,teachersValues));
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
    }
}

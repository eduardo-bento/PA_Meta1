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
    Label average,highest,lowest;

    public List(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
    }

    private void update(){
        average.setText(String.valueOf(model.getTeachersAverage()));
        lowest.setText(String.valueOf(model.getTeachersLowest()));
        highest.setText(String.valueOf(model.getTeachersHighest()));
    }

    private void createViews() {
        FinalProposalWithoutTeacher finalProposalWithoutTeacher = new FinalProposalWithoutTeacher(model);
        FinalProposalWithTeacher finalProposalWithTeacher = new FinalProposalWithTeacher(model);

        average = new Label(String.valueOf(model.getTeachersAverage()));
        lowest = new Label(String.valueOf(model.getTeachersLowest()));
        highest = new Label(String.valueOf(model.getTeachersHighest()));

        teachersValues = new HBox(new VBox(new Label("Average"),average),
                new VBox(new Label("Lowest"),lowest),
                new VBox(new Label("Highest"),highest));

        teachersValues.setAlignment(Pos.CENTER);
        teachersValues.setSpacing(10);

        this.setPadding(new Insets(20));
        this.getChildren().addAll(new VBox(new Label("Without teachers"),finalProposalWithoutTeacher),new VBox(new Label("With teachers"),finalProposalWithTeacher,teachersValues));
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
    }
}

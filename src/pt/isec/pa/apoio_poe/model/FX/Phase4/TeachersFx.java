package pt.isec.pa.apoio_poe.model.FX.Phase4;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.Helper.ReadFromFile;
import pt.isec.pa.apoio_poe.model.FX.Phase2.ListFilterProposal;
import pt.isec.pa.apoio_poe.model.FX.Phase5.ExportToFile;

public class TeachersFx extends BorderPane {
    ModelManager model;
    Label teacherData;
    Label listOfTeachers;
    MyButton previous,next, automaticAttribution,closePhase,undo,redo;
    ManualAttribution manualAttribution;
    ManualRemove manualRemove;
    ExportToFile export;

    public TeachersFx(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.TEACHER_ATTRIBUTION_PHASE);
        listOfTeachers.setText(model.getTeacherList());
        teacherData.setText(model.getAttributionTeacherData());
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());

        previous.setOnAction(event -> {
            model.back();
        });

        closePhase.setOnAction(event ->{
            model.closePhase();
        });

        automaticAttribution.setOnAction(event ->{
            model.automaticTeacherAttribution();
        });

        undo.setOnAction(event -> {
            model.undo();
        });

        redo.setOnAction(event -> {
            model.redo();
        });
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");

        previous = new MyButton("Back");
        automaticAttribution = new MyButton("Automatic Attribution");
        manualAttribution = new ManualAttribution(model);
        manualRemove = new ManualRemove(model);
        closePhase = new MyButton("Close Phase");
        export = new ExportToFile(model);

        listOfTeachers = new Label();
        listOfTeachers.setText(model.getListOfStudents());
        listOfTeachers.setPadding(new Insets(10));
        listOfTeachers.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");
        listOfTeachers.setAlignment(Pos.CENTER);

        teacherData = new Label();
        teacherData.setText(model.getAttributionTeacherData());
        teacherData.setPadding(new Insets(10));
        teacherData.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");
        teacherData.setAlignment(Pos.CENTER);

        undo = new MyButton("Undo");
        redo = new MyButton("Redo");

        HBox tools = new HBox(undo,redo);
        tools.setAlignment(Pos.CENTER);
        tools.setSpacing(10);

        VBox attribution = new VBox(manualRemove,manualAttribution,tools);
        attribution.setAlignment(Pos.CENTER);
        attribution.setSpacing(10);

        VBox r = new VBox(export,automaticAttribution);
        r.setAlignment(Pos.CENTER);
        r.setSpacing(10);

        VBox lists = new VBox(listOfTeachers,teacherData);
        lists.setSpacing(10);
        lists.setAlignment(Pos.CENTER);

        HBox center = new HBox(r,attribution,lists);
        center.setAlignment(Pos.CENTER);
        setCenter(center);

        VBox right = new VBox(previous,closePhase);
        right.setSpacing(10);

        setRight(right);
    }
}

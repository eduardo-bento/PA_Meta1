package pt.isec.pa.apoio_poe.model.FX.Phase4;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.Candidacy.Candidacy;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;

import java.util.Arrays;
import java.util.HashSet;



public class ManualAttribution extends VBox {
    protected final ModelManager model;

    protected final TextField teacherField = new TextField();
    protected final TextField proposalField = new TextField();
    protected final Button insert = new Button("Insert");

    public ManualAttribution(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
    }

    private void registerHandlers(){
        insert.setOnAction(event -> {
            String prosposalId,teacherId;
            prosposalId = proposalField.getText();
            teacherId = teacherField.getText();

            if (proposalField.getText().isBlank()){
                proposalField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                proposalField.setStyle(null);
            }
            if (teacherField.getText().isBlank()){
                teacherField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                teacherField.setStyle(null);
            }

            model.manualTeacherAttribution(prosposalId,teacherId);
            });
    }

    private void createViews() {
        proposalField.setPromptText("Enter the proposals id.");
        teacherField.setPromptText("Enter teacher id.");
        this.getChildren().addAll(insert,teacherField,proposalField);
        insert.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");
        setSpacing(10);
        setAlignment(Pos.CENTER);
    }
}

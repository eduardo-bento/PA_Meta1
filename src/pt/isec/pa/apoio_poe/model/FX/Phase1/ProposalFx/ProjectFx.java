package pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Project;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;

import java.util.ArrayList;
import java.util.List;

public class ProjectFx extends InsertProposal {
    private final TextField teacherField = new TextField();
    private  MyButton ras,si,da;
    public ProjectFx(ModelManager model) {
        super(model);
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
    }

    private void registerHandlers() {
        ras.setOnAction(event -> {
            if (ras.getStyle().contains("green")){
                ras.setStyle("-fx-background-color: #D0C9C0;");
            } else{
                ras.setStyle("-fx-background-color: green;");
            }
        });

        da.setOnAction(event -> {
            if (da.getStyle().contains("green")){
                da.setStyle("-fx-background-color: #D0C9C0;");
            } else{
                da.setStyle("-fx-background-color: green;");
            }
        });

        si.setOnAction(event -> {
            if (si.getStyle().contains("green")){
                si.setStyle("-fx-background-color: #D0C9C0;");
            } else{
                si.setStyle("-fx-background-color: green;");
            }
        });
        insert.setOnAction(event -> {
            Long studentId = -1l;
            if (idField.getText().isEmpty()){
                idField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                idField.setStyle(null);
            }
            if (titleField.getText().isEmpty()){
                titleField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else{
                titleField.setStyle(null);
            }
            if (titleField.getText().isEmpty()){
                titleField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else{
                titleField.setStyle(null);
            }
            if (!studentField.getText().isEmpty()){
                try {
                    studentId = Long.valueOf(studentField.getText());
                    studentField.setStyle(null);
                } catch (NumberFormatException e){
                    studentField.setStyle("-fx-background-color: #FF0000;");
                    return;
                }
            }
            if (teacherField.getText().isEmpty()) {
                teacherField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                teacherField.setStyle(null);
            }
            String branches = "";
            if (ras.getStyle().contains("green")){
                branches += "RAS";
            }
            if (da.getStyle().contains("green")){
                if (!branches.isEmpty()){
                    branches += "|";
                }
                branches += "DA";
            }
            if (si.getStyle().contains("green")){
                if (!branches.isEmpty()){
                    branches += "|";
                }
                branches += "SI";
            }
            if(!model.insert(new Project(idField.getText(),titleField.getText(),studentId,branches,teacherField.getText()))){
                insert.setStyle("-fx-background-color: #FF0000;");
            } else{
                insert.setStyle("-fx-background-color: #D0C9C0;");
            }
        });
    }

    public TextField getTeacherField() {
        return teacherField;
    }

    private void createViews() {
        teacherField.setPromptText("Enter your teacher.");

        ras = new MyButton("RAS");
        si = new MyButton("SI");
        da = new MyButton("DA");
        HBox branchs = new HBox(ras,si,da);
        branchs.setSpacing(10);
        branchs.setAlignment(Pos.CENTER);
        this.getChildren().addAll(teacherField,branchs);

    }
}

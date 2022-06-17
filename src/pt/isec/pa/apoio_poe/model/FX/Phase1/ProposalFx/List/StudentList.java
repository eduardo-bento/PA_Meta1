package pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx.List;

import javafx.scene.control.ChoiceBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.InterShip;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import javafx.scene.control.ListView;
import pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx.*;
import pt.isec.pa.apoio_poe.model.Log;

public class StudentList extends ListView<Student> {
    private ModelManager model;
    private ProjectFx project;
    private InterShipFx interShip;
    private SelfProposalFx proposal;
    private ChoiceBox<String> option;
    public StudentList(ModelManager model, ProjectFx project, InterShipFx interShip, SelfProposalFx proposal, ChoiceBox<String> option){
        this.model = model;
        this.project = project;
        this.interShip = interShip;
        this.proposal = proposal;
        this.option = option;

        createViews();
        registerHandlers();


    }
    private void createViews(){
        this.setMaxHeight(200);
    }
    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_UPDATE, evt -> update());

        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                Student student = this.getSelectionModel().getSelectedItem();
                switch (option.getValue()){
                    case "Project" ->  project.getStudentField().setText(String.valueOf(student.getId()));
                    case "InterShip" -> interShip.getStudentField().setText(String.valueOf(student.getId()));
                    case "SelfProposal" -> proposal.getStudentField().setText(String.valueOf(student.getId()));
                }
            }
        });
    }

    private void update() {
        this.getItems().clear();
        if (model.getStudents() != null){
            this.getItems().addAll(model.getStudents());
        }
    }

    public Student getSelected(){
        if (!model.getStudents().isEmpty() && this.getSelectionModel().getSelectedItem() == null){
            this.setStyle("-fx-background-color: #FF0000;");
            Log.getInstance().addMessage("You need to select one of the teachers to change the name");
            return null;
        }
        this.setStyle(null);
        return (Student) this.getSelectionModel().getSelectedItem();
    }
}

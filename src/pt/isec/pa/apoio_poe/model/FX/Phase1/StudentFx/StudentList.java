package pt.isec.pa.apoio_poe.model.FX.Phase1.StudentFx;

import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.FX.Helper.ListPane;
import pt.isec.pa.apoio_poe.model.Log;

public class StudentList extends ListPane {
    public StudentList(ModelManager model) {
        super(model);
        registerHandlers();
    }

    private void registerHandlers() {
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                Student student = (Student) this.getSelectionModel().getSelectedItem();
                model.remove(student.getId());
            }
        });
    }

    public Student getSelected(){
        if (!model.querying().isEmpty() && this.getSelectionModel().getSelectedItem() == null){
            this.setStyle("-fx-background-color: #FF0000;");
            Log.getInstance().addMessage("You need to select one of the teachers to change the name");
            return null;
        }
        this.setStyle(null);
        return (Student) this.getSelectionModel().getSelectedItem();
    }
}

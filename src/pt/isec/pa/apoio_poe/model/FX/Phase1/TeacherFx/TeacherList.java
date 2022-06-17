package pt.isec.pa.apoio_poe.model.FX.Phase1.TeacherFx;

import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;
import pt.isec.pa.apoio_poe.model.FX.ListPane;
import pt.isec.pa.apoio_poe.model.Log;

public class TeacherList extends ListPane {
    public TeacherList(ModelManager model) {
        super(model);
        registerHandlers();
    }

    private void registerHandlers() {
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                Teacher teacher = (Teacher) this.getSelectionModel().getSelectedItem();
                model.remove(teacher.getEmail());
            }
        });
    }

    public Teacher getSelected(){
        if (!model.querying().isEmpty() && this.getSelectionModel().getSelectedItem() == null){
            this.setStyle("-fx-background-color: #FF0000;");
            Log.getInstance().addMessage("You need to select one of the teachers to change the name");
            return null;
        }
        this.setStyle(null);
        return (Teacher) this.getSelectionModel().getSelectedItem();
    }
}

package pt.isec.pa.apoio_poe.model.FX.Phase4;

import javafx.scene.control.ListView;
import pt.isec.pa.apoio_poe.model.Data.FinalProposal.FinalProposal;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;


public class FinalProposalWithTeacher extends ListView<FinalProposal> {
    private final ModelManager model;

    public FinalProposalWithTeacher(ModelManager model) {
        this.model = model;
        this.setMaxHeight(150);
        registerHandlers();
    }
    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    }

    private void update() {
        this.getItems().clear();
        if (model.getFinalProposalWithTeacherList() != null){
            this.getItems().addAll(model.getFinalProposalWithTeacherList());
        }
    }
}

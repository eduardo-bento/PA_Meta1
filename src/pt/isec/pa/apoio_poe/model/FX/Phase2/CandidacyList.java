package pt.isec.pa.apoio_poe.model.FX.Phase2;

import pt.isec.pa.apoio_poe.model.Data.Candidacy.Candidacy;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.ListPane;


public class CandidacyList extends ListPane {
    public CandidacyList(ModelManager model) {
        super(model);
        registerHandlers();
    }

    private void registerHandlers() {
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                Candidacy candidacy = (Candidacy) this.getSelectionModel().getSelectedItem();
                model.remove(candidacy.getStudentId());
            }
        });
    }

    public Candidacy getSelected(){
        return (Candidacy) this.getSelectionModel().getSelectedItem();
    }
}

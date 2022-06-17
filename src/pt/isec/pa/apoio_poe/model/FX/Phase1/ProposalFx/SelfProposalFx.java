package pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx;

import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.InterShip;
import pt.isec.pa.apoio_poe.model.Data.Proposals.SelfProposal;

public class SelfProposalFx extends InsertProposal{
    public SelfProposalFx(ModelManager model) {
        super(model);
        registerHandlers();
    }

    private void registerHandlers(){
        insert.setOnAction(event -> {
            Long id;
            if (idField.getText().isBlank()) {
                idField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                idField.setStyle(null);
            }
            if (titleField.getText().isBlank()) {
                titleField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                titleField.setStyle(null);
            }
            try {
                id = Long.valueOf(studentField.getText());
                studentField.setStyle(null);
                if(!model.insert(new SelfProposal(idField.getText(),titleField.getText(),id))){
                    insert.setStyle("-fx-background-color: #FF0000;");
                } else {
                    insert.setStyle("-fx-background-color: #D0C9C0;");
                }
            } catch (NumberFormatException e){
                studentField.setStyle("-fx-background-color: #FF0000;");
            }

        });
    }
}

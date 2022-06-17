package pt.isec.pa.apoio_poe.model.Data.FinalProposalManager;

import pt.isec.pa.apoio_poe.model.Data.Command.ICommand;

public class CommandAdapter implements ICommand {
    protected FinalProposalManager receiver;

    public CommandAdapter(FinalProposalManager receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public boolean undo() {
        return false;
    }
}

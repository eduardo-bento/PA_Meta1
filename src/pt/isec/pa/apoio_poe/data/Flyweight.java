package pt.isec.pa.apoio_poe.data;

import java.util.HashMap;
import java.util.Map;

public class Flyweight {
    private final static Map<String,Integer> branch;
    private final static Map<String,Integer> branchProposals;
    static {
        branch = new HashMap<>();
        branchProposals = new HashMap<>();
    }

    public static void addStudentToBranch(String acronymBranch){
        branch.putIfAbsent(acronymBranch, 0);
        branch.get(acronymBranch);
        branch.put(acronymBranch,branch.get(acronymBranch) + 1);
    }

    public static void addProposalToBranch(String acronymBranch){
        branchProposals.putIfAbsent(acronymBranch, 0);
        branchProposals.get(acronymBranch);
        branchProposals.put(acronymBranch,branch.get(acronymBranch) + 1);
    }

    public static int getStudentBranchAmount(String acronymBranch){
        return 1/*branch.get(acronymBranch)*/;
    }

    public static int getProposalBranchAmount(String acronymBranch){
        return 1/*branch.get(acronymBranch)*/;
    }

    public static boolean branchGreatherProposals(){
        //todo: re
        return true;
    }
}

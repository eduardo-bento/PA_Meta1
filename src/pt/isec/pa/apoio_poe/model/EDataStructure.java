package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.model.Proposals.InterShip;
import pt.isec.pa.apoio_poe.model.Proposals.Project;
import pt.isec.pa.apoio_poe.model.Proposals.SelfProposal;

import java.util.Arrays;
import java.util.List;

public enum EDataStructure {
    STUDENTS,
    TEACHER,
    INTER_SHIP,
    PROJECT,
    SELF_PROPOSAL,
    CANDIDACY;

    public static EDataStructure fromInteger(int x) {
        return switch(x) {
            case 0 -> STUDENTS;
            case 1 -> TEACHER;
            case 2 -> INTER_SHIP;
            case 3 -> PROJECT;
            case 4 -> SELF_PROPOSAL;
            case 5 -> CANDIDACY;
            default -> null;
        };
    }

    public Class<?> getDataClass(){
        return switch (this){
            case STUDENTS -> Student.class;
            case TEACHER -> Teacher.class;
            case INTER_SHIP -> InterShip.class;
            case PROJECT -> Project.class;
            case SELF_PROPOSAL -> SelfProposal.class;
            case CANDIDACY -> Candidacy.class;
        };
    }

    public static List<String> getTypes() {
        return Arrays.asList("Student","Teacher","Proposal");
    }

    public static EDataStructure fromClass(Class<?> type){
        if (Student.class == type){
            return STUDENTS;
        } if (Teacher.class == type){
            return TEACHER;
        } if (Project.class == type){
            return PROJECT;
        } if (InterShip.class == type){
            return INTER_SHIP;
        } if (Candidacy.class == type){
            return CANDIDACY;
        }
        return SELF_PROPOSAL;
    }
}

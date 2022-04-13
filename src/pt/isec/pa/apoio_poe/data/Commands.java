package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.model.Proposals.InterShip;
import pt.isec.pa.apoio_poe.model.Proposals.Project;
import pt.isec.pa.apoio_poe.model.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.Student;
import pt.isec.pa.apoio_poe.model.Teacher;
import pt.isec.pa.apoio_poe.utils.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Commands {
    private final Map<Class,List<String>> commands;

    public Commands() {
       commands = new HashMap<>();
        initCommands();
    }

    private void initCommands(){
        commands.put(Student.class,new ArrayList<>());
        List<String> infoStudent = commands.get(Student.class);
        infoStudent.add("Write the student id");
        infoStudent.add("Write the student name");
        infoStudent.add("Write the student email");
        infoStudent.add("Write the student curse\n{LEI,LEI-PL}");
        infoStudent.add("Write the student branch\n{DA,RAS,SI}");
        infoStudent.add("Write the classification");
        infoStudent.add("Want to have stage?");

        commands.put(Teacher.class,new ArrayList<>());
        List<String> infoTeacher = commands.get(Teacher.class);
        infoTeacher.add("Write the teacher email");
        infoTeacher.add("Write the teacher name");
        infoTeacher.add("The teacher is an advisor?");

        commands.put(InterShip.class,new ArrayList<>());
        List<String> infoInterShip = commands.get(InterShip.class);
        infoInterShip.add("Write proposal id");
        infoInterShip.add("Write proposal title");
        infoInterShip.add("Write entity");
        infoInterShip.add("Write destiny");
        infoInterShip.add("Write student");

        commands.put(Project.class,new ArrayList<>());
        List<String> infoProject = commands.get(Project.class);
        infoProject.add("Write proposal id");
        infoProject.add("Write proposal title");
        infoProject.add("Write teacher");
        infoProject.add("Write destiny");
        infoProject.add("Write student");

        commands.put(SelfProposal.class,new ArrayList<>());
        List<String> infoSelfProposal = commands.get(SelfProposal.class);
        infoSelfProposal.add("Write proposal id");
        infoSelfProposal.add("Write proposal title");
        infoSelfProposal.add("Write student");
    }

    public List<String> getInfo(Class typeClass){
        return commands.get(typeClass);
    }
}

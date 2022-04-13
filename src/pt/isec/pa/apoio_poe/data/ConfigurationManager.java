package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Student;
import pt.isec.pa.apoio_poe.model.Teacher;

import java.util.HashSet;
import java.util.Set;

public class ConfigurationManager {
    private EManagement currentMode;
    private final Set<Student> students;
    private final Set<Teacher> teachers;
    private final Set<Proposal> proposals;

    public ConfigurationManager() {
        students = new HashSet<>();
        teachers = new HashSet<>();
        proposals = new HashSet<>();
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public Set<Proposal> getProposals() {
        return proposals;
    }
}

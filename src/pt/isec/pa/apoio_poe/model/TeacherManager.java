package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Teacher.Teacher;
import pt.isec.pa.apoio_poe.model.Teacher.TeacherOrder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TeacherManager extends Manager<Teacher> {
    public TeacherManager(Data data) {
        super(data);
    }

    @Override
    public void readFile(String filePath) {
        List<Teacher> items = new ArrayList<>();
            try(Scanner input = new Scanner(new File(filePath))) {
                input.useDelimiter(",\\s*|\r\n|\n");
                input.useLocale(Locale.US);
                while(input.hasNext()){
                    String name = input.next();
                    String email = input.next();
                    items.add(new Teacher(email,name,false));
                }
            }  catch (FileNotFoundException e){
                Log.getInstance().addMessage("The file does not exist");
            } catch (NoSuchElementException e){
                Log.getInstance().addMessage("Error reading");
            }
        items.forEach(this::insert);
    }

    @Override
    public boolean insert(Teacher item) {
        if (!item.getEmail().contains("@isec.pt")){
            Log.getInstance().addMessage(item.getEmail() + " is not a valid email because is not from isec");
            return false;
        }
        return super.insert(item);
    }

    public float average(){
        float count = 0;
        for (Teacher teacher : list){
            count += teacher.getAmount();
        }
        return count/list.size();
    }

    public int highest(){
        Teacher teacher = Collections.max(list,new TeacherOrder());
        return teacher.getAmount();
    }

    public int lowest(){
        Teacher teacher = Collections.min(list,new TeacherOrder());
        return teacher.getAmount();
    }

    public String getTeacherList(){
        StringBuilder builder = new StringBuilder();
        for (Teacher teacher : list){
            builder.append(teacher.getEmail()).append("\n");
        }
        return builder.toString();
    }
}

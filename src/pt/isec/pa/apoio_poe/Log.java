package pt.isec.pa.apoio_poe;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private static Log log = null;
    List<String> messages;

    public static Log getInstance(){
        if (log == null){
            log = new Log();
        }
        return log;
    }

    public Log() {
        messages = new ArrayList<>();
    }

    public List<String> getMessages(){
        return messages;
    }

    public void addMessage(String message){
        messages.add(message);
    }

    public void reset(){
        messages.clear();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String m : messages){
            stringBuilder.append("-").append(m).append("\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}

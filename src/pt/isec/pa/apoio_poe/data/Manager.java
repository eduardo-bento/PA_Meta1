package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.utils.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class Manager<T> {
    protected final Set<T> list;

    public Manager() {
        list = new HashSet<>();
    }

    public Set<T> getList(){
        return list;
    }

    public boolean insert(T item){
        return list.add(item);
    }

    public <K> boolean remove(K id,Class<?> ... type){
        return false;
    }

    protected <Q,K> K find(Q id,Class<K> type){
        Class<?> name = Utils.getFirstField(type).getType();
        String className = Utils.splitString(type.getName(),"\\.");

        try{
            Method method = type.getMethod("getFake" + className,name);
            Object object = method.invoke(null, id);
            for (var v : list){
                if(v.hashCode() == object.hashCode()){
                    return (K) v;
                }
            }
            Log.getInstance().addMessage("The " + Utils.splitString(type.getName(), "\\.") + " was not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <Q, K, A> boolean edit(Q id, K value, String label, Class<A> type) {
        A dataStructure = find(id, type);
        if (dataStructure != null){
            changeAttribute(value,label, type,dataStructure);
            return true;
        }
        return false;
    }

    private <K, A> void changeAttribute(K value, String label, Class<A> typeClass,Object s) {
        for (Field f : typeClass.getDeclaredFields()) {
            if (label.equals(f.getName())) {
                try {
                    Method method = typeClass.getDeclaredMethod("set" + Utils.capitalString(f.getName()), f.getType());
                    method.invoke(typeClass.cast(s), value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String querying() {
        StringBuilder stringBuilder = new StringBuilder();

        for (T o : list){
            stringBuilder.append(o.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}

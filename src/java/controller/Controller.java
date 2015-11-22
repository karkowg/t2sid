package controller;

import dao.DAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import model.Model;
import static util.Util.errorMessage;

/**
 *
 * @author GUSTAVO
 * @param <T>
 */
public abstract class Controller<T extends Model> implements Serializable {
    
    protected T insert(T t) {
        try {
            DAO<T> dao = new DAO<T>();
            t = dao.insert(t);
        } catch (Exception e) {
            errorMessage(e.getMessage());
        }
        return t;
    }
    
    protected T delete(T t) {
        try {
            DAO<T> dao = new DAO<T>();
            t = dao.delete(t);
        } catch (Exception e) {
            errorMessage(e.getMessage());
        }
        return t;
    }
    
    protected T update(T t) {
        try {
            DAO<T> dao = new DAO<T>();
            t = dao.update(t);
        } catch (Exception e) {
            errorMessage(e.getMessage());
        }
        return t;
    }
    
    protected ArrayList<T> getList(String query) {
        DAO<T> dao = new DAO<T>();
        Collection<T> all = dao.list(query);
        
        ArrayList<T> result = new ArrayList<T>();
        for (T t : all) {
            result.add(t);
        }
        
        return result;
    }
    
}

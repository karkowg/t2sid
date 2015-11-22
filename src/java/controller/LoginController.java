package controller;

import java.util.ArrayList;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.Person;

/**
 *
 * @author GUSTAVO
 */
@Named
@SessionScoped
public class LoginController extends Controller<Person> {
    
    private Person loggedUser;
    private Collection<Person> registered;
    
    public Person getLoggedUser() {
        return loggedUser;
    }

    private void setLoggedUser(Person loggedUser) {
        this.loggedUser = loggedUser;
    }
    
    private ArrayList<Person> getRegisteredUsers() {
        return getList("from Person");
    }
    
    public boolean isValid(Person user) {
        registered = getRegisteredUsers();
        
        for (Person p : registered) {
            if (p.getUsername().equals(user.getUsername()) &&
                p.getPassword().equals(user.getPassword())) {
                setLoggedUser(p);
                return true;
            }
        }
        
        return false;
    }
    
}

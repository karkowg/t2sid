package controller;

import java.io.Serializable;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author GUSTAVO
 */
@Named
@SessionScoped
public class LocaleController implements Serializable {
    
    public LocaleController() {
        
    }
    
    public String ptBR(){
        return change("pt_br");
    }
    
    public String en(){
        return change("en");
    }
    
    private String change(String language) {
        UIViewRoot vr = FacesContext.getCurrentInstance().getViewRoot();
        vr.setLocale(new Locale(language));
        return null;
    }
}

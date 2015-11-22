package util;

import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 *
 * @author GUSTAVO
 */
public class Util {
    
    public static void message(Severity severity, String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
    }
    
    public static void infoMessage(String msg) {
        message(FacesMessage.SEVERITY_INFO, msg);
    }
    
    public static void warningMessage(String msg) {
        message(FacesMessage.SEVERITY_WARN, msg);
    }
    
    public static void errorMessage(String msg) {
        message(FacesMessage.SEVERITY_ERROR, msg);
    }
    
    public static int getRandom(Integer max) {
        Random r = new Random();
        if (max == 0) return 0;
        return r.nextInt(max);
    }
    
    public static int getNextBase2(Integer n) {
        Double exp = 1.0;
        Double res = 2.0;
        while (res < n) {
            exp++;
            res = Math.pow(2.0, exp);
        }
        return res.intValue();
    }
    
    public static String getStageName(Integer n) {
        String name;
        switch (Util.getNextBase2(n)) {
            case 2: name = "Finale";
                break;
            case 4: name = "Semi-finale";
                break;
            case 8: name = "Quarter-finale";
                break;
            default: name = "Round of " + Util.getNextBase2(n);
                break;
        }
        return name;
    }
    
    public static String jsfPageName() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId;
    }
    
}

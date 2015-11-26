package converter;

import dao.DAO;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Stage;
import static util.Util.errorMessage;

/**
 *
 * @author GUSTAVO
 */
@FacesConverter(forClass=converter.StageConverter.class, value="stageConverter")
public class StageConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Stage stage = null;
        
        try {
            DAO<Stage> dao = new DAO<Stage>();
            String query = "from Stage s where s.idStage = " + value;
            stage = dao.search(query);
        } catch (Exception e) {
            errorMessage(e.getMessage());
        }
        return stage;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Stage stage = (Stage) value;
        return String.valueOf(stage.getIdStage());
    }
    
}

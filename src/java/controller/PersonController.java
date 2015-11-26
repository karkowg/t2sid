package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Championship;
import model.Game;
import model.Stage;
import model.Team;
import model.Person;
import util.Util;
import static util.Util.errorMessage;
import static util.Util.getStageName;
import static util.Util.infoMessage;
import static util.Util.jsfPageName;
import static util.Util.warningMessage;

/**
 *
 * @author GUSTAVO
 */
@Named
@SessionScoped
public class PersonController extends Controller<Person> {
    
    @Inject
    private TeamController teamCTRL;
    @Inject
    private GameController gameCTRL;
    @Inject
    private LoginController loginCTRL;
    @Inject
    private StageController stageCTRL;
    @Inject
    private ChampionshipController chCTRL;
    
    private boolean edit = false;
    private boolean logged = false;
    private Team team = new Team();
    private Stage stageC = null;
    private Stage stageF = null;
    private Person user = new Person();
    private Championship championship = new Championship();
    private Championship selectedC = null;
    private Championship selectedF = null;
    
    private Collection<Team> teams = new ArrayList<Team>();
    private Collection<Championship> championships;
    
    public Person getUser() {
        return user;
    }
    
    public void setUser(Person user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Stage getStageC() {
        return stageC;
    }
    
    public void setStageC(Stage s) {
        stageC = s;
    }

    public void setStageC(int index) {
        if (index == -1) stageC = null;
        else {
            ArrayList<Stage> l = new ArrayList<Stage>();
            for (Stage s : selectedC.getStageList()) {
                l.add(s);
            }
            stageC = l.get(index);
        }
    }
    
    public Stage getStageF() {
        return stageF;
    }
    
    public void setStageF(Stage s) {
        stageF = s;
    }
    
    public void setStageF(int index) {
        if (index == -1) stageF = null;
        else {
            ArrayList<Stage> l = new ArrayList<Stage>();
            for (Stage s : selectedF.getStageList()) {
                l.add(s);
            }
            stageF = l.get(index);
        }
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    public Championship getSelectedC() {
        return selectedC;
    }
    
    public void setSelectedC(Championship c) {
        if (c == null) {
            selectedC = null;
            setStageC(-1);
        } else {
            selectedC = c;
            setStageC(selectedC.getStageList().size() - 1);
        }
    }

    public void setSelectedC(int index) {
        if (index == -1) {
            selectedC = null;
            setStageC(-1);
        } else {
            ArrayList<Championship> l = new ArrayList<Championship>();
            for (Championship c : getCurrentChampionships()) {
                l.add(c);
            }
            selectedC = l.get(index);
            setStageC(selectedC.getStageList().size() - 1);
        }
    }
    
    public Championship getSelectedF() {
        return selectedF;
    }

    public void setSelectedF(int index) {
        if (index == -1) {
            selectedF = null;
            setStageF(-1);
        }
        else {
            ArrayList<Championship> l = new ArrayList<Championship>();
            for (Championship c : getFinishedChampionships()) {
                l.add(c);
            }
            selectedF = l.get(index);
            setStageF(selectedF.getStageList().size() - 1);
        }
    }

    public Collection<Team> getTeams() {
        return teams;
    }

    public void setTeams(Collection<Team> teams) {
        this.teams = teams;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
    
    public void insertUser() {
        insert(user);
        
        infoMessage("User added!");
        setUser(new Person());
    }
    
    public String deleteUser() {
        delete(user);
        
        infoMessage("User deleted!");
        return logout();
    }
    
    public void changePassword() {
        update(user);
        
        infoMessage("Password changed!");
        editOff();
    }
    
    public String login() {
        boolean valid = loginCTRL.isValid(user);
        setLogged(valid);
        
        if (!logged) {
            errorMessage("The username or password you entered is incorrect!");
            return "index";
        }
        
        setUser(loginCTRL.getLoggedUser());
        updateUserData();
        return "welcome";
    }
    
    public String logout() {
        setUser(new Person());
        setLogged(false);
        clearTeams();
        return "index";
    }
    
    public void setChampionshipFinished(Championship c) {
        c.setFinished(true);
        chCTRL.update(c);
        
        infoMessage("Championship finalized!");
        updateUserData();
        setChampionship(new Championship());
    }
    
    public void addTeam() {
        teams.add(team);
        setTeam(new Team());
    }
    
    public void clearTeams() {
        teams.clear();
        setTeam(new Team());
    }
    
    public void erase() {
        for (Championship c : championships) {
            chCTRL.delete(c);
        }
        
        infoMessage("Your championships have been deleted!");
        updateUserData();
        setChampionship(new Championship());
    }
    
    public boolean minimum() {
        return teams.size() > 1;
    }
    
    private void updateUserData() {
        String cList = "from Championship c where c.person.idPerson = " + user.getIdPerson();
        championships = chCTRL.getList(cList);
        for (Championship c : championships) {
            String sList = "from Stage s where s.championship.idChampionship = " + c.getIdChampionship();
            c.setStages(new HashSet<Stage>(stageCTRL.getList(sList)));
            for (Stage s : c.getStageList()) {
                String gList = "from Game g where g.stage.idStage = " + s.getIdStage();
                s.setGames(new HashSet<Game>(gameCTRL.getList(gList)));
            }
        }
        setSelectedC(-1);
        setSelectedF(-1);
    }
    
    public Collection<Championship> getCurrentChampionships() {
        Collection<Championship> current = new ArrayList<Championship>();
        for (Championship c : championships) {
            if (!c.isFinished()) {
                current.add(c);
            }
        }
        return current;
    }
    
    public Collection<Championship> getFinishedChampionships() {
        Collection<Championship> finished = new ArrayList<Championship>();
        for (Championship c : championships) {
            if (c.isFinished()) {
                finished.add(c);
            }
        }
        return finished;
    }
    
    public void setPlayoffs() {
        championship.setPerson(user);
        championship = chCTRL.insert(championship);
        
        Stage s = new Stage();
        s.setChampionship(championship);
        s.setNteams(teams.size());
        s.setName(getStageName(teams.size()));
        s = stageCTRL.insert(s);
        
        Collection<Team> teamsAux = new ArrayList<Team>();
        for (Team t : teams) {
            t.setChampionship(championship);
            teamCTRL.insert(t);
            teamsAux.add(t);
        }
        teams = teamsAux;
        
        Collection<Game> games = getPlayoffGames();
        for (Game g : games) {
            g.setStage(s);
            gameCTRL.insert(g);
        }
        
        infoMessage("Championship saved!");
        clearTeams();
        updateUserData();
        setChampionship(new Championship());
    }
    
    private ArrayList<Game> getPlayoffGames() {
        ArrayList<Game> games = new ArrayList<Game>();
        ArrayList<Team> tlist = new ArrayList<Team>();
        for (Team t : teams) tlist.add(t);
        int n = Util.getNextBase2(teams.size());
        int pre = n - teams.size();
        
        /* Pre-classificados */
        for (int i=1; i<=pre; i++) {
            Game g = new Game();
            int index = Util.getRandom(tlist.size());
            g.setNumber(i);
            Team t = tlist.remove(index);
            g.setTeamByFkHome(t);
            t = new Team("W.O.");
            t.setChampionship(championship);
            teamCTRL.insert(t);
            g.setTeamByFkVisitor(t);
            g.setHomeScore(1);
            g.setVisitScore(0);
            games.add(g);
        }
        
        /* Partidas */
        for (int i=pre+1; i<=n/2; i++) {
            Game g = new Game();
            int index;
            g.setNumber(i);
            index = Util.getRandom(tlist.size());
            Team t = tlist.remove(index);
            g.setTeamByFkHome(t);
            index = Util.getRandom(tlist.size());
            t = tlist.remove(index);
            g.setTeamByFkVisitor(t);
            g.setHomeScore(0);
            g.setVisitScore(0);
            games.add(g);
        }
        
        return games;
    }
    
    public void editOn() {
        if (selectedC == null && jsfPageName().equals("/current.xhtml")) {
            warningMessage("First of all, select a championship on the list or create a new one.");
        } else {
            setEdit(true);
        }
    }
    
    public void editOff() {
        setEdit(false);
    }
    
    public boolean evenRow(int index) {
        return index%2 == 0;
    }
    
    private Team getWinner(Game g) {
        return g.getHomeScore() > g.getVisitScore()? g.getTeamByFkHome() : g.getTeamByFkVisitor();
    }
    
    private void updateScores() {
        for (Game g : new ArrayList<Game>(stageC.getGames())) {
            gameCTRL.update(g);
        }
        editOff();
    }
    
    private ArrayList<Team> getWinnerTeams() {
        ArrayList<Team> winners = new ArrayList<Team>();
        for (Game g : new ArrayList<Game>(stageC.getGames())) {
            winners.add(getWinner(g));
        }
        return winners;
    }
    
    public void nextStage() {
        updateScores();
        
        teams = getWinnerTeams();
        
        if (teams.size() == 1) {
            selectedC.setFinished(true);
            chCTRL.update(selectedC);
            clearTeams();
            updateUserData();
            infoMessage("Championship finalized!");
        } else {
            Stage s = new Stage();
            s.setChampionship(selectedC);
            s.setNteams(teams.size());
            s.setName(getStageName(teams.size()));
            s = stageCTRL.insert(s);
            
            Collection<Game> games = getPlayoffGames();
            for (Game g : games) {
                g.setStage(s);
                gameCTRL.insert(g);
            }
            
            infoMessage("Next stage is rolling!");
            clearTeams();
            Integer id = selectedC.getIdChampionship();
            updateUserData();
            setSelectedC(findById(id));
        }
    }
    
    private Championship findById(Integer id) {
        for (Championship c : getCurrentChampionships()) {
            if (c.getIdChampionship() == id) return c;
        }
        return null;
    }
    
}

package model;
// Generated 26/11/2015 19:49:54 by Hibernate Tools 4.3.1

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Championship generated by hbm2java
 */
public class Championship extends Model implements java.io.Serializable {

    private Integer idChampionship;
    private Person person;
    private String name;
    private boolean finished;
    private Set stages = new HashSet(0);
    private Set teams = new HashSet(0);

    public Championship() {
    }

    public Championship(Person person, String name, boolean finished) {
        this.person = person;
        this.name = name;
        this.finished = finished;
    }

    public Championship(Person person, String name, boolean finished, Set stages, Set teams) {
        this.person = person;
        this.name = name;
        this.finished = finished;
        this.stages = stages;
        this.teams = teams;
    }

    public Integer getIdChampionship() {
        return this.idChampionship;
    }

    public void setIdChampionship(Integer idChampionship) {
        this.idChampionship = idChampionship;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Set getStages() {
        return this.stages;
    }
    
    public List<Stage> getStageList() {
        return new ArrayList<Stage>(stages);
    }

    public void setStages(Set stages) {
        this.stages = stages;
    }

    public Set getTeams() {
        return this.teams;
    }

    public void setTeams(Set teams) {
        this.teams = teams;
    }

}

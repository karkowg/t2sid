package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GUSTAVO
 */
@Entity
@Table(name = "championship")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Championship.findAll", query = "SELECT c FROM Championship c"),
    @NamedQuery(name = "Championship.findByIdChampionship", query = "SELECT c FROM Championship c WHERE c.idChampionship = :idChampionship"),
    @NamedQuery(name = "Championship.findByName", query = "SELECT c FROM Championship c WHERE c.name = :name"),
    @NamedQuery(name = "Championship.findByFinished", query = "SELECT c FROM Championship c WHERE c.finished = :finished")})
public class Championship extends Model implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idChampionship")
    private Integer idChampionship;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "finished")
    private boolean finished;
    @OneToMany(mappedBy = "championship")
    private List<Team> teamList;
    @OneToMany(mappedBy = "championship")
    private List<Stage> stageList;
    @JoinColumn(name = "fkPerson", referencedColumnName = "idPerson")
    @ManyToOne(optional = false)
    private Person owner;

    public Championship() {
        finished = false;
        owner = new Person();
        teamList = new ArrayList<Team>();
        stageList = new ArrayList<Stage>();
    }

    @Override
    public Integer getId() {
        return idChampionship;
    }

    @Override
    public void setId(Integer idChampionship) {
        this.idChampionship = idChampionship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @XmlTransient
    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    @XmlTransient
    public List<Stage> getStageList() {
        return stageList;
    }

    public void setStageList(List<Stage> stageList) {
        this.stageList = stageList;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idChampionship != null ? idChampionship.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Championship)) {
            return false;
        }
        Championship other = (Championship) object;
        return (this.idChampionship != null || other.idChampionship == null) && (this.idChampionship == null || this.idChampionship.equals(other.idChampionship));
    }

    @Override
    public String toString() {
        return "model.Championship[ idChampionship=" + idChampionship + " ]";
    }
    
}

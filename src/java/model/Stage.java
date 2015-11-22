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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author GUSTAVO
 */
@Entity
@Table(name = "stage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stage.findAll", query = "SELECT s FROM Stage s"),
    @NamedQuery(name = "Stage.findByIdStage", query = "SELECT s FROM Stage s WHERE s.idStage = :idStage"),
    @NamedQuery(name = "Stage.findByName", query = "SELECT s FROM Stage s WHERE s.name = :name"),
    @NamedQuery(name = "Stage.findByNTeams", query = "SELECT s FROM Stage s WHERE s.nTeams = :nTeams")})
public class Stage extends Model implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idStage")
    private Integer idStage;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Column(name = "nTeams")
    private Integer nTeams;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "stage")
    private List<Game> gameList;
    @JoinColumn(name = "fkChampionship", referencedColumnName = "idChampionship")
    @ManyToOne
    private Championship championship;

    public Stage() {
        championship = new Championship();
        gameList = new ArrayList<Game>();
    }

    @Override
    public Integer getId() {
        return idStage;
    }

    @Override
    public void setId(Integer idStage) {
        this.idStage = idStage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNTeams() {
        return nTeams;
    }

    public void setNTeams(Integer nTeams) {
        this.nTeams = nTeams;
    }

    @XmlTransient
    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStage != null ? idStage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stage)) {
            return false;
        }
        Stage other = (Stage) object;
        return (this.idStage != null || other.idStage == null) && (this.idStage == null || this.idStage.equals(other.idStage));
    }

    @Override
    public String toString() {
        return name;
    }
    
}

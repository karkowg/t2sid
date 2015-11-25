package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author GUSTAVO
 */
@Entity
@Table(name = "team")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
    @NamedQuery(name = "Team.findByIdTeam", query = "SELECT t FROM Team t WHERE t.idTeam = :idTeam"),
    @NamedQuery(name = "Team.findByName", query = "SELECT t FROM Team t WHERE t.name = :name")})
public class Team extends Model implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(generator="TeamIdGenerator")
    @GenericGenerator(name="TeamIdGenerator", strategy="org.hibernate.shards.id.ShardedUUIDGenerator")
    @Column(name = "idTeam")
    private Integer idTeam;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "visitor")
    private List<Game> gameListV;
    @OneToMany(mappedBy = "home")
    private List<Game> gameListH;
    @JoinColumn(name = "fkChampionship", referencedColumnName = "idChampionship")
    @ManyToOne
    private Championship championship;

    public Team() {
        championship = new Championship();
        gameListV = new ArrayList<Game>();
        gameListH = new ArrayList<Game>();
    }
    
    public Team(String value) {
        name = value;
    }

    @Override
    public Integer getId() {
        return idTeam;
    }

    @Override
    public void setId(Integer idTeam) {
        this.idTeam = idTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Game> getGameListV() {
        return gameListV;
    }

    public void setGameListV(List<Game> gameListV) {
        this.gameListV = gameListV;
    }

    @XmlTransient
    public List<Game> getGameListH() {
        return gameListH;
    }

    public void setGameListH(List<Game> gameListH) {
        this.gameListH = gameListH;
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
        hash += (idTeam != null ? idTeam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        return (this.idTeam != null || other.idTeam == null) && (this.idTeam == null || this.idTeam.equals(other.idTeam));
    }

    @Override
    public String toString() {
        return "model.Team[ idTeam=" + idTeam + " ]";
    }
    
}

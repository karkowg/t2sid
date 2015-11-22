package model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GUSTAVO
 */
@Entity
@Table(name = "game")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
    @NamedQuery(name = "Game.findByIdGame", query = "SELECT g FROM Game g WHERE g.idGame = :idGame"),
    @NamedQuery(name = "Game.findByNumber", query = "SELECT g FROM Game g WHERE g.number = :number"),
    @NamedQuery(name = "Game.findByHomeScore", query = "SELECT g FROM Game g WHERE g.homeScore = :homeScore"),
    @NamedQuery(name = "Game.findByVisitScore", query = "SELECT g FROM Game g WHERE g.visitScore = :visitScore")})
public class Game extends Model implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGame")
    private Integer idGame;
    @Basic(optional = false)
    @NotNull
    @Column(name = "number")
    private int number;
    @Column(name = "homeScore")
    private Integer homeScore;
    @Column(name = "visitScore")
    private Integer visitScore;
    @JoinColumn(name = "fkVisitor", referencedColumnName = "idTeam")
    @ManyToOne
    private Team visitor;
    @JoinColumn(name = "fkHome", referencedColumnName = "idTeam")
    @ManyToOne
    private Team home;
    @JoinColumn(name = "fkStage", referencedColumnName = "idStage")
    @ManyToOne
    private Stage stage;

    public Game() {
        home = new Team();
        visitor = new Team();
        stage = new Stage();
    }

    @Override
    public Integer getId() {
        return idGame;
    }

    @Override
    public void setId(Integer idGame) {
        this.idGame = idGame;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getVisitScore() {
        return visitScore;
    }

    public void setVisitScore(Integer visitScore) {
        this.visitScore = visitScore;
    }

    public Team getVisitor() {
        return visitor;
    }

    public void setVisitor(Team visitor) {
        this.visitor = visitor;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGame != null ? idGame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        return (this.idGame != null || other.idGame == null) && (this.idGame == null || this.idGame.equals(other.idGame));
    }

    @Override
    public String toString() {
        return number + "- " + home.getName() + " " + homeScore + " X " + visitScore + " " + visitor.getName();
    }
    
}

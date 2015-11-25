package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "person")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT p FROM Person p"),
    @NamedQuery(name = "User.findByIdPerson", query = "SELECT p FROM Person p WHERE p.idPerson = :idPerson"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT p FROM Person p WHERE p.username = :username"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT p FROM Person p WHERE p.password = :password")})
public class Person extends Model implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(generator="PersonIdGenerator")
    @GenericGenerator(name="PersonIdGenerator", strategy="org.hibernate.shards.id.ShardedUUIDGenerator")
    @Column(name = "idPerson")
    private Integer idPerson;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Championship> championshipList;

    public Person() {
        championshipList = new ArrayList<Championship>();
    }

    @Override
    public Integer getId() {
        return idPerson;
    }

    @Override
    public void setId(Integer idPerson) {
        this.idPerson = idPerson;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<Championship> getChampionshipList() {
        return championshipList;
    }

    public void setChampionshipList(List<Championship> championshipList) {
        this.championshipList = championshipList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerson != null ? idPerson.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        return (this.idPerson != null || other.idPerson == null) && (this.idPerson == null || this.idPerson.equals(other.idPerson));
    }

    @Override
    public String toString() {
        return "model.User[ idUser=" + idPerson + " ]";
    }
    
}

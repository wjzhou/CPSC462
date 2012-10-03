/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * @author wujun
 */
@Entity
@Table(name = "ADVISORS", catalog = "", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Advisors.findAll", query = "SELECT a FROM Advisors a"),
    @NamedQuery(name = "Advisors.findByAId", query = "SELECT a FROM Advisors a WHERE a.aId = :aId"),
    @NamedQuery(name = "Advisors.findByFirstname", query = "SELECT a FROM Advisors a WHERE a.firstname = :firstname"),
    @NamedQuery(name = "Advisors.findByLastname", query = "SELECT a FROM Advisors a WHERE a.lastname = :lastname"),
    @NamedQuery(name = "Advisors.findByOffice", query = "SELECT a FROM Advisors a WHERE a.office = :office")})
public class Advisors implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "A_ID")
    private Integer aId;
    @Size(max = 20)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "LASTNAME")
    private String lastname;
    @Size(max = 6)
    @Column(name = "OFFICE")
    private String office;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advisorId")
    private Collection<Slots> slotsCollection;

    public Advisors() {
    }

    public Advisors(Integer aId) {
        this.aId = aId;
    }

    public Advisors(Integer aId, String lastname) {
        this.aId = aId;
        this.lastname = lastname;
    }

    public Integer getAId() {
        return aId;
    }

    public void setAId(Integer aId) {
        this.aId = aId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    @XmlTransient
    public Collection<Slots> getSlotsCollection() {
        return slotsCollection;
    }

    public void setSlotsCollection(Collection<Slots> slotsCollection) {
        this.slotsCollection = slotsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aId != null ? aId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Advisors)) {
            return false;
        }
        Advisors other = (Advisors) object;
        if ((this.aId == null && other.aId != null) || (this.aId != null && !this.aId.equals(other.aId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.fullerton.AcademyAdvisorAppointment.Advisors[ aId=" + aId + " ]";
    }
    
}

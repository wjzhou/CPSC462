/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wujun
 */
@Entity
@Table(name = "ADVISORS")
@DiscriminatorValue("Advisor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Advisor.findAll", query = "SELECT a FROM Advisor a"),
    @NamedQuery(name = "Advisor.findAllAdvisorName", query = "SELECT a.firstname,a.lastname FROM Advisor a"),
    
    
    //@NamedQuery(name = "Advisor.findByAId", query = "SELECT a FROM Advisor a WHERE a.aId = :aId"),
    //@NamedQuery(name = "Advisor.findByFirstname", query = "SELECT a FROM Advisor a WHERE a.firstname = :firstname"),
    //@NamedQuery(name = "Advisor.findByLastname", query = "SELECT a FROM Advisor a WHERE a.lastname = :lastname"),
    //@NamedQuery(name = "Advisor.findByOffice", query = "SELECT a FROM Advisor a WHERE a.office = :office")
})
public class Advisor extends Person implements Serializable {
   // public enum ADVISINGTYPE{UNDER_GRADUATE, GRADUATE};
    @ElementCollection(targetClass=Type.class)
    @CollectionTable(name="Advisor_Types")
    @Column(name="type") // Column name in 
    Collection<Type> advisingTypes;
    
    @Column(name = "PREFERRED_LOCATION")
    private String preferredLocation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advisor")
    private Collection<Slot> slots;

    public Advisor() {
         this.advisingTypes = new ArrayList<Type>();
    }

    public Advisor(String firstname, String lastname, String preferredOffice) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.advisingTypes = new ArrayList<Type>();
        this.preferredLocation = preferredOffice;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public Collection<Type> getAdvisingTypes() {    
        return advisingTypes;
    }
    
    public String getAdvisingTypesTableString() {
        String result=new String();
        if (advisingTypes.contains(Type.UNDER_GRADUATE)) {
            result+="U";
        }
        if (advisingTypes.contains(Type.GRADUATE)) {
            result+="G";
        }
        return result;
    }

     public void setAdvisingTypesOptions(List<String> selectedTypes) {  
        for(String s:selectedTypes){
            if("U".equals(s)) {
                this.advisingTypes.add(Type.UNDER_GRADUATE);
            }
            if("G".equals(s)) {
                this.advisingTypes.add(Type.GRADUATE);
            }
        }
    }
     
    public List<String> getAdvisingTypesOptions() {  
        List<String> result=new ArrayList<String>(2);
        if (advisingTypes.contains(Type.UNDER_GRADUATE)) {
            result.add("U");
        }
        if (advisingTypes.contains(Type.GRADUATE)) {
            result.add("G");
        }
        return result;
    }
     

    
    public void setAdvisingTypes(Collection<Type> advisingTypes) {
        this.advisingTypes = advisingTypes;
    }
    public void addAdvisingTypes(Type advisingType) {
        this.advisingTypes.add(advisingType);
    }

    public Collection<Slot> getSlots() {
        return slots;
    }

    public void setSlots(Collection<Slot> slots) {
        this.slots = slots;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Advisor)) {
            return false;
        }
        Advisor other = (Advisor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.fullerton.AcademyAdvisorAppointment.Advisors[ aId=" + id + " ]";
    }
}

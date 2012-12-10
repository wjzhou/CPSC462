/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
/**
 *
 * @author wujun
 */
@Entity
@Table(name = "STUDENTS")
@DiscriminatorValue("Student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "Student.findByCwid", query = "SELECT s FROM Student s WHERE s.cwid = :cwid"),
    @NamedQuery(name = "Student.findByFirstname", query = "SELECT s FROM Student s WHERE s.firstname = :firstname"),
    @NamedQuery(name = "Student.findByLastname", query = "SELECT s FROM Student s WHERE s.lastname = :lastname"),
    @NamedQuery(name = "Student.findByEmail", query = "SELECT s FROM Student s WHERE s.email = :email"),
    @NamedQuery(name = "Student.findByPhone", query = "SELECT s FROM Student s WHERE s.phone = :phone")})
public class Student extends Person implements Serializable {
    
    @NotNull
    @Column(name = "CWID")
    private String cwid;    
    @Column(name = "TYPE")
    private Type type;    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @OneToMany(mappedBy = "student")
    private Collection<Slot> slotsCollection;

    public Student() {
    }

    public Student(String cwid, Type type, String firstname, String lastname, String email, String phone) {
        super(firstname, lastname, email, phone);
        this.cwid = cwid;
        this.type=type;
    }

    public String getCwid() {
        return cwid;
    }

    public void setCwid(String cwid) {
        this.cwid = cwid;
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<Slot> getSlotsCollection() {
        return slotsCollection;
    }

    public void setSlotsCollection(Collection<Slot> slotsCollection) {
        this.slotsCollection = slotsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += ((id != null) ? id.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }

        Student other = (Student) object;

        if (((this.id == null) && (other.id != null))
                || ((this.id != null) && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return cwid+" "+getName();
    }

}

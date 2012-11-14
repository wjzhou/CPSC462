/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author wujun
 */
@Entity
@Table(name = "SLOTS", catalog = "", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Slot.findAll", query = "SELECT s FROM Slot s ORDER BY s.id"),
    @NamedQuery(name = "Slot.findByAdvisor", query = "SELECT s FROM Slot s WHERE s.advisor=:advisor"),
    @NamedQuery(name = "Slot.findByid", query = "SELECT s FROM Slot s WHERE s.id = :id"),
    @NamedQuery(name = "Slot.findByStudent", query = "SELECT s FROM Slot s WHERE s.student=:student"),
     
    //@NamedQuery(name = "Slot.findByStarttime", query = "SELECT s FROM Slot s WHERE s.starttime = :starttime"),
    //@NamedQuery(name = "Slot.findByEndtime", query = "SELECT s FROM Slot s WHERE s.endtime = :endtime"),
    @NamedQuery(name = "Slot.findByStatus", query = "SELECT s FROM Slot s WHERE s.status = :status")})
public class Slot implements Serializable {
    public enum Status {
        AVAILABLE, BOOKED, APPROVED, INVALID, CANCELED;
    }
    private static final long serialVersionUID = 1L;
    @TableGenerator(
        name = "SlotIdGen",
        table = "SEQUENCE_GENERATOR",
        pkColumnName = "GEN_KEY",
        valueColumnName = "GEN_VALUE",
        pkColumnValue = "SLOT_ID",
        allocationSize = 10)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SlotIdGen")
    @Column(name = "S_ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STARTTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date starttime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENDTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endtime;
    @NotNull
    @Column(name = "STATUS")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "STUDENT_ID") 
    @XmlTransient
    private Student student;
    @Column(name = "DESCRIPTION")
    private String description;
    @ManyToOne
    @JoinColumn(name = "ADVISOR_ID")
    @XmlTransient
    private Advisor advisor;
    @ManyToOne
    @JoinColumn(name = "REASON_ID")
    @XmlTransient
    private Reason reason;
    @Version int version;

    public Slot() {
    }

    public Slot(Date starttime, Date endtime) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.status=Status.AVAILABLE;
    }
    
    public Slot(Date starttime, Date endtime, Status status) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.status = status;
    }
    
    public Slot(Date starttime, Date endtime, Status status, Advisor advisor) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.status = status;
        this.advisor=advisor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
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
        if (!(object instanceof Slot)) {
            return false;
        }
        Slot other = (Slot) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.fullerton.AcademyAdvisorAppointment.Slots[ sId=" + id + " ]";
    }
    
}

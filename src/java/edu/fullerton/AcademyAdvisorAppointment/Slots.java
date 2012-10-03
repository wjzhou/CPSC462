/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wujun
 */
@Entity
@Table(name = "SLOTS", catalog = "", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Slots.findAll", query = "SELECT s FROM Slots s"),
    @NamedQuery(name = "Slots.findBySId", query = "SELECT s FROM Slots s WHERE s.sId = :sId"),
    @NamedQuery(name = "Slots.findByStarttime", query = "SELECT s FROM Slots s WHERE s.starttime = :starttime"),
    @NamedQuery(name = "Slots.findByEndtime", query = "SELECT s FROM Slots s WHERE s.endtime = :endtime"),
    @NamedQuery(name = "Slots.findByVersion", query = "SELECT s FROM Slots s WHERE s.version = :version"),
    @NamedQuery(name = "Slots.findByDescription", query = "SELECT s FROM Slots s WHERE s.description = :description"),
    @NamedQuery(name = "Slots.findByStatus", query = "SELECT s FROM Slots s WHERE s.status = :status")})
public class Slots implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "S_ID")
    private Integer sId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STARTTIME")
    @Temporal(TemporalType.TIME)
    private Date starttime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENDTIME")
    @Temporal(TemporalType.TIME)
    private Date endtime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VERSION")
    private int version;
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    private int status;
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ST_ID")
    @ManyToOne
    private Students studentId;
    @JoinColumn(name = "ADVISOR_ID", referencedColumnName = "A_ID")
    @ManyToOne(optional = false)
    private Advisors advisorId;

    public Slots() {
    }

    public Slots(Integer sId) {
        this.sId = sId;
    }

    public Slots(Integer sId, Date starttime, Date endtime, int version, int status) {
        this.sId = sId;
        this.starttime = starttime;
        this.endtime = endtime;
        this.version = version;
        this.status = status;
    }

    public Integer getSId() {
        return sId;
    }

    public void setSId(Integer sId) {
        this.sId = sId;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Students getStudentId() {
        return studentId;
    }

    public void setStudentId(Students studentId) {
        this.studentId = studentId;
    }

    public Advisors getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Advisors advisorId) {
        this.advisorId = advisorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sId != null ? sId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Slots)) {
            return false;
        }
        Slots other = (Slots) object;
        if ((this.sId == null && other.sId != null) || (this.sId != null && !this.sId.equals(other.sId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.fullerton.AcademyAdvisorAppointment.Slots[ sId=" + sId + " ]";
    }
    
}

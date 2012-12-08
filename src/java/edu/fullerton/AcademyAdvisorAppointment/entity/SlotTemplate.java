/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.entity;

import edu.fullerton.AcademyAdvisorAppointment.entity.Slot.Status;
import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author wujun
 */
@Entity
@Table(uniqueConstraints={
   @UniqueConstraint(columnNames={"adviosr", "templateStartTime", "templateEndTime"})
})
public class SlotTemplate implements Serializable {
         public enum Day {
           SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
           THURSDAY, FRIDAY, SATURDAY 
         }
   
    private static final long serialVersionUID = 1L;
    @TableGenerator(
        name = "TemplateIdGen",
        table = "SEQUENCE_GENERATOR",
        pkColumnName = "GEN_KEY",
        valueColumnName = "GEN_VALUE",
        pkColumnValue = "Template_ID",
        allocationSize = 10)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TemplateIdGen")
    @Column(name = "T_ID")
    private Long id;
    
    @ManyToOne
    Advisor advisor;
    
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date templateStartTime;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date templateEndTime;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date slotLength;
    @NotNull
    @ManyToOne
    private Location location;
    private Slot.Status initStatus;
    Collection<Day> days;

    public SlotTemplate(Long id, Advisor advisor, Date templateStartTime, Date templateEndTime, Date slotLength, Location location, boolean enabled) {
        this.id = id;
        this.advisor = advisor;
        this.templateStartTime = templateStartTime;
        this.templateEndTime = templateEndTime;
        this.slotLength = slotLength;
        this.location = location;
        this.enabled = enabled;
    }
    
    
    boolean enabled;

    public SlotTemplate() {
        this.slotLength=new Time(0, 15, 0); 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public Date getTemplateStartTime() {
        return templateStartTime;
    }

    public void setTemplateStartTime(Date templateStartTime) {
        this.templateStartTime = templateStartTime;
    }

    public Date getTemplateEndTime() {
        return templateEndTime;
    }

    public void setTemplateEndTime(Date templateEndTime) {
        this.templateEndTime = templateEndTime;
    }

    public Date getSlotLength() {
        return slotLength;
    }

    public void setSlotLength(Date slotLength) {
        this.slotLength = slotLength;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Status getInitStatus() {
        return initStatus;
    }

    public void setInitStatus(Status initStatus) {
        this.initStatus = initStatus;
    }

    public Collection<Day> getDays() {
        return days;
    }

    public void setDays(Collection<Day> days) {
        this.days = days;
    }  

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SlotTemplate other = (SlotTemplate) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}

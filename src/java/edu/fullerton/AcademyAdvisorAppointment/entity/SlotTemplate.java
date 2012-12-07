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
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author wujun
 */
@Entity
public class SlotTemplate implements Serializable {
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date templateStartTime;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date templateEndTime;
    @Temporal(TemporalType.TIME)
    private Date slotLength;
    @ManyToOne
    private Location location;
    
    boolean enabled;

    public SlotTemplate() {
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;

/**
 *
 * @author wujun
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Reason.findAll", query = "SELECT r FROM Reason r")})
public class Reason implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableGenerator(
        name = "ReasonIdGen",
        table = "SEQUENCE_GENERATOR",
        pkColumnName = "GEN_KEY",
        valueColumnName = "GEN_VALUE",
        pkColumnValue = "Reason_ID",
        allocationSize = 10)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ReasonIdGen")
    @Column(name = "R_ID")
    private Long id;
    private String reason;
    private boolean backtoback;
    
    

    public Reason() {
    }

    public Reason( String reason, boolean backtoback) {
        
        this.reason = reason;
        this.backtoback = backtoback;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isBacktoback() {
        return backtoback;
    }

    public void setBacktoback(boolean backtoback) {
        this.backtoback = backtoback;
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
        if (!(object instanceof Reason)) {
            return false;
        }
        Reason other = (Reason) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return reason;
    }
    
}

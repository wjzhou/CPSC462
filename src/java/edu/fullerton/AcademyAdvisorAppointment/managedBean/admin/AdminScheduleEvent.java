/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.managedBean.admin;

import edu.fullerton.AcademyAdvisorAppointment.entity.Slot;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot.Status;
import edu.fullerton.AcademyAdvisorAppointment.entity.Student;
import java.io.Serializable;
import java.util.Date;
import org.primefaces.model.ScheduleEvent;

/**
 *
 * @author wujun
 */
public class AdminScheduleEvent implements ScheduleEvent, Serializable{

    private Slot slot;
    public AdminScheduleEvent(Slot slot) {
        this.slot=slot;
    }

    public AdminScheduleEvent() {
        slot=new Slot();
        slot.setStatus(Status.INVALID);
    }
    
    @Override
    public String getId() {
        return slot.getId().toString();
    }

    @Override
    public void setId(String string) {
        throw new UnsupportedOperationException("SetId is not allowed.");
    }


    @Override
    public Slot getData() {
        return slot;
    }

    @Override
    public String getTitle() {
        //if (slot.getStatus()==Slot.Status.)
        Student s=slot.getStudent();
        if(s==null) {
            return slot.getStatus().toString();
        }
        return s.getName();
    }

    @Override
    public Date getStartDate() {
        return slot.getStarttime();
    }
    
    public void setStartDate(Date date) {
        slot.setStarttime(date);
    }   

    @Override
    public Date getEndDate() {
        return slot.getEndtime();
    }
    
    public void setEndDate(Date date) {
        slot.setEndtime(date);
    }      

    @Override
    public boolean isAllDay() {
        return false;
    }

    @Override
    public String getStyleClass() {
        //return "ScheduleEventBooked";
        return "ScheduleEvent" + slot.getStatus().toString();
    }

    @Override
    public boolean isEditable() {
        return true;
    }

    
    public String getAdvisorName() {
        if (slot.getAdvisor()==null) {
            return "";
        }      
        return slot.getAdvisor().getName();
    }
    
    public void setAdvisorName(String advisorName) {
        //todo
    }    

    public String getStudentName() {
        if (slot.getStudent()==null) {
            return "";
        }
        return slot.getStudent().getName();
    }
    
    public void setStudentName(String studentName) {
        //throw new UnsupportedOperationException("set StudentName not supported yet");
    }
  /*  public Slot getSlot() {
        return this.slot;
    }*/
    
    
    /*
     public String[] getStatusList(){
        return Status.values();
    }
    */

  
}

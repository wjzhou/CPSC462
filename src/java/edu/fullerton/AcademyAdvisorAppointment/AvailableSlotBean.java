/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author wujun
 */
@ManagedBean
//@RequestScoped
@ViewScoped
public class AvailableSlotBean implements Serializable{

    private ScheduleModel model;
    private ScheduleEvent event = new DefaultScheduleEvent(); 
    public AvailableSlotBean() {
        //model = new AvailableSlotModel();
        model=new DefaultScheduleModel();
        Calendar cInstance = Calendar.getInstance();
        for (int i = 0; i < 10; i++) {
            cInstance.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            cInstance.set(Calendar.HOUR_OF_DAY, 14);
            cInstance.set(Calendar.MINUTE, 0);
            cInstance.set(Calendar.SECOND, 0);
            cInstance.set(Calendar.MILLISECOND, 0);
            Date time1 = cInstance.getTime();
            cInstance.add(Calendar.MINUTE, 15);
            Date time2 = cInstance.getTime();
            //System.out.print(time2);
            model.addEvent(new DefaultScheduleEvent("title", time1, time2));
            cInstance.add(Calendar.MINUTE, 15);
            time1 = time2;
            time2 = cInstance.getTime();
            model.addEvent(new DefaultScheduleEvent("title", time1, time2));
            cInstance.add(Calendar.MINUTE, 15);
            time1 = time2;
            time2 = cInstance.getTime();
            model.addEvent(new DefaultScheduleEvent("title", time1, time2));
            cInstance.add(Calendar.MINUTE, 15);
            time1 = time2;
            time2 = cInstance.getTime();
            model.addEvent(new DefaultScheduleEvent("title", time1, time2));
            cInstance.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            cInstance.set(Calendar.HOUR_OF_DAY, 14);
            cInstance.set(Calendar.MINUTE, 0);
            cInstance.set(Calendar.SECOND, 0);
            cInstance.set(Calendar.MILLISECOND, 0);
            time1 = cInstance.getTime();
            cInstance.add(Calendar.MINUTE, 15);
            time2 = cInstance.getTime();
            //System.out.print(time2);
            model.addEvent(new DefaultScheduleEvent("title", time1, time2));
            cInstance.add(Calendar.MINUTE, 15);
            time1 = time2;
            time2 = cInstance.getTime();
            model.addEvent(new DefaultScheduleEvent("title", time1, time2));
            cInstance.add(Calendar.MINUTE, 15);
            time1 = time2;
            time2 = cInstance.getTime();
            model.addEvent(new DefaultScheduleEvent("title", time1, time2));
            cInstance.add(Calendar.MINUTE, 15);
            time1 = time2;
            time2 = cInstance.getTime();
            model.addEvent(new DefaultScheduleEvent("title", time1, time2));
            cInstance.add(Calendar.DAY_OF_WEEK, 5);
        }

    }

    public ScheduleModel getModel() {
        return model;
    }

    public void addEvent(ActionEvent actionEvent) {  
        if(event.getId() == null) {
            model.addEvent(event);
        }  
        else {
            
            model.updateEvent(event);
        }  
          
        event = new DefaultScheduleEvent();  
    }

    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
        event = selectEvent.getScheduleEvent();
    }

    public void onDateSelect(DateSelectEvent selectEvent) {
        event = new DefaultScheduleEvent(Math.random() + "", selectEvent.getDate(), selectEvent.getDate());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
    
}

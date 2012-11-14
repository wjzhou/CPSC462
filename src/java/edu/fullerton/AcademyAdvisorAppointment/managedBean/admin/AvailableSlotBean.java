/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.managedBean.admin;

import edu.fullerton.AcademyAdvisorAppointment.ejb.AdminBean;
import edu.fullerton.AcademyAdvisorAppointment.entity.Advisor;
import edu.fullerton.AcademyAdvisorAppointment.entity.Reason;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot.Status;
import edu.fullerton.AcademyAdvisorAppointment.managedBean.admin.AdminScheduleEvent;
import edu.fullerton.AcademyAdvisorAppointment.managedBean.util.MyScheduleModel;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.EnumConverter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author wujun
 */
@ManagedBean
@SessionScoped

public class AvailableSlotBean implements Serializable{
    //private ScheduleModel model;
    //private ScheduleModel model;
    private AdminScheduleEvent event = new AdminScheduleEvent(); 
    @EJB
    private AdminBean adminBean; 
    private ScheduleModel model;
    private Advisor advisor;
    private Reason reason;
    public AvailableSlotBean() {
       //model=new DefaultScheduleModel();
    }

    @PostConstruct
    void updateModel(){
         //this.reason=appli
         updateModelBasedOnAdvisor();
    }
    public ScheduleModel getModel(){
        return model;
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
    
    
    public void handleToggle(ToggleEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());  
          
        addMessage(message);  
    }  
    /*public void setModel(ScheduleModel model) {
        this.model =  model;
    }*/

    
    public String addEvent() {  
        if(event.getId() == null) {
            //model.addEvent(event);
            throw new UnsupportedOperationException("add event not supported yet");
        }  
        else {          
            adminBean.updateSlot((Slot)event.getData());
        }
        return "success";
          
      // event = new DefaultScheduleEvent();  
    }

    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
        event = (AdminScheduleEvent) selectEvent.getScheduleEvent();
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/admin/editAppointment.xhtml"));
        try {
            extContext.redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
       // RequestContext.getCurrentInstance().execute("window.open('http://www.google.com');");
    }

    public void onDateSelect(DateSelectEvent selectEvent) {
      //  event = new DefaultScheduleEvent(Math.random() + "", selectEvent.getDate(), selectEvent.getDate());
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
        this.event = (AdminScheduleEvent) event;
    }
    
    public Status getStatus(){
        return ((Slot)event.getData()).getStatus();
    }
    public void setStatus(Slot.Status s){
        ((Slot)event.getData()).setStatus(s);
    } 
    /*public void setStatus(String s){
        slot.setStatus(Status.valueOf(s));
    }*/
    public List getStatusList(){
        List statusList = new ArrayList();
        for (Status s : Status.values()){
            statusList.add(new SelectItem(s));
        }
        return statusList;
    }
    
    public Converter getStatusConverter(){
        return new EnumConverter(Status.class);
    }
    
    /*public List<String> getAdvisorList(){
        List nameList = new ArrayList();
        List<Object[]> names=adminBean.getAdviosrNameList();
        for (Object[] name : names){
            nameList.add(new SelectItem(name, (String)name[0]+' '+(String)name[1]));
        }
        return nameList;
    }
                     
    public String getCurrentAdvisorByName(){
        return "Wang";
    }
    public void setCurrentAdvisorByName(Object[] advisorName){
        
    } */   
    public void updateModelBasedOnAdvisor(){
        List<Slot> allSlots;
        if (advisor==null) {
            allSlots = adminBean.getAllSlots();
        }
        else {
            allSlots = adminBean.getSlotsByAdvisor(advisor);
        }
        this.model=new MyScheduleModel(allSlots);
    }
    
}

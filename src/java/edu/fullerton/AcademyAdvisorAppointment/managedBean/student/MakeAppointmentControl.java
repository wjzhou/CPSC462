/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.managedBean.student;

import edu.fullerton.AcademyAdvisorAppointment.ejb.AdminBean;
import edu.fullerton.AcademyAdvisorAppointment.ejb.AdvisorFacade;
import edu.fullerton.AcademyAdvisorAppointment.ejb.MailBean;
import edu.fullerton.AcademyAdvisorAppointment.ejb.SlotFacade;
import edu.fullerton.AcademyAdvisorAppointment.entity.Advisor;
import edu.fullerton.AcademyAdvisorAppointment.entity.Reason;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot.Status;
import edu.fullerton.AcademyAdvisorAppointment.managedBean.ApplecationManagedBean;
import edu.fullerton.AcademyAdvisorAppointment.managedBean.admin.AdminScheduleEvent;
import edu.fullerton.AcademyAdvisorAppointment.managedBean.admin.util.JsfUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.EnumConverter;
import javax.faces.model.SelectItem;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import edu.fullerton.AcademyAdvisorAppointment.managedBean.util.MyScheduleModel;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author wujun
 */
@ManagedBean
@SessionScoped

public class MakeAppointmentControl implements Serializable{
    @EJB
    private MailBean mailBean;
    @EJB
    private SlotFacade slotFacade;
    @EJB
    private AdvisorFacade advisorFacade;
    
    @Inject
    ApplecationManagedBean applecationManagedBean;
    //private AvailableSlotModel model;
    //private ScheduleModel model;
    private AdminScheduleEvent event = new AdminScheduleEvent(); 
    @EJB
    private AdminBean adminBean;
    
    @ManagedProperty(value="#{studentControl}")
    private StudentControl studentControl;
    private MyScheduleModel model;
    private Advisor advisor;
    private Reason reason;
    private Slot selectedSlot;
    public MakeAppointmentControl() {
    }

    
    void updateModel(){
         updateModelBasedOnAdvisor();
    }
    public ScheduleModel getModel(){
        updateModel();
        return model;
    }

    public StudentControl getStudentControl() {
        return studentControl;
    }

    public void setStudentControl(StudentControl studentControl) {
        this.studentControl = studentControl;
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
    /*public void setModel(AvailableSlotModel model) {
        this.model =  model;
    }*/
    
    public void select(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/confirmAppointment.xhtml"));
        try {
            extContext.redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
        event = (AdminScheduleEvent) selectEvent.getScheduleEvent();
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
    
      
    public void updateModelBasedOnAdvisor(){
        List<Slot> allSlots;
        if (advisor==null) {
            model=new MyScheduleModel();
            for(Advisor advisor : getAdvisors()){
                 allSlots = adminBean.getSlotsByAdvisor(advisor);
                 model.add(allSlots);
            }
        }
        else {
            allSlots = adminBean.getSlotsByAdvisor(advisor);
            model=new MyScheduleModel(allSlots);
        }
    }
    public String confirmAppointment(){
        /*if (slotFacade.haveAppointMent(studentControl.getStudent())){           
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "test of add message","detail");         
        //addMessage(message);  
        return "oneAppointment";
        }*/
        try{
            if (event.getData().getStatus()!=Status.AVAILABLE){
                JsfUtil.addErrorMessage("The status of slot is not available, please try again");
                return "tryAgain";
            }
            event.getData().setStatus(Status.BOOKED);
            event.getData().setStudent(studentControl.getStudent());         
            slotFacade.edit(event.getData());
        }
        catch(Exception e){
            return "tryAgain";
        }
        
        String remoteAddr = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
        String remoteHost = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteHost();
        int remotePort = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemotePort();
        String requestContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        String confirmUrl="http://"+remoteHost+":"+remotePort+requestContextPath+"/faces/admin/slot/List.xhtml";        
        try {
            mailBean.sendMail(applecationManagedBean.getCurrentAdmin(), "New Appointment arrive", "Please visit the following url:" +confirmUrl+"to review\n\n");
        } catch (NamingException ex) {
            Logger.getLogger(MakeAppointmentControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MakeAppointmentControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MakeAppointmentControl.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return "congratulation";
    }
    public List<Slot> getSlotByStudent(){
        return slotFacade.findByStudent(studentControl.getStudent());
    }

    public Slot getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(Slot selectedSlot) {
        this.selectedSlot = selectedSlot;
    }
    
    public String cancelAppointment(){
        try{
            selectedSlot.setStatus(Status.AVAILABLE);
            selectedSlot.setStudent(null);         
            slotFacade.edit(selectedSlot);
        }
        catch(Exception e){
            return "InternalError";
        }
            
        return null;
    }
    private List<Advisor> advisors;
    public List<Advisor> getAdvisors(){
        if (advisors==null){
            advisors=advisorFacade.findByType(studentControl.getType());
        }
        return advisors;
    }
    
}

package edu.fullerton.AcademyAdvisorAppointment.managedBean.admin;

import edu.fullerton.AcademyAdvisorAppointment.entity.Slot;
import edu.fullerton.AcademyAdvisorAppointment.managedBean.admin.util.JsfUtil;
import edu.fullerton.AcademyAdvisorAppointment.managedBean.admin.util.PaginationHelper;
import edu.fullerton.AcademyAdvisorAppointment.ejb.SlotFacade;
import edu.fullerton.AcademyAdvisorAppointment.entity.Advisor;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot.Status;
import edu.fullerton.AcademyAdvisorAppointment.entity.SlotTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.jboss.weld.util.collections.ArraySet;

@ManagedBean(name = "slotController")
@SessionScoped
public class SlotController implements Serializable {

    private Slot current;
    @EJB
    private edu.fullerton.AcademyAdvisorAppointment.ejb.SlotFacade ejbFacade;
    
    private int selectedItemIndex;

    public SlotController() {
    }
    @PostConstruct
    public void init(){
       if(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("approveMode")!=null){
           statusSet=new ArrayList<Status>();
           statusSet.add(Status.BOOKED);
           advisor=null;
           startDay=endDay=null;
       }
    }

    public Slot getSelected() {
        if (current == null) {
            current = new Slot();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SlotFacade getFacade() {
        return ejbFacade;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Slot) getSlots().getRowData();
        selectedItemIndex =  getSlots().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Slot();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SlotCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Slot) getSlots().getRowData();
        selectedItemIndex = getSlots().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SlotUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Slot) getSlots().getRowData();
        selectedItemIndex = getSlots().getRowIndex();
        performDestroy();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SlotDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }
    
    public DataModel getBookedItems(){
        return new ListDataModel(getFacade().findByStatus(Slot.Status.BOOKED));
    }

    private void recreateModel() {
        slots = null;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Slot.class)
    public static class SlotControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SlotController controller = (SlotController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "slotController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Slot) {
                Slot o = (Slot) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Slot.class.getName());
            }
        }
    }
    
    private DataModel<Slot> slots=null;
    public DataModel<Slot> getSlots() {
        
       // if (slots==null){
            slots=new SlotDataModle(ejbFacade.findByCondition(
                startDay,endDay,advisor, statusSet));
        //}
        return slots;
    }
    
    Slot[] selectedSlots;
    public Slot[] getSelectedSlots() {  
        return selectedSlots;  
    }  
    public void setSelectedSlots(Slot[] selectedSlots) {  
        this.selectedSlots = selectedSlots;  
    }
    
    Slot[] filteredSlots;
    public Slot[] getFilteredSlots() {  
        return filteredSlots;  
    }  
    public void setFilteredSlots(Slot[] filteredSlots) {  
        this.filteredSlots = filteredSlots;  
    }

    public Converter getStatusConverter() {
        return new EnumConverter(Status.class);
    }
    Date currentDate=new Date();
    public Date getCurrentDate(){
        return currentDate;
    }
    
        private Date startDay;
    private Date endDay;

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }
    
    private Advisor advisor;

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }
    
    public Status[] getAllStatus(){
        return Status.values();
    }
    
    Collection<Status> statusSet=new ArrayList<Status>();

    public Collection<Status> getStatusSet() {
        return statusSet;
    }

    public void setStatusSet(Collection<Status> statusSet) {
        this.statusSet = statusSet;
    }
   
    public void updateTable(){
        slots=null;
    }
    
    public String approve() {
        current = (Slot) getSlots().getRowData();
        if (current.getStatus()!=Status.BOOKED) {
            return null;
        }
        current.setStatus(Status.APPROVED);       
        ejbFacade.edit(current);
        return null;
    }
}

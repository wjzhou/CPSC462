package edu.fullerton.AcademyAdvisorAppointment.managedBean.admin;

import edu.fullerton.AcademyAdvisorAppointment.ejb.SlotFacade;
import edu.fullerton.AcademyAdvisorAppointment.ejb.SlotTemplateFacade;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot.Status;
import edu.fullerton.AcademyAdvisorAppointment.entity.SlotTemplate;
import edu.fullerton.AcademyAdvisorAppointment.entity.SlotTemplate.Day;
import edu.fullerton.AcademyAdvisorAppointment.managedBean.admin.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

    
@ManagedBean(name = "slotTemplateController")
@SessionScoped
public class SlotTemplateController implements Serializable {
    @EJB
    private SlotFacade slotFacade;

    private SlotTemplate current;
    private DataModel items = null;
    @EJB
    private edu.fullerton.AcademyAdvisorAppointment.ejb.SlotTemplateFacade ejbFacade;
    private int selectedItemIndex;

    public SlotTemplateController() {
    }

    public SlotTemplate getSelected() {
        if (current == null) {
            current = new SlotTemplate();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SlotTemplateFacade getFacade() {
        return ejbFacade;
    }


    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (SlotTemplate) getItems().getRowData();
        selectedItemIndex = getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new SlotTemplate();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SlotTemplateCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (SlotTemplate) getItems().getRowData();
        selectedItemIndex = getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SlotTemplateUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (SlotTemplate) getItems().getRowData();
        selectedItemIndex = getItems().getRowIndex();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SlotTemplateDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            return new SlotTemplateDataModle(getFacade().findAll());
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = SlotTemplate.class)
    public static class SlotTemplateControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SlotTemplateController controller = (SlotTemplateController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "slotTemplateController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SlotTemplate) {
                SlotTemplate o = (SlotTemplate) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SlotTemplate.class.getName());
            }
        }
    }
    
    public SlotTemplate.Day[] getAllDays() {
        return SlotTemplate.Day.values();
    }

    public Converter getDayConverter() {
        return new EnumConverter(SlotTemplate.Day.class);
    }
    
   public Status[] getAllStatus(){
        final Status[] status={Status.AVAILABLE, Status.INVALID};
        return status;
    }

    public Converter getStatusConverter() {
        return new EnumConverter(Slot.Status.class);
    }
    private List<SlotTemplate> filteredSlotTemplates;
    public List<SlotTemplate> getFilteredSlotTemplates() {  
        return filteredSlotTemplates;  
    }  
  
    public void setFilteredSlotTemplates(List<SlotTemplate> filteredSlotTemplates) {  
        this.filteredSlotTemplates = filteredSlotTemplates;  
    }
    
    SlotTemplate[] selectedSlotTemplates;
    public SlotTemplate[] getSelectedSlotTemplates() {  
        return selectedSlotTemplates;  
    }  
    public void setSelectedSlotTemplates(SlotTemplate[] selectedSlotTemplates) {  
        this.selectedSlotTemplates = selectedSlotTemplates;  
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
    
    
    public void createPreView(){
        renderPreview=true;
        Calendar cInstance = Calendar.getInstance();
        List<Slot> slots=new ArrayList<Slot>();
        
        cInstance.setTime(endDay);
        cInstance.add(Calendar.DAY_OF_WEEK, 1);
        Date endDayTime=cInstance.getTime();
        cInstance.add(Calendar.DAY_OF_WEEK, 6);
        Date endDayWeekTime=cInstance.getTime();
        
        
        int id = 1;
        for (SlotTemplate slotTemplate : selectedSlotTemplates) {
             cInstance.setTime(startDay);
             Date startDayTime=cInstance.getTime();
            
                int startHour = slotTemplate.getTemplateStartTime().getHours();
                int startMinute = slotTemplate.getTemplateStartTime().getMinutes();
                int endHour = slotTemplate.getTemplateEndTime().getHours();
                int endMinute = slotTemplate.getTemplateEndTime().getMinutes();
                int lengthHour = slotTemplate.getSlotLength().getHours();
                int lengthMinute = slotTemplate.getSlotLength().getMinutes();

             while (cInstance.getTime().before(endDayWeekTime)) {
                for (Day day : slotTemplate.getDays()) {
                    cInstance.set(Calendar.DAY_OF_WEEK, day.getDayOfWeek());
                    cInstance.set(Calendar.HOUR_OF_DAY, endHour);
                    cInstance.set(Calendar.MINUTE, endMinute);
                    Date templateEndTime = cInstance.getTime();

                    cInstance.set(Calendar.HOUR_OF_DAY, startHour);
                    cInstance.set(Calendar.MINUTE, startMinute);
                    cInstance.set(Calendar.SECOND, 0);
                    cInstance.set(Calendar.MILLISECOND, 0);

                    Date time1 = cInstance.getTime();
                    
                    if(time1.after(endDayTime)||time1.before(startDayTime)){
                        continue;
                    }

                    cInstance.add(Calendar.HOUR_OF_DAY, lengthHour);
                    cInstance.add(Calendar.MINUTE, lengthMinute);
                    Date time2 = cInstance.getTime();

                    while (time2.compareTo(templateEndTime) <= 0) {
                        Slot slot = new Slot(time1, time2);
                        slot.setAdvisor(slotTemplate.getAdvisor());
                        slot.setId(id++);
                        slot.setLocation(slotTemplate.getLocation().toString());
                        slot.setStatus(slotTemplate.getInitStatus());
                        slots.add(slot);
                        //slotFacade.create(slot);
                        cInstance.add(Calendar.HOUR_OF_DAY, lengthHour);
                        cInstance.add(Calendar.MINUTE, lengthMinute);
                        time1 = time2;
                        time2 = cInstance.getTime();
                    }
                }/*for day inside slotTemplate*/
                cInstance.add(Calendar.DAY_OF_WEEK, 7);
            }/*while indie time*/
        }/*for slotTemplate*/
        previewSlots=new SlotDataModle(slots);
        /*for (Slot slot : slots){
            if findSlotInRange(slot)
        }*/
        selectedPreviewSlots=slots.toArray(new Slot[slots.size()]);
    }
    
    private DataModel previewSlots = null;
    public DataModel getPreviewSlots() {
        return previewSlots;
    }
    boolean renderPreview = false;
    public boolean getRenderPreview(){
        return renderPreview;
    }
    
    Slot[] selectedPreviewSlots;
    public Slot[] getSelectedPreviewSlots() {  
        return selectedPreviewSlots;  
    }  
    public void setSelectedPreviewSlots(Slot[] selectedPreviewSlots) {  
        this.selectedPreviewSlots = selectedPreviewSlots;  
    }
    
    public String confirm(){
        for (Slot slot : selectedPreviewSlots){
            slot.setId(null);
            slotFacade.create(slot);
        }
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SlotTemplateSlotsCreateSuccess"));
        previewSlots=null;
        selectedPreviewSlots=null;
        renderPreview=false;
        startDay=null;
        endDay=null;
        return "List";      
    }
    Date currentDate=new Date();
    public Date getCurrentDate(){
        return currentDate;
    }
}

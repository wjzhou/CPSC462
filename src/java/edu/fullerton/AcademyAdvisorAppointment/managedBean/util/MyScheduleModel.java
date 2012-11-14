/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.managedBean.util;

import edu.fullerton.AcademyAdvisorAppointment.entity.Slot;
import edu.fullerton.AcademyAdvisorAppointment.managedBean.admin.AdminScheduleEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author wujun
 */

//@ManagedBean(name="MyScheduleModel")
//@ViewScoped
public class MyScheduleModel implements ScheduleModel,Serializable {
    private List<ScheduleEvent> events;
  //  private boolean inited=false;
    
    public MyScheduleModel(List<Slot> slots ) {
        events = new ArrayList<ScheduleEvent> (slots.size());
        for (Iterator<Slot> it = slots.iterator(); it.hasNext();) {
            Slot s = it.next();
            events.add(new AdminScheduleEvent(s));
        }

        //model=new HashMap<String,ScheduleEvent>();
    }
    
    /*public void setEJB(AdminBean adminBean)
    {
        this.adminBean=adminBean;
        init();
    }
    //The EJB injection does not work inside a construction 
    public void init() {
        if (!inited) {
            inited=true;
            
          
        }
    }*/

    @Override
    public void addEvent(ScheduleEvent se) {
        events.add(se);
    }

    @Override
    public boolean deleteEvent(ScheduleEvent se) {
      throw new UnsupportedOperationException("We are not expect delete event");
    }

    @Override
    public List<ScheduleEvent> getEvents() {
        return events;
    }

    @Override
    public ScheduleEvent getEvent(String id) {
        for(ScheduleEvent event : events) {
            if(event.getId().equals(id)) {
                return event;
            }
	}		
	return null;
    }

    /* We will reuse the exist se, thus no work needs to be done here*/
    @Override
    public void updateEvent(ScheduleEvent se) {
        
    }

    @Override
    public int getEventCount() {
        return events.size();
    }

    /*Don't know what is does*/
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Unsupported");
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.faces.model.ListDataModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author wujun
 */
class AvailableSlotModel implements ScheduleModel {

    Map<String,ScheduleEvent> model;
    public AvailableSlotModel() {
        model=new HashMap<String,ScheduleEvent>();
    }

    @Override
    public void addEvent(ScheduleEvent se) {
        model.put(se.getId(),se);
    }

    @Override
    public boolean deleteEvent(ScheduleEvent se) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ScheduleEvent> getEvents() {
        return new ArrayList<ScheduleEvent>(model.values());
    }

    @Override
    public ScheduleEvent getEvent(String string) {
        return model.get(string);
    }

    @Override
    public void updateEvent(ScheduleEvent se) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getEventCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

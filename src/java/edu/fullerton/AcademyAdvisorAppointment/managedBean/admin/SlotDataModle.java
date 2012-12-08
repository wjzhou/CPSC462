/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.managedBean.admin;

import edu.fullerton.AcademyAdvisorAppointment.entity.Slot;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author wujun
 */

public class SlotDataModle extends ListDataModel<Slot> implements SelectableDataModel<Slot> {    
  
    public SlotDataModle() {  
    }  
  
    public SlotDataModle(List<Slot> data) {  
        super(data);  
    }  
      
    @Override  
    public Slot getRowData(String rowKey) {
          
        List<Slot> Slots = (List<Slot>) getWrappedData();  
          
        for(Slot Slot : Slots) {  
            if(Slot.getId().equals(Integer.valueOf(rowKey))) {
                return Slot;
            }
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Slot Slot) {  
        return Slot.getId();
    }  
}  

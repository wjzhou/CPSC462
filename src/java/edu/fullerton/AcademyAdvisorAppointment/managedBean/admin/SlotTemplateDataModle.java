/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.managedBean.admin;

import edu.fullerton.AcademyAdvisorAppointment.entity.SlotTemplate;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author wujun
 */

public class SlotTemplateDataModle extends ListDataModel<SlotTemplate> implements SelectableDataModel<SlotTemplate> {    
  
    public SlotTemplateDataModle() {  
    }  
  
    public SlotTemplateDataModle(List<SlotTemplate> data) {  
        super(data);  
    }  
      
    @Override  
    public SlotTemplate getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<SlotTemplate> slotTemplates = (List<SlotTemplate>) getWrappedData();  
          
        for(SlotTemplate slotTemplate : slotTemplates) {  
            if(slotTemplate.getId().equals(Long.valueOf(rowKey))) {
                return slotTemplate;
            }
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(SlotTemplate slotTemplate) {  
        return slotTemplate.getId();  
    }  
}  

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.ejb;

import edu.fullerton.AcademyAdvisorAppointment.ejb.AdminBean;
import edu.fullerton.AcademyAdvisorAppointment.entity.Admin;
import edu.fullerton.AcademyAdvisorAppointment.entity.Advisor;
import edu.fullerton.AcademyAdvisorAppointment.entity.Location;
import edu.fullerton.AcademyAdvisorAppointment.entity.Reason;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot;
import edu.fullerton.AcademyAdvisorAppointment.entity.SlotTemplate;
import edu.fullerton.AcademyAdvisorAppointment.entity.SlotTemplate.Day;
import edu.fullerton.AcademyAdvisorAppointment.entity.Student;
import edu.fullerton.AcademyAdvisorAppointment.entity.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.jboss.weld.util.collections.ArraySet;

/**
 *
 * @author wujun
 */
@Singleton
@Startup
public class ConfigBean {
    @EJB
    private SlotTemplateFacade slotTemplateFacade;
    @EJB
    private LocationFacade locationFacade;
    @EJB
    private AdminBean adminBean;
    @EJB
    private ReasonFacade reasonFacade;
    @EJB
    private AdminFacade adminFacade;
    @PostConstruct
    public void createData() {
        Advisor advisor=new Advisor("Shawn", "Wang", "CS 522C");
        advisor.addAdvisingTypes(Type.GRADUATE);
        
        Advisor advisor1=new Advisor("Test", "Advisor1", "CS 522C");
        advisor1.addAdvisingTypes(Type.GRADUATE);
        advisor1.addAdvisingTypes(Type.UNDER_GRADUATE);
        
        adminBean.createAdvisor(advisor);
        adminBean.createAdvisor(advisor1);
        Advisor advisor2=new Advisor("Test", "Advisor2", "CS 522C");
        advisor2.addAdvisingTypes(Type.UNDER_GRADUATE);
        adminBean.createAdvisor(advisor2);
        
        Admin admin=new Admin();
        admin.setFirstname("Sandra");
        admin.setLastname("Boulanger");
        admin.setPassword("123456");
        admin.setEmail("wujun_zhou@csu.fullerton.edu");
        adminFacade.create(admin);
        
        Location location=new Location("CS 578");
        locationFacade.create(location);
        
        
        Student student=new Student("893900076", Type.GRADUATE, "Wujun", "Zhou", "zwj.echo@gmail.com", "714-515-9869");
        student.setPassword("123456");
        adminBean.createStudent(student);
        
        Reason reason1=new Reason("Build Study Plan", true);
        Reason reason2=new Reason("Change Study Plan", false);
        reasonFacade.create(reason1);
        reasonFacade.create(reason2);
        
        SlotTemplate slotTemplate=new SlotTemplate();
        slotTemplate.setAdvisor(advisor);
        slotTemplate.setEnabled(true);
        Collection<Day> days=new ArrayList<Day>();
        days.add(Day.MONDAY);
        days.add(Day.WEDNESDAY);
        slotTemplate.setDays(days);
        slotTemplate.setInitStatus(Slot.Status.AVAILABLE);
        slotTemplate.setLocation(location);
        slotTemplate.setTemplateStartTime(new Date(0,0,0,14,0));
        slotTemplate.setTemplateEndTime(new Date(0,0,0,15,0));
        slotTemplate.setSlotLength(new Date(0,0,0,0,15));
        slotTemplateFacade.create(slotTemplate);
        
        Calendar cInstance = Calendar .getInstance();
        for (int i = 0; i < 10; i++) {
            cInstance.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            cInstance.set(Calendar.HOUR_OF_DAY, 14);
            cInstance.set(Calendar.MINUTE, 0);
            cInstance.set(Calendar.SECOND, 0);
            cInstance.set(Calendar.MILLISECOND, 0);
            Date time1 = cInstance.getTime();
            cInstance.add(Calendar.MINUTE, 15);
            Date time2 = cInstance.getTime();
            for (int j = 0; j < 4; j++) {
                adminBean.addAvailableSlot(time1, time2, advisor);
                cInstance.add(Calendar.MINUTE, 15);
                time1 = time2;
                time2 = cInstance.getTime();
            }
            cInstance.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            cInstance.set(Calendar.HOUR_OF_DAY, 14);
            cInstance.set(Calendar.MINUTE, 0);
            cInstance.set(Calendar.SECOND, 0);
            cInstance.set(Calendar.MILLISECOND, 0);
            time1 = cInstance.getTime();
            cInstance.add(Calendar.MINUTE, 15);
            time2 = cInstance.getTime();
            for (int j = 0; j < 4; j++) {
                adminBean.addAvailableSlot(time1, time2, advisor);
                cInstance.add(Calendar.MINUTE, 15);
                time1 = time2;
                time2 = cInstance.getTime();
            }
            cInstance.add(Calendar.DAY_OF_WEEK, 5);
        }
        //adminBean.makeAppointment(student, slot);
        
    }

    @PreDestroy
    public void deleteData() {
    }

    Admin getAdmin() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

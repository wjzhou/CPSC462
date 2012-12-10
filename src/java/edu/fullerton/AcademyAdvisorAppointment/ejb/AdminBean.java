/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.ejb;

import edu.fullerton.AcademyAdvisorAppointment.entity.Advisor;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot;
import edu.fullerton.AcademyAdvisorAppointment.entity.Student;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Path;

/**
 *
 * @author wujun
 */
@Named
@Path("AAA/Admin")
@Stateless
public class AdminBean implements Serializable{

    /**
     * Creates a new instance of AdminBean
     */
    @PersistenceContext
    private EntityManager em;
    public AdminBean() {
        
    }
    public String createAdvisor(Advisor advisor) {
        em.persist(advisor);
        return "createdAdvisor";
    }

    public String editAdvisor(Advisor advisor) {
        em.merge(advisor);
        return "editedAdvisor";
    }

    public String removeAdvisor(Advisor advisor) {
        em.remove(advisor);
        return "removedAdvisor";
    }

    /* The student part also have a createStudent part, because
     * new student can be added without the advisor's interaction
     */
    public String createStudent(Student student) {
        em.persist(student);
        return "createdStudent";
    }

    public String editStudent(Student student) {
        em.merge(student);
 
        return "editedStudent";
    }

    public String removeStudent(Student student) {
        em.remove(student);
        return "removedStudent";
    }

    public void addAvailableSlot(Date time1, Date time2, Advisor advisor) {
        Slot slot=new Slot(time1, time2, Slot.Status.AVAILABLE, advisor);
       // slot.setAdvisor(advisor);
        em.persist(slot);
    }
    public List<Slot> getAllSlots(){
        Query createNamedQuery = em.createNamedQuery("Slot.findAll");
        return createNamedQuery.getResultList();  
    }
    
    public Slot updateSlot(Slot slot){
        return em.merge(slot);
        //return createNamedQuery.getResultList();  
    }
    
    public List<Object[]> getAdviosrNameList() {
        Query createNamedQuery = em.createNamedQuery("Advisor.findAllAdvisorName");
        return createNamedQuery.getResultList();
    }  

    public List<Slot> getSlotsByAdvisor(Advisor advisor) {
        Query createNamedQuery = em.createNamedQuery("Slot.findByAdvisor")
                .setParameter("advisor", advisor);
        return createNamedQuery.getResultList();
    }
}

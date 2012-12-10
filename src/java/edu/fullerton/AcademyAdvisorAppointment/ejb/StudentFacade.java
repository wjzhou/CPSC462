/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.ejb;

import edu.fullerton.AcademyAdvisorAppointment.entity.Person_;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot_;
import edu.fullerton.AcademyAdvisorAppointment.entity.Student;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author wujun
 */
@Stateless
public class StudentFacade extends AbstractFacade<Student> {
    @PersistenceContext(unitName = "AAAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
    }
    
    public Student findByEmail(String email){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root<Student> s = cq.from(Student.class);
        cq.select(s);
        cq.where(cb.equal(s.get(Person_.email), email));
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return (Student) q.getResultList().get(0);
    }
    
}

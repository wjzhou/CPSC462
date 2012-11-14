/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.ejb;

import edu.fullerton.AcademyAdvisorAppointment.entity.Slot;
import edu.fullerton.AcademyAdvisorAppointment.entity.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 *
 * @author wujun
 */
@Stateless
public class SlotFacade extends AbstractFacade<Slot> {
    @PersistenceContext(unitName = "AAAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SlotFacade() {
        super(Slot.class);
    }
    @Override
    public List<Slot> findAll() {
        Query createNamedQuery = em.createNamedQuery("Slot.findAll");
        return createNamedQuery.getResultList(); 
    }
    
    @Override
    public List<Slot> findRange(int[] range) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root<Slot> s = cq.from(Slot.class);
        cq.select(s);
        cq.orderBy(cb.asc(s.get("starttime")));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    public List<Slot> findByStudent(Student student) {
        Query createNamedQuery = em.createNamedQuery("Slot.findByStudent");
        createNamedQuery.setParameter("student", student);
        return createNamedQuery.getResultList();
    }
}

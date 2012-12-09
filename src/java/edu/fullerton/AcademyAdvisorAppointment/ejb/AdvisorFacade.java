/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.ejb;

import edu.fullerton.AcademyAdvisorAppointment.entity.Advisor;
import edu.fullerton.AcademyAdvisorAppointment.entity.Advisor_;
import edu.fullerton.AcademyAdvisorAppointment.entity.Type;
import java.util.ArrayList;
import java.util.List;
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
public class AdvisorFacade extends AbstractFacade<Advisor> {
    @PersistenceContext(unitName = "AAAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdvisorFacade() {
        super(Advisor.class);
    }
    
    public List<Advisor> findByType(Type type){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root<Advisor> s = cq.from(Advisor.class);
        cq.select(s);
        cq.where(cb.isMember(type,s.get(Advisor_.advisingTypes)));
        //cq.where(cb.greaterThanOrEqualTo(s.get(Slot_.starttime),startDay));
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
            
    
}

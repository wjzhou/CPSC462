/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.managedBean.admin;

import edu.fullerton.AcademyAdvisorAppointment.entity.SlotTemplate;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wujun
 */
@Stateless
public class SlotTemplateFacade extends AbstractFacade<SlotTemplate> {
    @PersistenceContext(unitName = "AAAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SlotTemplateFacade() {
        super(SlotTemplate.class);
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.managedBean;

import edu.fullerton.AcademyAdvisorAppointment.entity.Advisor;
import edu.fullerton.AcademyAdvisorAppointment.entity.Reason;
import edu.fullerton.AcademyAdvisorAppointment.entity.Slot;
import edu.fullerton.AcademyAdvisorAppointment.entity.Type;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.EnumConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wujun
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class ApplecationManagedBean {
    
    @PersistenceContext
    private EntityManager em;
    private List<Reason> reasons;
    private List<Advisor> advisors;
    
    public ApplecationManagedBean() {
    }
    public class ListConverter<T> implements Converter {
        private final List<T> values;
        public ListConverter(List<T> values) {
            this.values=values;
        }

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            int select = Integer.parseInt(value);
            if (select == -1) {
                return null;
            }
            return values.get(select);
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return Integer.toString(values.indexOf(value));
        }
    }
    public void preRenderView() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        //tune session params, eg. session.setMaxInactiveInterval(..);
        //perform other pre-render stuff, like setting user context...
    }
    @PostConstruct
    void postConstruct(){
        Query createNamedQuery = em.createNamedQuery("Reason.findAll");
        this.reasons=createNamedQuery.getResultList();
        this.advisors=em.createNamedQuery("Advisor.findAll").getResultList();
    }

    public Type[] getStudentTypes() {
        return new Type[]{Type.UNDER_GRADUATE, Type.GRADUATE};
    }

    public Type[] getAdvisorTypes() {
        return Type.values();
    }

    public Converter getTypeConverter() {
        return new EnumConverter(Type.class);
    }
    
    public List<Reason> getReasons() {
        return reasons;
    }
    
    public Converter getReasonConverter() {
        return new ListConverter<Reason>(reasons);
        
    }
    
    public List<Advisor> getAdvisors() {
        return advisors;
    }
    public Converter getAdvisorConverter() {
        return new Converter() {

            @Override
            public Object getAsObject(FacesContext context, UIComponent component, String value) {
                int select=Integer.parseInt(value);
                if (select == -1) {
                    return null;
                }
                return advisors.get(select);
            }

            @Override
            public String getAsString(FacesContext context, UIComponent component, Object value) {
                return Integer.toString(advisors.indexOf(value));
            }
        };
    }
    
    void sendEmail(){
        
    }
    
}

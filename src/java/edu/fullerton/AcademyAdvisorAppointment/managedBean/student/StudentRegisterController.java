/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.managedBean.student;

import edu.fullerton.AcademyAdvisorAppointment.ejb.StudentFacade;
import edu.fullerton.AcademyAdvisorAppointment.entity.Student;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author wujun
 */
@ManagedBean
@SessionScoped
public class StudentRegisterController implements Serializable{
    @EJB
    private StudentFacade studentFacade;
    @Inject
    private Auth auth;
    
    private Student student;
    
    public StudentRegisterController() {
        student=new Student();
    }
    
    public void forgetPassword() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("login", new FacesMessage("forget Password has not been implemented yet"));
    }
    public String createStudent() throws IOException {
        try{
             studentFacade.create(student);
        }
        catch (Exception e){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("login", new FacesMessage("Create student error"));
            return "login";
        }
        auth.loginAfterRegister(student);
        return "makeAppointment";
    }
}

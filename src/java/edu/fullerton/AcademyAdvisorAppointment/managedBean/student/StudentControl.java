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
public class StudentControl implements Serializable{
    @EJB
    private StudentFacade studentFacade;
    @Inject
    private Auth auth;
    
    private Student student;
    
    public StudentControl() {
        student=new Student();
    }
    
    public void forgetPassword() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("login", new FacesMessage("forget Password has not been implemented yet"));
    }
    public String createStudent() throws IOException {
        try{
            student.setPassword(rawPassword);
             studentFacade.create(student);
        }
        catch (Exception e){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("login", new FacesMessage("Create student error"));
            return "register";
        }
        auth.loginAfterRegister(student.getEmail(),rawPassword);
        return "makeAppointment";
    }

    String rawPassword;

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }
    
    public Student getStudent() {
        return student;
    }
    
}

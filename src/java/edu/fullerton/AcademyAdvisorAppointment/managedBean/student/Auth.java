/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.managedBean.student;

/**
 *
 * @author wujun
 */
import edu.fullerton.AcademyAdvisorAppointment.ejb.StudentFacade;
import edu.fullerton.AcademyAdvisorAppointment.entity.Student;
import java.io.IOException;
import java.security.Principal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class Auth {
    private Student student;
    private String username;
    private String password;
    private String originalURL;
    @EJB
    private StudentFacade studentFacade;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + "/faces/index.xhtml";
        }
    }

    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            request.login(username, password);
            student = studentFacade.findByEmail(username);
            externalContext.redirect(originalURL);
        } catch (ServletException e) {
            // Handle unknown username/password in request.login().
            context.addMessage(null, new FacesMessage("Username or password error"));
        }
    }
    public void loginAfterRegister(Student student) throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            request.login(student.getEmail(), student.getPassword());
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Internal error."));
        }
    }

    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml");
    }
    
    public Student getStudent() {
        if (student == null) {
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (principal != null) {
                student = studentFacade.find(principal.getName());
            }
        }
        return student;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

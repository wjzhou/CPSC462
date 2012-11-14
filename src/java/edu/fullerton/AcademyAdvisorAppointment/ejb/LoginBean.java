/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.ejb;

import edu.fullerton.AcademyAdvisorAppointment.entity.Person;
import edu.fullerton.AcademyAdvisorAppointment.entity.Student;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wujun
 */
@Stateless
@Named("loginService")
public class LoginBean implements Serializable{
    //private Person person;
   
    @PersistenceContext
    private EntityManager em;
    public LoginBean() {   
    }

    public Person authenticate(String email, String password) {        
        Query createNamedQuery = em.createNamedQuery("Person.findByEmail")
                .setParameter("email", email);
        return (Person)createNamedQuery.getSingleResult();      
    }
    public void createStudent(Student student) {
        em.persist(student);
        //return "createdStudent";
    }

    public Student authenticateStudent(String loginEmail, String loginPassword) {
        Student student;
        try{
        Query createNamedQuery = em.createNamedQuery("Student.findByEmail")
                .setParameter("email", loginEmail);
        student=(Student)createNamedQuery.getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }
        if(student.verifyPassword(loginPassword)) {
            return student;
        }
        return null;
    }
    
}

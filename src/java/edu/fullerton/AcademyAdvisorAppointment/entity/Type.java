/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fullerton.AcademyAdvisorAppointment.entity;

/**
 *
 * @author wujun
 */
public enum Type {
    UNDER_GRADUATE("Under Graduate"),
    GRADUATE("Graduate");

    private String name;
    Type(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
}

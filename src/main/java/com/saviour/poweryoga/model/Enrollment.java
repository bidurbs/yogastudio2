/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saviour.poweryoga.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
/**
 *
 * @author Guest
 */
@Entity
@Table(name="ENROLMENT")
@NamedQueries({
    @NamedQuery(name = "Enrollment.getAllEnrollments", query = "Select e From Enrollment e Where e.user.userId=:customerId AND e.customerStatus=:status"),
    @NamedQuery(name = "Enrollment.getAllEnrollmentStatus", query = "Select e From Enrollment e Where e.section.id=:sectionId AND e.customerStatus=:status"),
    @NamedQuery(name = "Enrollment.displayAllSections", query = "Select Distinct s From Section s"),
    @NamedQuery(name = "Enrollment.getCurrentCount", query = "Select e From Enrollment e Where e.section.id=:myId AND e.customerStatus=:status"),
    @NamedQuery(name = "Enrollment.getSectionHistory", query = "Select e.section.course From Enrollment e Where e.user.userId=:id AND e.customerStatus=:status"),
    @NamedQuery(name = "Enrollment.isRegistered", query = "Select e From Enrollment e Where e.user.userId=:cid AND e.section.course.id=:sid")
})
public class Enrollment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public enum StatusType{
        active,waitinglist,withdrawal,completed;
    }
    @Enumerated(EnumType.STRING)
    private StatusType customerStatus;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date enrolledDate=new Date();
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private Section section;
    @ManyToOne (cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private Users user;
    
    public Enrollment() {
    }       
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusType getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(StatusType customerStatus) {
        this.customerStatus = customerStatus;
    }  
    
    public Date getEnrolledDate() {
        return enrolledDate;
    }

    public void setEnrolledDate(Date enrolledDate) {
        this.enrolledDate = enrolledDate;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }   

    public void addCustSec(Users customer, Section section){
        this.user=customer;
        this.section=section;
    }
}

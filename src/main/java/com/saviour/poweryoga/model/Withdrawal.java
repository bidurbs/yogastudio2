/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saviour.poweryoga.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Guest
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Withdrawal.getAllWithdrawals", query = "from Withdrawal w"),
    @NamedQuery(name = "Withdrawal.getEnrollmentObs", query = "Select Distinct e From Enrollment e Where e.user.userId=:customerId And e.customerStatus=:status"),
    @NamedQuery(name = "Withdrawal.getWithdrawalOb", query = "Select Distinct w From Withdrawal w Where w.enrollment.id=:enrollmentId AND w.enrollment.user.userId=:customerId AND w.withdrawalStatus=:status")
})
public class Withdrawal implements Serializable {
    public enum statusType{
        request,approved,rejected;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date withdrawalDate=new Date();
    private String reason;
    @Enumerated (EnumType.STRING)
    private statusType withdrawalStatus;
    @OneToOne
    private Enrollment enrollment;

    public Long getId() {
        return id;
    }

    public statusType getWithdrawalStatus() {
        return withdrawalStatus;
    }

    public void setWithdrawalStatus(statusType withdrawalStatus) {
        this.withdrawalStatus = withdrawalStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(Date withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }    

    public String getReason() {
        return reason;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }    

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public void addEnrollment(Enrollment enrollment){
        this.enrollment=enrollment;
    }
    
}

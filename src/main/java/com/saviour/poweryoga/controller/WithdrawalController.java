/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saviour.poweryoga.controller;

import com.saviour.poweryoga.model.Customer;
import com.saviour.poweryoga.model.Enrollment;
import com.saviour.poweryoga.model.Withdrawal;
import com.saviour.poweryoga.serviceI.IWithdrawalService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Guest
 */
@Named
@SessionScoped
public class WithdrawalController implements Serializable{
    private Withdrawal withdrawal;
    @Autowired
    private IWithdrawalService withdrawalService;
    private String message;
    private List<Withdrawal> withdrawals=new ArrayList<>();
    private List<Enrollment> enrollments;
    private String enrollmentId;
    @Autowired
    private UserController userController;
    
    public WithdrawalController() {
        withdrawal=new Withdrawal();
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }        

    public Withdrawal getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(Withdrawal withdrawal) {
        this.withdrawal = withdrawal;
    }

    public List<Withdrawal> getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(List<Withdrawal> withdrawals) {
        this.withdrawals = withdrawals;
    }

    public String displayEnrollment(){
        Customer customer=(Customer)userController.getCurrentUser();
        enrollments = withdrawalService.getEnrollmentObs(customer);
        withdrawals=withdrawalService.getAllWithdrawal();
        return "courseWithdrawal";
    }
    
    public String withdrawFromSection(){   
       withdrawal.setWithdrawalStatus(Withdrawal.statusType.request);
       for(Enrollment en:enrollments){          
           if(en.getId()==Long.parseLong(enrollmentId)){
               withdrawal.addEnrollment(en);
           }
       }       
       withdrawalService.saveWithdrawal(withdrawal);
       withdrawals=withdrawalService.getAllWithdrawal();
       return "courseWithdrawal";
    }
    
    public String deleteRequest(){       
        withdrawalService.deleteWithdrawal(withdrawal);
        withdrawals=withdrawalService.getAllWithdrawal();
        return "courseWithdrawal";
    }
}

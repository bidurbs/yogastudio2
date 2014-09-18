/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saviour.poweryoga.serviceImpl;

import com.saviour.poweryoga.crudfacade.CRUDFacadeImpl;
import com.saviour.poweryoga.model.Enrollment;
import com.saviour.poweryoga.model.Users;
import com.saviour.poweryoga.model.Withdrawal;
import com.saviour.poweryoga.serviceI.IWithdrawalService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Guest
 */
@Service
@Transactional
public class WithdrawalService implements IWithdrawalService{
    
    @Autowired
    private CRUDFacadeImpl crudfacade;
    @Override
    public void saveWithdrawal(Withdrawal withdrawal){
        //withdrawalDAO.saveWithdrawal(withdrawal);
        crudfacade.save(withdrawal);
    }

    @Override
    public List<Withdrawal> getAllWithdrawal(){
       // return withdrawalDAO.getAllWithdrawal();
        return crudfacade.findWithNamedQuery("Withdrawal.getAllWithdrawals");
    }

    @Override
    public void updateWithdrawal(Withdrawal withdrawal){
       // withdrawalDAO.updateWithdrawal(withdrawal);
        crudfacade.update(withdrawal);
    }

    @Override
    public Withdrawal getWithdrawalById(Long Id){
        //return withdrawalDAO.getWithdrawalById(Id);
         return (Withdrawal)crudfacade.read(Id, Withdrawal.class);
    }

    @Override
    public void deleteWithdrawal(Withdrawal withdrawal){
        //withdrawalDAO.deleteWithdrawal(withdrawal);
        crudfacade.delete(withdrawal);
    }
    
    @Override
    public List<Enrollment> getEnrollmentObs(Users customer){
        //return withdrawalDAO.getEnrollmentObs(customer);        
        Map<String, Enrollment.StatusType> paramaters = new HashMap<>(1);
        Map<String, Long> paramaters2 = new HashMap<>(1);        
        paramaters.put("status", Enrollment.StatusType.active);
        paramaters2.put("customerId", customer.getUserId());
        return crudfacade.findWithNamedQuery("Withdrawal.getEnrollmentObs", paramaters,paramaters2);
    }    

    @Override
    public Withdrawal getWithdrawalOb(Users customer,Enrollment enrollment){
        //return withdrawalDAO.getWithdrawalOb(customer, enrollment);
        Map<String, Withdrawal.statusType> paramaters = new HashMap<>(1);
        Map<String, Long> paramaters2 = new HashMap<>(2);        
        paramaters.put("status", Withdrawal.statusType.request);
        paramaters2.put("enrollmentId", enrollment.getId());
        paramaters2.put("customerId", customer.getUserId());
        List withdrawal=crudfacade.findWithNamedQuery("Withdrawal.getEnrollmentObs", paramaters,paramaters2);
        return (Withdrawal)withdrawal.get(0);
    }
}

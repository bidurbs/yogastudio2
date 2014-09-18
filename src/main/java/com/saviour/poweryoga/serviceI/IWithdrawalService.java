/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saviour.poweryoga.serviceI;

import com.saviour.poweryoga.model.Enrollment;
import com.saviour.poweryoga.model.Users;
import com.saviour.poweryoga.model.Withdrawal;
import java.util.List;

/**
 *
 * @author Guest
 */
public interface IWithdrawalService {
    public void saveWithdrawal(Withdrawal withdrawal);

    public List<Withdrawal> getAllWithdrawal();

    public void updateWithdrawal(Withdrawal withdrawal);

    public Withdrawal getWithdrawalById(Long Id);

    public void deleteWithdrawal(Withdrawal withdrawal);
    
    public List<Enrollment> getEnrollmentObs(Users customer);
    
    public Withdrawal getWithdrawalOb(Users customer,Enrollment enrollment);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saviour.poweryoga.serviceI;

import com.saviour.poweryoga.model.Customer;
import com.saviour.poweryoga.model.Section;
import com.saviour.poweryoga.model.Users;
import com.saviour.poweryoga.model.Waiver;
import java.util.List;

/**
 *
 * @author Guest
 */
public interface IWaiverService {
    public void saveWaiver(Waiver waiver);

    public List<Waiver> getAllWaiver();
    
    public List<Waiver> checkWaiver(Users customer,Section section);

    public void updateWaiver(Waiver waiver);

    public Waiver getWaiverById(Long Id);

    public void deleteWaiver(Waiver waiver);

    public List<Section> displayAllSections();
    
    public Users getCustomer();
    
    public Section getSectionOb(Long Id);
    
    public List<Waiver> showPendingWaivers();
}

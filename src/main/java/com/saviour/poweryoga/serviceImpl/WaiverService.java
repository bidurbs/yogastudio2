/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.serviceImpl;

import com.saviour.poweryoga.crudfacade.CRUDFacadeImpl;
import com.saviour.poweryoga.model.Section;
import com.saviour.poweryoga.model.Users;
import com.saviour.poweryoga.model.Waiver;
import com.saviour.poweryoga.serviceI.IWaiverService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Guest
 * @author TalakB
 * @version 0.0.1
 */
@Service
@Transactional
public class WaiverService implements IWaiverService {

    @Autowired
    private CRUDFacadeImpl crudfacade;

    @Override
    public void saveWaiver(Waiver waiver) {
        crudfacade.save(waiver);

    }

    @Override
    public List<Waiver> getAllWaiver() {
        return crudfacade.findWithNamedQuery("Waiver.findAllWaivers");
    }

    /**
     * Return lists of waivers with PENDING request status. 
     * @return
     */
    @Override
    public List<Waiver> showPendingWaivers() {
        try {
            Map<String, Waiver.statusType> paramaters = new HashMap<>(1);
            paramaters.put("wstatus", Waiver.statusType.PENDING);

            List pendingWaivers = crudfacade.findWithNamedQuery("Waiver.showPendingWaivers", paramaters);

            return (pendingWaivers);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }

    }

    @Override
    public void updateWaiver(Waiver waiver) {

    }
    @Override
    public List<Waiver> checkWaiver(Users customer,Section section){
        Map<String, Waiver.statusType> paramaters = new HashMap<>(1);
        Map<String, Long> paramaters2 = new HashMap<>(2);
        paramaters.put("status", Waiver.statusType.APPROVED); 
        paramaters2.put("cid", customer.getUserId()); 
        paramaters2.put("sid", section.getCourse().getId());        
        List isRegister=crudfacade.findWithNamedQuery("Waiver.checkWaiver", paramaters,paramaters2); 
        return isRegister;
    }
    @Override
    public Waiver getWaiverById(Long Id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteWaiver(Waiver waiver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Section> displayAllSections() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Users getCustomer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Section getSectionOb(Long Id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saviour.poweryoga.serviceImpl;

import com.saviour.poweryoga.crudfacade.CRUDFacadeImpl;
import com.saviour.poweryoga.model.Course;
import com.saviour.poweryoga.model.Enrollment;
import com.saviour.poweryoga.model.Section;
import com.saviour.poweryoga.model.Users;
import com.saviour.poweryoga.serviceI.IEnrollmentService;
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
public class EnrollmentService implements IEnrollmentService {
    @Autowired
    private CRUDFacadeImpl crudfacade;
    @Override
    public void saveEnrollment(Enrollment enrollment){
        //enrollmentDAO.saveEnrollment(enrollment);
        crudfacade.save(enrollment);
    }

    @Override
    public List<Enrollment> getAllEnrollments(Long id){       
        //return  enrollmentDAO.getAllEnrollments(id);
        Map<String, Enrollment.StatusType> paramaters = new HashMap<>(1);
        Map<String, Long> paramaters2 = new HashMap<>(1);        
        paramaters.put("status", Enrollment.StatusType.completed);
        paramaters2.put("customerId", id);
        return crudfacade.findWithNamedQuery("Enrollment.getAllEnrollments", paramaters,paramaters2);        
    }
    @Override
    public List<Enrollment> getAllEnrollmentStatus(Long id,String status){       
        //return  enrollmentDAO.getAllEnrollmentStatus(id,status);
        Map<String, Enrollment.StatusType> paramaters = new HashMap<>(1);
        Map<String, Long> paramaters2 = new HashMap<>(1);     
        paramaters.put("status", Enrollment.StatusType.valueOf(status));
        paramaters2.put("sectionId", id);
        return crudfacade.findWithNamedQuery("Enrollment.getAllEnrollmentStatus", paramaters,paramaters2);      
    }
    @Override
    public void updateEnrollment(Enrollment enrollment){
        //enrollmentDAO.updateEnrollment(enrollment);
        crudfacade.update(enrollment);
    }

    @Override
    public Enrollment getEnrollmentById(Long Id){
        return (Enrollment)crudfacade.read(Id, Enrollment.class) ;//enrollmentDAO.getEnrollmentById(Id);
    }

    @Override
    public void deleteEnrollment(Enrollment enrollment){
        //enrollmentDAO.deleteEnrollment(enrollment);
        crudfacade.delete(enrollment);
    }
    @Override
    public List<Section> displayAllSections(){        
        //return enrollmentDAO.displayAllSections();
        return crudfacade.findWithNamedQuery("Section.findAll"); 
    }
    @Override
    public Section getSectionOb(Long id){
        //return enrollmentDAO.getSectionOb(id);
        return (Section)crudfacade.read(id, Section.class);
    }
    @Override
    public int getCurrentCount(Long id){
        //return enrollmentDAO.getCurrentCount(id);
        Map<String, Enrollment.StatusType> paramaters = new HashMap<>(1);
        Map<String, Long> paramaters2 = new HashMap<>(1);     
        paramaters.put("status", Enrollment.StatusType.active);
        paramaters2.put("myId", id); 
        List count=crudfacade.findWithNamedQuery("Enrollment.getCurrentCount", paramaters,paramaters2); 
        return count.size();
    }
    @Override
    public List<Course> getSectionHistory(Users custmomer){
        //return enrollmentDAO.getSectionHistory(custmomer);
        Map<String, Enrollment.StatusType> paramaters = new HashMap<>(1);
        Map<String, Long> paramaters2 = new HashMap<>(1);     
        paramaters.put("status", Enrollment.StatusType.completed);
        paramaters2.put("id", custmomer.getUserId());        
        return crudfacade.findWithNamedQuery("Enrollment.getSectionHistory", paramaters,paramaters2); 
    }
    
    @Override
    public Enrollment isRegistered(Users customer,Section section){
        //return enrollmentDAO.isRegistered(customer, section);
        Map<String, Long> paramaters = new HashMap<>(2);
        paramaters.put("cid", customer.getUserId()); 
        paramaters.put("sid", section.getCourse().getId());        
        List isRegister=crudfacade.findWithNamedQuery2("Enrollment.isRegistered", paramaters); 
        if(isRegister.isEmpty())
            return null;
        return (Enrollment)isRegister.get(0);
    }
    
}

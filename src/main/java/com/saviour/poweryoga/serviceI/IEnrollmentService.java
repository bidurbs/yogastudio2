/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saviour.poweryoga.serviceI;
import com.saviour.poweryoga.model.Course;
import com.saviour.poweryoga.model.Customer;
import com.saviour.poweryoga.model.Enrollment;
import com.saviour.poweryoga.model.Section;
import com.saviour.poweryoga.model.Users;
import java.util.List;

/**
 *
 * @author Guest
 */
public interface IEnrollmentService {
    public void saveEnrollment(Enrollment enrollment);

    public List<Enrollment> getAllEnrollments(Long id);
    
    public List<Enrollment> getAllEnrollmentStatus(Long id,String status);

    public void updateEnrollment(Enrollment enrollment);

    public Enrollment getEnrollmentById(Long Id);

    public void deleteEnrollment(Enrollment enrollment);
    
    public List<Section> displayAllSections();
    
    public Section getSectionOb(Long id);
    
    public int getCurrentCount(Long id); 
    
    public List<Course> getSectionHistory(Users custmomer);    
    
    public Enrollment isRegistered(Users customer,Section section);
    
}

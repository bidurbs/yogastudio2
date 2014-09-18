/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.serviceI;

import com.saviour.poweryoga.model.Course;
import com.saviour.poweryoga.model.Enrollment;
import com.saviour.poweryoga.model.Section;
import com.saviour.poweryoga.model.Users;
import java.util.List;

/**
 *
 * @author bidur
 */
public interface ISectionService {

    public void saveSection(Section section);

    public List<Section> getAllSections();

    public void updateSection(Section section);

    public Section getSectionById(Long Id);


    public Section getSectionByName(String sectionName);

    public void deleteSection(Long Id);

    public void deleteSection(Section section);

    public List<Section> listSectionByCourseId(Long Id);

    public List<Course> getAllCourses();


    public Enrollment checkCustomerEnrolled(Section section, Users customer);
    


}

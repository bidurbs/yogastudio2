/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.serviceI;

import com.saviour.poweryoga.model.Course;
import java.util.List;

/**
 *
 * @author bidur
 */
public interface ICourseService {

    public void saveCourse(Course course);

    public List<Course> getAllCourses();
    
    public List<Course> getActiveCourses();

    public void updateCourse(Course course);

    public Course getCourseById(long Id);

    public void deleteCourse(Course course);

    public Course findByName(String cname);

    public List<Course> getAllCompletedCourse(Long userId);
}

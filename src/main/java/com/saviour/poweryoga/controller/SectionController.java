/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.controller;

import com.saviour.poweryoga.model.Course;
import com.saviour.poweryoga.model.Section;
import com.saviour.poweryoga.model.Semester;
import com.saviour.poweryoga.serviceI.ICourseService;
import com.saviour.poweryoga.serviceI.ISectionService;
import com.saviour.poweryoga.serviceI.ISemesterService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author bidur
 * @author TalakB
 * @version 0.0.1
 */
@Named("sectionController")
@SessionScoped
public class SectionController implements Serializable {

    @Autowired
    private ISectionService SectionService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ISemesterService semesterService;

    private Course course;
    private Course coursePrerequisite;
    private Semester semester;
    private Section section;
    private List<Section> listOfSection;
    private List<Course> listOfCourse;
    private List<Semester> listOfSemester;
    private List<Section> listOfSectionForCourse;
    private String selectedCourse;

    private Long selectedSemester;

    private String errorMsg = null;

    private String successMsg = null;

    public SectionController() {
        //section = new Section();
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public Long getSelectedSemester() {
        return selectedSemester;
    }

    public void setSelectedSemester(Long selectedSemester) {
        this.selectedSemester = selectedSemester;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public List<Semester> getListOfSemester() {
        listOfSemester = semesterService.getAllSemester();
        return listOfSemester;
    }

    public void setListOfSemester(List<Semester> listOfSemester) {
        this.listOfSemester = listOfSemester;
    }

    public List<Section> getListOfSectionForCourse() {
        return listOfSectionForCourse;
    }

    public void setListOfSectionForCourse(List<Section> listOfSectionForCourse) {
        this.listOfSectionForCourse = listOfSectionForCourse;
    }

    /**
     * Save Section data
     *
     * @return
     */
    public String saveSection() {
        try {
            // find the section based on selected course name and set course of this section 
            if (!selectedCourse.isEmpty()) {
                course = courseService.findByName(selectedCourse);
                section.setCourse(course);
            }
            //assign semester
            if (selectedSemester != null) {
                semester = semesterService.getSemesterById(selectedSemester);
                section.setSemester(semester);
            }
            section.setStatus(Section.statusType.ACTIVE);
            SectionService.saveSection(section);
            successMsg = "Section is created successfully";
        } catch (Exception ex) {
            errorMsg = "Section Create Failed";
        }
        return ("/views/admin/manageSection.xhtml?faces-redirect=true");
    }

    /**
     * Display update Section data page
     *
     * @return
     */
    public String updateSection() {
        try {
            // find the section based on selected course name and set course of this section 
            if (!selectedCourse.isEmpty()) {
                course = courseService.findByName(selectedCourse);
                section.setCourse(course);
            }
            //assign semester
            if (selectedSemester != null) {
                semester = semesterService.getSemesterById(selectedSemester);
                section.setSemester(semester);
            }
            SectionService.updateSection(section);
            successMsg = "Section is updated successfully";
        } catch (Exception ex) {
            errorMsg = "Section Update Failed";
        }
        return ("/views/admin/manageSection.xhtml?faces-redirect=true");
    }

    /**
     * Update Section data
     *
     * @param Id
     * @return
     */
    public String editSection(Long Id) {
        section = SectionService.getSectionById(Id);
        return "editSection";
    }

    /**
     * display add Section data form
     *
     * @return
     */
    public String addSection() {
        section = new Section();
        return ("/views/admin/addSection.xhtml?faces-redirect=true");
    }

    /**
     * Delete Section entry
     *
     * @param Id
     * @return
     */
    public String deleteSection(Long Id) {
        try {
            section = SectionService.getSectionById(Id);
            //SectionService.deleteSection(section);
            section.setStatus(Section.statusType.INACTIVE);
            SectionService.updateSection(section);
            successMsg = "Section is deleted successfully";
        } catch (Exception ex) {
            errorMsg = "Delete Section Failed";
        }
        return ("/views/admin/manageSection.xhtml?faces-redirect=true");
    }

    /**
     * list all the Section for requested course data
     *
     * @param courseId
     * @return
     */
    public String viewSection(Long courseId) {
        listOfSection = SectionService.listSectionByCourseId(courseId);
        return ("/views/admin/manageSection.xhtml?faces-redirect=true");
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Section> getListOfSection() {
        listOfSection = SectionService.getAllSections();
        return listOfSection;
    }

    public void setListOfSection(List<Section> listOfSection) {
        this.listOfSection = listOfSection;
    }

    public List<Course> getListOfCourse() {
        listOfCourse = courseService.getActiveCourses();
        return listOfCourse;
    }

    public void setListOfCourse(List<Course> listOfCourse) {
        this.listOfCourse = listOfCourse;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCoursePrerequisite() {
        return coursePrerequisite;
    }

    public void setCoursePrerequisite(Course coursePrerequisite) {
        this.coursePrerequisite = coursePrerequisite;
    }

    /**
     * Display all the Section for requested course data for customer
     *
     * @param courseId
     * @return
     */
    public String displaySection(long courseId) {
        try {
            course = courseService.getCourseById(courseId);
            listOfSectionForCourse = SectionService.listSectionByCourseId(courseId);
            return ("/views/customer/section.xhtml?faces-redirect=true");
        } catch (Exception ex) {
            return null;
        }
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

}

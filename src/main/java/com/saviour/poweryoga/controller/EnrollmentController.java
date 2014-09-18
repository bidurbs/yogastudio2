/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.controller;

import com.saviour.poweryoga.model.Course;
import com.saviour.poweryoga.model.Enrollment;
import com.saviour.poweryoga.model.Section;
import com.saviour.poweryoga.model.Users;
import com.saviour.poweryoga.model.Waiver;
import com.saviour.poweryoga.serviceI.IEnrollmentService;
import com.saviour.poweryoga.serviceI.IWaiverService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Guest
 */
@Named
@RequestScoped
public class EnrollmentController implements Serializable {

    private int maxCapacity = 0, count = 0, flag = 0;
    private Enrollment enrollment;
    private String sectionId;
    private List<Section> sections = new ArrayList<>();
    @Autowired
    private IEnrollmentService enrollmentService;
    @Autowired
    private UserController userController;
    private String message;
    private List<Enrollment> enrollments = new ArrayList<>();
    private String status;
    private List<String> statuses = new ArrayList<>();
    @Autowired
    private IWaiverService waiverService;

    public EnrollmentController() {
        enrollment = new Enrollment();
        statuses.add(Enrollment.StatusType.active.toString());
        statuses.add(Enrollment.StatusType.completed.toString());
        statuses.add(Enrollment.StatusType.waitinglist.toString());
        statuses.add(Enrollment.StatusType.withdrawal.toString());
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public void ajaxListner(AjaxBehaviorEvent event) {
//        sectionId = (String) ((UIOutput) event.getSource()).getValue();
//        enrollments = enrollmentService.getAllEnrollments(Long.parseLong(sectionId));
//    }
    
    public String displayAllSectionHistory() {
       // sections = enrollmentService.displayAllSections();
        Users user = userController.getCurrentUser();
        enrollments = enrollmentService.getAllEnrollments(user.getUserId());
        return "viewCourseHistory";
    }
    public String enrollCustomer(Long sectionId) {
        Users user = userController.getCurrentUser();
        if (user == null) {
            message = "You are not a logged in user.";
            return "section";
        }
        Section section = enrollmentService.getSectionOb(sectionId);
        Enrollment enrolmentOb = enrollmentService.isRegistered(user, section);
        if (enrolmentOb != null) {
            message = "You are already enrolled in this course!!!";
            return "section";
        }
        maxCapacity = section.getMaxNoStudent();
        count = enrollmentService.getCurrentCount(sectionId);
        if (count == maxCapacity) {
            return addToWaitingList(user, section);
        }
        if (checkPrerequisite(user, section) == 0) {
            return "section";
        }
        return addToEnrolled(user, section);
    }

    private String addToWaitingList(Users customer, Section section) {
        enrollment.setCustomerStatus(Enrollment.StatusType.waitinglist);
        enrollment.addCustSec(customer, section);
        enrollmentService.saveEnrollment(enrollment);
        message = "Maximum limit reached, You are added to a waiting list!";
        // enrollments = enrollmentService.getAllEnrollments(Long.parseLong(sectionId));
        return "section";
    }

    private String addToEnrolled(Users customer, Section section) {

        enrollment.setCustomerStatus(Enrollment.StatusType.active);
        enrollment.addCustSec(customer, section);
        enrollmentService.saveEnrollment(enrollment);
        message = "Successfully enrolled in the course.";//customer="+customer.getFirstName()+"section="+section.getSectionName();
        flag = 0;
        //enrollments = enrollmentService.getAllEnrollments(Long.parseLong(sectionId));

        return "section";
    }

    private int checkPrerequisite(Users customer, Section section) {
        Course prerequisite = section.getCourse().getPrerequisites();
        List<Course> sectionHistory = enrollmentService.getSectionHistory(customer); 
        List<Waiver> waiver=waiverService.checkWaiver(customer, section);
        if (prerequisite == null || !waiver.isEmpty() ) { // check this if course doesn't have prerequisite
            flag = 1;
        } else {
            if (!sectionHistory.isEmpty()) {
                for (Course c2 : sectionHistory) {
                    if (Objects.equals(prerequisite.getId(), c2.getId())) {
                        flag = 1;
                    }
                }
            }
        }
        if (flag == 0) {
            message = "You didn't take prerequisites of the course!!!  ";
        }
        return flag;
    }

}

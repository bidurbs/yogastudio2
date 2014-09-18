/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.controller;

import com.saviour.poweryoga.model.Course;
import com.saviour.poweryoga.serviceI.ICourseService;
import com.saviour.poweryoga.serviceI.ISectionService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author bidur
 * @version 0.0.1
 */
@Named("transcriptController")
@SessionScoped
public class TranscriptController implements Serializable {

    @Autowired
    private ICourseService CourseService;

    @Autowired
    private ISectionService SectionService;

    @Autowired
    private UserController userControler;

    @Autowired
    private NotificationController notificationController;

    private List<Course> transcriptList;

    public TranscriptController() {

    }

    public List<Course> getTranscriptList() {
        return transcriptList;
    }

    public void setTranscriptList(List<Course> transcriptList) {
        this.transcriptList = transcriptList;
    }
    
    

    public String viewTranscript() {
        try {
            Long userId = (Long) userControler.getActiveSession().getAttribute("loggedUserId");
            if (userId == null) {
                notificationController.setErrorMsg("Please login to continue");
            }
            
            transcriptList = CourseService.getAllCompletedCourse(userId);

        } catch (Exception ex) {
            ex.printStackTrace();
            notificationController.setErrorMsg("Nodata to display");
        }
        return ("/views/customer/transcript.xhtml?faces-redirect=true");
    }

}

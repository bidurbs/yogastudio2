package com.saviour.poweryoga.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Guest
 * @author TalakB
 * @version 0.0.1
 */
@Entity
@Table(name = "COURSE")
@NamedQueries({
    @NamedQuery(name = "Course.findByName", query = "FROM Course c WHERE c.courseName=:cname"),
    @NamedQuery(name = "Course.findAll", query = "FROM Course c"),
    @NamedQuery(name = "Course.findActiveCourses",
            query = "FROM Course c WHERE c.status=:cstatus"),})
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseCode;

    private String courseName;

    private double courseFee;

    private String description;

    public enum statusType {

        ACTIVE, INACTIVE;
    }

    @Enumerated(EnumType.STRING)
    private statusType status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Course prerequisites;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<Section> sections;

    public Course(String courseName, double courseFee, String description, Course prerequisites) {
        this.courseName = courseName;
        this.courseFee = courseFee;
        this.description = description;
        this.prerequisites = prerequisites;
        this.status = Course.statusType.ACTIVE;
    }

    public Course() {

    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCourseFee() {
        return courseFee;
    }

    public statusType getStatus() {
        return status;
    }

    public void setStatus(statusType status) {
        this.status = status;
    }

    public void setCourseFee(double courseFee) {
        this.courseFee = courseFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(Course prerequisites) {
        this.prerequisites = prerequisites;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

}

package com.saviour.poweryoga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Guest
 * @author TalakB
 * @version 0.0.1
 *
 */
@Entity
@Table(name = "SECTION")
@NamedQueries({
    @NamedQuery(name = "Section.findAll", query = "from Section s"),
    @NamedQuery(name = "Section.findAllActiveSections", query = "FROM Section s WHERE s.status=:status"),
    @NamedQuery(name = "Section.findAllByCourse", query = "FROM Section s  WHERE s.course.id=:courseId AND s.status=:status"),
    @NamedQuery(name = "Section.findByName", query = "FROM Section s WHERE s.sectionName=:sname"),
    @NamedQuery(name = "Section.findUserInSection", query = "from Section s")

})
public class Section implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sectionName;
    private int maxNoStudent;
    private int roomNumber;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Semester semester;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Course course;

    @ManyToMany(mappedBy = "sections")
    private List<Users> users = new ArrayList<>();
    
    public enum statusType {

        ACTIVE, INACTIVE;
    }

    @Enumerated(EnumType.STRING)
    private statusType status;

    public Section() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getMaxNoStudent() {
        return maxNoStudent;
    }

    public void setMaxNoStudent(int maxNoStudent) {
        this.maxNoStudent = maxNoStudent;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public statusType getStatus() {
        return status;
    }

    public void setStatus(statusType status) {
        this.status = status;
    }
    
    

}

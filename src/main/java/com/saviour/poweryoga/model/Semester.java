package com.saviour.poweryoga.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Guest
 */
@Entity
@Table(name = "SEMESTER")
@NamedQueries({
    @NamedQuery(name = "Semester.findAll", query = "from Semester s"),
    @NamedQuery(name = "Semester.findAllActiveSemesters", query = "FROM Semester s WHERE s.status=:status")
})
public class Semester implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String semesterName;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDate;

    public enum statusType {

        ACTIVE, INACTIVE;
    }

    @Enumerated(EnumType.STRING)
    private statusType status;

    public Semester() {
        
    }

    public Semester(String semesterName, Date startDate, Date endDate) {
        this.semesterName = semesterName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = statusType.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public statusType getStatus() {
        return status;
    }

    public void setStatus(statusType status) {
        this.status = status;
    }
    
    
}

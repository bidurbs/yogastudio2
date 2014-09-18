package com.saviour.poweryoga.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Guest
 * @author TalakB
 */
@Entity
@Table(name = "WAIVER")
@NamedQueries({
    @NamedQuery(name = "Waiver.findAllWaivers", query = "from Waiver w"),
    @NamedQuery(name = "Waiver.showPendingWaivers", query = "FROM Waiver w WHERE w.status=:wstatus"),
    @NamedQuery(name = "Waiver.checkWaiver", query = "Select w FROM Waiver w WHERE w.section.course.id=:sid AND w.user.userId=:cid AND w.status=:status")
    
})
public class Waiver implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date requestDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Users user;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Section section;

    public enum statusType {
        APPROVED, PENDING, NOTAPPROVED;
    }

    @Enumerated(EnumType.STRING)
    private statusType status;

    public Waiver() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public statusType getStatus() {
        return status;
    }

    public void setStatus(statusType status) {
        this.status = status;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public void addCustSec(Users customer, Section section) {
        this.user = customer;
        this.section = section;
    }

}

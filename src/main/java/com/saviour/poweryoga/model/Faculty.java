package com.saviour.poweryoga.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author TalakB
 * @version 0.0.1
 */
@Entity
@Table(name = "FACULTY")

@NamedQueries({
    @NamedQuery(name = "Faculty.findAll", query = "FROM Faculty f"),
    @NamedQuery(name = "Faculty.findActiveDeactive", 
            query = "FROM Users u, Faculty f WHERE u.userId=f.userId AND f.status=:fstatus"),
    @NamedQuery(name = "Faculty.listMyAdvisee",
            query = "FROM Customer c WHERE c.myAdvisor.userId = :fid")
})
public class Faculty extends Users implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "myAdvisor")
    private List<Customer> myAdvisee;

    public enum statusType {
        ACTIVE, INACTIVE;
    }

    @Enumerated(EnumType.STRING)
    private statusType status;

    public List<Customer> getMyAdvisee() {
        return myAdvisee;
    }

    public void setMyAdvisee(List<Customer> myAdvisee) {
        this.myAdvisee = myAdvisee;
    }

    public statusType getStatus() {
        return status;
    }

    public void setStatus(statusType status) {
        this.status = status;
    }

}

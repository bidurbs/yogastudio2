package com.saviour.poweryoga.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * This class is for Role of Users and contains Credit Card features.
 *
 *
 * @author Talak
 * @version 0.0.1
 */
@Entity
@Table(name = "ROLE")
@NamedQueries({
    @NamedQuery(name = "Role.findRoleByUserCode", query = "select r from Role r where r.userCode = :rcode")
})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int ROLE_ADMIN_CODE = 1;
    public static final int ROLE_FACULTY_CODE = 2;
    public static final int ROLE_CUSTOMER_CODE = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int userCode;

    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

}

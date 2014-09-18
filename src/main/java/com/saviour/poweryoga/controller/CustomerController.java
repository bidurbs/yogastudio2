package com.saviour.poweryoga.controller;

import com.saviour.poweryoga.model.Address;
import com.saviour.poweryoga.model.Customer;
import com.saviour.poweryoga.model.Faculty;
import com.saviour.poweryoga.model.Role;
import com.saviour.poweryoga.serviceI.IFacultyService;
import com.saviour.poweryoga.serviceI.IRoleService;
import com.saviour.poweryoga.serviceI.IUserService;
import com.saviour.poweryoga.util.EmailManager;
import com.saviour.poweryoga.util.PasswordService;
import com.saviour.poweryoga.util.YogaValidator;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 * @author Md Mojahidul Islam
 * @author TalakB
 * @version 0.0.1
 */
@Named(value = "customerController")
@SessionScoped
public class CustomerController implements Serializable {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IFacultyService facultyService;

    private String errorMsg = null;

    private String successMsg = null;

    private List<Customer> customers;

    private Customer customer;

    private Address address;

    private String rePassword;

    @Autowired
    private JavaMailSender mailSender;

    public CustomerController() {
        customer = new Customer();
        address = new Address();
        customers = new ArrayList<>();

    }

    /**
     * Save customer information for registration
     *
     * @return String Based on success or failiure return to same page with
     * message
     */
    public String saveCustomer() {
        try {
            if (validateEmail(customer.getEmail())
                    && findCustomerByEmail(customer.getEmail()) == false
                    && checkPassword(customer.getPassword(), rePassword)) {
                customer.setAddress(address);
                customer.setPassword(PasswordService.encrypt(customer.getPassword()));
                Role custRRole = roleService.getRoleByUserCode(Role.ROLE_CUSTOMER_CODE);

                //set role 
                customer.setRole(custRRole);
                //assign advisor 
                Faculty myAdvisor = facultyService.pickAdvisor();
                customer.setMyAdvisor(myAdvisor);
                customer.setApproved(false);
                customer.setValidationLink(findNextNumber().toString());
                userService.saveUser(customer);
                sendRegistrationEmail(customer);
                successMsg = "Welcome !! " + customer.getFirstName() + " " + customer.getLastName() + ". Please check your email to complete registration process.";
                errorMsg = null;

                customer = new Customer();
                address = new Address();
            }
        } catch (Exception ex) {
            errorMsg = "Customer saving failed";
            successMsg = null;
        }
        return null;
    }

    /**
     * Find next number for validation
     *
     * @return Long a random number
     */
    public Long findNextNumber() {
        long number = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
        return number;
    }

    /**
     * Send email after customer put information and hit Register
     *
     * @param mycustomer Customer for which we will send email
     */
    public void sendRegistrationEmail(Customer mycustomer) {

        //EMAIL SENDING
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest servletRequest = (HttpServletRequest) ctx.getExternalContext().getRequest();
        String ctr = servletRequest.getRequestURL().toString().replace(servletRequest.getRequestURI().substring(0), "") + servletRequest.getContextPath();

        String vLink = ctr + "/views/customer/confirmCustomer.xhtml?id=" + mycustomer.getValidationLink();
        String myIp = findMyIP();
        vLink = vLink.replace("localhost", myIp);

        StringBuilder body = new StringBuilder(" Welcome !!! to PowerYoga family.\n\n Your registration information\n\n");
        body.append("   First Name:   ").append(customer.getFirstName()).append("\n   Last Name:   ").append(customer.getLastName()).append("\n   Email:   ").append(customer.getEmail()).append("\n Click to the link below to confirm your registration \n\n");
        body.append(vLink);
        body.append("\n\n");
        body.append("Regards\n-PowerYoga Team");
        EmailManager.sendEmail(mailSender, "Welcome to PowerYoga studio", body.toString(), mycustomer.getEmail());
    }

    private String findMyIP() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (java.net.UnknownHostException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ip != null) {
            return ip.getHostAddress();
        }
        return null;
    }

    /**
     * Validate customer email format
     *
     * @param email Customer email
     * @return true/false based on email given
     */
    public boolean validateEmail(String email) {
        if (YogaValidator.emailValidator(customer.getEmail()) == false) {
            FacesContext.getCurrentInstance().addMessage("frmCustomerRegistration:email",
                    new FacesMessage("Invalid email format", "Invalid email format"));
            return false;
        }
        return true;
    }

    /**
     * Find a customer from database by email
     *
     * @param email Customer email
     * @return true/false based on email
     */
    public boolean findCustomerByEmail(String email) {
        Customer cust = userService.findCustomerByEmail(email.trim());
        if (cust == null) {
            return false;
        }
        FacesContext.getCurrentInstance().addMessage("frmCustomerRegistration:email",
                new FacesMessage("Email ID already exists", "Email ID already exists"));
        return true;
    }

    public boolean checkPassword(String password, String rePassword) {
        if (password.equals(rePassword)) {
            return true;
        }
        FacesContext.getCurrentInstance().addMessage("frmCustomerRegistration:password",
                new FacesMessage("Password and confirm password mismatch", "Password and confirm password mismatch"));
        return false;
    }

    /**
     * Update customer to database
     */
    public void updateCustomer() {
        try {
            userService.updateUser(customer);
            errorMsg = null;
            successMsg = "Customer " + customer.getFirstName() + " updated successfully";
        } catch (Exception ex) {
            errorMsg = "Customer update failed";
            successMsg = null;

        }
    }

    /**
     * Find customer to update/view his/her profile
     *
     * @return Customer update page
     */
    public String findCustomerById() {

        errorMsg = null;
        successMsg = null;

        HttpSession activeSession = (HttpSession) FacesContext
                .getCurrentInstance().getExternalContext().getSession(true);

        Long customerId = (Long) activeSession.getAttribute("loggedUserId");

        customer = userService.findCustomerById(customerId);
        if (customer != null) {
            return "/views/customer/customerUpdate?faces-redirect=true";
        }
        return null;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

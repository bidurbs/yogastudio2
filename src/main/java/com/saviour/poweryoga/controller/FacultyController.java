package com.saviour.poweryoga.controller;

import com.saviour.poweryoga.model.Address;
import com.saviour.poweryoga.model.Customer;
import com.saviour.poweryoga.serviceI.IFacultyService;
import com.saviour.poweryoga.model.Faculty;
import com.saviour.poweryoga.model.Role;
import com.saviour.poweryoga.model.Section;
import com.saviour.poweryoga.model.Waiver;
import com.saviour.poweryoga.serviceI.IRoleService;
import com.saviour.poweryoga.serviceI.ISectionService;
import com.saviour.poweryoga.util.EmailManager;
import com.saviour.poweryoga.util.NotificationUtil;
import com.saviour.poweryoga.util.PasswordService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 * @author AnurR
 * @author TalakB
 * @version 0.0.1
 */
@Named("facultyController")
@SessionScoped
public class FacultyController implements Serializable {

    @Autowired
    private IFacultyService facultyService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private UserController usercontroller;

    @Autowired
    private ISectionService sectionService;

    @Autowired
    private JavaMailSender mailSender;

//    @Autowired
//    private NotificationController notificationController;
    private Faculty faculty;

    private Customer selectedCustomer;

    private String EmailToAdvisee;

    private List<Faculty> listOfFaculty;

    private List<Section> sectionList = new ArrayList<>();

    private List<Customer> myadvisee;

    private Address address;

    private Long selectedSectionId;
    
    private String errorMsg = null;

    private String successMsg = null;

    public FacultyController() {
        faculty = new Faculty();
        address = new Address();
        selectedCustomer = new Customer();
    }

    public Long getSelectedSectionId() {
        return selectedSectionId;
    }

    public void setSelectedSectionId(Long selectedSectionId) {
        this.selectedSectionId = selectedSectionId;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    /**
     * Get my advisee.
     *
     * @return
     */
    public List<Customer> getMyadvisee() {
        //Get logged in Faculty. 
        Faculty fac = (Faculty) usercontroller.getUser();
        //Pass the faulty and get the lists of advisee

        myadvisee = facultyService.myAdvisee(fac);
        if (!myadvisee.isEmpty()) {
            return myadvisee;
        } else {
            FacesContext.getCurrentInstance()
                    .addMessage("advList:NoAdviList",
                            new FacesMessage("No advisee assigned. ", "No advisee assigned."));
            return null;
        }
    }

    public String getEmailToAdvisee() {
        return EmailToAdvisee;
    }

    public void setEmailToAdvisee(String EmailToAdvisee) {
        this.EmailToAdvisee = EmailToAdvisee;
    }

    public void setMyadvisee(List<Customer> myadvisee) {
        this.myadvisee = myadvisee;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Save faculty data
     *
     * @return
     */
    public String saveFaculty() {

        try {
            Section sectionsAssigned = sectionService.getSectionById(selectedSectionId);
            sectionList.add(sectionsAssigned);
            faculty.setSections(sectionList);

            //set faculty role from userController
            Role facRRole = roleService.getRoleByUserCode(Role.ROLE_FACULTY_CODE);
            //  facRRole.setUserCode(UserController.ROLE_FACULTY_CODE);
            faculty.setPassword(PasswordService.encrypt(faculty.getPassword()));

            //set address 
            faculty.setAddress(address);

            //set role 
            faculty.setRole(facRRole);

            //set approved
            faculty.setApproved(true);

            //set stauts 
            faculty.setStatus(Faculty.statusType.ACTIVE);
            facultyService.saveFaculty(faculty);
            successMsg = "Faculty is created successfully";
            return ("/views/admin/manageFaculty.xhtml?faces-redirect=true");
        } catch (Exception ex) {
            ex.printStackTrace();

            String sucessErrmsg = "Customer saving failed";
            NotificationUtil.flashScope().put(sucessErrmsg, this);
            setErrorMsg("Faculty saving failed");
        }
        return null;
    }

    public String nextpage(String msg) {
        NotificationUtil.flashScope().put("msg", msg);
        return "page2?faces-redirect=true";
    }

    /**
     * Display update faculty data page
     *
     * @return
     */
    public String updateFaculty() {
        facultyService.updateFaculty(faculty);
        successMsg = "Faculty is created successfully";
        return ("/views/admin/manageFaculty.xhtml?faces-redirect=true");
    }

    /**
     * List all the sections assigned to a faculty
     *
     * @return
     */
    public List<Section> getMySections() {
        Faculty fac = (Faculty) usercontroller.getUser();
        List<Section> mySections = fac.getSections();
        if (!mySections.isEmpty()) {
            return mySections;
        } else {
            FacesContext.getCurrentInstance()
                    .addMessage("advList:NoAdviList",
                            new FacesMessage("You are not assigned.",
                                    "You are not assigned"));
            return null;
        }
        //return fac.getSections();
    }

    /**
     * Update Faculty data
     *
     * @param Id
     * @return
     */
    public String editFaculty(long Id) {
        faculty = facultyService.getFacultyById(Id);
        return "editFaculty";
    }

    /**
     * add Faculty form
     *
     * @return
     */
    public String addFaculty() {
        return ("/views/admin/addFaculty.xhtml?faces-redirect=true");
    }

    /**
     *
     * @param cus
     * @return
     */
    public String emailToAdvisee(Customer cus) {
        selectedCustomer = cus;
        return ("/views/faculty/sendEmailToAdvisee.xhtml?faces-redirect=true");

    }

    /**
     * Send email to advisee.
     *
     * @param cus
     * @param faculty
     * @param customer
     * @return
     */
    public String sendEMailToMyAdvisee() {

        StringBuilder body = new StringBuilder(" Dear " + selectedCustomer.getFirstName() + "\n\n");
        //body.append("<a href=" + vLink + " target=_blank></a>");
        body.append(EmailToAdvisee);
        body.append("\n\n Kind regards,\n\n");
        body.append(usercontroller.getUser().getFirstName());
        EmailManager.sendEmail(mailSender, "Message from your advisor",
                body.toString(), selectedCustomer.getEmail());

        return ("/views/faculty/facultyViewAdvisee.xhtml?faces-redirect=true");

      // return null;
    }

    /**
     * Delete Faculty entry. A faculty status will be changed to DEACTIVE but it
     * will not be deleted because it is referenced by customers.
     *
     * @param Id
     * @return
     */
    public String deleteFaculty(long Id) {
        faculty = facultyService.getFacultyById(Id);

        //set status deactive. 
        faculty.setStatus(Faculty.statusType.INACTIVE);

        //update not Delete!
        facultyService.updateFaculty(faculty);
        return ("/views/admin/manageFaculty.xhtml?faces-redirect=true");
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Faculty> getListOfActiveFaculty() {
        List<Faculty> listOfFaculty = facultyService.getListOfActiveFaculty();

        return listOfFaculty;
    }

    public void setListOfFaculty(List<Faculty> listOfFaculty) {
        this.listOfFaculty = listOfFaculty;
    }

    /**
     * Approve pending requests
     *
     * @param waiver
     * @return
     */
    public String approveWaiverRequest(Waiver waiver) {
        try {
            waiver.setStatus(Waiver.statusType.APPROVED);
            facultyService.updateWaiverRequest(waiver);

            //send email to the customer 
            notifyWaiverRequestDecision(waiver);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return "";

    }

    /**
     * Reject pending requests
     *
     * @param waiver
     * @return
     */
    public String rejectWaiverRequest(Waiver waiver) {
        try {
            waiver.setStatus(Waiver.statusType.NOTAPPROVED);
            facultyService.updateWaiverRequest(waiver);
            //send email to the customer 
            notifyWaiverRequestDecision(waiver);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return "";

    }

    /**
     * Notify user about the waiver decision.
     *
     * @param waiver
     */
    public void notifyWaiverRequestDecision(Waiver waiver) {
        try {
            StringBuilder body = new StringBuilder(" Dear " + waiver.getUser().getFirstName() + "\n\n");

            body.append("Your waiver request for the course "
                    + waiver.getSection().getCourse().getCourseName() + " has beed " + waiver.getStatus().toString().toLowerCase() + ".");
            body.append("\n\n Kind regards,\n\n");
            body.append("Yoga Studio");
            EmailManager.sendEmail(mailSender, "Waiver request decision",
                    body.toString(), waiver.getUser().getEmail());
            
            //notification message
            FacesContext.getCurrentInstance()
                    .addMessage("emailSent:emSent",
                            new FacesMessage("Email sent.",
                                    "Email sent"));
        } catch (Exception ex) {
            ex.printStackTrace();
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

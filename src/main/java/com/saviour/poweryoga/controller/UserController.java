package com.saviour.poweryoga.controller;

import com.saviour.poweryoga.model.Role;
import com.saviour.poweryoga.model.Users;
import com.saviour.poweryoga.serviceI.IUserService;
import com.saviour.poweryoga.util.PasswordService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author TalakB
 * @version 0.0.1
 */
@Named
@SessionScoped
public class UserController implements Serializable {

    @Autowired
    private IUserService userService;

    //for success and error message notifications 
    @Autowired
    private NotificationController notoficationController;
//    

    private Users user;
    //= new Users();
    private Role userRole;

    //to keep user related data on the session 
    private HttpSession activeSession;

    private boolean isAdmin;
    private boolean isFaculty;
    private boolean isCustomer;
    private boolean isLoggedin;

    private static String redirect = "";

    public static String getRedirect() {
        return redirect;
    }

    public static void setRedirect(String redirect) {
        UserController.redirect = redirect;
    }

    public UserController() {
        user = new Users();
        userRole = new Role();
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public HttpSession getActiveSession() {
        return activeSession;
    }

    public void setActiveSession(HttpSession activeSession) {
        this.activeSession = activeSession;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsFaculty() {
        return isFaculty;
    }

    public void setIsFaculty(boolean isFaculty) {
        this.isFaculty = isFaculty;
    }

    public boolean isIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    public boolean isIsLoggedin() {
        return isLoggedin;
    }

    public void setIsLoggedin(boolean isLoggedin) {
        this.isLoggedin = isLoggedin;
    }

    /**
     * Authenticate user and redirect to the respective home page based on role.
     *
     * @return
     * @throws Exception
     */
    public String loginUser() throws Exception {

        //encrypt password to check it against the DB. 
        String encPass = PasswordService.encrypt(user.getPassword());
        user.setPassword(encPass);
        String retURL = "";
        try {
            user = userService.authenticateUser(user);
            //check if the user is registered and authenticated 
            if (user != null) {
                //set the authenticated user info. on the session  
                setUserSessionData(user);

                //check user code 
                int userRoleCode = user.getRole().getUserCode();
                //admin
                if (userRoleCode == Role.ROLE_ADMIN_CODE) {
                    isAdmin = true;
                    retURL = "/views/admin/adminHome.xhtml?faces-redirect=true";
                } //faculty user 
                else if (userRoleCode == Role.ROLE_FACULTY_CODE) {
                    // activeSession.setAttribute("loggedUser", user);
//                userLogged = true;
//                isAdminUser = true;
                    isFaculty = true;
                    retURL = "/views/faculty/facultyHome.xhtml?faces-redirect=true";

                } //vedor user
                else if (userRoleCode == Role.ROLE_CUSTOMER_CODE) {
                    // activeSession.setAttribute("loggedUser", user);
//                userLogged = true;
//                isAdminUser = true;
                    isCustomer = true;
                    retURL = "/views/index.xhtml?faces-redirect=true";
                }

            } else {
                FacesContext.getCurrentInstance()
                        .addMessage("logInForm:unamePassErr", new FacesMessage("Please check username and/or password.", "Please check username and/or password."));
                user=new Users();
                return null;
            }
            if (redirect != null && redirect.length() > 0) {
                return redirect;
            } else {
                return retURL;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            //    notoficationController.setErrorMsg("Login failed. Please cehck username/password.");
        }
        return null;

    }

    /**
     * Save userID and first name on session
     *
     * @param user
     */
    public void setUserSessionData(Users user) {
        //set loggedin flag true 
        isLoggedin = true;
        activeSession = (HttpSession) FacesContext
                .getCurrentInstance().getExternalContext().getSession(true);
        activeSession.setAttribute("loggedUserId", user.getUserId());
        activeSession.setAttribute("loggedUserFname", user.getFirstName());
    }

    /**
     * Logout user -Invalidate the session and redirect to home page
     *
     * @return to home page
     */
    public String logoutUser() {
        HttpSession mySession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        mySession.invalidate();
        isLoggedin = false;
        return ("/views/index.xhtml?faces-redirect=true");
    }

    // CHANGE PASSWORD
    private String currentPassword;
    private String newPassword;
    private String newRePassword;

    public void changePassword() {
        try {
            Users usr = getCurrentUser();
            String encPass = PasswordService.encrypt(currentPassword);
            usr.setPassword(encPass);

            usr = userService.authenticateUser(usr);

            if (usr != null) {
                if (checkPassword(newPassword, newRePassword)) {
                    encPass = PasswordService.encrypt(newPassword);
                    usr.setPassword(encPass);
                    userService.updateUser(usr);
                    notoficationController.setSuccessMsg("Password updated successfully");
                    return;
                } else {
                    notoficationController.setErrorMsg("New password doesn't match with re-password.");
                }
            } else {
                notoficationController.setErrorMsg("Current password you have entered is not correct.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkPassword(String password, String rePassword) {
        if (password.equals(rePassword)) {
            return true;
        }
        notoficationController.setErrorMsg("Password and RePassword doesn't match");
        return false;
    }

    public Users getCurrentUser() {
        HttpSession mySession = (HttpSession) FacesContext
                .getCurrentInstance().getExternalContext().getSession(true);

        Long userId = (Long) mySession.getAttribute("loggedUserId");

        return userService.findUserById(userId);
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewRePassword() {
        return newRePassword;
    }

    public void setNewRePassword(String newRePassword) {
        this.newRePassword = newRePassword;
    }
    
    /**
     * Check if the user is logged in and what role is he assigned to restrict
     * pages.
     * @return 
     * @param event
     */
    public void isAdminUser(ComponentSystemEvent event) {

        FacesContext context = FacesContext.getCurrentInstance();
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();

        //if the user is not logged in then redirect to log in page 
        if (!isLoggedin) {
           // return null;
           handler.performNavigation("/views/index.xhtml?faces-redirect=true");
        }//check type of user
        else {
            //show access denied page for non admin users. 
            if (!(user.getRole().getUserCode() == Role.ROLE_ADMIN_CODE)) {
                handler.performNavigation("/views/accessDenied.xhtml?faces-redirect=true");
            }
        }
    }
    
    
    public void isFacultyUser(ComponentSystemEvent event) {

        FacesContext context = FacesContext.getCurrentInstance();
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();

        //if the user is not logged in then redirect to log in page 
        if (!isLoggedin) {
           // return null;
           handler.performNavigation("/views/index.xhtml?faces-redirect=true");
        }//check type of user
        else {
            //show access denied page for non admin users. 
            if (!(user.getRole().getUserCode() == Role.ROLE_FACULTY_CODE)) {
                handler.performNavigation("/views/accessDenied.xhtml?faces-redirect=true");
            }
        }
        
    }
    
    public void isCustomerUser(ComponentSystemEvent event) {

        FacesContext context = FacesContext.getCurrentInstance();
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();

        //if the user is not logged in then redirect to log in page 
        if (!isLoggedin) {
           // return null;
           handler.performNavigation("/views/index.xhtml?faces-redirect=true");
        }//check type of user
        else {
            //show access denied page for non admin users. 
            if (!(user.getRole().getUserCode() == Role.ROLE_CUSTOMER_CODE)) {
                handler.performNavigation("/views/accessDenied.xhtml?faces-redirect=true");
            }
        }
    }

}

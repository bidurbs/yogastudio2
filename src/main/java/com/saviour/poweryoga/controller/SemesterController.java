package com.saviour.poweryoga.controller;

import com.saviour.poweryoga.model.Semester;
import com.saviour.poweryoga.serviceI.ISemesterService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author AnurR
 * @version 0.0.1
 */
@Named("semesterController")
@SessionScoped
public class SemesterController implements Serializable {

    @Autowired
    private ISemesterService SemesterService;

    private Semester semester;
    private List<Semester> listOfSemester;

    private String errorMsg = null;

    private String successMsg = null;

    public SemesterController() {
        //semester = new Semester();
    }

    /**
     * Save Semester data
     *
     * @return
     */
    public String saveSemester() {
        semester.setStatus(Semester.statusType.ACTIVE);
        SemesterService.saveSemester(semester);
        successMsg = "Semester is created successfully";
        return ("/views/admin/manageSemester.xhtml?faces-redirect=true");
    }

    /**
     * Display update Semester data page
     *
     * @return
     */
    public String updateSemester() {
        SemesterService.updateSemester(semester);
        successMsg = "Semester is updated successfully";
        return ("/views/admin/manageSemester.xhtml?faces-redirect=true");
    }

    /**
     * Update Semester data
     *
     * @param Id
     * @return
     */
    public String editSemester(long Id) {
        semester = SemesterService.getSemesterById(Id);
        return "editSemester";
    }

    /**
     * add Semester form
     *
     * @return
     */
    public String addSemester() {
        semester = new Semester();
        return ("/views/admin/addSemester.xhtml?faces-redirect=true");
    }

    /**
     * Delete Course entry
     *
     * @param Id
     * @return
     */
    public String deleteSemester(long Id) {
        try {
            semester = SemesterService.getSemesterById(Id);

            //Set its status inactive
            semester.setStatus(Semester.statusType.INACTIVE);
            SemesterService.updateSemester(semester);
            successMsg = "Semester is deleted successfully";
        } catch (Exception ex) {
            errorMsg = "Delete Semester Failed";
        }
        return ("/views/admin/manageSemester.xhtml?faces-redirect=true");
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public List<Semester> getListOfSemester() {
        listOfSemester = SemesterService.getAllSemester();
        return listOfSemester;
    }

    public void setListOfSemester(List<Semester> listOfSemester) {
        this.listOfSemester = listOfSemester;
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

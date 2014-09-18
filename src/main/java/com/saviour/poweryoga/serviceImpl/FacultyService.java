package com.saviour.poweryoga.serviceImpl;

import com.saviour.poweryoga.crudfacade.CRUDFacadeImpl;
import com.saviour.poweryoga.model.Customer;
import com.saviour.poweryoga.model.Faculty;
import com.saviour.poweryoga.model.Section;
import com.saviour.poweryoga.model.Waiver;
import com.saviour.poweryoga.serviceI.IFacultyService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author AnurR
 * @author TalakB
 * @version 0.0.1
 */
@Service
@Transactional
public class FacultyService implements IFacultyService {

    @Autowired
    private CRUDFacadeImpl crudfacade;

    @Override
    public void saveFaculty(Faculty faculty) {
        //set faculty role 

        crudfacade.save(faculty);

    }

    @Override
    public List<Faculty> getListOfFaculty() {
        return crudfacade.findWithNamedQuery("Faculty.findAll");
    }

    @Override
    public void updateFaculty(Faculty faculty) {
        crudfacade.update(faculty);
    }

    @Override
    public Faculty getFacultyById(long Id) {
        return (Faculty) crudfacade.read(Id, Faculty.class);
    }

    @Override
    public void deleteFaculty(Faculty faculty) {
        crudfacade.delete(faculty);
    }

    /**
     * Find the sections where the faculty is assigned.
     *
     * @return
     */
    @Override
    public List<Section> getMySections(Faculty faculty) {

//        Map<String, String> paramaters = new HashMap<>(1);
//        paramaters.put("uemail", facult);
//        paramaters.put("upass", user.getPassword());
//
//        List authUser = crudfacade.findWithNamedQuery("User.authenticateUser", paramaters);
//
//        return (Users) authUser.get(0);
        return null;
    }

    /**
     * Approve or reject waiver. The status is sent from the controller that
     * would be "APPROVED" or "NOTAPPROVED", so only update is required.
     *
     * @param waiver
     */
    @Override
    public void updateWaiverRequest(Waiver waiver) {
        crudfacade.update(waiver);
    }

    /**
     * Select an advisor from a list of Faculty who has minimum no of advisee.
     *
     * @return
     */
    @Override
    public Faculty pickAdvisor() {
        try {

            String pickAdv = "select FACULTY.userId from (select FACULTY.userId, count(myAdvisor_userId) as totalAdvisee from FACULTY LEFT JOIN CUSTOMER on CUSTOMER.myAdvisor_userId = FACULTY.userId group by myAdvisor_userId order by totalAdvisee asc limit 1) as FACULTY";
            // Object obj = crudfacade.findWithNativeQuery(pickAdv);
            String qResult = crudfacade.findWithNativeQuery(pickAdv).toString();

            Faculty selectedAdvisor = (Faculty) crudfacade.read(Long.valueOf(qResult), Faculty.class);
        //List<Faculty> facultyNew = crudfacade.findWithNativeQuery(pickAdv);
            // return facultyNew.get(0);
            return selectedAdvisor;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return lists of customers advised by this faculty. 
     * @param faculty
     * @return 
     */
    @Override
    public List<Customer> myAdvisee(Faculty faculty) {
        try {
            Map<String, Long> paramaters = new HashMap<>(1);
            paramaters.put("fid", faculty.getUserId());
            List advisee = crudfacade.findWithNamedQuery("Faculty.listMyAdvisee",
                    paramaters);
            return advisee;
        } catch (Exception ex) {
            return null;

        }

    }

    @Override
    public Faculty myAdvisor(Customer customer) {
        try {
            Map<String, Long> paramaters = new HashMap<>(1);
            paramaters.put("fid", customer.getUserId());
            List advisor = crudfacade.findWithNamedQuery("Customer.MyAdvisor",
                    paramaters);
            return (Faculty) advisor.get(0);
        } catch (Exception ex) {
            return null;

        }
    }

    /**
     * Get all Faculty either active or deactive faculty members.
     *
     * @param stauts
     * @return
     */
    @Override
    public List<Faculty> getListOfActiveFaculty() {

        try {
            Map<String, Faculty.statusType> paramaters = new HashMap<>(1);
            paramaters.put("fstatus", Faculty.statusType.ACTIVE);

            List<Object[]> facObj = crudfacade.findWithNamedQuery("Faculty.findActiveDeactive",
                    paramaters);

            List<Faculty> faculty = new ArrayList<>();
            for (Object[] f : facObj) {
                faculty.add((Faculty) f[0]);
            }
            return faculty;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.serviceI;

import com.saviour.poweryoga.model.Customer;
import com.saviour.poweryoga.model.Faculty;
import com.saviour.poweryoga.model.Section;
import com.saviour.poweryoga.model.Waiver;
import java.util.List;

/**
 *
 * @author AnurR
 */
public interface IFacultyService {

    public void saveFaculty(Faculty faculty);

    public List<Faculty> getListOfFaculty();

    public List<Faculty> getListOfActiveFaculty();

    public void updateFaculty(Faculty faculty);

    public Faculty getFacultyById(long Id);

    public void deleteFaculty(Faculty faculty);

    public List<Section> getMySections(Faculty faculty);

    public void updateWaiverRequest(Waiver waiver);

    public Faculty pickAdvisor();

    public List<Customer> myAdvisee(Faculty faculty);

    public Faculty myAdvisor(Customer customer);

}

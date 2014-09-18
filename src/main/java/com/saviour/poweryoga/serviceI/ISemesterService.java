package com.saviour.poweryoga.serviceI;

import com.saviour.poweryoga.model.Semester;
import java.util.List;

/**
 *
 * @author AnurR
 */
public interface ISemesterService {

    public void saveSemester(Semester semester);

    public List<Semester> getAllSemester();

    public boolean updateSemester(Semester semester);

    public Semester getSemesterById(long Id);

    public boolean deleteSemester(long Id);
}

package com.saviour.poweryoga.serviceImpl;

import com.saviour.poweryoga.crudfacade.CRUDFacadeImpl;
import com.saviour.poweryoga.model.Semester;
import com.saviour.poweryoga.serviceI.ISemesterService;
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
public class SemesterService implements ISemesterService {

    @Autowired
    private CRUDFacadeImpl crudfacade;

    @Override
    public void saveSemester(Semester semester) {
        crudfacade.save(semester);
    }

    @Override
    public List<Semester> getAllSemester() {
        Map<String, Enum> paramaters = new HashMap<>(1);
        paramaters.put("status", Semester.statusType.ACTIVE);
        return crudfacade.findWithNamedQuery("Semester.findAllActiveSemesters", paramaters);
    }

    @Override
    public boolean updateSemester(Semester semester) {

        return crudfacade.update(semester);
    }

    @Override
    public Semester getSemesterById(long Id) {
        return (Semester) crudfacade.read(Id, Semester.class);
    }

    @Override
    public boolean deleteSemester(long Id) {
        return crudfacade.delete(Id);
    }
}

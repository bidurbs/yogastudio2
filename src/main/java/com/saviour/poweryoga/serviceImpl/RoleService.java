package com.saviour.poweryoga.serviceImpl;

import com.saviour.poweryoga.crudfacade.CRUDFacadeImpl;
import com.saviour.poweryoga.model.Role;
import com.saviour.poweryoga.model.Users;
import com.saviour.poweryoga.serviceI.IRoleService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TalakB
 */
@Service
@Transactional
public class RoleService implements IRoleService {

    @Autowired
    private CRUDFacadeImpl crudfacade;

    @Override
    public Role getRoleById(Long id) {
        return (Role) crudfacade.read(id, Role.class);
    }

    @Override
    public Role getRoleByUserCode(int userCode) {
        Map<String, Integer> paramaters = new HashMap<>(1);
        paramaters.put("rcode", userCode);

        List userRole = crudfacade.findWithNamedQuery("Role.findRoleByUserCode", paramaters);

        return (Role) userRole.get(0);
    }

}

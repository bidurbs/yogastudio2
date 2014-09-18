
package com.saviour.poweryoga.serviceI;

import com.saviour.poweryoga.model.Role;

/**
 *
 * @author TalakB
 */
public interface IRoleService {
    
    public Role getRoleById(Long id);
    
    public Role getRoleByUserCode(int userCode);
    
    
    
}

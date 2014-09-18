package com.saviour.poweryoga.serviceImpl;

import com.saviour.poweryoga.crudfacade.CRUDFacadeImpl;
import com.saviour.poweryoga.model.Customer;
import com.saviour.poweryoga.model.Users;
import com.saviour.poweryoga.serviceI.IUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Md Mojahidul Islam
 * @author TalakB
 * @version 0.0.1
 */
@Transactional
@Service
public class UserService implements IUserService {

    @Autowired
    private CRUDFacadeImpl crudfacade;

    /**
     * Save the user and return Users object if it is saved successfully.
     *
     * @param user
     * @return
     */
    @Override
    public Users saveUser(Users user) {

        boolean userSaved = false;
        try {
            crudfacade.create(user);

            //assign faculty for this registered user. 
            userSaved = true;
        } catch (Exception ex) {
        }
        if (userSaved) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public List<Customer> findAllCustomer() {
        return crudfacade.findWithNamedQuery("Users.findAllCustomer");
    }

    /**
     * This method will find a customer by his/her id
     *
     * @param customerId
     * @return
     */
    @Transactional
    @Override
    public Customer findCustomerById(Long customerId) {
        try {
            return (Customer) crudfacade.read(customerId, Customer.class);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * This method will find customer by his/her email
     *
     * @param email Customer email
     * @return Customer based on email or null
     */
    @Override
    public Customer findCustomerByEmail(String email) {

        Map<String, String> paramaters = new HashMap<>(1);
        paramaters.put("email", email);

        List<Customer> customer = crudfacade.findWithNamedQuery("Users.findCustomerByEmail", paramaters);
        if (!customer.isEmpty()) {
            return (Customer) customer.get(0);
        } else {
            return null;
        }
    }

    /**
     * This method will find customer by validation link sent to his/her email
     *
     * @param link
     * @return Customer based on link or null
     */
    @Override
    public Customer findCustomerByValidationLink(String link) {
        Map<String, String> paramaters = new HashMap<>(1);
        paramaters.put("validationLink", link);

        List<Customer> customer = crudfacade.findWithNamedQuery("User.findCustomerByValidationLink", paramaters);
        if (!customer.isEmpty()) {
            return (Customer) customer.get(0);
        }
        return null;

    }

    /**
     * This method will update user information
     *
     * @param user
     */
    @Override
    public void updateUser(Users user) {
        crudfacade.merge(user);
    }

    /**
     * This method pass authentication request to DAO .
     *
     * @param user
     * @return userAuthenticated or null
     */
    @Override
    public Users authenticateUser(Users user) {

        try {
            Map<String, String> paramaters = new HashMap<>(2);
            paramaters.put("uemail", user.getEmail());
            paramaters.put("upass", user.getPassword());

            List authUser = crudfacade.findWithNamedQuery("User.authenticateUser", paramaters);

            if (!authUser.isEmpty()) {
                Users authenticatedUser = (Users) authUser.get(0);
                if (authenticatedUser instanceof Customer) {
                    if (authenticatedUser.isApproved()) {
                        return (Users) authUser.get(0);
                    } else {
                        return null;
                    }
                }
                return authenticatedUser;
            }

        } catch (Exception ex) {
            return null;

        }
        return null;

    }

    @Override
    public Users findUserById(Long userId) {
        return (Users) crudfacade.read(userId, Users.class);
    }

}

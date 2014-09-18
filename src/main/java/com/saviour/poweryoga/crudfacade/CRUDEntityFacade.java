package com.saviour.poweryoga.crudfacade;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 *
 * @author TalakB
 * @version 0.0.1
 */


public abstract class CRUDEntityFacade<T> implements PoweryogaEntityFacade<T> {

    public Class entityClass;

   // @Autowired
    private SessionFactory sessionFactory;

    private boolean operationSuccessful;

      
    /**
     *
     * @param entity
     * @return
     * @throws EntityExistsException
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     * @throws TransactionRequiredException
     */
    @Override
    public T create(T entity) throws EntityExistsException,
            IllegalStateException, IllegalArgumentException,
            TransactionRequiredException {
        sessionFactory.getCurrentSession().persist(entity);
        // manager.flush();
        return entity;
    }

    /**
     *
     * @param primaryKey
     * @return
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     */
    @Override
    public T read(final Serializable primaryKey, Class c) throws IllegalStateException,
            IllegalArgumentException {
        return (T) sessionFactory.getCurrentSession().get(c, primaryKey);
    }

    /**
     *
     * @param entity
     * @return
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     * @throws TransactionRequiredException
     */
    @Override
    public boolean update(final T entity) throws IllegalStateException,
            IllegalArgumentException, TransactionRequiredException {
        try {
            sessionFactory.getCurrentSession().update(entity);
            operationSuccessful = true;
        } catch (Exception ex) {

        }
        return operationSuccessful;
    }

    /**
     *
     * @param entity
     * @return
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     * @throws TransactionRequiredException
     * @throws PersistenceException
     */
    @Override
    public boolean delete(final T entity) throws IllegalStateException,
            IllegalArgumentException, TransactionRequiredException,
            PersistenceException {
        try {
            sessionFactory.getCurrentSession().delete(entity);
            operationSuccessful = true;
        } catch (Exception ex) {

        }
        return operationSuccessful;

    }

    /**
     *named query without parameter 
     * @param queryName
     * @return
     */
    @Override
    public List findWithNamedQuery(String queryName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * named query with limited number of results 
     * @param queryName
     * @param resultLimit
     * @return 
     */

    @Override
    public List findWithNamedQuery(String queryName, int resultLimit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * named query with parameters 
     * @param namedQueryName
     * @param parameters
     * @return 
     */
    @Override
    public List findWithNamedQuery(String namedQueryName, Map<String, String> parameters) {
        //  Set parameters = parameters.entrySet();
        Query query = sessionFactory.openSession().getNamedQuery(namedQueryName);

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.list();
    }

    /**
     * named query with parameters and limited number of results
     * @param namedQueryName
     * @param parameters
     * @param resultLimit
     * @return 
     */
    @Override
    public List findWithNamedQuery(String namedQueryName, Map<String, String> parameters, int resultLimit) {
        Query query = sessionFactory.openSession().getNamedQuery(namedQueryName);

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.list().subList(0, resultLimit - 1);
    }
}

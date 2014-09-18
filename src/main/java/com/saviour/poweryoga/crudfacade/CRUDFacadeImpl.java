package com.saviour.poweryoga.crudfacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * CRUD facade
 *
 * @author TalakB
 * @version 0.0.1
 */
@Component
public class CRUDFacadeImpl<T> extends CRUDEntityFacade<T> {

    //  private transient final Class entityClass;
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    private boolean operationSuccessful;

    /**
     * Persist an entity.
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
        try {
            sessionFactory.getCurrentSession().persist(entity);
            return entity;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public T save(T entity) throws EntityExistsException,
            IllegalStateException, IllegalArgumentException,
            TransactionRequiredException {
        try {
            sessionFactory.getCurrentSession().save(entity);
            return entity;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Find by Id (Primary key).
     *
     * @param primaryKey
     * @return
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     */
    @Override
    public T read(final Serializable primaryKey, Class entClass) throws IllegalStateException,
            IllegalArgumentException {
        try {
            return (T) sessionFactory.getCurrentSession().get(entClass, primaryKey);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    /**
     * Update an entity.
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
            ex.printStackTrace();
            operationSuccessful = false;

        }
        return operationSuccessful;
    }

    @Override
    public T merge(final T entity) throws IllegalStateException,
            IllegalArgumentException, TransactionRequiredException {
        try {
            return (T) sessionFactory.getCurrentSession().merge(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    /**
     * Delete an entity.
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
            ex.printStackTrace();
            operationSuccessful = false;

        }
        return operationSuccessful;

    }

    /**
     * Named query without parameter
     *
     * @param queryName
     * @return
     */
    @Override
    public List findWithNamedQuery(String queryName) {
        try {
            Query query = sessionFactory.openSession().getNamedQuery(queryName);
            List qResult = query.list();
            if (!qResult.isEmpty()) {
                return qResult;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Named query with limited number of results
     *
     * @param queryName
     * @param resultLimit
     * @return
     */
    @Override
    public List findWithNamedQuery(String queryName, int resultLimit) {
        try {
            Query query = sessionFactory.openSession().getNamedQuery(queryName);
            List qResult = query.list();
            if (!qResult.isEmpty()) {
                return qResult;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * named query with parameters
     *
     * @param namedQueryName
     * @param parameters
     * @return
     */
    
    @Override
    public List findWithNamedQuery(String namedQueryName, Map<String, String> parameters) {
        try {
            //  Set parameters = parameters.entrySet();
            List qResult = new ArrayList();
            Query query = sessionFactory.openSession().getNamedQuery(namedQueryName);

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            qResult = query.list();
            if (!qResult.isEmpty()) {
                return qResult;
            } else {
                //
                return qResult;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * named query with parameters and limited number of results
     *
     * @param namedQueryName
     * @param parameters
     * @param resultLimit
     * @return
     */
    @Override
    public List findWithNamedQuery(String namedQueryName, Map<String, String> parameters, int resultLimit) {
        try {
            Query query = sessionFactory.openSession().getNamedQuery(namedQueryName);

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            List qResult = query.list().subList(0, resultLimit - 1);
            if (!qResult.isEmpty()) {
                return qResult;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Send native query. The result type depends on the type of query that is
     * sent to mySQL.
     *
     * @param nativeQuery
     * @return
     */
    @Override
    public Object findWithNativeQuery(String nativeQuery) {
        try {
            Query query = sessionFactory.openSession().createSQLQuery(nativeQuery);
            Object result = query.uniqueResult();
            return result;
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return null;
        }

    }
     @Override
    public List findWithNamedQuery2(String namedQueryName,Map<String, Long> parameters){
        Query query = sessionFactory.getCurrentSession().getNamedQuery(namedQueryName);

        for (Map.Entry<String, Long> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.list();
    }
    @Override
    public List findWithNamedQuery(String namedQueryName, Map<String, String> parameters,Map<String, Long> parameters2) {
        //  Set parameters = parameters.entrySet();
        Query query = sessionFactory.openSession().getNamedQuery(namedQueryName);

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Long> entry : parameters2.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }        
        return query.list();
    }

}

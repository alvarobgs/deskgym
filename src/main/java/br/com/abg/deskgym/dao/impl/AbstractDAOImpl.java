package br.com.abg.deskgym.dao.impl;

import br.com.abg.deskgym.dao.AbstractDAO;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.utils.Validator;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import lombok.Getter;
import org.hibernate.StaleObjectStateException;

/**
 * Implementação para o DAO genérico
 * 
 * @author alvaro
 */
public abstract class AbstractDAOImpl<T extends Serializable> implements AbstractDAO<T> {
     
    /**
     * Entity Manager.
     */
    @PersistenceContext(unitName = "DESKGYM-PU")
    private EntityManager em;
    
    /**
     * Entity Manager Factory
     */
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("DESKGYM-PU");
    
    /**
     * Entidade.
     */
    private final Class<T> type;
    
    
    @Override
    public T save(final T entity) throws ObjectExistsException {
        final EntityTransaction transaction = this.getEm().getTransaction();
        try {
            transaction.begin();     
            this.em.persist(entity);
	    transaction.commit();
        
            this.close();
            return entity;
        } catch (final EntityExistsException e) {
            transaction.rollback();
            this.close();
            throw new ObjectExistsException(e.getMessage());
        }
    }

    @Override
    public T getById(final Long id) throws ObjectNotFoundException, FieldInvalidException {
        try {
            final T entity = this.em.find(type, id);
            
            if (Validator.isEmpty(entity)) {
                throw new ObjectNotFoundException(type.getSimpleName());
            }
            return entity;
        } catch (final IllegalArgumentException e) {
            throw new FieldInvalidException("não pode ser vazio", "id");
        }
    }

    @Override
    public void update(final T entity) throws ObjectBeingEditedException {
        final EntityTransaction transaction = this.getEm().getTransaction();
        try {
            transaction.begin();     
            this.em.merge(entity);
//            transaction.commit();
        
            this.close();
        } catch (final StaleObjectStateException | OptimisticLockException e) {
            transaction.rollback();
            this.close();
            throw new ObjectBeingEditedException(e.getMessage());
        }
    }

    @Override
    public void remove(final T entity) throws CannotRemoveException {
        final EntityTransaction transaction = this.getEm().getTransaction();
        try {
            transaction.begin();     
            this.em.remove(entity);
            transaction.commit();
        
            this.close();
        } catch (final PersistenceException e) {
            transaction.rollback();
            this.close();
            throw new CannotRemoveException(e.getMessage());
        }
    }
    
    /**
     * Fecha a conexão se estiver aberta.
     */
    private void close() {
        if (this.em.isOpen()) {
            this.em.close();
        }
    }
    
    /**
     * Construtor.
     */
    public AbstractDAOImpl() {
        type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    /**
     * Getter para o Entity Manager.
     */
    public EntityManager getEm() {
	this.em = factory.createEntityManager();
	return this.em;
    }
}

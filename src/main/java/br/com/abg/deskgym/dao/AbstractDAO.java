package br.com.abg.deskgym.dao;

import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import java.io.Serializable;

/**
 * Interface genérica para o DAO.
 * 
 * @author alvaro
 */
public interface AbstractDAO<T extends Serializable> {
    
    /**
     * Salva uma entidade.
     * 
     * @param entity
     * 
     * @throws ObjectExistsException
     * 
     * @return 
     */
    T save(T entity) throws ObjectExistsException;

    /**
     * Recupera um registro por id.
     * 
     * @param id
     * 
     * @throws ObjectNotFoundException
     * @throws FieldInvalidException
     * 
     * @return 
     */
    T getById(Long id) throws ObjectNotFoundException, FieldInvalidException;

    /**
     * Atualiza uma entidade.
     * 
     * @param entity
     * 
     * @throws ObjectBeingEditedException
     */
    void update(T entity) throws ObjectBeingEditedException;

    /**
     * Remove entidade pelo id.
     * 
     * @param entity
     * 
     * @throws CannotRemoveException
     */
    void remove(T entity) throws CannotRemoveException;
}

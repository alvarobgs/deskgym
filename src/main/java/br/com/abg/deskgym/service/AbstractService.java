package br.com.abg.deskgym.service;

import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import java.io.IOException;

/**
 * Interface abstrata para o serviço
 * 
 * @author Alvaro
 */
public interface AbstractService<T> {
    
    /**
     * Salva a entidade.
     * 
     * @param entity
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * @throws IOException
     * @throws FieldInvalidException
     * @throws ObjectNotFoundException
     * 
     * @return
     */
    T save(T entity) throws ObjectExistsException, CustomMessageException, IOException, FieldInvalidException, ObjectNotFoundException;
    
    /**
     * Atualiza a entidade
     * 
     * @param entity
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     * @throws IOException
     * @throws FieldInvalidException
     * @throws ObjectNotFoundException
     */
    void update(T entity) throws CustomMessageException, ObjectBeingEditedException, IOException, FieldInvalidException, ObjectNotFoundException;
    
    /**
     * Busca pelo id.
     * 
     * @param pk
     * 
     * @throws ObjectNotFoundException
     * @throws FieldInvalidException
     * 
     * @return
     */
    T getById(Long pk) throws ObjectNotFoundException, FieldInvalidException; 
    
    /**
     * Remove a entidade.
     * 
     * @param entity
     * 
     * @throws FieldInvalidException
     * @throws CannotRemoveException
     */
    void remove(T entity) throws FieldInvalidException, CannotRemoveException; 
}

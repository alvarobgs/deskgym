package br.com.abg.deskgym.service;

import br.com.abg.deskgym.entity.Permission;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;

/**
 * Interface abstrata para o serviço de permissões
 * 
 * @author Alvaro
 */
public interface PermissionService {
    
    /**
     * Salva a entidade.
     * 
     * @param permission
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * @throws FieldInvalidException
     * 
     * @return
     */
    Permission save(Permission permission) throws ObjectExistsException, CustomMessageException, FieldInvalidException;
    
    /**
     * Atualiza a entidade
     * 
     * @param permission
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     * @throws FieldInvalidException
     */
    void update(Permission permission) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException;
    
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
    Permission getById(Long pk) throws ObjectNotFoundException, FieldInvalidException; 
    
    /**
     * Remove a entidade.
     * 
     * @param permission
     * 
     * @throws FieldInvalidException
     * @throws CannotRemoveException
     */
    void remove(Permission permission) throws FieldInvalidException, CannotRemoveException; 
}

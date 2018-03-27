package br.com.abg.deskgym.service;

import br.com.abg.deskgym.entity.Key;
import br.com.abg.deskgym.enums.Gender;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import java.util.List;

/**
 * Interface para o serviço de chaves
 * 
 * @author Alvaro
 */
public interface KeyService {
    
    /**
     * Salva a entidade.
     * 
     * @param key
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * 
     * @return
     */
    Key save(Key key) throws CustomMessageException, ObjectExistsException;
    
    /**
     * Atualiza a entidade
     * 
     * @param key
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     */
    void update(Key key) throws CustomMessageException, ObjectBeingEditedException;
    
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
    Key getById(Long pk) throws ObjectNotFoundException, FieldInvalidException; 
    
    /**
     * Remove a entidade.
     * 
     * @param key
     * 
     * @throws FieldInvalidException
     * @throws CannotRemoveException
     */
    void remove(Key key) throws FieldInvalidException, CannotRemoveException; 
    
    /**
     * Lista as chaves de acordo com o sexo.
     * 
     * @param gender
     * 
     * @return
     */
    List<Key> listByGender(Gender gender);
    
    /**
     * Salva ou atualiza uma chave.
     * 
     * @param key
     * 
     * @throws CustomMessageException
     * @throws ObjectExistsException
     * @throws ObjectBeingEditedException
     */
    void saveOrUpdateKey(Key key) throws CustomMessageException, ObjectExistsException, ObjectBeingEditedException;
}

package br.com.abg.deskgym.service;

import java.util.List;

import br.com.abg.deskgym.entity.MonetaryMovement;
import br.com.abg.deskgym.enums.MonetaryMovementType;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;

/**
 * Interface abstrata para o serviço de ganhos e gastos.
 * 
 * @author Alvaro
 */
public interface MonetaryMovementService {
    
    /**
     * Salva a entidade.
     * 
     * @param monetaryMovement
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * @throws FieldInvalidException
     * 
     * @return
     */
    MonetaryMovement save(MonetaryMovement monetaryMovement) throws ObjectExistsException, CustomMessageException, FieldInvalidException;
    
    /**
     * Atualiza a entidade
     * 
     * @param monetaryMovement
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     * @throws FieldInvalidException
     */
    void update(MonetaryMovement monetaryMovement) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException;
    
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
    MonetaryMovement getById(Long pk) throws ObjectNotFoundException, FieldInvalidException; 
    
    /**
     * Remove a entidade.
     * 
     * @param monetaryMovement
     * 
     * @throws FieldInvalidException
     * @throws CannotRemoveException
     */
    void remove(MonetaryMovement monetaryMovement) throws FieldInvalidException, CannotRemoveException;

	/**
	 * Lista de acordo com os filtros
	 *
	 * @param month
	 * @param year
	 * @param type
	 *
	 * @return
	 */
	List<MonetaryMovement> listByMonthAndYearAndType(int month, int year, MonetaryMovementType type);
}

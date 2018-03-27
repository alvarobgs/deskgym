package br.com.abg.deskgym.service;

import br.com.abg.deskgym.entity.ExtraTaxPayment;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;

/**
 * Interface abstrata para o serviço de taxa de matrícula.
 * 
 * @author Alvaro
 */
public interface ExtraTaxPaymentService {
    
    /**
     * Salva a entidade.
     * 
     * @param extraTaxPayment
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * @throws FieldInvalidException
     * 
     * @return
     */
    ExtraTaxPayment save(ExtraTaxPayment extraTaxPayment) throws ObjectExistsException, CustomMessageException, FieldInvalidException;
    
    /**
     * Atualiza a entidade
     * 
     * @param extraTaxPayment
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     * @throws FieldInvalidException
     */
    void update(ExtraTaxPayment extraTaxPayment) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException;
    
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
    ExtraTaxPayment getById(Long pk) throws ObjectNotFoundException, FieldInvalidException;
    
    /**
     * Remove a entidade.
     * 
     * @param extraTaxPayment
     * 
     * @throws FieldInvalidException
     * @throws CannotRemoveException
     */
    void remove(ExtraTaxPayment extraTaxPayment) throws FieldInvalidException, CannotRemoveException;
}

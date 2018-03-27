package br.com.abg.deskgym.service;

import br.com.abg.deskgym.entity.EmployeePayment;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;

/**
 * Interface abstrata para o serviço de pagamento de funcionário.
 * 
 * @author Alvaro
 */
public interface EmployeePaymentService {
    
    /**
     * Salva a entidade.
     * 
     * @param employeePayment
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * @throws FieldInvalidException
     * 
     * @return
     */
    EmployeePayment save(EmployeePayment employeePayment) throws ObjectExistsException, CustomMessageException, FieldInvalidException;
    
    /**
     * Atualiza a entidade
     * 
     * @param employeePayment
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     * @throws FieldInvalidException
     */
    void update(EmployeePayment employeePayment) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException;
    
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
    EmployeePayment getById(final Long pk) throws ObjectNotFoundException, FieldInvalidException;
    
    /**
     * Remove a entidade.
     * 
     * @param employeePayment
     * 
     * @throws FieldInvalidException
     * @throws CannotRemoveException
     */
    void remove(EmployeePayment employeePayment) throws FieldInvalidException, CannotRemoveException; 
    
    /**
     * Verifica os funcionários com pagamentos atrasados.
     */
    void verifyEmployeesPayments();
}

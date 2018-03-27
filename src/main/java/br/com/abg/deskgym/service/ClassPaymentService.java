package br.com.abg.deskgym.service;


import br.com.abg.deskgym.dto.PaymentNearEndDTO;
import br.com.abg.deskgym.entity.ClassPayment;
import br.com.abg.deskgym.entity.ExtraTaxPayment;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interface abstrata para o serviço de mensalidade
 * 
 * @author Alvaro
 */
public interface ClassPaymentService {
    
    /**
     * Salva a entidade.
     * 
     * @param classPayment
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * @throws FieldInvalidException
     * 
     * @return
     */
    ClassPayment save(ClassPayment classPayment) throws ObjectExistsException, CustomMessageException, FieldInvalidException;
    
    /**
     * Atualiza a entidade
     * 
     * @param classPayment
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     * @throws FieldInvalidException
     */
    void update(ClassPayment classPayment) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException;
    
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
    ClassPayment getById(Long pk) throws ObjectNotFoundException, FieldInvalidException; 
    
    /**
     * Remove a entidade.
     * 
     * @param classPayment
     * 
     * @throws FieldInvalidException
     * @throws CannotRemoveException
     */
    void remove(ClassPayment classPayment) throws FieldInvalidException, CannotRemoveException; 
    
    /**
     * Verifica as mensalidades que expiram hoje.
     * 
     * @return
     */
    List<PaymentNearEndDTO> verifyClassPayments();

    /**
     * Registra um novo pagamento de mensalidade.
     *
     * @param classPayment
	 * @param extraTaxPayment
	 * @param creditTax
	 * @param debitTax
	 *
	 * @throws ObjectNotFoundException
	 * @throws FieldInvalidException
	 * @throws ObjectBeingEditedException
	 * @throws CustomMessageException
	 * @throws IOException
	 * @throws ObjectExistsException
     */
    void registerNewPayment(ClassPayment classPayment, ExtraTaxPayment extraTaxPayment, BigDecimal creditTax, BigDecimal debitTax) throws ObjectNotFoundException, FieldInvalidException, ObjectBeingEditedException, CustomMessageException, IOException, ObjectExistsException;

	/**
	 * Verifica se existe uma mensalidade ativa para o aluno.
	 *
	 * @param studentId id do aluno.
	 *
	 * @return true se tiver.
	 */
	boolean checkActivePaymentByStudent(Long studentId);
}

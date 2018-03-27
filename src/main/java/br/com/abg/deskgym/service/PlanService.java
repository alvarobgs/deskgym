package br.com.abg.deskgym.service;

import java.util.List;

import br.com.abg.deskgym.entity.Plan;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;

/**
 * Interface abstrata para o serviço de planos.
 * 
 * @author Alvaro
 */
public interface PlanService {
    
    /**
     * Salva a entidade.
     * 
     * @param plan
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * @throws FieldInvalidException
     * 
     * @return
     */
    Plan save(Plan plan) throws ObjectExistsException, CustomMessageException, FieldInvalidException;
    
    /**
     * Atualiza a entidade
     * 
     * @param plan
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     * @throws FieldInvalidException
     */
    void update(Plan plan) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException;
    
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
    Plan getById(Long pk) throws ObjectNotFoundException, FieldInvalidException; 
    
    /**
     * Remove a entidade.
     * 
     * @param plan
     * 
     * @throws FieldInvalidException
     * @throws CannotRemoveException
     */
    void remove(Plan plan) throws FieldInvalidException, CannotRemoveException;

    /**
     * Inativa o plano, ou remove se não houver nenhum pagamento.
     *
     * @param plan
     */
    void inactive(Plan plan);

    /**
     * Lista os planos ativos.
     *
     * @return
     */
    List<Plan> listActivePlans();
}

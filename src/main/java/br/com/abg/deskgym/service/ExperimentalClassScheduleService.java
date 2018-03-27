package br.com.abg.deskgym.service;

import br.com.abg.deskgym.entity.ExperimentalClassSchedule;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import java.util.Date;
import java.util.List;

/**
 * Interface abstrata para o serviço de agendamento de aula experimental.
 * 
 * @author Alvaro
 */
public interface ExperimentalClassScheduleService {
    
    /**
     * Salva a entidade.
     * 
     * @param experimentalClassSchedule
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * 
     * @return
     */
    ExperimentalClassSchedule save(ExperimentalClassSchedule experimentalClassSchedule) throws CustomMessageException, ObjectExistsException;
    
    /**
     * Atualiza a entidade
     * 
     * @param experimentalClassSchedule
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     */
    void update(ExperimentalClassSchedule experimentalClassSchedule) throws CustomMessageException, ObjectBeingEditedException;
    
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
    ExperimentalClassSchedule getById(Long pk) throws ObjectNotFoundException, FieldInvalidException; 
    
    /**
     * Remove a entidade.
     * 
     * @param experimentalClassSchedule
     * 
     * @throws FieldInvalidException
     * @throws CannotRemoveException
     */
    void remove(ExperimentalClassSchedule experimentalClassSchedule) throws FieldInvalidException, CannotRemoveException; 
    
    /**
     * Carrega as aulas experimentais para determinado dia.
     * 
     * @param date
     * 
     * @return
     */
    List<ExperimentalClassSchedule> getScheduleClassesByDate(Date date);
}

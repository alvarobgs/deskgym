package br.com.abg.deskgym.service;


import java.util.Date;
import java.util.List;

import br.com.abg.deskgym.entity.StudentStatusChange;
import br.com.abg.deskgym.enums.StudentStatus;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;

/**
 * Interface para o serviço de mudança de status do aluno.
 * 
 * @author Alvaro
 */
public interface StudentStatusChangeService {
    
    /**
     * Salva a entidade.
     * 
     * @param studentStatusChange
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * @throws FieldInvalidException
     */
    void save(StudentStatusChange studentStatusChange) throws ObjectExistsException, CustomMessageException, FieldInvalidException;

	/**
	 * Lista as mudanças de status por data.
	 *
	 * @param date
	 *
	 * @return
	 */
	List<StudentStatusChange> listByDate(Date date);

	/**
	 * Lista as mudanças de status por data de estudantes não ativos.
	 *
	 * @param date
	 *
	 * @return
	 */
	List<StudentStatusChange> listNotActivesByDate(Date date);

	/**
	 * Lista as mudanças de status por data.
	 *
	 * @param date
	 * @param studentStatus
	 *
	 * @return
	 */
	List<StudentStatusChange> listByDateAndStatus(Date date, StudentStatus studentStatus);
}

package br.com.abg.deskgym.dao;


import java.util.Date;
import java.util.List;

import br.com.abg.deskgym.entity.StudentStatusChange;
import br.com.abg.deskgym.enums.StudentStatus;

/**
 * Interface para manipulação de mudança de status do aluno.
 * 
 * @author alvaro
 */
public interface StudentStatusChangeDAO extends AbstractDAO<StudentStatusChange> {

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

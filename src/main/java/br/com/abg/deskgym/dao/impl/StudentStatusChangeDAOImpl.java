package br.com.abg.deskgym.dao.impl;


import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import br.com.abg.deskgym.dao.StudentStatusChangeDAO;
import br.com.abg.deskgym.entity.StudentStatusChange;
import br.com.abg.deskgym.enums.StudentStatus;

/**
 * Implementação para o DAO de mudança de status do aluno.
 * 
 * @author alvaro
 */
public class StudentStatusChangeDAOImpl extends AbstractDAOImpl<StudentStatusChange> implements StudentStatusChangeDAO {

	@Override
	public List<StudentStatusChange> listByDate(final Date date) {
		final String strQuery = "SELECT ssc " +
								"FROM StudentStatusChange ssc " +
								"WHERE ssc.date = :date";

		final Query query = this.getEm().createQuery(strQuery);
		query.setParameter("date", date);

		return query.getResultList();
	}

	@Override
	public List<StudentStatusChange> listNotActivesByDate(final Date date) {
		final String strQuery = "SELECT ssc " +
								"FROM StudentStatusChange ssc " +
								"WHERE ssc.date = :date " +
								"AND ssc.newStatus <> :status " +
								"ORDER BY ssc.newStatus, ssc.student.name ASC";

		final Query query = this.getEm().createQuery(strQuery);
		query.setParameter("date", date);
		query.setParameter("status", StudentStatus.ACTIVE);

		return query.getResultList();
	}

	@Override
	public List<StudentStatusChange> listByDateAndStatus(final Date date, final StudentStatus studentStatus) {
		final String strQuery = "SELECT ssc " +
								"FROM StudentStatusChange ssc " +
								"WHERE ssc.date = :date " +
								"AND ssc.newStatus = :status";

		final Query query = this.getEm().createQuery(strQuery);
		query.setParameter("date", date);
		query.setParameter("status", studentStatus);

		return query.getResultList();
	}
}

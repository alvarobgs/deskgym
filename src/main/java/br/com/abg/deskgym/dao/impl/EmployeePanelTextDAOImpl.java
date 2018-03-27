package br.com.abg.deskgym.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.abg.deskgym.dao.EmployeePanelTextDAO;
import br.com.abg.deskgym.entity.EmployeePanelText;

/**
 * Implementação para o DAO de EmployeePanelText.
 * 
 * @author alvaro
 */
public class EmployeePanelTextDAOImpl extends AbstractDAOImpl<EmployeePanelText> implements EmployeePanelTextDAO {

	@Override
	public EmployeePanelText getByEmployeeId(final Long id) {
		final String strQuery = "SELECT ept " +
								"FROM EmployeePanelText ept " +
								"WHERE ept.employee.id = :id";

		final Query query = this.getEm().createQuery(strQuery);
		query.setParameter("id", id);

		try {
			return (EmployeePanelText) query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}
}

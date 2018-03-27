package br.com.abg.deskgym.dao.impl;

import br.com.abg.deskgym.dao.UserDAO;
import br.com.abg.deskgym.entity.User;
import br.com.abg.deskgym.enums.EmployeeStatus;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Implementação para o DAO de User.
 * 
 * @author alvaro
 */
public class UserDAOImpl extends AbstractDAOImpl<User> implements UserDAO {
   
    @Override
    public User getByUserName(final String userName) {
		final String str = "SELECT u " +
						   "FROM User u " +
						   "WHERE u.userName = :n";

		final Query query = this.getEm().createQuery(str);
		query.setParameter("n", userName);

		try {
			return (User) query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
    }
    
    @Override
    public List<User> listActiveEmployeeUsers() {
		final String str = "SELECT u " +
						   "FROM User u " +
						   "JOIN u.employee e " +
						   "WHERE e IS NOT NULL " +
						   "AND e.status <> :s";

		final Query query = this.getEm().createQuery(str);
		query.setParameter("s", EmployeeStatus.INACTIVE);

		return query.getResultList();
    }
}

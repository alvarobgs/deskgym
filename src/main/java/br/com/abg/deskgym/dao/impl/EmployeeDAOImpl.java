package br.com.abg.deskgym.dao.impl;

import br.com.abg.deskgym.dao.EmployeeDAO;
import br.com.abg.deskgym.entity.Employee;
import br.com.abg.deskgym.enums.EmployeeStatus;
import br.com.abg.deskgym.utils.Validator;
import java.util.List;
import javax.persistence.Query;

/**
 * Implementação para o DAO de Employee.
 * 
 * @author alvaro
 */
public class EmployeeDAOImpl extends AbstractDAOImpl<Employee> implements EmployeeDAO {
    
    @Override
    public List<Employee> listActiveEmployees() {
		final String str = "SELECT e " +
						   "FROM Employee e " +
						   "WHERE e.status <> :s";

		final Query query = this.getEm().createQuery(str);
		query.setParameter("s", EmployeeStatus.INACTIVE);

		return query.getResultList();
    }
    
    @Override
    public List<Employee> searchByNameAndStatus(final String name, final EmployeeStatus employeeStatus) {
		final StringBuilder str =  new StringBuilder();
			str.append("SELECT e ");
			str.append("FROM Employee e ");
			str.append("WHERE true");
			if (!Validator.isEmpty(name)) {
				str.append(" AND e.name LIKE :n");
			}
			if (!Validator.isEmpty(employeeStatus)) {
				str.append(" AND e.status = :s");
			}

		final Query query = this.getEm().createQuery(str.toString());
		if (!Validator.isEmpty(name)) {
				query.setParameter("n", "%" + name + "%");
			}
			if (!Validator.isEmpty(employeeStatus)) {
				query.setParameter("s", employeeStatus);
			}

		return query.getResultList();
    }
    
    @Override
    public List<String> searchNameByNameAndStatus(final String name, final EmployeeStatus employeeStatus) {
		final StringBuilder str =  new StringBuilder();
			str.append("SELECT e.name ");
			str.append("FROM Employee e ");
			str.append("WHERE true");
			if (!Validator.isEmpty(name)) {
				str.append(" AND e.name LIKE :n");
			}
			if (!Validator.isEmpty(employeeStatus)) {
				str.append(" AND e.status = :s");
			}

		final Query query = this.getEm().createQuery(str.toString());
			if (!Validator.isEmpty(name)) {
				query.setParameter("n", "%" + name + "%");
			}
			if (!Validator.isEmpty(employeeStatus)) {
				query.setParameter("s", employeeStatus);
			}

		return query.getResultList();
    }
    
    @Override
    public int validateUniqueFields(final Employee employee) {
        final String str = "SELECT COUNT(e) " + 
                           "FROM Employee e " + 
                           "WHERE e.cpf = :cpf OR (e.name = :name AND e.birthday = :bday)";
	
		final Query query = this.getEm().createQuery(str);
		query.setParameter("name", employee.getName());
			query.setParameter("bday", employee.getBirthday());
			query.setParameter("cpf", employee.getCpf());

			final Long count = (long) query.getSingleResult();
		return count.intValue();
    }
    
    @Override
    public List<Employee> getByName(final String name) {
        final String str = "SELECT e " +
						   "FROM Employee e " +
						   "WHERE e.name = :name";
	
		final Query query = this.getEm().createQuery(str);
		query.setParameter("name", name);

		return query.getResultList();
    }
}

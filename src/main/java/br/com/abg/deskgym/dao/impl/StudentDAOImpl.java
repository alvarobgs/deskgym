package br.com.abg.deskgym.dao.impl;

import br.com.abg.deskgym.dao.StudentDAO;
import br.com.abg.deskgym.entity.Student;
import br.com.abg.deskgym.enums.StudentStatus;
import br.com.abg.deskgym.utils.Validator;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 * Implementação para o DAO de Student.
 * 
 * @author alvaro
 */
public class StudentDAOImpl extends AbstractDAOImpl<Student> implements StudentDAO {
    
    @Override
    public int validateUniqueFields(final Student student) {
        final String str = "SELECT COUNT(s) " +
						   "FROM Student s " +
						   "WHERE s.name = :name AND s.birthday = :bday";
	
		final Query query = this.getEm().createQuery(str);
		query.setParameter("name", student.getName());
			query.setParameter("bday", student.getBirthday());

		return (int) query.getSingleResult();
    }
    
    @Override
    public List<Student> searchByNameAndStatus(final String name, final StudentStatus studentStatus) {
		final StringBuilder str =  new StringBuilder();
			str.append("SELECT s ");
			str.append("FROM Student s ");
			str.append("WHERE true");

			if (!Validator.isEmpty(name)) {
				str.append(" AND s.name LIKE :n");
			}

			if (!Validator.isEmpty(studentStatus)) {
				str.append(" AND s.status = :st");
			}

		final Query query = this.getEm().createQuery(str.toString());
			if (!Validator.isEmpty(name)) {
				query.setParameter("n", "%" + name + "%");
			}
			if (!Validator.isEmpty(studentStatus)) {
				query.setParameter("st", studentStatus);
			}

		return query.getResultList();
    }
    
    @Override
    public List<String> searchNameByNameAndStatus(final String name, final StudentStatus studentStatus) {
		final StringBuilder str =  new StringBuilder();
		str.append("SELECT s.name ");
		str.append("FROM Student s ");
		str.append("WHERE true ");
		if (!Validator.isEmpty(name)) {
			str.append(" AND s.name LIKE :n");
		}
		if (!Validator.isEmpty(studentStatus)) {
			str.append(" AND s.status = :status");
		}

		final Query query = this.getEm().createQuery(str.toString());
		if (!Validator.isEmpty(name)) {
				query.setParameter("n", "%" + name + "%");
		}
		if (!Validator.isEmpty(studentStatus)) {
			query.setParameter("status", studentStatus);
		}

		return query.getResultList();
    }

    @Override
    public List<Student> getByName(final String name) {
        final String str = "SELECT s " +
						   "FROM Student s " +
						   "WHERE s.name = :name";
	
		final Query query = this.getEm().createQuery(str);
		query.setParameter("name", name);

        return query.getResultList();
    }

    @Override
    public List<Student> listByStatus(final StudentStatus status) {
        final String str = "SELECT s " +
						   "FROM Student s " +
						   "WHERE s.status = :status";
	
		final Query query = this.getEm().createQuery(str);
		query.setParameter("status", status);
        
        return query.getResultList();
    }

    @Override
    public List<Student> listActiveStudentsByBirthday(final Date initDateInterval, final Date endDateInterval) {
        final String str = "SELECT s " +
						   "FROM Student s " +
						   "WHERE s.birthday > :init AND s.birthday < :end AND s.status <> :status";
	
		final Query query = this.getEm().createQuery(str);
		query.setParameter("init", initDateInterval);
        query.setParameter("end", endDateInterval);
        query.setParameter("status", StudentStatus.INACTIVE);
        
        return query.getResultList();
    }
    
    @Override
    public List<Student> listStudentsToCheckStatus() {
        final String str = "SELECT s " + 
                           "FROM Student s " +
                           "WHERE s.status <> :status " + 
                           "AND s.id NOT IN " + 
                            "(SELECT DISTINCT cp.student.id " + 
                            "FROM ClassPayment cp " + 
                            "WHERE cp.outDated = :outdated)";
	
		final Query query = this.getEm().createQuery(str);
		query.setParameter("status", StudentStatus.INACTIVE);
        query.setParameter("outdated", false);
        
        return query.getResultList();
    }
}

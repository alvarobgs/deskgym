package br.com.abg.deskgym.dao.impl;

import br.com.abg.deskgym.dao.ClassPaymentDAO;
import br.com.abg.deskgym.entity.ClassPayment;
import br.com.abg.deskgym.utils.Validator;

import java.util.List;
import javax.persistence.Query;

/**
 * Implementação para o DAO de ClassPayment.
 * 
 * @author alvaro
 */
public class ClassPaymentDAOImpl extends AbstractDAOImpl<ClassPayment> implements ClassPaymentDAO {

    @Override
    public List<ClassPayment> listActivePayments() {
        final String strQuery = "SELECT cp " +
                                "FROM ClassPayment cp " +
                                "WHERE cp.outDated = :outDated";
        
        final Query query = this.getEm().createQuery(strQuery);
        query.setParameter("outDated", false);
        
        return query.getResultList();
    }
    
    @Override
    public void updateOutdatedClassPaymentsById(final List<Long> ids) {
        final String strQuery = "UPDATE ClassPayment cp " +
                                "SET cp.outDated = :t " +
                                "WHERE cp.id IN :ids";
        
        final Query query = this.getEm().createQuery(strQuery);
        query.setParameter("t", true);
        query.setParameter("ids", ids);
        
        query.executeUpdate();
    }

	@Override
	public boolean checkActivePaymentByStudent(final Long studentId) {
		final String strQuery = "SELECT cp " +
								"FROM ClassPayment cp " +
								"WHERE cp.outDated = :outDated " +
								"AND cp.student.id = :id";

		final Query query = this.getEm().createQuery(strQuery);
		query.setParameter("outDated", false);
		query.setParameter("id", studentId);

		return !Validator.isEmpty(query.getResultList());
	}
}

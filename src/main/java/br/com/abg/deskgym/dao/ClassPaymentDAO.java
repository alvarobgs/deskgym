package br.com.abg.deskgym.dao;

import br.com.abg.deskgym.entity.ClassPayment;
import java.util.List;

/**
 * Interface para manipulação de dados de ClassPayment.
 * 
 * @author alvaro
 */
public interface ClassPaymentDAO extends AbstractDAO<ClassPayment> {
    
    /**
     * Busca os pagamentos ativos.
     * 
     * @return
     */
    List<ClassPayment> listActivePayments();
    
    /**
     * Atualiza várias mensalidades para obsoletas dado o id.
     * 
     * @param ids
     */
    void updateOutdatedClassPaymentsById(List<Long> ids);

	/**
	 * Verifica se o aluno possui alguma mensalidade não obsoleta.
	 *
	 * @param studentId id do aluno
	 *
	 * @return true se tiver mensalidade ativa
	 */
	boolean checkActivePaymentByStudent(Long studentId);
}

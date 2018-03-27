package br.com.abg.deskgym.dao;

import br.com.abg.deskgym.entity.EmployeePanelText;

/**
 * Interface para manipulação de dados de EmployeePanelText.
 * 
 * @author alvaro
 */
public interface EmployeePanelTextDAO extends AbstractDAO<EmployeePanelText> {
    
    /**
     * Busca o texto pelo id do funcionário.
     * 
     * @param id
     * 
     * @return 
     */
    EmployeePanelText getByEmployeeId(Long id);
}

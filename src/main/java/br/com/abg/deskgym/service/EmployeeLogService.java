package br.com.abg.deskgym.service;

import br.com.abg.deskgym.entity.Employee;
import br.com.abg.deskgym.entity.EmployeeLog;
import br.com.abg.deskgym.enums.LogType;

/**
 * Interface abstrata para o serviço de log de funcionário
 * 
 * @author Alvaro
 */
public interface EmployeeLogService {
    
    /**
     * Salva a entidade.
     * 
     * @param employeeLog
     */
    void save(EmployeeLog employeeLog);

	/**
	 * Salva a entidade.
	 *
	 * @param employee
	 * @param logType
	 * @param description
	 */
	void save(Employee employee, LogType logType, String description);
}

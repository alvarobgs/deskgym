package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.EmployeeLogDAO;
import br.com.abg.deskgym.dao.impl.EmployeeLogDAOImpl;
import br.com.abg.deskgym.entity.Employee;
import br.com.abg.deskgym.entity.EmployeeLog;
import br.com.abg.deskgym.enums.LogType;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.service.EmployeeLogService;
import br.com.abg.deskgym.utils.Logger;

import java.io.Serializable;

/**
 * Implementação para o serviço de funcionário.
 * 
 * @author Alvaro
 */
public class EmployeeLogServiceImpl implements EmployeeLogService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 1549277187439009590L;

	/**
     * DAO de log de funcionário.
     */
    private transient EmployeeLogDAO employeeLogDAO;

    /**
     * Log.
     */
    private transient Logger logger;
    
    /**
     * Construtor.
     */
    public EmployeeLogServiceImpl() {
        this.employeeLogDAO = new EmployeeLogDAOImpl();
        this.logger = new Logger();
    }

    @Override
    public void save(final EmployeeLog employeeLog) {
		try {
			this.employeeLogDAO.save(employeeLog);
		} catch (ObjectExistsException e) {
			this.logger.save(e.getMessage(), e.getClass().toString(), "EmployeeLogServiceImpl", "save");
		}
    }

	@Override
	public void save(final Employee employee, final LogType logType, final String description) {
		this.save(new EmployeeLog(employee, logType, description));
	}
}

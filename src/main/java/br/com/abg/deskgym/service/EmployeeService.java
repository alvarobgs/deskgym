package br.com.abg.deskgym.service;

import br.com.abg.deskgym.entity.Employee;
import br.com.abg.deskgym.entity.EmployeePanelText;
import br.com.abg.deskgym.enums.EmployeeStatus;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Interface abstrata para o serviço de funcionário
 * 
 * @author Alvaro
 */
public interface EmployeeService {
    
    /**
     * Salva a entidade.
     * 
     * @param employee
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * @throws IOException
     * @throws FieldInvalidException
     * @throws ObjectNotFoundException
     * 
     * @return
     */
    Employee save(Employee employee) throws CustomMessageException, IOException, ObjectExistsException, FieldInvalidException, ObjectNotFoundException;
    
    /**
     * Atualiza a entidade
     * 
     * @param employee
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     * @throws IOException
     * @throws FieldInvalidException
     * @throws ObjectNotFoundException
     */
    void update(Employee employee) throws CustomMessageException, IOException, FieldInvalidException, ObjectNotFoundException, ObjectBeingEditedException;
    
    /**
     * Busca pelo id.
     * 
     * @param pk
     * 
     * @throws ObjectNotFoundException
     * @throws FieldInvalidException
     * 
     * @return
     */
    Employee getById(Long pk) throws ObjectNotFoundException, FieldInvalidException; 
    
    /**
     * Remove a entidade.
     * 
     * @param employee
     * 
     * @throws FieldInvalidException
     * @throws CannotRemoveException
     */
    void remove(Employee employee) throws FieldInvalidException, CannotRemoveException;
    
    /**
     * Lista os funcionários ativos.
     * 
     * @return
     */
    List<Employee> listActiveEmployees();
    
    /**
     * Busca pelo nome.
     * 
     * @param name
     * 
     * @throws FieldInvalidException
     * 
     * @return
     */
    Employee getByName(String name) throws FieldInvalidException;
    
    /**
     * Busca os funcionários por nome e status.
     * 
     * @param name
     * @param status
     * 
     * @return
     */
    List<Employee> filterByNameAndStatus(String name, EmployeeStatus status);
    
    /**
     * Busca os nomes dos funcionários por nome e status.
     * 
     * @param name
     * @param status
     * 
     * @return
     */
    List<String> filterNameByNameAndStatus(String name, EmployeeStatus status);
    
    
    /**
     * Cria o funcionário Admin.
     * 
     * @throws CustomMessageException
     * @throws IOException
     * @throws ObjectExistsException
     * @throws FieldInvalidException
     * @throws ObjectNotFoundException
     */
    void createAdminEmployee() throws CustomMessageException, IOException, FieldInvalidException, ObjectNotFoundException, ObjectExistsException;

    /**
     * Atualiza o texto do painel do funcionário.
     *
	 * @throws ObjectExistsException
	 * @throws ObjectBeingEditedException
	 *
     * @param employee
     * @param text
     */
    void updateEmployeePanelText(Employee employee, String text) throws ObjectExistsException, ObjectBeingEditedException;

	/**
	 * Busca o texto do painel do funcionário.
	 *
	 * @param id do funcionário.
	 *
	 * @throws FieldInvalidException
	 *
	 * @return
	 */
	EmployeePanelText getEmployeePanelText(Long id) throws FieldInvalidException;

	/**
	 * Atualiza o texto do painel do funcionário.
	 *
	 * @param id do funcionário.
	 * @param text texto
	 *
	 * @throws FieldInvalidException
	 * @throws ObjectBeingEditedException
	 */
	void updateEmployeePanelText(Long id, String text) throws FieldInvalidException, ObjectBeingEditedException;
}

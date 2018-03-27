package br.com.abg.deskgym.dao;

import br.com.abg.deskgym.entity.Employee;
import br.com.abg.deskgym.enums.EmployeeStatus;
import java.util.List;

/**
 * Interface para manipulação de dados de Employee.
 * 
 * @author alvaro
 */
public interface EmployeeDAO extends AbstractDAO<Employee> {
 
    /**
     * Lista os funcionários ativos.
     * 
     * @return lista com os funcionários que possuem status diferente de inativo.
     */
    List<Employee> listActiveEmployees();
    
    /**
     * Busca os funcionários por nome e status
     * 
     * @param name nome do funcionário
     * @param employeeStatus status do funcionário. Se for nulo, pesquisa em todos.
     * 
     * @return lista com os resultados encontrados.
     */
    List<Employee> searchByNameAndStatus(String name, EmployeeStatus employeeStatus);
    
    /**
     * Busca o nome dos funcionários por nome e status
     * 
     * @param name nome do funcionário
     * @param employeeStatus status do funcionário. Se for nulo, pesquisa em todos.
     * 
     * @return lista com os resultados encontrados.
     */
    List<String> searchNameByNameAndStatus(String name, EmployeeStatus employeeStatus);
    
    /**
     * Verifica se já existe algum funcionário com os campos únicos.
     * 
     * @param employee
     * 
     * @return quantidade de registros encontrados.
     */
    int validateUniqueFields(Employee employee);
    
    /**
     * Busca os funcionários pelo nome completo.
     * 
     * @param name
     * 
     * @return 
     */
    List<Employee> getByName(String name);
}

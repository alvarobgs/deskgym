package br.com.abg.deskgym.dao;

import br.com.abg.deskgym.entity.Student;
import br.com.abg.deskgym.enums.StudentStatus;
import java.util.Date;
import java.util.List;

/**
 * Interface para manipulação de dados de Student.
 * 
 * @author alvaro
 */
public interface StudentDAO extends AbstractDAO<Student> {
    
    /**
     * Verifica se já existe algum aluno com os campos únicos.
     * 
     * @param student
     * 
     * @return quantidade de registros encontrados.
     */
    int validateUniqueFields(Student student);
    
    /**
     * Busca os alunos por nome e status.
     * 
     * @param name nome do aluno.
     * @param studentStatus status do aluno. Se vier nulo, busca em todos.
     * 
     * @return lista com os resultados encontrados.
     */
    List<Student> searchByNameAndStatus(String name, StudentStatus studentStatus);
    
    /**
     * Busca os nomes dos alunos por nome e status.
     * 
     * @param name nome do aluno.
     * @param studentStatus status do aluno. Se vier nulo, busca em todos.
     * 
     * @return lista com os resultados encontrados.
     */
    List<String> searchNameByNameAndStatus(String name, StudentStatus studentStatus);
    
    /**
     * Busca os alunos pelo nome completo.
     * 
     * @param name
     * 
     * @return 
     */
    List<Student> getByName(String name);
    
    /**
     * Lista os alunos por status.
     * 
     * @param status
     * 
     * @return 
     */
    List<Student> listByStatus(StudentStatus status);
    
    /**
     * Lista os aniversariantes do mês.
     * 
     * @param endDateInterval 
     * @param initDateInterval 
     * 
     * @return
     */
    List<Student> listActiveStudentsByBirthday(Date initDateInterval, Date endDateInterval);
    
    /**
     * Lista os alunos que serão inativados.
     * 
     * @return 
     */
    List<Student> listStudentsToCheckStatus();
}

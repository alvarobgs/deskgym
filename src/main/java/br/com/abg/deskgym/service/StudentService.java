package br.com.abg.deskgym.service;

import br.com.abg.deskgym.entity.Student;
import br.com.abg.deskgym.enums.StudentStatus;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Interface abstrata para o serviço de aluno.
 * 
 * @author Alvaro
 */
public interface StudentService {
    
    /**
     * Salva a entidade.
     * 
     * @param student
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * @throws IOException
     * @throws FieldInvalidException
     * @throws ObjectNotFoundException
     * 
     * @return
     */
    Student save(Student student) throws CustomMessageException, IOException, ObjectNotFoundException, FieldInvalidException, ObjectExistsException;
    
    /**
     * Atualiza a entidade
     * 
     * @param student
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     * @throws IOException
     * @throws FieldInvalidException
     * @throws ObjectNotFoundException
     */
    void update(Student student) throws CustomMessageException, IOException, FieldInvalidException, ObjectNotFoundException, ObjectBeingEditedException;
    
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
    Student getById(Long pk) throws ObjectNotFoundException, FieldInvalidException; 
    
    /**
     * Remove a entidade. 
     * 
     * @param student
     * 
     * @throws FieldInvalidException
     * @throws CannotRemoveException
     */
    void remove(Student student) throws FieldInvalidException, CannotRemoveException; 
    
    /**
     * Atualiza o status dos alunos não inativos com mensalidades vencidas.
     * 
     * @throws CustomMessageException
     * @throws IOException
     * @throws FieldInvalidException
     * @throws ObjectNotFoundException
     * @throws ObjectBeingEditedException 
     */
    void checkAndUpdateStudentStatus() throws CustomMessageException, IOException, FieldInvalidException, ObjectNotFoundException, ObjectBeingEditedException, ObjectExistsException;
    
    /**
     * Lista os alunos por status.
     * 
     * @param status
     * 
     * @throws FieldInvalidException
     * 
     * @return 
     */
    List<Student> listByStatus(StudentStatus status) throws FieldInvalidException;
    
    /**
     * Lista os aniversariantes do mês.
     * 
     * @return
     */
    List<Student> listActiveStudentsByBirthday();
    
    /**
     * Busca o aluno pelo nome.
     * 
     * @param name
     * 
     * @throws FieldInvalidException
     * 
     * @return
     */
    Student getByName(String name) throws FieldInvalidException;
    
    /**
     * Busca os alunos por nome e status.
     * 
     * @param name
     * @param status
     * 
     * @return
     */
    List<Student> filterByNameAndStatus(String name, StudentStatus status);
    
    /**
     * Busca os nomes dos alunos por nome e status.
     * 
     * @param name
     * @param status
     * 
     * @return
     */
    List<String> filterNameByNameAndStatus(String name, StudentStatus status);
}

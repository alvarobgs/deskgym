package br.com.abg.deskgym.service;

import br.com.abg.deskgym.entity.User;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import java.util.List;

/**
 * Interface abstrata para o serviço de usuário.
 * 
 * @author Alvaro
 */
public interface UserService {
    
    /**
     * Recupera um usuário pelo username.
     * 
     * @param userName
     * 
     * @throws FieldInvalidException
     * 
     * @return
     */
    User getByUserName(String userName) throws FieldInvalidException;
    
    /**
     * Recupera os usuários de funcionários que estão ativos.
     * 
     * @return
     */
    List<User> listActiveEmployeeUsers();
}

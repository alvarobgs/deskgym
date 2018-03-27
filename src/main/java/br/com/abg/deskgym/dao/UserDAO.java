package br.com.abg.deskgym.dao;

import br.com.abg.deskgym.entity.User;
import java.util.List;

/**
 * Interface para manipulação de dados de User.
 * 
 * @author alvaro
 */
public interface UserDAO extends AbstractDAO<User> {
    
    /**
     * Recupera um usuário pelo username.
     */
    User getByUserName(String userName);
    
    /**
     * Recupera os usuários de funcionários que estão ativos.
     */
    List<User> listActiveEmployeeUsers();
    
}

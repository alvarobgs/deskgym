package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.UserDAO;
import br.com.abg.deskgym.dao.impl.UserDAOImpl;
import br.com.abg.deskgym.entity.User;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.service.UserService;
import br.com.abg.deskgym.utils.Validator;
import java.io.Serializable;
import java.util.List;

/**
 * Implementação para o serviço de usuário.
 * 
 * @author Alvaro
 */
public class UserServiceImpl implements UserService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 7729859928488284384L;

	/**
     * DAO de usuário.
     */
    private transient UserDAO userDAO;
    
    /**
     * Construtor.
     */
    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }
    
    @Override
    public User getByUserName(final String userName) throws FieldInvalidException {
		if (Validator.isEmpty(userName)) {
		   throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "Nome do usuário");
		}
		 return this.userDAO.getByUserName(userName);
    }
    
    @Override
    public List<User> listActiveEmployeeUsers() {
		return this.userDAO.listActiveEmployeeUsers();
    }
}

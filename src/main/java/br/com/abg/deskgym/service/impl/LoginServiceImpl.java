package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.entity.User;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.service.LoginService;
import br.com.abg.deskgym.service.SystemConfigurationService;
import br.com.abg.deskgym.session.LoggedUser;
import br.com.abg.deskgym.exceptions.IncorrectPasswordException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.service.UserService;
import br.com.abg.deskgym.utils.PasswordEncrypter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Implementação para o serviço de login.
 * 
 * @author Alvaro
 */
public class LoginServiceImpl implements LoginService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = -8204701020939278594L;

	/**
     * Serviço de usuário
     */
    private transient UserService userService;
    
    /**
     * Serviço de configurações do sistema.
     */
    private transient SystemConfigurationService systemConfigurationService;
    
    /**
     * Construtor.
     */
    public LoginServiceImpl() {
		this.userService = new UserServiceImpl();
        this.systemConfigurationService = new SystemConfigurationServiceImpl();
    }
 
    @Override
    public void logIn(final String userName, final String password) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException, NoSuchAlgorithmException, UnsupportedEncodingException, IncorrectPasswordException {
		final User user = this.userService.getByUserName(userName);
		final String encryptedPassword = PasswordEncrypter.encrypt(password);

		if (!encryptedPassword.equals(user.getPassword())){
			throw new IncorrectPasswordException("Usuário ou senha incorreta");
		}
		new LoggedUser().setInstancedEmployee(user.getEmployee());
		this.systemConfigurationService.updateLastLogin();
    }
}

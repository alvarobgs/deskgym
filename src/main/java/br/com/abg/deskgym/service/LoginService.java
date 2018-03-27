package br.com.abg.deskgym.service;

import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.IncorrectPasswordException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Interface abstrata para o serviço de login
 * 
 * @author Alvaro
 */
public interface LoginService {
    
    /**
     * Realiza o login no sistema.
     * 
     * @param userName
     * @param password
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     * @throws FieldInvalidException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws IncorrectPasswordException
     */
    void logIn(String userName, String password) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException, NoSuchAlgorithmException, UnsupportedEncodingException, IncorrectPasswordException;
}

package br.com.abg.deskgym.service;

import br.com.abg.deskgym.entity.Contact;
import br.com.abg.deskgym.exceptions.CustomMessageException;

/**
 * Interface abstrata para o serviço de contato.
 * 
 * @author Alvaro
 */
public interface ContactService {
    
    /**
     * Verifica os campos obrigatórios.
     * 
     * @param contact
     * 
     * @throws CustomMessageException
     */
    void validateRequiredFields(Contact contact) throws CustomMessageException;
    
    /**
     * Valida o tamanhos dos campos.
     * 
     * @param contact
     * 
     * @throws CustomMessageException
     */
    void validateFieldSize(Contact contact) throws CustomMessageException;
    
    /**
     * Valida os valores nos campos.
     * 
     * @param contact
     * 
     * @throws CustomMessageException
     */
    void validateFieldValues(Contact contact) throws CustomMessageException;
}

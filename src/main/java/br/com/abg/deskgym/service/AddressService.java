package br.com.abg.deskgym.service;

import br.com.abg.deskgym.entity.Address;
import br.com.abg.deskgym.entity.City;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;

/**
 * Interface abstrata para o serviço de endereço.
 * 
 * @author Alvaro
 */
public interface AddressService {

    /**
     * Busca uma cidade pelo nome e o nome do estado.
     *
     * @param city
     * @param state
     *
     * @throws ObjectNotFoundException
     * @throws FieldInvalidException
     *
     * @return
     */
    City getCityByNameAndStateName(String city, String state) throws ObjectNotFoundException, FieldInvalidException;

    /**
     * Verifica os campos obrigatórios.
     * 
     * @param address
     * 
     * @throws CustomMessageException
     */
    void validateRequiredFields(Address address) throws CustomMessageException;
    
    /**
     * Valida o tamanhos dos campos.
     * 
     * @param address
     * 
     * @throws CustomMessageException
     */
    void validateFieldSize(Address address) throws CustomMessageException;
    
    /**
     * Valida os valores nos campos.
     * 
     * @param address
     * 
     * @throws CustomMessageException
     */
    void validateFieldValues(Address address) throws CustomMessageException;
}

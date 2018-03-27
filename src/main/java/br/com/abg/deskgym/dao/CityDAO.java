package br.com.abg.deskgym.dao;

import br.com.abg.deskgym.entity.City;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;

/**
 * Interface para manipulação de dados de Cidade.
 * 
 * @author alvaro
 */
public interface CityDAO extends AbstractDAO<City> {
    
    /**
     * Busca uma cidade pelo nome e nome do estado.
     * 
     * @param city
     * @param state
     * 
     * @throws ObjectNotFoundException
     * 
     * @return 
     */
    City getCityByNameAndStateName(String city, String state) throws ObjectNotFoundException;
    
}

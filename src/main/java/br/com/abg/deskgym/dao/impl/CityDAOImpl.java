package br.com.abg.deskgym.dao.impl;

import br.com.abg.deskgym.entity.City;
import br.com.abg.deskgym.dao.CityDAO;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Implementação para o DAO de City.
 * 
 * @author alvaro
 */
public class CityDAOImpl extends AbstractDAOImpl<City> implements CityDAO {
    
    @Override
    public City getCityByNameAndStateName(final String city, final String state) throws ObjectNotFoundException {
        final String strQuery = "SELECT c " +
                                "FROM City c " +
                                "JOIN c.state s " +
                                "WHERE c.name = :city " +
                                "AND s.name = :state";
        
        final Query query = this.getEm().createQuery(strQuery);
        query.setParameter("city", city);
        query.setParameter("state", state);
        
        try {
            return (City) query.getSingleResult();
        } catch (final NoResultException ex) {
            throw new ObjectNotFoundException("Cidade não encontrada.");
        }
    }
    
}

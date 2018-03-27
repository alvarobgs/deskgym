package br.com.abg.deskgym.dao.impl;

import br.com.abg.deskgym.dao.KeyDAO;
import br.com.abg.deskgym.entity.Key;
import br.com.abg.deskgym.enums.Gender;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Implementação para o DAO de Key.
 * 
 * @author alvaro
 */
public class KeyDAOImpl extends AbstractDAOImpl<Key> implements KeyDAO {
    
    @Override
    public List<Key> listByGender(final Gender gender) {
		/*final String strQuery = "SELECT k " +
                            	"FROM Key k " +
                            	"WHERE k.gender = :g";
        
        final Query query = this.getEm().createQuery(strQuery);
        query.setParameter("g", gender);
        
        return query.getResultList();*/
                return new ArrayList<>();
    }
    
    @Override
    public Key getByNumberAndGender(int number, Gender gender) {
        final String strQuery = "SELECT k " +
                                "FROM Key k " +
                                "WHERE k.number = :n AND k.gender = :g";
        
        final Query query = this.getEm().createQuery(strQuery);
        query.setParameter("n", number);
        query.setParameter("g", gender);
        
        try {
            return (Key) query.getSingleResult();
        } catch (final NoResultException e) {
            return null;
        }
    }
}

package br.com.abg.deskgym.dao;

import br.com.abg.deskgym.entity.Key;
import br.com.abg.deskgym.enums.Gender;
import java.util.List;

/**
 * Interface para manipulação de dados de Key.
 * 
 * @author alvaro
 */
public interface KeyDAO extends AbstractDAO<Key> {
    
    /**
     * Lista as chaves de acordo com o sexo.
     * 
     * @param gender
     * 
     * @return
     */
    List<Key> listByGender(Gender gender);
    
    /**
     * Busca uma chave pelo seu número e sexo.
     * 
     * @param number
     * @param gender
     * 
     * @return 
     */
    Key getByNumberAndGender(int number, Gender gender);
    
}

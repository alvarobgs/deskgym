package br.com.abg.deskgym.dao.impl;

import br.com.abg.deskgym.dao.SystemConfigurationDAO;
import br.com.abg.deskgym.entity.SystemConfiguration;
import br.com.abg.deskgym.utils.Validator;
import java.util.List;
import javax.persistence.Query;

/**
 * Implementação para o DAO de SystemConfiguration.
 * 
 * @author alvaro
 */
public class SystemConfigurationDAOImpl extends AbstractDAOImpl<SystemConfiguration> implements SystemConfigurationDAO {
    
    @Override
    public SystemConfiguration getSystemConfiguration() {
		final String strQuery = "SELECT s " +
								"FROM SystemConfiguration s " +
								"ORDER BY s.id ASC";

		final Query query = this.getEm().createQuery(strQuery);

		final List<SystemConfiguration> configurations = query.getResultList();

		if (!Validator.isEmpty(configurations)) {
			return configurations.get(0);
		}
		return null;
	}
}

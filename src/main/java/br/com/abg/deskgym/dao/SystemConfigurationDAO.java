package br.com.abg.deskgym.dao;

import br.com.abg.deskgym.entity.SystemConfiguration;

/**
 * Interface para manipulação de dados de Student.
 * 
 * @author alvaro
 */
public interface SystemConfigurationDAO extends AbstractDAO<SystemConfiguration> {
    
    /**
     * Retorna o primeiro objeto da tabela de configurações.
     */
    SystemConfiguration getSystemConfiguration();
}

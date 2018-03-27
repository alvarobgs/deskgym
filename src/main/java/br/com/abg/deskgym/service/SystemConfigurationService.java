package br.com.abg.deskgym.service;

import br.com.abg.deskgym.entity.SystemConfiguration;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import java.io.IOException;

/**
 * Interface abstrata para o serviço de configurações do sistema.
 * 
 * @author Alvaro
 */
public interface SystemConfigurationService {
    
    /**
     * Salva a entidade.
     * 
     * @param systemConfiguration
     *  
     * @throws ObjectExistsException
     * @throws CustomMessageException
     * 
     * @return
     */
    SystemConfiguration save(SystemConfiguration systemConfiguration) throws ObjectExistsException, CustomMessageException;
    
    /**
     * Atualiza a entidade
     * 
     * @param systemConfiguration
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     */
    void update(SystemConfiguration systemConfiguration) throws CustomMessageException, ObjectBeingEditedException;
    
    /**
     * Valida o último login efetuado.
     * 
     * @return true se o último login foi ha menos de 7 dias.
     */
    boolean validateLastLogin();
    
    /**
     * Atualiza a data de último login no sistema.
     * 
     * @throws CustomMessageException
     * @throws ObjectBeingEditedException
     */
    void updateLastLogin() throws CustomMessageException, ObjectBeingEditedException;
    
    /**
     * Busca o texto do painel livre.
     * 
     * @return
     */
    String getFreePanelText();
    
    /**
     * Realiza o backup do banco de dados.
	 *
	 * @throws InterruptedException
	 * @throws IOException
     */
    void backupDataBase() throws InterruptedException, IOException;
    
    /**
     * Verifica se é o primeiro acesso ao sistema e cria as variáveis de configuração e admin.
     * 
     * @throws CustomMessageException
     * @throws ObjectExistsException
     * @throws IOException
     * @throws FieldInvalidException
     * @throws ObjectNotFoundException
     */
    void verifyFirstSystemAccess() throws ObjectExistsException, CustomMessageException, IOException, FieldInvalidException, ObjectNotFoundException;
    
    /**
     * Retorna as configurações do sistema.
     *
     * @return o primeiro objeto da tabela de configurações.
     */
    SystemConfiguration getSystemConfiguration();

    /**
     * Verifica se já utilizaram o sistema na data de hoje.
     *
     * @return true se sim, false se não
     */
    boolean alreadyLoggedInToday();

	/**
	 * Atualiza o texto do painel livre.
	 * 
	 * @throws CustomMessageException
	 * @throws ObjectBeingEditedException
	 *
	 * @param text
	 */
	void updateFreePanelText(String text) throws CustomMessageException, ObjectBeingEditedException;
}

package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.SystemConfigurationDAO;
import br.com.abg.deskgym.dao.impl.SystemConfigurationDAOImpl;
import br.com.abg.deskgym.entity.SystemConfiguration;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.EmployeeService;
import br.com.abg.deskgym.session.Constant;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Logger;
import br.com.abg.deskgym.service.SystemConfigurationService;
import br.com.abg.deskgym.utils.DateUtil;
import br.com.abg.deskgym.utils.Validator;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Implementação para o serviço de configuração do sistema.
 * 
 * @author Alvaro
 */
public class SystemConfigurationServiceImpl implements SystemConfigurationService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 1519442327191942492L;

	/**
     * DAO de configuração do sistema.
     */
    private transient SystemConfigurationDAO systemConfigurationDAO;


    /**
     * Serviço de funcionário.
     */
    private transient EmployeeService employeeService;
	
    /**
	 * Log.
	 */
    private transient Logger logger;
    
    /**
     * Construtor.
     */
    public SystemConfigurationServiceImpl() {
        this.systemConfigurationDAO = new SystemConfigurationDAOImpl();
        this.employeeService = new EmployeeServiceImpl();
        this.logger = new Logger();
    }
    
    @Override
    public SystemConfiguration save(final SystemConfiguration systemConfiguration) throws ObjectExistsException, CustomMessageException {
		this.validateNotEmptyFields(systemConfiguration);
		this.validateFieldValues(systemConfiguration);
		this.validateFieldsSize(systemConfiguration);
		this.systemConfigurationDAO.save(systemConfiguration);

		return systemConfiguration;
    }

    @Override
    public void update(final SystemConfiguration systemConfiguration) throws CustomMessageException, ObjectBeingEditedException {
		this.validateNotEmptyFields(systemConfiguration);
		this.validateFieldValues(systemConfiguration);
		this.validateFieldsSize(systemConfiguration);
		this.systemConfigurationDAO.update(systemConfiguration);
    }

    @Override
    public boolean validateLastLogin() {
		final Date lastLogin = this.getSystemConfiguration().getLastLogin();
		return !Validator.isEmpty(lastLogin) && DateUtil.getInterval(lastLogin, new Date()) < 7;
    }

    @Override
    public void updateLastLogin() throws CustomMessageException, ObjectBeingEditedException {
		final SystemConfiguration systemConfiguration = this.getSystemConfiguration();

		if (!Validator.isEmpty(systemConfiguration)) {
			systemConfiguration.setLastLogin(new Date());
			this.update(systemConfiguration);
		}
    }

    @Override
    public String getFreePanelText() {
		return this.getSystemConfiguration().getFreePanelText();
    }

    @Override
    public void backupDataBase() throws InterruptedException, IOException {
		final SystemConfiguration systemConfiguration = this.getSystemConfiguration();

		final String data = DateUtil.convertDateToString(new Date(), true, false);
		final String backupPath = systemConfiguration.getBackupPath() +"Backup(" + data + ").sql";
		final String mysqlPath = systemConfiguration.getMysqlPath();

		Runtime.getRuntime().exec(Constant.gerenateDatabaseBackupCommand(mysqlPath, backupPath)).waitFor();
    }
    
    @Override
    public void verifyFirstSystemAccess() throws ObjectExistsException, CustomMessageException, IOException, FieldInvalidException, ObjectNotFoundException {
		if (Validator.isEmpty(this.getSystemConfiguration())) {
			this.insertConfigurationDefaultValues();
			this.employeeService.createAdminEmployee();
		}
    }
    
    @Override
    public SystemConfiguration getSystemConfiguration() {
		return this.systemConfigurationDAO.getSystemConfiguration();
    }

    @Override
	public boolean alreadyLoggedInToday() {
    	return DateUtil.getInterval(new Date(), this.getSystemConfiguration().getLastLogin()) == 0;
	}

	@Override
	public void updateFreePanelText(final String text) throws CustomMessageException, ObjectBeingEditedException {
    	final SystemConfiguration systemConfiguration = this.getSystemConfiguration();
    	systemConfiguration.setFreePanelText(text);
		this.update(systemConfiguration);
	}

	/**
     * Cria os valores padrões para o primeiro acesso ao sistema.
     */
    private void insertConfigurationDefaultValues() throws ObjectExistsException, CustomMessageException {
		final SystemConfiguration configuration = new SystemConfiguration();

		configuration.setPendingDays(0);
		configuration.setPendingDaysToInactive(0);
		configuration.setInactivityMinutesToLogout(0);
		configuration.setEnableExperimentalClass(false);
		configuration.setCreditCardTax(BigDecimal.ZERO);
		configuration.setDebitCardTax(BigDecimal.ZERO);
		configuration.setRegistrationTax(BigDecimal.ZERO);
		configuration.setExperimentalClassPrice(BigDecimal.ZERO);
		configuration.setHourPrice(BigDecimal.ZERO);
		configuration.setBackupPath("C://");
		configuration.setMysqlPath("C://");
		configuration.setFreePanelText("Primeiro acesso ao sistema!");
		configuration.setLastLogin(new Date());

		this.save(configuration);
    }

	/**
	 * Verifica os campos obrigatórios.
	 *
	 * @param systemConfiguration
	 *
	 * @throws CustomMessageException
	 */
    private void validateNotEmptyFields(final SystemConfiguration systemConfiguration) throws CustomMessageException {
    	final StringBuilder errorMessage = new StringBuilder();
		if (Validator.isEmpty(systemConfiguration)) {
			throw new CustomMessageException("Configurações do sistema " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(systemConfiguration.getPendingDays())) {
			errorMessage.append("Dias de tolerância para mensalidade em atraso " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(systemConfiguration.getPendingDaysToInactive())) {
			errorMessage.append("Dias de tolerância para inativar aluno " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(systemConfiguration.getInactivityMinutesToLogout())) {
			errorMessage.append("Tempo para loggout automático " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(systemConfiguration.isEnableExperimentalClass())) {
			errorMessage.append("Status da aula experimental " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(systemConfiguration.isEnableRegistrationTax())) {
			errorMessage.append("Status da taxa de matrícula " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(systemConfiguration.getCreditCardTax())) {
			errorMessage.append("Taxa para cartão de crédito " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(systemConfiguration.getDebitCardTax())) {
			errorMessage.append("Taxa para cartão de débito " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(systemConfiguration.getRegistrationTax())) {
			errorMessage.append("Taxa de matrícula " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(systemConfiguration.getHourPrice())) {
			errorMessage.append("Valor da hora/aula " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(systemConfiguration.getExperimentalClassPrice())) {
			errorMessage.append("Valor da aula experimental " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(systemConfiguration.getBackupPath())) {
			errorMessage.append("Caminho para o backup do banco de dados " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(systemConfiguration.getMysqlPath())) {
			errorMessage.append("Caminho para o mysql " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
    }

	/**
	 * Valida os valores dos campos
	 *
	 * @param systemConfiguration
	 *
	 * @throws CustomMessageException
	 */
	private void validateFieldValues(final SystemConfiguration systemConfiguration) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (!systemConfiguration.isEnableExperimentalClass()) {
			systemConfiguration.setExperimentalClassPrice(new BigDecimal(0));
		}

		if (systemConfiguration.getPendingDays() < 0 || systemConfiguration.getPendingDays() > 30) {
			errorMessage.append("Dias de tolerância para mensalidade em atraso" + Messages.valueBetweenMessage(0, 30) + "\n");
		}
		if (systemConfiguration.getPendingDaysToInactive() < 0 || systemConfiguration.getPendingDaysToInactive() > 30) {
			errorMessage.append("Dias de tolerância para inativar aluno" + Messages.valueBetweenMessage(0, 30) + "\n");
		}
		if (systemConfiguration.getPendingDays() > systemConfiguration.getPendingDaysToInactive() ) {
			errorMessage.append("Dias de tolerância para mensalidade deve ser menor que dias de tolerância para inativar aluno.\n");
		}
		if (systemConfiguration.getInactivityMinutesToLogout() < 0) {
			errorMessage.append("Tempo para loggout automático" + Messages.valueBiggerMessage(0) + "\n");
		}
		if (systemConfiguration.getCreditCardTax().compareTo(BigDecimal.ZERO) < 0 ||
			systemConfiguration.getCreditCardTax().compareTo(new BigDecimal(100)) > 0) {
			errorMessage.append("Taxa do cartão de crédito" + Messages.valueBetweenMessage(0, 100) + "\n");
		}
		if (systemConfiguration.getDebitCardTax().compareTo(BigDecimal.ZERO) < 0 ||
			systemConfiguration.getDebitCardTax().compareTo(new BigDecimal(100)) > 0) {
			errorMessage.append("Taxa do cartão de débito" + Messages.valueBetweenMessage(0, 30) + "\n");
		}
		if (systemConfiguration.getRegistrationTax().compareTo(BigDecimal.ZERO) < 0) {
			errorMessage.append("Taxa de matrícula" + Messages.valueBiggerMessage(0) + "\n");
		}
		if (systemConfiguration.getHourPrice().compareTo(BigDecimal.ZERO) < 0) {
			errorMessage.append("Valor da hora/aula" + Messages.valueBiggerMessage(0) + "\n");
		}
		if (systemConfiguration.getExperimentalClassPrice().compareTo(BigDecimal.ZERO) < 0) {
			errorMessage.append("Valor da aula experimental" + Messages.valueBiggerMessage(0) + "\n");
		}
		if (!Validator.isEmpty(systemConfiguration.getLastLogin()) &&
			systemConfiguration.getLastLogin().compareTo(new Date()) > 0) {
			errorMessage.append("Último login não pode ser uma data futura.\n");
		}
		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}

	/**
	 * Valida o tamanho dos campos.
	 *
	 * @param systemConfiguration
	 *
	 * @throws CustomMessageException
	 */
	private void validateFieldsSize(final SystemConfiguration systemConfiguration) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();
		if (systemConfiguration.getBackupPath().length() > 255) {
			errorMessage.append("Caminho para o backup do banco de dados" + Messages.characterLengthMessage(255) + "\n");
		}
		if (systemConfiguration.getMysqlPath().length() > 255) {
			errorMessage.append("Caminho para o mysql" + Messages.characterLengthMessage(255) + "\n");
		}
		if (!Validator.isEmpty(systemConfiguration.getFreePanelText()) &&
			systemConfiguration.getFreePanelText().length() > 10000) {
			errorMessage.append("Texto do painel livre" + Messages.characterLengthMessage(10000) + "\n");
		}
		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}
}

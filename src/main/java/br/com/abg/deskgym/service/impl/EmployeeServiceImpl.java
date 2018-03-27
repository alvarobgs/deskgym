package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.EmployeeDAO;
import br.com.abg.deskgym.dao.EmployeePanelTextDAO;
import br.com.abg.deskgym.dao.impl.EmployeeDAOImpl;
import br.com.abg.deskgym.dao.impl.EmployeePanelTextDAOImpl;
import br.com.abg.deskgym.entity.Employee;
import br.com.abg.deskgym.entity.EmployeePanelText;
import br.com.abg.deskgym.entity.User;
import br.com.abg.deskgym.enums.EmployeeStatus;
import br.com.abg.deskgym.enums.Gender;
import br.com.abg.deskgym.enums.LogType;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.AddressService;
import br.com.abg.deskgym.service.ContactService;
import br.com.abg.deskgym.service.EmployeeLogService;
import br.com.abg.deskgym.service.EmployeeService;
import br.com.abg.deskgym.session.LoggedUser;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Validator;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Implementação para o serviço de funcionário.
 * 
 * @author Alvaro
 */
public class EmployeeServiceImpl implements EmployeeService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 6998161543175432254L;

	/**
     * DAO de funcionário.
     */
    private transient EmployeeDAO employeeDAO;

	/**
	 * DAO de texto de funcionário.
	 */
	private transient EmployeePanelTextDAO employeePanelTextDAO;
    
    /**
     * Serviço de endereço.
     */
    private transient AddressService addressService;
    
    /**
     * Serviço de contato.
     */
    private transient ContactService contactService;
    
    /**
     * Log de funcionário.
     */
    private transient EmployeeLogService employeeLogService;
    
    /**
     * Construtor.
     */
    public EmployeeServiceImpl() {
        this.employeeDAO = new EmployeeDAOImpl();
        this.employeePanelTextDAO = new EmployeePanelTextDAOImpl();
		this.addressService = new AddressServiceImpl();
		this.contactService = new ContactServiceImpl();
        this.employeeLogService = new EmployeeLogServiceImpl();
    }

    @Override
    public Employee save(final Employee employee) throws CustomMessageException, IOException, ObjectExistsException, FieldInvalidException, ObjectNotFoundException {
		//VALIDAR CAMPOS EXTRAS DO FUNCIONÁRIO
		this.validateRequiredFields(employee);
		this.validateFieldValues(employee);
		this.validateFieldsSize(employee);
        this.validateUniqueFields(employee);
	
		if (!Validator.isEmpty(employee.getPicture()) && !Validator.isEmpty(employee.getPicture().getBufferedPicture())) {
			employee.getPicture().convertBufferToBytes();
		}
        
        if (!Validator.isEmpty(employee.getAddress())) {
            final String city = employee.getAddress().getCity().getName();
            final String state = employee.getAddress().getCity().getState().getName();
            employee.getAddress().setCity(this.addressService.getCityByNameAndStateName(city, state));
        }
	
		final Employee savedEmployee = this.employeeDAO.save(employee);
                if (!Validator.isEmpty(new LoggedUser().getInstancedEmployee())) {
                this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.SAVE_EMPLOYEE, "ID: " + String.valueOf(savedEmployee.getId()));
                }
		
		return savedEmployee;
    }

    @Override
    public void update(final Employee employee) throws CustomMessageException, IOException, FieldInvalidException, ObjectNotFoundException, ObjectBeingEditedException {
        this.validateRequiredFields(employee);
		this.validateFieldValues(employee);
		this.validateFieldsSize(employee);
        
        if (!Validator.isEmpty(employee.getPicture()) && !Validator.isEmpty(employee.getPicture().getBufferedPicture())) {
	    	employee.getPicture().convertBufferToBytes();
		}
        
        if (!Validator.isEmpty(employee.getAddress())) {
            final String city = employee.getAddress().getCity().getName();
            final String state = employee.getAddress().getCity().getState().getName();
            employee.getAddress().setCity(this.addressService.getCityByNameAndStateName(city, state));
        }
        
        this.employeeDAO.update(employee);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.EDIT_EMPLOYEE, "ID: " + String.valueOf(employee.getId()));
    }

    @Override
    public Employee getById(final Long pk) throws ObjectNotFoundException, FieldInvalidException {
		if (Validator.isEmpty(pk)) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
		return this.employeeDAO.getById(pk);
    }

    @Override
    public void remove(final Employee employee) throws FieldInvalidException, CannotRemoveException {
    	if (Validator.isEmpty(employee)) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, Messages.EMPLOYEE);
		}
		this.employeeDAO.remove(employee);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.REMOVE_EMPLOYEE, "ID: " + String.valueOf(employee.getId()));
    }

    @Override
    public List<Employee> listActiveEmployees() {
	return this.employeeDAO.listActiveEmployees();
    }

    @Override
    public Employee getByName(final String name) throws FieldInvalidException {
	if (Validator.isEmpty(name)){
           throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "Nome");
        }
        
        final List<Employee> employees = this.employeeDAO.getByName(name);
        if (Validator.isEmpty(employees) || employees.size() > 1) {
            return null;
        }
        return employees.get(0);
    }
    
    @Override
    public List<Employee> filterByNameAndStatus(final String name, final EmployeeStatus status) {
        return this.employeeDAO.searchByNameAndStatus(name, status);
    }
    
    @Override
    public List<String> filterNameByNameAndStatus(final String name, final EmployeeStatus status) {
        return this.employeeDAO.searchNameByNameAndStatus(name, status);
    }
    
    @Override
    public void createAdminEmployee() throws CustomMessageException, IOException, FieldInvalidException, ObjectNotFoundException, ObjectExistsException {
		final Employee employee = new Employee();
		final User user = new User();

		employee.setName("Administrador");
		employee.setGender(Gender.MALE);
		employee.setBirthday(new Date());
		employee.setStatus(EmployeeStatus.ACTIVE);
		employee.setAddress(null);
		employee.setContact(null);
		employee.setPicture(null);

		user.setUserName("admin");
		user.setPassword("03175A4E60C74519D434860E0D62029656A2127C65BA4F796DB5A0F6CB7EB7D5");
		user.setEmployee(employee);

		employee.setUser(user);

		this.save(employee);
    }

	@Override
	public void updateEmployeePanelText(final Employee employee, final String text) throws ObjectExistsException, ObjectBeingEditedException {
		EmployeePanelText employeePanelText = this.employeePanelTextDAO.getByEmployeeId(employee.getId());
    	if (Validator.isEmpty(employeePanelText)) {
    		employeePanelText = new EmployeePanelText(employee, text);
			this.employeePanelTextDAO.save(employeePanelText);
		} else {
			employeePanelText.setText(text);
			this.employeePanelTextDAO.update(employeePanelText);
		}
	}

	@Override
	public EmployeePanelText getEmployeePanelText(final Long id) throws FieldInvalidException {
		if (Validator.isEmpty(id)) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
		return this.employeePanelTextDAO.getByEmployeeId(id);
	}

	@Override
	public void updateEmployeePanelText(final Long id, final String text) throws FieldInvalidException, ObjectBeingEditedException {
		if (Validator.isEmpty(id)) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, Messages.EMPLOYEE);
		}
		final EmployeePanelText employeePanelText = this.employeePanelTextDAO.getByEmployeeId(id);
		employeePanelText.setText(text);
		this.employeePanelTextDAO.update(employeePanelText);
	}

	/**
     * Valida os campos obrigatórios.
	 *
	 * @param employee
	 *
	 * @throws
     */
    private void validateRequiredFields(final Employee employee) throws CustomMessageException {
	final StringBuilder errorMessage = new StringBuilder();
        
		if (Validator.isEmpty(employee)){
            throw new CustomMessageException(Messages.EMPLOYEE + " " + Messages.CANNOT_BE_EMPTY + "\n");
        }
        if (Validator.isEmpty(employee.getName())){
            errorMessage.append("Nome  " + Messages.CANNOT_BE_EMPTY + "\n");
        }    

        if (Validator.isEmpty(employee.getGender())){
            errorMessage.append("Sexo " + Messages.CANNOT_BE_EMPTY + "\n");
        }
        if (Validator.isEmpty(employee.getBirthday())){
            errorMessage.append("Data de nascimento " + Messages.CANNOT_BE_EMPTY + "\n");
        }
		if (Validator.isEmpty(employee.getRegistrationDate())){
			errorMessage.append("Data de cadastro no sistema " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(employee.getStatus())){
			errorMessage.append("Status " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (!Validator.isEmpty(employee.getContact())) {
			this.contactService.validateRequiredFields(employee.getContact());
		}
		if (!Validator.isEmpty(employee.getAddress())) {
			this.addressService.validateRequiredFields(employee.getAddress());
		}
        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
    
    /**
     * Valida os valores dos campos
	 *
	 * @param employee
	 *
	 * @throws CustomMessageException
     */
    private void validateFieldValues(final Employee employee) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (employee.getBirthday().compareTo(new Date()) > 0) {
			 errorMessage.append("Data de aniversário deve ser anterior a hoje.");
		}
		if (!Validator.isEmpty(employee.getContact())) {
			this.contactService.validateFieldValues(employee.getContact());
		}
		if (!Validator.isEmpty(employee.getAddress())) {
			this.addressService.validateFieldValues(employee.getAddress());
		}
		if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
    
    /**
     * Valida o tamanho dos campos.
	 *
	 * @param employee
	 *
	 * @throws CustomMessageException
     */
    private void validateFieldsSize(final Employee employee) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (employee.getName().length() > 100){
             errorMessage.append("Nome" + Messages.characterLengthMessage(100) + "\n");
        }
        if (!Validator.isEmpty(employee.getRg()) && employee.getRg().length() > 20){
            errorMessage.append("RG" + Messages.characterLengthMessage(20) + "\n");
        }   
        if (!Validator.isEmpty(employee.getCpf()) && employee.getCpf().length() > 20){
             errorMessage.append("CPF" + Messages.characterLengthMessage(20) + "\n");
        }
		if (!Validator.isEmpty(employee.getContact())) {
			this.contactService.validateFieldSize(employee.getContact());
		}
		if (!Validator.isEmpty(employee.getAddress())) {
			this.addressService.validateFieldSize(employee.getAddress());
		}

		if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
    
    /**
     * Verifica se já existe um funcionário com os campos únicos.
     */
    private void validateUniqueFields(final Employee employee) throws ObjectExistsException {
        if (this.employeeDAO.validateUniqueFields(employee) > 0) {
            throw new ObjectExistsException(Messages.EMPLOYEE + " " + Messages.ALREADY_EXISTS);
        }
    }
}

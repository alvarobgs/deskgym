package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.StudentDAO;
import br.com.abg.deskgym.dao.impl.StudentDAOImpl;
import br.com.abg.deskgym.entity.Student;
import br.com.abg.deskgym.entity.StudentStatusChange;
import br.com.abg.deskgym.entity.SystemConfiguration;
import br.com.abg.deskgym.enums.LogType;
import br.com.abg.deskgym.enums.StudentStatus;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.ContactService;
import br.com.abg.deskgym.service.EmployeeLogService;
import br.com.abg.deskgym.service.StudentService;
import br.com.abg.deskgym.service.StudentStatusChangeService;
import br.com.abg.deskgym.service.SystemConfigurationService;
import br.com.abg.deskgym.session.LoggedUser;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Validator;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Implementação para o serviço de aluno.
 * 
 * @author Alvaro
 */
public class StudentServiceImpl implements StudentService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 2216940080816774265L;

	/**
     * DAO de aluno.
     */
    private transient StudentDAO studentDAO;

	/**
	 * Serviço de endereço.
	 */
	private transient AddressServiceImpl addressService;

	/**
	 * Serviço de contato.
	 */
	private transient ContactService contactService;

	/**
	 * Log do funcionário.
	 */
	private transient EmployeeLogService employeeLogService;

	/**
	 * Serviço de configurações do sistema.
	 */
	private transient SystemConfigurationService systemConfigurationService;

	/**
	 * Serviço de mudança de status do aluno.
	 */
    private transient StudentStatusChangeService studentStatusChangeService;

    /**
     * Construtor.
     */
    public StudentServiceImpl() {
        this.studentDAO = new StudentDAOImpl();
        this.addressService = new AddressServiceImpl();
        this.contactService = new ContactServiceImpl();
        this.employeeLogService = new EmployeeLogServiceImpl();
        this.systemConfigurationService = new SystemConfigurationServiceImpl();
        this.studentStatusChangeService = new StudentStatusChangeServiceImpl();
    }

    @Override
    public Student save(final Student student) throws CustomMessageException, IOException, ObjectNotFoundException, FieldInvalidException, ObjectExistsException {
		this.validateNotEmptyFields(student);
		this.validateFieldValues(student);
		this.validateFieldsSize(student);
        this.validateUniqueFields(student);
        
        if (!Validator.isEmpty(student.getPicture()) && !Validator.isEmpty(student.getPicture().getBufferedPicture())) {
            student.getPicture().convertBufferToBytes();
        }
        if (!Validator.isEmpty(student.getMedicalCertificate()) && !Validator.isEmpty(student.getMedicalCertificate().getBufferedMedicalCertificate())) {
            student.getMedicalCertificate().convertBufferToBytes();
        }
        
        if (!Validator.isEmpty(student.getAddress())) {
            final String city = student.getAddress().getCity().getName();
            final String state = student.getAddress().getCity().getState().getName();
            student.getAddress().setCity(this.addressService.getCityByNameAndStateName(city, state));
        }

		final Student savedStudent = this.studentDAO.save(student);
        this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.SAVE_STUDENT, String.valueOf(savedStudent.getId()));

        return savedStudent;
    }

    @Override
    public void update(final Student student) throws CustomMessageException, IOException, FieldInvalidException, ObjectNotFoundException, ObjectBeingEditedException {
		this.validateNotEmptyFields(student);
		this.validateFieldValues(student);
		this.validateFieldsSize(student);
        
        if (!Validator.isEmpty(student.getPicture()) && !Validator.isEmpty(student.getPicture().getBufferedPicture())) {
            student.getPicture().convertBufferToBytes();
        }
        if (!Validator.isEmpty(student.getMedicalCertificate()) && !Validator.isEmpty(student.getMedicalCertificate().getBufferedMedicalCertificate())) {
            student.getMedicalCertificate().convertBufferToBytes();
        }
        
        if (!Validator.isEmpty(student.getAddress())) {
            final String city = student.getAddress().getCity().getName();
            final String state = student.getAddress().getCity().getState().getName();
            student.getAddress().setCity(this.addressService.getCityByNameAndStateName(city, state));
        }
	
		this.studentDAO.update(student);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.EDIT_STUDENT, String.valueOf(student.getId()));
    }
    
    @Override
    public Student getById(final Long pk) throws ObjectNotFoundException, FieldInvalidException {
		if (Validator.isEmpty(pk)) {
            throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
        }
        return this.studentDAO.getById(pk);
    }   

    @Override
    public void remove(final Student student) throws FieldInvalidException, CannotRemoveException {
		if (Validator.isEmpty(student)) {
            throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, Messages.STUDENT);
        }
        this.studentDAO.remove(student);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.REMOVE_STUDENT, String.valueOf(student.getId()));
    }

    @Override
    public void checkAndUpdateStudentStatus() throws CustomMessageException, IOException, FieldInvalidException, ObjectNotFoundException, ObjectBeingEditedException, ObjectExistsException {
        final SystemConfiguration systemConfiguration = this.systemConfigurationService.getSystemConfiguration();
    	final int daysToGetDelayed = systemConfiguration.getPendingDays();
    	final int daysToGetInactivated = systemConfiguration.getPendingDaysToInactive();
        final List<Student> students = this.studentDAO.listStudentsToCheckStatus();
        
        for (final Student student : students) {
            final StudentStatus oldStatus = student.getStatus();
            student.generateNewStudentStatus(daysToGetDelayed, daysToGetInactivated);

			if (oldStatus != student.getStatus()) {
				final StudentStatusChange stChange = new StudentStatusChange(student, oldStatus);
				this.studentStatusChangeService.save(stChange);
				this.update(student);
			}
        }
    }

    @Override
    public List<Student> listByStatus(final StudentStatus status) throws FieldInvalidException {
		if (Validator.isEmpty(status)){
           throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "Status");
        }
        return this.studentDAO.listByStatus(status);
    }

    @Override
    public List<Student> listActiveStudentsByBirthday() {   
        final Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));       
        final Date initDate = calendar.getTime();
        
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));       
        final Date endDate = calendar.getTime();
        
		return this.studentDAO.listActiveStudentsByBirthday(initDate, endDate);
    }
    
    @Override
    public Student getByName(final String name) throws FieldInvalidException {
		if (Validator.isEmpty(name)){
           throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "Nome");
        }
        
        final List<Student> students = this.studentDAO.getByName(name);
        
        if (Validator.isEmpty(students) || students.size() > 1) {
            return null;
        }
        return students.get(0);
    }
    
    @Override
    public List<Student> filterByNameAndStatus(final String name, final StudentStatus status) {
        return this.studentDAO.searchByNameAndStatus(name, status);
    }
    
    @Override
    public List<String> filterNameByNameAndStatus(final String name, final StudentStatus status) {
        return this.studentDAO.searchNameByNameAndStatus(name, status);
    }
    
    /**
     * Valida os campos obrigatórios.
	 *
	 * @param student
	 *
	 * @throws CustomMessageException
     */
    private void validateNotEmptyFields(final Student student) throws CustomMessageException {
	final StringBuilder errorMessage = new StringBuilder();

		if (Validator.isEmpty(student)) {
			throw new CustomMessageException(Messages.STUDENT + Messages.CANNOT_BE_EMPTY);
		}
        if (Validator.isEmpty(student.getName())){
            errorMessage.append("O campo nome " + Messages.CANNOT_BE_EMPTY + "\n");
        }
        if (Validator.isEmpty(student.getRg())){
           errorMessage.append("O campo RG " + Messages.CANNOT_BE_EMPTY + "\n");
        }           
        if (Validator.isEmpty(student.getCpf())){
            errorMessage.append("O campo CPF " + Messages.CANNOT_BE_EMPTY + "\n");
        }
        if (Validator.isEmpty(student.getGender())){
            errorMessage.append("Sexo " + Messages.CANNOT_BE_EMPTY + "\n");
        }
        if (Validator.isEmpty(student.getBirthday())){
            errorMessage.append("Data de nascimento " + Messages.CANNOT_BE_EMPTY + "\n");
        }
        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
    
    /**
     * Valida os valores dos campos.
	 *
	 * @param student
	 *
	 * @throws CustomMessageException
     */
    private void validateFieldValues(final Student student) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();

        this.contactService.validateFieldValues(student.getContact());
        this.addressService.validateFieldValues(student.getAddress());

        if (student.getBirthday().after(new Date())) {
             errorMessage.append("Data de aniversário deve ser anterior a hoje.");
        }
        if (!Validator.isEmpty(errorMessage.toString())) {
                throw new CustomMessageException(errorMessage.toString());
        }
    }
    
    /**
     * Valida o tamanho dos campos.
	 *
	 * @param student
	 *
	 * @throws CustomMessageException
     */
    private void validateFieldsSize(final Student student) throws CustomMessageException {
	    final StringBuilder errorMessage = new StringBuilder();
	
	    if (student.getName().length() > 100){
             errorMessage.append("Nome" + Messages.characterLengthMessage(100) + "\n");
        }
        if (student.getRg().length() > 20){
            errorMessage.append("RG" + Messages.characterLengthMessage(20) + "\n");
        }   
        if (student.getCpf().length() > 20){
             errorMessage.append("CPF" + Messages.characterLengthMessage(20) + "\n");
        }                        
        if (student.getMedicObservation().length() > 500){
            errorMessage.append("Observação médica" + Messages.characterLengthMessage(500) + "\n");
        }

        this.contactService.validateFieldSize(student.getContact());
       	this.addressService.validateFieldSize(student.getAddress());

	    if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
    
    /**
     * Valida os campos únicos.
	 *
	 * @param student
	 *
	 * @throws ObjectExistsException
     */
    private void validateUniqueFields(final Student student) throws ObjectExistsException {
        if (this.studentDAO.validateUniqueFields(student) > 0) {
            throw new ObjectExistsException(Messages.STUDENT + " " + Messages.ALREADY_EXISTS);
        }
    }
}

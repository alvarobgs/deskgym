package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.ExperimentalClassScheduleDAO;
import br.com.abg.deskgym.dao.impl.ExperimentalClassScheduleDAOImpl;
import br.com.abg.deskgym.entity.ExperimentalClassSchedule;
import br.com.abg.deskgym.enums.LogType;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.EmployeeLogService;
import br.com.abg.deskgym.service.ExperimentalClassScheduleService;
import br.com.abg.deskgym.session.LoggedUser;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.DateUtil;
import br.com.abg.deskgym.utils.Validator;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Implementação para o serviço de agendamento de aula experimental.
 * 
 * @author Alvaro
 */
public class ExperimentalClassScheduleServiceImpl implements ExperimentalClassScheduleService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 93468491972140708L;

	/**
     * DAO de reserva de aula experimental.
     */
    private transient ExperimentalClassScheduleDAO experimentalClassScheduleDAO;

    /**
	 * Log de funcionário.
	 */
	private transient EmployeeLogService employeeLogService;
    
    /**
     * Construtor.
     */
    public ExperimentalClassScheduleServiceImpl() {
        this.experimentalClassScheduleDAO = new ExperimentalClassScheduleDAOImpl();
        this.employeeLogService = new EmployeeLogServiceImpl();
    }
  
    @Override
    public ExperimentalClassSchedule save(final ExperimentalClassSchedule experimentalClassSchedule) throws CustomMessageException, ObjectExistsException {
	    this.validateNotEmptyFields(experimentalClassSchedule);
        this.validateFieldsSize(experimentalClassSchedule);
        this.validateFieldValues(experimentalClassSchedule);
        
        final ExperimentalClassSchedule ecs = this.experimentalClassScheduleDAO.save(experimentalClassSchedule);
        this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.SAVE_EXPERIMENTAL_CLASS_SCHEDULE, "ID: " + String.valueOf(ecs.getId()) + " Aluno: " + ecs.getStudentName());
        return ecs;
    }

    @Override
    public void update(final ExperimentalClassSchedule experimentalClassSchedule) throws CustomMessageException, ObjectBeingEditedException {
		this.validateNotEmptyFields(experimentalClassSchedule);
        this.validateFieldsSize(experimentalClassSchedule);
		this.validateFieldValues(experimentalClassSchedule);
        
        this.experimentalClassScheduleDAO.update(experimentalClassSchedule);
    }

    @Override
    public ExperimentalClassSchedule getById(final Long pk) throws ObjectNotFoundException, FieldInvalidException {
		return this.experimentalClassScheduleDAO.getById(pk);
    }

    @Override
    public void remove(final ExperimentalClassSchedule experimentalClassSchedule) throws FieldInvalidException, CannotRemoveException {
		this.experimentalClassScheduleDAO.remove(experimentalClassSchedule);
    }

    @Override
    public List<ExperimentalClassSchedule> getScheduleClassesByDate(final Date date) {
	    final Date startDate = DateUtil.startDayDate(date);
	    final Date endDate = DateUtil.endDayDate(date);

	    return this.experimentalClassScheduleDAO.getScheduleClassesByDate(startDate, endDate);
    }
    
    /**
     * Verifica os campos obrigatórios o agendamento de aula experimental.
     * 
     * @param experimentalClassSchedule
     * 
     * @throws CustomMessageException
     */
    private void validateNotEmptyFields(final ExperimentalClassSchedule experimentalClassSchedule) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();
        
        if (Validator.isEmpty(experimentalClassSchedule)){
            throw new CustomMessageException("Agendamento da aula experimental " + Messages.CANNOT_BE_EMPTY + "\n");
        }

        if (Validator.isEmpty(experimentalClassSchedule.getStudentName())) {
        	errorMessage.append("Nome " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(experimentalClassSchedule.getDate())) {
			errorMessage.append("Data da aula " + Messages.CANNOT_BE_EMPTY + "\n");
		}
        
        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
    
    /**
     * Verifica o tamanho dos campos para a chave.
     * 
     * @param experimentalClassSchedule
     * 
     * @throws CustomMessageException
     */
    private void validateFieldsSize(final ExperimentalClassSchedule experimentalClassSchedule) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();
        
        if (experimentalClassSchedule.getStudentName().length() < 5 || experimentalClassSchedule.getStudentName().length() > 50){
             errorMessage.append("Nome" + Messages.characterLengthMessage(5, 50) + "\n");
        }
        
        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }

    /**
     * Valida os valores dos campos
     *
     * @param experimentalClassSchedule
     *
     * @throws CustomMessageException
     */
    private void validateFieldValues(final ExperimentalClassSchedule experimentalClassSchedule) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();

        if (experimentalClassSchedule.getDate().compareTo(new Date()) < 0) {
			errorMessage.append("Data para agendamento não pode ser anterior a hoje.\n");
		}

        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
}

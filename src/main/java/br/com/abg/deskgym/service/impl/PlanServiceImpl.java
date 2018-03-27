package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.PlanDAO;
import br.com.abg.deskgym.dao.impl.PlanDAOImpl;
import br.com.abg.deskgym.entity.Plan;
import br.com.abg.deskgym.enums.LogType;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.EmployeeLogService;
import br.com.abg.deskgym.service.PlanService;
import br.com.abg.deskgym.session.LoggedUser;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Converter;
import br.com.abg.deskgym.utils.Validator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação para o serviço de planos.
 * 
 * @author Alvaro
 */
public class PlanServiceImpl implements PlanService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = -2036961383111927532L;

	/**
     * DAO de planos.
     */
    private transient PlanDAO planDAO;

	/**
	 * Log do funcionário.
	 */
	private transient EmployeeLogService employeeLogService;

	/**
     * Construtor.
     */
    public PlanServiceImpl() {
        this.planDAO = new PlanDAOImpl();
        this.employeeLogService = new EmployeeLogServiceImpl();
    }
    
    @Override
    public Plan save(final Plan plan) throws ObjectExistsException, CustomMessageException, FieldInvalidException {
    	this.validateNotEmptyFields(plan);
		this.validateFieldsSize(plan);
		this.validateFieldValues(plan);

		final Plan savedPlan = this.planDAO.save(plan);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(),LogType.SAVE_PLAN,
									 "ID: " + String.valueOf(savedPlan.getId()) + " Nome: " + savedPlan.getName() + " Duração: " +String.valueOf(savedPlan.getDuration()));
		return savedPlan;
    }

    @Override
    public void update(final Plan plan) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException {
    	this.validateNotEmptyFields(plan);
		this.validateFieldsSize(plan);
		this.validateFieldValues(plan);

		this.planDAO.update(plan);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(),LogType.EDIT_PLAN,
									 "ID: " + String.valueOf(plan.getId()) + " Nome: " + plan.getName() + " Duração: " +String.valueOf(plan.getDuration()));
    }

    @Override
    public Plan getById(final Long pk) throws ObjectNotFoundException, FieldInvalidException {
		if (Validator.isEmpty(pk)) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
		return this.planDAO.getById(pk);
    }

    @Override
    public void remove(final Plan plan) throws FieldInvalidException, CannotRemoveException {
		if (Validator.isEmpty(plan.getId())) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
		this.planDAO.remove(plan);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(),LogType.REMOVE_PLAN,
									 "ID: " + String.valueOf(plan.getId()) + " Nome: " + plan.getName() + " Duração: " +String.valueOf(plan.getDuration()));
    }

	@Override
	public void inactive(final Plan plan) {
		//TODO inativa o plano se houver alguma mensalidade usando, ou remove.
	}

	@Override
	public List<Plan> listActivePlans() {
		return new ArrayList<>();
	}

	/**
	 * Verifica os campos obrigatórios.
	 *
	 * @param plan
	 *
	 * @throws CustomMessageException
	 */
	private void validateNotEmptyFields(final Plan plan) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (Validator.isEmpty(plan)) {
			throw new CustomMessageException("Plano " + Messages.CANNOT_BE_EMPTY);
		}

		if (Validator.isEmpty(plan.getName())) {
			errorMessage.append("Nome " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(plan.getDuration())) {
			errorMessage.append("Duração " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (Validator.isEmpty(plan.getPrice())) {
			errorMessage.append("Valor " + Messages.CANNOT_BE_EMPTY + "\n");
		}
		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}

	/**
	 * Valida os valores dos campos
	 *
	 * @param plan
	 *
	 * @throws CustomMessageException
	 */
	private void validateFieldValues(final Plan plan) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (plan.getDuration() < 1) {
			errorMessage.append("Duração" + Messages.valueBiggerMessage(1) + "\n");
		}
		if (plan.getPrice().compareTo(new BigDecimal(0)) < 0) {
			errorMessage.append("Valor" + Messages.valueBiggerMessage(0) + "\n");
		}

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}

	/**
	 * Valida o tamanho dos campos.
	 *
	 * @param plan
	 *
	 * @throws CustomMessageException
	 */
	private void validateFieldsSize(final Plan plan) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (plan.getName().length() > 100) {
			errorMessage.append("Nome" + Messages.characterLengthMessage(100) + "\n");
		}

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}

}

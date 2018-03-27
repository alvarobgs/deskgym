package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.ExtraTaxPaymentDAO;
import br.com.abg.deskgym.dao.impl.ExtraTaxPaymentDAOImpl;
import br.com.abg.deskgym.entity.ExtraTaxPayment;
import br.com.abg.deskgym.enums.LogType;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.EmployeeLogService;
import br.com.abg.deskgym.service.ExtraTaxPaymentService;
import br.com.abg.deskgym.session.LoggedUser;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Validator;

import java.io.Serializable;

/**
 * Implementação para o serviço de taxa de matrícula.
 * 
 * @author Alvaro
 */
public class ExtraTaxPaymentServiceImpl implements ExtraTaxPaymentService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = -570460602049881477L;

	/**
     * DAO de taxa de matrícula.
     */
    private transient ExtraTaxPaymentDAO registrationTaxPaymentDAO;

    /**
     * Log do funcionário.
     */
    private transient EmployeeLogService employeeLogService;
    
    /**
     * Construtor.
     */
    public ExtraTaxPaymentServiceImpl() {
        this.registrationTaxPaymentDAO = new ExtraTaxPaymentDAOImpl();
        this.employeeLogService = new EmployeeLogServiceImpl();
    }
    
    @Override
    public ExtraTaxPayment save(final ExtraTaxPayment extraTaxPayment) throws ObjectExistsException, CustomMessageException, FieldInvalidException {
		this.validateNotEmptyFields(extraTaxPayment);
		this.validateFieldsSize(extraTaxPayment);
		this.validateFieldValues(extraTaxPayment);

		final ExtraTaxPayment etp = this.registrationTaxPaymentDAO.save(extraTaxPayment);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.SAVE_EXTRA_TAX_PAYMENT, "ID: " + String.valueOf(etp.getId()) + " Aluno: " + etp.getStudent().getId());
		return etp;
    }

    @Override
    public void update(final ExtraTaxPayment extraTaxPayment) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException {
		this.validateNotEmptyFields(extraTaxPayment);
		this.validateFieldsSize(extraTaxPayment);
		this.validateFieldValues(extraTaxPayment);

		this.registrationTaxPaymentDAO.update(extraTaxPayment);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.EDIT_EXTRA_TAX_PAYMENT, "ID: " + String.valueOf(extraTaxPayment.getId()) + " Aluno: " + extraTaxPayment.getStudent().getId());
    }

    @Override
    public ExtraTaxPayment getById(final Long pk) throws ObjectNotFoundException, FieldInvalidException {
		if (Validator.isEmpty(pk)) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
		return this.registrationTaxPaymentDAO.getById(pk);
    }

    @Override
    public void remove(final ExtraTaxPayment extraTaxPayment) throws FieldInvalidException, CannotRemoveException {
		if (Validator.isEmpty(extraTaxPayment.getId())) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
    	this.registrationTaxPaymentDAO.remove(extraTaxPayment);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.REMOVE_EXTRA_TAX_PAYMENT, "ID: " + String.valueOf(extraTaxPayment.getId()) + " Aluno: " + extraTaxPayment.getStudent().getId());
    }

    /**
     * Verifica os campos obrigatórios.
     *
     * @param extraTaxPayment
     *
     * @throws CustomMessageException
     */
    private void validateNotEmptyFields(final ExtraTaxPayment extraTaxPayment) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();

        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }

    /**
     * Valida os valores dos campos
     *
     * @param extraTaxPayment
     *
     * @throws CustomMessageException
     */
    private void validateFieldValues(final ExtraTaxPayment extraTaxPayment) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();

        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }

    /**
     * Valida o tamanho dos campos.
     *
     * @param extraTaxPayment
     *
     * @throws CustomMessageException
     */
    private void validateFieldsSize(final ExtraTaxPayment extraTaxPayment) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();

        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
}

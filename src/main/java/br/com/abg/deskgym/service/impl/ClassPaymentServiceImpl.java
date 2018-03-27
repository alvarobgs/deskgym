package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.ClassPaymentDAO;
import br.com.abg.deskgym.dao.impl.ClassPaymentDAOImpl;
import br.com.abg.deskgym.dto.PaymentNearEndDTO;
import br.com.abg.deskgym.entity.ClassPayment;
import br.com.abg.deskgym.entity.ExtraTaxPayment;
import br.com.abg.deskgym.entity.SystemConfiguration;
import br.com.abg.deskgym.enums.LogType;
import br.com.abg.deskgym.enums.PaymentMethod;
import br.com.abg.deskgym.enums.StudentStatus;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.ClassPaymentService;
import br.com.abg.deskgym.service.EmployeeLogService;
import br.com.abg.deskgym.service.ExtraTaxPaymentService;
import br.com.abg.deskgym.service.StudentService;
import br.com.abg.deskgym.service.SystemConfigurationService;
import br.com.abg.deskgym.session.LoggedUser;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.DateUtil;
import br.com.abg.deskgym.utils.Validator;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementação para o serviço de pagamento de mensalidade.
 * 
 * @author Alvaro
 */
public class ClassPaymentServiceImpl implements ClassPaymentService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 4795271952616137068L;

	/**
     * DAO de pagamento de mensalidade.
     */
    private transient ClassPaymentDAO classPaymentDAO;

    /**
     * Serviço de configurações do sistema.
     */
    private transient SystemConfigurationService systemConfigurationService;

	/**
	 * Log de funcionário.
	 */
	private transient EmployeeLogService employeeLogService;

	/**
	 * Serviço de aluno.
	 */
	private StudentService studentService;

	/**
	 * Serviço de pagamento de taxa de matrícula.
	 */
	private ExtraTaxPaymentService registrationTaxPaymentService;
    
    /**
     * Construtor.
     */
    public ClassPaymentServiceImpl() {
        this.classPaymentDAO = new ClassPaymentDAOImpl();
        this.employeeLogService = new EmployeeLogServiceImpl();
        this.studentService = new StudentServiceImpl();
        this.registrationTaxPaymentService = new ExtraTaxPaymentServiceImpl();
        this.systemConfigurationService = new SystemConfigurationServiceImpl();
    }

    @Override
    public ClassPayment save(final ClassPayment classPayment) throws ObjectExistsException, CustomMessageException, FieldInvalidException {
		this.validateNotEmptyFields(classPayment);
		this.validateFieldsSize(classPayment);
		this.validateFieldValues(classPayment);

    	final ClassPayment savedPayment = this.classPaymentDAO.save(classPayment);
    	this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.SAVE_CLASS_PAYMENT, "ID:" + String.valueOf(savedPayment.getId()));
    	return savedPayment;
    }

    @Override
    public void update(final ClassPayment classPayment) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException {
		this.validateNotEmptyFields(classPayment);
		this.validateFieldsSize(classPayment);
		this.validateFieldValues(classPayment);

    	this.classPaymentDAO.update(classPayment);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.EDIT_CLASS_PAYMENT, "ID:" + String.valueOf(classPayment.getId()));
    }

    @Override
    public ClassPayment getById(final Long pk) throws ObjectNotFoundException, FieldInvalidException {
		if (Validator.isEmpty(pk)) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
    	return this.classPaymentDAO.getById(pk);
    }

    @Override
    public void remove(final ClassPayment classPayment) throws FieldInvalidException, CannotRemoveException {
		if (Validator.isEmpty(classPayment.getId())) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
		this.classPaymentDAO.remove(classPayment);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.REMOVE_CLASS_PAYMENT, "ID:" + String.valueOf(classPayment.getId()) + " Aluno: " + String.valueOf(classPayment.getStudent().getId()));
    }

    @Override
    public List<PaymentNearEndDTO> verifyClassPayments() {
        final List<ClassPayment> payments = this.classPaymentDAO.listActivePayments();
        final Set<PaymentNearEndDTO> paymentsNearEnd = new HashSet<>();
 
        if (!Validator.isEmpty(payments)) {
            final int toleranceDays = this.systemConfigurationService.getSystemConfiguration().getPendingDays();
            final List<Long> paymentIdsToSetOutdated = new ArrayList<>();
            final Calendar maxToleranceDate = Calendar.getInstance();
            if (toleranceDays > 0) {
				maxToleranceDate.setTime(DateUtil.addDaysToDate(new Date(), toleranceDays));
			}
            final Calendar nearEndDate = Calendar.getInstance();
            nearEndDate.add(Calendar.DAY_OF_YEAR, 7);
           
            for (final ClassPayment classPayment : payments) {
                if (classPayment.getValidityDate().after(maxToleranceDate.getTime())) {
                    paymentIdsToSetOutdated.add(classPayment.getId());
                } else if (classPayment.getValidityDate().before(nearEndDate.getTime())) {
                    final PaymentNearEndDTO paymentNearEnd = new PaymentNearEndDTO(classPayment.getStudent().getId(),
                                                                  classPayment.getStudent().getName(),
                                                                  DateUtil.getInterval(new Date(), classPayment.getValidityDate()));
					paymentsNearEnd.add(paymentNearEnd);
                }
            }
            
            if (!Validator.isEmpty(paymentIdsToSetOutdated)) {
                this.classPaymentDAO.updateOutdatedClassPaymentsById(paymentIdsToSetOutdated);
            }
        }      
        return new ArrayList<>(paymentsNearEnd);
    }

	@Override
	public void registerNewPayment(final ClassPayment classPayment, final ExtraTaxPayment extraTaxPayment, final BigDecimal creditTax, final BigDecimal debitTax)
			throws ObjectNotFoundException, FieldInvalidException, ObjectBeingEditedException, CustomMessageException, IOException, ObjectExistsException {
    	classPayment.setOutDated(classPayment.getValidityDate().before(new Date()));
		classPayment.setDiscount(this.calculateDiscount(classPayment.getPaymentMethod(), classPayment.getPrice(), creditTax, debitTax));

		if (classPayment.isCashPayment()) {
			classPayment.setPlots(0);
		}

		if (!classPayment.isOutDated() && !classPayment.getStudent().isActive()) {
			classPayment.getStudent().setStatus(StudentStatus.ACTIVE);
			this.studentService.update(classPayment.getStudent());
		}

		if (!Validator.isEmpty(extraTaxPayment)) {
			extraTaxPayment.setStudent(classPayment.getStudent());
			extraTaxPayment.setPaymentDate(classPayment.getPaymentDate());
			extraTaxPayment.setPaymentMethod(classPayment.getPaymentMethod());
			extraTaxPayment.setDiscount(this.calculateDiscount(extraTaxPayment.getPaymentMethod(), extraTaxPayment.getPrice(), creditTax, debitTax));
			//SALVAR TAXA EXTRA
		}
		//VERIFICAR PARA VALIDAR TODOS OS CAMPOS DA TAXA EXTRA PARA QUE NÃO DÊ PROBLEMA DE SALVAR UM E NÃO SALVAR OUTRO
		this.save(classPayment);
	}

	@Override
	public boolean checkActivePaymentByStudent(final Long studentId) {
		return this.classPaymentDAO.checkActivePaymentByStudent(studentId);
	}

	/**
	 * Verifica os campos obrigatórios.
	 *
	 * @param classPayment
	 *
	 * @throws CustomMessageException
	 */
	private void validateNotEmptyFields(final ClassPayment classPayment) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (Validator.isEmpty(classPayment)) {
			throw new CustomMessageException("Mensalidade " + Messages.CANNOT_BE_EMPTY);
		}

		if (Validator.isEmpty(classPayment.getName())) {
			errorMessage.append("Nome do plano " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(classPayment.getPrice())) {
			errorMessage.append("Valor pago " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(classPayment.getPaymentDate())) {
			errorMessage.append("Data do pagamento " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(classPayment.getValidityDate())) {
			errorMessage.append("Data de validade " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(classPayment.getStartDate())) {
			errorMessage.append("Data de início " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(classPayment.getPaymentMethod())) {
			errorMessage.append("Método de pagamento " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(classPayment.getPlots())) {
			errorMessage.append("Número de parcelas " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(classPayment.getDiscount())) {
			errorMessage.append("Desconto " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(classPayment.isOutDated())) {
			errorMessage.append("Estado do plano " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(classPayment.isExperimentalClass())) {
			errorMessage.append("Tipo de plano " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(classPayment.getStudent())) {
			errorMessage.append("Aluno " + Messages.CANNOT_BE_EMPTY);
		}

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}

	/**
	 * Valida os valores dos campos
	 *
	 * @param classPayment
	 *
	 * @throws CustomMessageException
	 */
	private void validateFieldValues(final ClassPayment classPayment) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (classPayment.getPrice().compareTo(new BigDecimal(0)) <= 0) {
			errorMessage.append("Valor pago" + Messages.valueBiggerMessage(0) + ".\n");
		}
		if (classPayment.getStartDate().after(classPayment.getValidityDate())) {
			errorMessage.append("Data de início deve ser anterior a data de validade.\n");
		}
		if (classPayment.getPlots() < 0 || (!classPayment.isCashPayment() && classPayment.getPlots() < 1)) {
			errorMessage.append("Número de parcelas" + Messages.valueBiggerMessage(0) + ".\n");
		}
		if (classPayment.getDiscount().compareTo(new BigDecimal(0)) <= 0) {
			errorMessage.append("Desconto" + Messages.valueBiggerMessage(0) + ".\n");
		}

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}

	/**
	 * Valida o tamanho dos campos.
	 *
	 * @param classPayment
	 *
	 * @throws CustomMessageException
	 */
	private void validateFieldsSize(final ClassPayment classPayment) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (classPayment.getName().length() > 100) {
			errorMessage.append("Nome do plano" + Messages.characterLengthMessage(100) + ".\n");
		}

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}

	/**
	 * Calcula o desconto que será efetuado de acordo com o método de pagamento.
	 */
	private BigDecimal calculateDiscount(final PaymentMethod paymentMethod, final BigDecimal price, final BigDecimal creditTax, final BigDecimal debitTax) {
		switch (paymentMethod) {
			case CREDIT_CARD: return price.multiply(creditTax);
			case DEBIT_CARD: return price.multiply(debitTax);
			default: return new BigDecimal(0);
		}
	}
}

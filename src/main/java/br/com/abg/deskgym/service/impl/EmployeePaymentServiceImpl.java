package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.EmployeePaymentDAO;
import br.com.abg.deskgym.dao.impl.EmployeePaymentDAOImpl;
import br.com.abg.deskgym.entity.EmployeePayment;
import br.com.abg.deskgym.enums.LogType;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.EmployeeLogService;
import br.com.abg.deskgym.service.EmployeePaymentService;
import br.com.abg.deskgym.session.LoggedUser;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Validator;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Implementação para o serviço de pagamento de funcionário.
 * 
 * @author Alvaro
 */
public class EmployeePaymentServiceImpl implements EmployeePaymentService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 5903910540998582942L;

	/**
     * DAO de pagamento de funcionário.
     */
    private transient EmployeePaymentDAO employeePaymentDAO;

	/**
	 * Log de funcionário.
	 */
	private transient EmployeeLogService employeeLogService;
    
    /**
     * Construtor.
     */
    public EmployeePaymentServiceImpl() {
        this.employeePaymentDAO = new EmployeePaymentDAOImpl();
        this.employeeLogService = new EmployeeLogServiceImpl();
    }
    
    @Override
    public EmployeePayment save(final EmployeePayment employeePayment) throws ObjectExistsException, CustomMessageException, FieldInvalidException {
		this.validateNotEmptyFields(employeePayment);
		this.validateFieldsSize(employeePayment);
		this.validateFieldValues(employeePayment);

		final EmployeePayment savedEmployeePayment = this.employeePaymentDAO.save(employeePayment);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.SAVE_EMPLOYEE_PAYMENT, "ID: " + String.valueOf(savedEmployeePayment.getId()));
		return savedEmployeePayment;
    }

    @Override
    public void update(final EmployeePayment employeePayment) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException {
		this.validateNotEmptyFields(employeePayment);
		this.validateFieldsSize(employeePayment);
		this.validateFieldValues(employeePayment);

		this.employeePaymentDAO.update(employeePayment);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.EDIT_EMPLOYEE_PAYMENT, "ID: " + String.valueOf(employeePayment.getId()));
    }

    @Override
    public EmployeePayment getById(final Long pk) throws ObjectNotFoundException, FieldInvalidException {
		if (Validator.isEmpty(pk)) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
		return this.employeePaymentDAO.getById(pk);
    }

    @Override
    public void remove(final EmployeePayment employeePayment) throws FieldInvalidException, CannotRemoveException {
		if (Validator.isEmpty(employeePayment.getId())) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
		this.employeePaymentDAO.remove(employeePayment);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(), LogType.REMOVE_EMPLOYEE_PAYMENT, "ID: " + String.valueOf(employeePayment.getId()) + " Funcionário: " + String.valueOf(employeePayment.getEmployee().getId()));
    }

    @Override
    public void verifyEmployeesPayments() {
//	Verifica direto no banco. 
//	O dia do pagamento já passou de hoje? 
//	Verifica se existe um pagamento realizado neste mês.
//	Verificar um modo de guardar o histórico de meses ativos do funcionário para casos em que o pagamento for realizado dia 30
//PAGAMENTO DE FUNCIONÁRIOS
//         List<String> liFunAtr = new ArrayList<>();
//        List<Funcionario> liFun = funcDado.buscaTodosFuncionariosPorStatus(2);
//        for(int i = 0; i<liFun.size();i++){
//            Pagamento p = pagDado.buscarPagamentoFuncionarioPeriodo(liFun.get(i).getId(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
//            if(p.getId() == -1){
//                LocalDate dataEsperada = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), liFun.get(i).getDiaPagamento());
//                if(dataEsperada.isBefore(LocalDate.now())){
//                    liFunAtr.add(liFun.get(i).getNome());
//                }
//            }
//            
//        }
    }

    /**
     * Verifica os campos obrigatórios.
     *
     * @param employeePayment
     *
     * @throws CustomMessageException
     */
    private void validateNotEmptyFields(final EmployeePayment employeePayment) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();

		if (Validator.isEmpty(employeePayment)) {
			throw new CustomMessageException("Pagamento " + Messages.CANNOT_BE_EMPTY);
		}

		if (Validator.isEmpty(employeePayment.getValue())) {
			errorMessage.append("Valor pago " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(employeePayment.getPaymentDate())) {
			errorMessage.append("Data do pagamento " + Messages.CANNOT_BE_EMPTY);
		}
		if (Validator.isEmpty(employeePayment.isOutDated())) {
			errorMessage.append("Status do pagamento " + Messages.CANNOT_BE_EMPTY);
		}

        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }

    /**
     * Valida os valores dos campos
     *
     * @param employeePayment
     *
     * @throws CustomMessageException
     */
    private void validateFieldValues(final EmployeePayment employeePayment) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();

		if (employeePayment.getValue().compareTo(new BigDecimal(0)) <= 0) {
			errorMessage.append("Valor pago" + Messages.valueBiggerMessage(0) + ".\n");
		}

        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }

    /**
     * Valida o tamanho dos campos.
     *
     * @param employeePayment
     *
     * @throws CustomMessageException
     */
    private void validateFieldsSize(final EmployeePayment employeePayment) throws CustomMessageException {
        return;
    }
}

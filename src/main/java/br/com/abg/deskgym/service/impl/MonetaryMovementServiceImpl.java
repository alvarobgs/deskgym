package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.MonetaryMovementDAO;
import br.com.abg.deskgym.dao.impl.MonetaryMovementDAOImpl;
import br.com.abg.deskgym.entity.MonetaryMovement;
import br.com.abg.deskgym.enums.LogType;
import br.com.abg.deskgym.enums.MonetaryMovementType;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.EmployeeLogService;
import br.com.abg.deskgym.service.MonetaryMovementService;
import br.com.abg.deskgym.session.LoggedUser;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Validator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Implementação para o serviço de ganhos e gastos.
 * 
 * @author Alvaro
 */
public class MonetaryMovementServiceImpl implements MonetaryMovementService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 1323440921918812723L;

	/**
     * DAO de movimentação financeira.
     */
    private transient MonetaryMovementDAO monetaryMovementDAO;

	/**
	 * Log de funcionário.
	 */
	private transient EmployeeLogService employeeLogService;
    
    /**
     * Construtor.
     */
    public MonetaryMovementServiceImpl() {
        this.monetaryMovementDAO = new MonetaryMovementDAOImpl();
        this.employeeLogService = new EmployeeLogServiceImpl();
    }
    
    @Override
    public MonetaryMovement save(final MonetaryMovement monetaryMovement) throws ObjectExistsException, CustomMessageException, FieldInvalidException {
		this.validateNotEmptyFields(monetaryMovement);
		this.validateFieldsSize(monetaryMovement);
		this.validateFieldValues(monetaryMovement);

		final MonetaryMovement mm = this.monetaryMovementDAO.save(monetaryMovement);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(),
									 monetaryMovement.isProfit() ? LogType.SAVE_PROFIT : LogType.SAVE_SPENT,
									 "ID: " + String.valueOf(mm.getId()) + " Desc: " + mm.getName() + " / " + mm.getDescription());
		return mm;
    }

    @Override
    public void update(final MonetaryMovement monetaryMovement) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException {
		this.validateNotEmptyFields(monetaryMovement);
		this.validateFieldsSize(monetaryMovement);
		this.validateFieldValues(monetaryMovement);

		this.monetaryMovementDAO.update(monetaryMovement);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(),
									 monetaryMovement.isProfit() ? LogType.EDIT_PROFIT : LogType.EDIT_SPENT,
									 "ID: " + String.valueOf(monetaryMovement.getId()) + " Desc: " + monetaryMovement.getName() + " / " + monetaryMovement.getDescription());
    }

    @Override
    public MonetaryMovement getById(final Long pk) throws ObjectNotFoundException, FieldInvalidException {
		if (Validator.isEmpty(pk)) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
		return this.monetaryMovementDAO.getById(pk);
    }

    @Override
    public void remove(final MonetaryMovement monetaryMovement) throws FieldInvalidException, CannotRemoveException {
		if (Validator.isEmpty(monetaryMovement.getId())) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
		this.monetaryMovementDAO.remove(monetaryMovement);
		this.employeeLogService.save(new LoggedUser().getInstancedEmployee(),
									 monetaryMovement.isProfit() ? LogType.REMOVE_PROFIT : LogType.REMOVE_SPENT,
									 "ID: " + String.valueOf(monetaryMovement.getId()) + " Desc: " + monetaryMovement.getName() + " / " + monetaryMovement.getDescription());
    }

	@Override
	public List<MonetaryMovement> listByMonthAndYearAndType(final int month, final int year, final MonetaryMovementType type) {
		return null;
	}

	private void checkRequiredWaste() {
    //int mesReferenciaAux = mesReferencia + 1;
//        String msgErro = "";
//        LocalDate dataAux = jDateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        
//        if (String.valueOf(jComboBox3.getSelectedItem()).equals("Selecione...")) {
//            msgErro = msgErro + "Escolha um valor na Caixa de Seleção!\n";
//        }
//        if (jDateChooser1.getDate() == null){
//            msgErro = msgErro + "Selecione uma data para o gasto.\n";
//        }                       
//        if (dataAux.getMonthValue() != mesReferenciaAux || dataAux.getYear() != anoReferencia){
//             msgErro = msgErro +
//                "O mês e ano selecionados no calendário devem ser iguais ao que foram selecionados na busca.\n"
//                    + "Para adicionar valores para outro mês/ano, busque o mês/ano correspondente.\n";
//        }       
//        if(jFormattedTextField1.isEnabled()){
//            try {
//                String valorString = jFormattedTextField1.getText();
//                valorString = valorString.replace(",",".");
//                float valor = Float.parseFloat(valorString);
//                 if(valor < 0){
//                     msgErro = msgErro + "Valor deve ser maior que zero!\n";
//                 }
//            } catch(NumberFormatException ne){
//                   msgErro = msgErro + "Insira apenas números no campo VALOR!\n"
//                                + "Utilize ponto final (.) ao invés de vírgulas (,) para os centavos\n";
//            }
//        } 
//       
//        if(!msgErro.equals("")) {
//            JOptionPane.showMessageDialog(this, msgErro, "ATENÇÃO", JOptionPane.ERROR_MESSAGE);  
//            return false;
//        }
    }
    
    private void checkRequiredProfit() {
    //        int mesReferenciaAux = mesReferencia + 1;
//        String msgErro = "";
//        LocalDate dataAux = jDateChooser2.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        
//        if (String.valueOf(jComboBox4.getSelectedItem()).equals("Selecione...")) {
//            msgErro = msgErro + "Escolha um valor na Caixa de Seleção!\n";
//        } 
//        if (jDateChooser2.getDate() == null){
//            msgErro = msgErro + "Selecione uma data para o ganho.\n";
//        }             
//        if (dataAux.getMonthValue() != mesReferenciaAux || dataAux.getYear() != anoReferencia){
//            msgErro = msgErro + "O mês e ano selecionados no calendário devem ser iguais ao que foram selecionados na busca.\n"
//                    + "Para adicionar valores para outro mês/ano, busque o mês/ano correspondente.\n";
//        }          
//        if(jFormattedTextField2.isEnabled()) {
//            try {
//                String valorString = jFormattedTextField2.getText();
//                valorString = valorString.replace(",",".");
//                float valor = (Float.parseFloat(valorString));
//                if(valor < 0){
//                    msgErro = msgErro + "Valor deve ser maior que zero!";
//                }
//            } catch(NumberFormatException ne){
//                msgErro = msgErro + "Insira apenas números no campo VALOR!\n"
//                                + "Utilize ponto final (.) ao invés de vírgulas (,) para os centavos\n";
//            }        
//        }    
//        


    }

	/**
	 * Verifica os campos obrigatórios.
	 *
	 * @param monetaryMovement
	 *
	 * @throws CustomMessageException
	 */
	private void validateNotEmptyFields(final MonetaryMovement monetaryMovement) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (Validator.isEmpty(monetaryMovement)) {
			throw new CustomMessageException("Movimentação financeira " + Messages.CANNOT_BE_EMPTY);
		}

		if (Validator.isEmpty(monetaryMovement.getName())) {
			errorMessage.append("Nome " + Messages.CANNOT_BE_EMPTY + "\n");
		}

		if (Validator.isEmpty(monetaryMovement.getValue())) {
			errorMessage.append("Valor " + Messages.CANNOT_BE_EMPTY + "\n");
		}

		if (Validator.isEmpty(monetaryMovement.getDate())) {
			errorMessage.append("Data " + Messages.CANNOT_BE_EMPTY_FEMALE + "\n");
		}

		if (Validator.isEmpty(monetaryMovement.getDate())) {
			errorMessage.append("Tipo de movimentação financeira " + Messages.CANNOT_BE_EMPTY + "\n");
		}

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}

	/**
	 * Valida os valores dos campos
	 *
	 * @param monetaryMovement
	 *
	 * @throws CustomMessageException
	 */
	private void validateFieldValues(final MonetaryMovement monetaryMovement) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (monetaryMovement.getValue().compareTo(new BigDecimal(0)) < 0) {
			errorMessage.append("Valor" + Messages.valueBiggerMessage(0) + "\n");
		}

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}

	/**
	 * Valida o tamanho dos campos.
	 *
	 * @param monetaryMovement
	 *
	 * @throws CustomMessageException
	 */
	private void validateFieldsSize(final MonetaryMovement monetaryMovement) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (monetaryMovement.getName().length() > 100){
			errorMessage.append("Nome" + Messages.characterLengthMessage(100) + "\n");
		}

		if (!Validator.isEmpty(monetaryMovement.getDescription()) && monetaryMovement.getDescription().length() > 500){
			errorMessage.append("Descrição" + Messages.characterLengthMessage(500) + "\n");
		}

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}
}

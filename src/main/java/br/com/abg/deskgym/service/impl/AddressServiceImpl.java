package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.CityDAO;
import br.com.abg.deskgym.dao.impl.CityDAOImpl;
import br.com.abg.deskgym.entity.Address;
import br.com.abg.deskgym.entity.City;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.AddressService;
import br.com.abg.deskgym.service.EmployeeLogService;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Validator;
import java.io.Serializable;

/**
 * Implementação para o serviço de endereço.
 * 
 * @author Alvaro
 */
public class AddressServiceImpl implements AddressService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 7179326370550577483L;

	/**
	 * DAO de cidade.
	 */
	private transient CityDAO cityDAO;

	/**
	 * Log de funcionário.
	 */
	private transient EmployeeLogService employeeLogService;

	/**
	 * Construtor.
	 */
	public AddressServiceImpl() {
		this.cityDAO = new CityDAOImpl();
		this.employeeLogService = new EmployeeLogServiceImpl();
	}

	@Override
	public City getCityByNameAndStateName(final String city, final String state) throws ObjectNotFoundException, FieldInvalidException {
		if (Validator.isEmpty(city)) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "Nome da cidade");
		}
		if (Validator.isEmpty(state)) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "Nome do estado");
		}

		return this.cityDAO.getCityByNameAndStateName(city, state);
	}

    @Override
    public void validateRequiredFields(final Address address) throws CustomMessageException {
	    final StringBuilder errorMessage = new StringBuilder();

        if (!Validator.isEmpty(address.getCity())) {
             errorMessage.append("Cidade " + Messages.CANNOT_BE_EMPTY);
        }
	
	    if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
    
    @Override
    public void validateFieldSize(final Address address) throws CustomMessageException {
	    final StringBuilder errorMessage = new StringBuilder();
	
	    if (!Validator.isEmpty(address.getPlace()) && address.getPlace().length() > 100) {
            errorMessage.append("Logradouro" + Messages.characterLengthMessage(100) + "\n");
        } 
        if (!Validator.isEmpty(address.getNumber()) && address.getNumber().length() > 20) {
             errorMessage.append("Número da residência" + Messages.characterLengthMessage(20) + "\n");
        }
        if (!Validator.isEmpty(address.getComplement()) && address.getComplement().length() > 100) {
             errorMessage.append("Complemento do endereço" + Messages.characterLengthMessage(100) + "\n");
        }
        if (!Validator.isEmpty(address.getNeighborhood()) && address.getNeighborhood().length() > 100) {
             errorMessage.append("Bairro" + Messages.characterLengthMessage(100) + "\n");
        }
        if (!Validator.isEmpty(address.getZipCode()) && address.getZipCode().length() > 9) {
             errorMessage.append("CEP" + Messages.characterLengthMessage(9) + "\n");
        }
	    if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
    
    @Override
    public void validateFieldValues(final Address address) throws CustomMessageException {
	    return;
    }

    /**
     * Verifica os campos obrigatórios.
     *
     * @param city
     *
     * @throws CustomMessageException
     */
    private void validateCityNotEmptyFields(final City city) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();

        if (Validator.isEmpty(city)) {
        	throw new CustomMessageException("Cidade " + Messages.CANNOT_BE_EMPTY_FEMALE);
		}

		if (Validator.isEmpty(city.getName())) {
			errorMessage.append("Nome da cidade " + Messages.CANNOT_BE_EMPTY + ".\n");
		}
		if (Validator.isEmpty(city.getState())) {
			errorMessage.append("Estado " + Messages.CANNOT_BE_EMPTY + ".\n");
		} else {
        	if (Validator.isEmpty(city.getState().getName())) {
				errorMessage.append("Nome do estado " + Messages.CANNOT_BE_EMPTY + ".\n");
			}
			if (Validator.isEmpty(city.getState().getUf())) {
				errorMessage.append("UF do estado " + Messages.CANNOT_BE_EMPTY + ".\n");
			}
			if (Validator.isEmpty(city.getState().getCountry())) {
				errorMessage.append("País " + Messages.CANNOT_BE_EMPTY + ".\n");
			} else {
				if (Validator.isEmpty(city.getState().getCountry().getName())) {
					errorMessage.append("Nome do país " + Messages.CANNOT_BE_EMPTY + ".\n");
				}
				if (Validator.isEmpty(city.getState().getCountry().getPhoneCode())) {
					errorMessage.append("Código telefônico do país " + Messages.CANNOT_BE_EMPTY + ".\n");
				}
				if (Validator.isEmpty(city.getState().getCountry().getName())) {
					errorMessage.append("Silga do país " + Messages.CANNOT_BE_EMPTY + ".\n");
				}
			}
		}

        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }

    /**
     * Valida os valores dos campos
     *
     * @param city
     *
     * @throws CustomMessageException
     */
    private void validateCityFieldValues(final City city) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (city.getState().getCountry().getPhoneCode() <= 0) {
			errorMessage.append("Código telefônico do país" + Messages.valueBiggerMessage(0) + ".\n");
		}

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
    }

    /**
     * Valida o tamanho dos campos.
     *
     * @param city
     *
     * @throws CustomMessageException
     */
    private void validateCityFieldsSize(final City city) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();

        if (city.getName().length() > 50) {
			errorMessage.append("Nome da cidade" + Messages.characterLengthMessage(50) + ".\n");
		}
		if (city.getState().getName().length() > 50) {
			errorMessage.append("Nome do estado" + Messages.characterLengthMessage(50) + ".\n");
		}
		if (city.getState().getUf().length() > 10) {
			errorMessage.append("UF do estado" + Messages.characterLengthMessage(10) + ".\n");
		}
		if (city.getState().getCountry().getName().length() > 50) {
			errorMessage.append("Nome do país" + Messages.characterLengthMessage(50) + ".\n");
		}
		if (city.getState().getCountry().getAbbreviation().length() > 10) {
			errorMessage.append("Sigla do país" + Messages.characterLengthMessage(10) + ".\n");
		}

        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
}

package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.PermissionDAO;
import br.com.abg.deskgym.dao.impl.PermissionDAOImpl;
import br.com.abg.deskgym.entity.Permission;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.EmployeeLogService;
import br.com.abg.deskgym.service.PermissionService;
import br.com.abg.deskgym.utils.Validator;

import java.io.Serializable;

/**
 * Implementação para o serviço de permissão.
 * 
 * @author Alvaro
 */
public class PermissionServiceImpl implements PermissionService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 8172106663355420412L;

	/**
     * DAO de permissão.
     */
    private transient PermissionDAO permissionDAO;

	/**
	 * Log de funcionário.
	 */
	private transient EmployeeLogService employeeLogService;

	/**
     * Construtor.
     */
    public PermissionServiceImpl() {
        this.permissionDAO = new PermissionDAOImpl();
        this.employeeLogService = new EmployeeLogServiceImpl();
    }
    
    @Override
    public Permission save(final Permission permission) throws ObjectExistsException, CustomMessageException, FieldInvalidException {
		this.validateNotEmptyFields(permission);
		this.validateFieldsSize(permission);
		this.validateFieldValues(permission);

		return this.permissionDAO.save(permission);
    }

    @Override
    public void update(final Permission permission) throws CustomMessageException, ObjectBeingEditedException, FieldInvalidException {
		this.validateNotEmptyFields(permission);
		this.validateFieldsSize(permission);
		this.validateFieldValues(permission);

		this.permissionDAO.update(permission);
    }

    @Override
    public Permission getById(final Long pk) throws ObjectNotFoundException, FieldInvalidException {
		return this.permissionDAO.getById(pk);
    }

    @Override
    public void remove(final Permission permission) throws FieldInvalidException, CannotRemoveException {
		this.permissionDAO.remove(permission);
    }

	/**
	 * Verifica os campos obrigatórios.
	 *
	 * @param permission
	 *
	 * @throws CustomMessageException
	 */
	private void validateNotEmptyFields(final Permission permission) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}

	/**
	 * Valida os valores dos campos
	 *
	 * @param permission
	 *
	 * @throws CustomMessageException
	 */
	private void validateFieldValues(final Permission permission) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}

	/**
	 * Valida o tamanho dos campos.
	 *
	 * @param permission
	 *
	 * @throws CustomMessageException
	 */
	private void validateFieldsSize(final Permission permission) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}
}

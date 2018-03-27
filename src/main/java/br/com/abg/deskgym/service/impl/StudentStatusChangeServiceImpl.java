package br.com.abg.deskgym.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.abg.deskgym.dao.StudentStatusChangeDAO;
import br.com.abg.deskgym.dao.impl.StudentStatusChangeDAOImpl;
import br.com.abg.deskgym.entity.StudentStatusChange;
import br.com.abg.deskgym.enums.StudentStatus;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.service.StudentStatusChangeService;
import br.com.abg.deskgym.utils.Validator;

/**
 * Implementação para o serviço de usuário.
 * 
 * @author Alvaro
 */
public class StudentStatusChangeServiceImpl implements StudentStatusChangeService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 9190660513542373103L;

	/**
     * DAO de mudança de status do aluno.
     */
    private transient StudentStatusChangeDAO studentStatusChangeDAO;

    /**
     * Construtor.
     */
    public StudentStatusChangeServiceImpl() {
		this.studentStatusChangeDAO = new StudentStatusChangeDAOImpl();
    }
    
    @Override
    public void save(final StudentStatusChange studentStatusChange) throws ObjectExistsException, CustomMessageException, FieldInvalidException {
		this.validateNotEmptyFields(studentStatusChange);
		this.studentStatusChangeDAO.save(studentStatusChange);
    }

	@Override
	public List<StudentStatusChange> listByDate(final Date date) {
		return this.studentStatusChangeDAO.listByDate(date);
	}

	@Override
	public List<StudentStatusChange> listNotActivesByDate(final Date date) {
		return this.studentStatusChangeDAO.listNotActivesByDate(date);
	}

	@Override
	public List<StudentStatusChange> listByDateAndStatus(final Date date, final StudentStatus studentStatus) {
		return this.studentStatusChangeDAO.listByDateAndStatus(date, studentStatus);
	}

	/**
	 * Verifica os campos obrigatórios.
	 *
	 * @param studentStatusChange
	 *
	 * @throws CustomMessageException
	 */
	private void validateNotEmptyFields(final StudentStatusChange studentStatusChange) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}
}

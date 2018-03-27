package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.dao.KeyDAO;
import br.com.abg.deskgym.dao.impl.KeyDAOImpl;
import br.com.abg.deskgym.entity.Key;
import br.com.abg.deskgym.enums.Gender;
import br.com.abg.deskgym.exceptions.CannotRemoveException;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.exceptions.FieldInvalidException;
import br.com.abg.deskgym.exceptions.ObjectBeingEditedException;
import br.com.abg.deskgym.exceptions.ObjectExistsException;
import br.com.abg.deskgym.exceptions.ObjectNotFoundException;
import br.com.abg.deskgym.service.KeyService;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Validator;
import java.io.Serializable;
import java.util.List;

/**
 * Implementação para o serviço de funcionário.
 * 
 * @author Alvaro
 */
public class KeyServiceImpl implements KeyService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 309857349507353410L;

	/**
     * DAO de chaves.
     */
    private transient KeyDAO keyDAO;
    
    /**
     * Construtor.
     */
    public KeyServiceImpl() {
        this.keyDAO = new KeyDAOImpl();
    }
    
    @Override
    public Key save(final Key key) throws CustomMessageException, ObjectExistsException {
        this.validateNotEmptyFields(key);
        this.validateFieldsSize(key);
        this.validateFieldValues(key);
        
        return this.keyDAO.save(key);
    }

    @Override
    public void update(final Key key) throws CustomMessageException, ObjectBeingEditedException {
		this.validateNotEmptyFields(key);
        this.validateFieldsSize(key);
		this.validateFieldValues(key);
        
        this.keyDAO.update(key);
    }

    @Override
    public Key getById(final Long pk) throws ObjectNotFoundException, FieldInvalidException {
		if (Validator.isEmpty(pk)) {
			throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
		}
		return this.keyDAO.getById(pk);
    }

    @Override
    public void remove(final Key key) throws FieldInvalidException, CannotRemoveException {
        if (Validator.isEmpty(key.getId())) {
            throw new FieldInvalidException(Messages.CANNOT_BE_EMPTY, "ID");
        }
		this.keyDAO.remove(key);
    }

    @Override
    public List<Key> listByGender(final Gender gender) {
        return this.keyDAO.listByGender(gender);
    }
    
    @Override
    public void saveOrUpdateKey(Key key) throws CustomMessageException, ObjectExistsException, ObjectBeingEditedException {
        final Key keyAux = this.keyDAO.getByNumberAndGender(key.getNumber(), key.getGender());
        
        if (Validator.isEmpty(keyAux)) {
            this.save(key);
        } else {
            keyAux.setStudentName(key.getStudentName());
            this.update(keyAux);
        }
    }
   
    /**
     * Verifica os campos obrigatórios para a chave.
     * 
     * @param key
     * 
     * @throws CustomMessageException
     */
    private void validateNotEmptyFields(final Key key) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();
        
        if (Validator.isEmpty(key.getStudentName())){
            errorMessage.append("Nome do aluno " + Messages.CANNOT_BE_EMPTY + "\n");
        }
        if (Validator.isEmpty(key.getNumber())){
           errorMessage.append("Número da chave " + Messages.CANNOT_BE_EMPTY + "\n");
        }           
        if (Validator.isEmpty(key.getGender())){
            errorMessage.append("Sexo " + Messages.CANNOT_BE_EMPTY + "\n");
        } 
        
        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
  
    /**
     * Verifica o tamanho dos campos para a chave.
     * 
     * @param key
     * 
     * @throws CustomMessageException
     */
    private void validateFieldsSize(final Key key) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();
        
        if (key.getStudentName().length() < 5 || key.getStudentName().length() > 50){
             errorMessage.append("Nome" + Messages.characterLengthMessage(5, 50) + "\n");
        }
        
        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }

    /**
     * Valida os valores dos campos
     *
     * @param key
     *
     * @throws CustomMessageException
     */
    private void validateFieldValues(final Key key) throws CustomMessageException {
        final StringBuilder errorMessage = new StringBuilder();

        if (key.getNumber() < 0) {
        	errorMessage.append("Número da chave" + Messages.valueBiggerMessage(0) + "\n");
		}

        if (!Validator.isEmpty(errorMessage.toString())) {
            throw new CustomMessageException(errorMessage.toString());
        }
    }
}

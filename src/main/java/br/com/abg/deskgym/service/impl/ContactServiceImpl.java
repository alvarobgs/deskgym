package br.com.abg.deskgym.service.impl;

import br.com.abg.deskgym.entity.Contact;
import br.com.abg.deskgym.exceptions.CustomMessageException;
import br.com.abg.deskgym.service.ContactService;
import br.com.abg.deskgym.session.Messages;
import br.com.abg.deskgym.utils.Validator;
import java.io.Serializable;

/**
 * Implementação para o serviço de contato.
 * 
 * @author Alvaro
 */
public class ContactServiceImpl implements ContactService, Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = -512199744801245338L;

	@Override
    public void validateRequiredFields(final Contact contact) throws CustomMessageException {
		return;
    }
    
    @Override
    public void validateFieldSize(final Contact contact) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (!Validator.isEmpty(contact.getCellPhone()) && contact.getCellPhone().length() > 12) {
			errorMessage.append("Celular" + Messages.characterLengthMessage(12) + "\n");
		}
		if (!Validator.isEmpty(contact.getTelephone()) && contact.getTelephone().length() > 12) {
			errorMessage.append("Telefone fixo" + Messages.characterLengthMessage(12) + "\n");
		}
		if (!Validator.isEmpty(contact.getMail()) && contact.getMail().length() > 100) {
			errorMessage.append("E-mail" + Messages.characterLengthMessage(100) + "\n");
		}

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
    }
    
    @Override
    public void validateFieldValues(final Contact contact) throws CustomMessageException {
		final StringBuilder errorMessage = new StringBuilder();

		if (!Validator.isEmpty(contact.getCellPhone()) &&
				(contact.getCellPhone().length() < 12 ||
				contact.getCellPhone().charAt(0) != '(' ||
				contact.getCellPhone().charAt(3) != ')')) {
			   errorMessage.append("Celular deve estar no padrão (**)*****-****.\n");
		}
		if (!Validator.isEmpty(contact.getTelephone()) &&
			(contact.getTelephone().length() < 12 ||
			contact.getTelephone().charAt(0) != '(' ||
			contact.getTelephone().charAt(3) != ')')) {
			errorMessage.append("Telefone fixo deve estar no padrão (**)****-****.\n");
		}
		if (!Validator.isEmpty(contact.getMail()) &&
				(!contact.getMail().contains("@") ||
				!contact.getMail().contains("."))) {
			errorMessage.append("E-mail deve estar no padrão pessoa@mail.com\n");
		}

		if (!Validator.isEmpty(errorMessage.toString())) {
			throw new CustomMessageException(errorMessage.toString());
		}
	}
}

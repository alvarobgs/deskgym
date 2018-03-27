package br.com.abg.deskgym.exceptions;

import br.com.abg.deskgym.session.Messages;

/**
 * Exceção de campo inválido.
 * 
 * @author Alvaro
 */
public class FieldInvalidException extends Exception {
    
    /**
     * Nova exception com mensagem.
     */
    public FieldInvalidException(final String msg) {
       super(msg);
    }

    /**
     * Nova exception com mensagem e causa.
     */
    public FieldInvalidException(final String msg, final Throwable cause){
        super(msg, cause);
    }
    
    /**
     * Nova exception com mensagem e campo.
     */
    public FieldInvalidException(final String msg, final String field){
        super("O campo " + field + " " + msg);
    }
}

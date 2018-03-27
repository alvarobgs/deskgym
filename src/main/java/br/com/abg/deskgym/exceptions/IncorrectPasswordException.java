package br.com.abg.deskgym.exceptions;

/**
 * Exceção de senha incorreta.
 * 
 * @author Alvaro
 */
public class IncorrectPasswordException extends Exception {
    
    /**
     * Nova exception com mensagem.
     */
    public IncorrectPasswordException(final String msg){
        super(msg);
    }

    /**
     * Nova exception com mensagem e causa.
     */
    public IncorrectPasswordException(final String msg, final Throwable cause){
        super(msg, cause);
    }
    
}

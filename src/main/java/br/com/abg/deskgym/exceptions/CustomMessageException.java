package br.com.abg.deskgym.exceptions;

/**
 * Exceção com mensagem personalizada.
 * 
 * @author Alvaro
 */
public class CustomMessageException extends Exception {
    
    /**
     * Nova exception com mensagem.
     */
    public CustomMessageException(final String msg){
        super(msg);
    }

    /**
     * Nova exception com mensagem e causa.
     */
    public CustomMessageException(final String msg, final Throwable cause){
        super(msg, cause);
    }
}

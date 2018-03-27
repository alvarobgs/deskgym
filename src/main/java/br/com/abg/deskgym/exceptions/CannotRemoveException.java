package br.com.abg.deskgym.exceptions;

/**
 * Exceção de objeto não pôde ser removido.
 * 
 * @author Alvaro
 */
public class CannotRemoveException extends Exception {
    
    /**
     * Nova exception com mensagem.
     */
    public CannotRemoveException(final String msg){
        super(msg);
    }

    /**
     * Nova exception com mensagem e causa.
     */
    public CannotRemoveException(final String msg, final Throwable cause){
        super(msg, cause);
    }
}

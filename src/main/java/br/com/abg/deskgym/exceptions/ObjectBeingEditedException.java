package br.com.abg.deskgym.exceptions;

/**
 * Exceção de objeto já sendo editado.
 * 
 * @author Alvaro
 */
public class ObjectBeingEditedException extends Exception {
    
    /**
     * Nova exception com mensagem.
     */
    public ObjectBeingEditedException(final String msg){
        super(msg);
    }

    /**
     * Nova exception com mensagem e causa.
     */
    public ObjectBeingEditedException(final String msg, final Throwable cause){
        super(msg, cause);
    }
}

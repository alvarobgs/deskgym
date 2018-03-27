package br.com.abg.deskgym.exceptions;

/**
 * Exceção de objeto já existente no banco de dados.
 * 
 * @author Alvaro
 */
public class ObjectExistsException extends Exception {
    
    /**
     * Nova exception com mensagem.
     */
    public ObjectExistsException(final String msg){
        super(msg);
    }

    /**
     * Nova exception com mensagem e causa.
     */
    public ObjectExistsException(final String msg, final Throwable cause){
        super(msg, cause);
    }
}

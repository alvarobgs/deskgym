package br.com.abg.deskgym.exceptions;

/**
 * Exceção de objeto já existente no banco de dados.
 * 
 * @author Alvaro
 */
public class ObjectNotFoundException extends Exception {
    
    /**
     * Nova exception com mensagem.
     */
    public ObjectNotFoundException(final String msg){
        super(msg);
    }

    /**
     * Nova exception com mensagem e causa.
     */
    public ObjectNotFoundException(final String msg, final Throwable cause){
        super(msg, cause);
    }
}

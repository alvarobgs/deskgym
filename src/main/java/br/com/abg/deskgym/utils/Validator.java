package br.com.abg.deskgym.utils;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe para validação
 * 
 * @author Alvaro
 */
public class Validator {
    
    /**
     * Valida se a data está nula.
     */
    public static boolean isEmpty(final LocalDate localDate) {
	return localDate == null;
    }
    
    /**
     * Valida se a string está vazia.
     */
    public static boolean isEmpty(final String string) {
	return string == null || string.equals("");
    }
    
    /**
     * Valida se uma lista está vazia.
     */
    public static boolean isEmpty(final List<?> list) {
	return list == null || list.isEmpty();
    }
    
    /**
     * Valida se um objeto está vazio.
     */
    public static boolean isEmpty(final Object object) {
	return object == null;
    }
    
    /**
     * Valida se a string está nula.
     */
    public static boolean isNull(final String string) {
	return string == null;
    }
    
}

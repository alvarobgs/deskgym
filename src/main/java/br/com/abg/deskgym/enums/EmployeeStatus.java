package br.com.abg.deskgym.enums;

/**
 * Enum para sexo.
 * 
 * @author Alvaro
 */
public enum EmployeeStatus {

    /**
     * Ativo.
     */
    ACTIVE("Ativo"),

    /**
     * Inativo.
     */
    INACTIVE("Inativo");

    /**
     * Enum em string.
     */
    private final String status;
    
    /**
     * Construtor com o parâmetro.
     */
    private EmployeeStatus(final String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return this.status;
    }
}

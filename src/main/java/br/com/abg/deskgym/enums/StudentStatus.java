package br.com.abg.deskgym.enums;

/**
 * Enum para status do aluno.
 * 
 * @author Alvaro
 */
public enum StudentStatus {
    
    /**
     * Ativo, mensalidade paga em dia.
     */
    ACTIVE("Ativo"),
    
    /**
     * Pendente, mensalidade paga mas no período de carência.
     */
    PENDING("Período de carência"),
    
    /**
     * Atrasado, última mensalidade ativa já venceu.
     */
    DELAYED("Mensalidade em atraso"),
    
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
    private StudentStatus(final String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return this.status;
    }
}

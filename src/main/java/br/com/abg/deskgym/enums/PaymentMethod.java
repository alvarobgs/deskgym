package br.com.abg.deskgym.enums;

/**
 * Enum para forma de pagamento.
 * 
 * @author Alvaro
 */
public enum PaymentMethod {

    /**
     * Dinheiro.
     */
    CASH("Dinheiro"),

    /**
     * Cartão de débito.
     */
    DEBIT_CARD("Cartão de Débito"),

    /**
     * Cartão de crédito.
     */
    CREDIT_CARD("Cartão de Crédito");

    /**
     * Enum em string.
     */
    private final String method;
    
    /**
     * Construtor com o parâmetro.
     */
    private PaymentMethod(final String method) {
        this.method = method;
    }
    
    @Override
    public String toString() {
        return this.method;
    }

    /**
     * Retorna o enum de acordo com o valor em string
     *
     * @param paymentMethodString
     * @return
     */
    public static PaymentMethod toEnum(final String paymentMethodString) {
        switch (paymentMethodString) {
            case "Dinheiro":
                return PaymentMethod.CASH;
            case "Cartão de Débito":
                return PaymentMethod.DEBIT_CARD;
            case "Cartão de Crédito":
                return PaymentMethod.CREDIT_CARD;
            default:
                return null;
        }
    }
}

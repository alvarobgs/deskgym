package br.com.abg.deskgym.enums;

/**
 * Enum para tipo de movimentação financeira.
 * 
 * @author Alvaro
 */
public enum MonetaryMovementType {

	/**
	 * Ganho.
	 */
	PROFIT("Ganho"),

	/**
	 * Gasto.
	 */
	SPENT("Gasto");

    /**
     * Enum em string.
     */
    private final String type;
    
    /**
     * Construtor com o parâmetro.
     */
    private MonetaryMovementType(final String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return this.type;
    }

    /**
     * Retorna o enum de acordo com o valor em string
     *
     * @param monetaryTypeString
     * @return
     */
    public static MonetaryMovementType toEnum(final String monetaryTypeString) {
        switch (monetaryTypeString) {
            case "Ganho":
                return MonetaryMovementType.PROFIT;
            case "Gasto":
                return MonetaryMovementType.SPENT;
            default:
                return null;
        }
    }
}

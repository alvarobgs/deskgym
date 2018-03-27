package br.com.abg.deskgym.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * DTO para configurações do sistema.
 * 
 * @author alvaro
 */
public class SystemConfigurationDTO {

    /**
     * Dias de tolerância para mensalidade em atraso.
     */
    @Getter
    @Setter
    private String pendingDaysString;

    /**
     * Dias de tolerância até ser automaticamente inativado.
     */
    @Getter
    @Setter
    private String pendingDaysToInactiveString;

    /**
     * Minutos de inatividade para ser kickado do sistema.
     */
    @Getter
    @Setter
    private String inactivityMinutesToLogoutString;

	/**
	 * Taxa do cartão de crédito.
	 */
    @Getter
    @Setter
    private String creditCardTaxString;

	/**
	 * Taxa do cartão de débito.
	 */
    @Getter
    @Setter
    private String debitCardTaxString;

	/**
	 * Valor da taxa de matrícula.
	 */
    @Getter
    @Setter
    private String registrationTaxString;

	/**
	 * Valor da hora aula do professor.
	 */
    @Getter
    @Setter
    private String hourPriceString;

}

package br.com.abg.deskgym.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para planos.
 * 
 * @author alvaro
 */
public class PlanDTO {
    
    /**
     * Nome do plano.
     */
    @Getter
    @Setter
    private String name;
    
    /**
     * Valor.
     */
    @Getter
    @Setter
    private String value;
    
    /**
     * Duração.
     */
    @Getter
    @Setter
    private String duration;
}

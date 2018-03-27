package br.com.abg.deskgym.dto;

import java.util.Comparator;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para pagamento próximo do final.
 * 
 * @author alvaro
 */
public class PaymentNearEndDTO {
    
    /**
     * ID do aluno.
     */
    @Getter
    @Setter
    private Long studentId;
    
    /**
     * Nome do aluno.
     */
    @Getter
    @Setter
    private String studentName;
    
    /**
     * Dias restantes até o vencimento.
     */
    @Getter
    @Setter
    private int daysToEnd;
    
    /**
     * Dias restantes até o vencimento em String.
     */
    @Getter
    @Setter
    private String daysToEndString;
    
    /**
     * Construtor.
     */
    public PaymentNearEndDTO() {}
    
    /**
     * Construtor sobrescrito.
     * 
     * @param id
     * @param name
     * @param days
     * 
     */
    public PaymentNearEndDTO(final Long id, final String name, final int days) {
        this.studentId = id;
        this.studentName = name;
        this.daysToEnd = days;
	switch (days) {
	    case 0:
		this.daysToEndString = "HOJE";
		break;
	    case 1:
		this.daysToEndString = "AMANHÃ";
		break;
	    default:
		this.daysToEndString = String.valueOf(days);
	}
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.studentId);
        hash = 41 * hash + Objects.hashCode(this.studentName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PaymentNearEndDTO other = (PaymentNearEndDTO) obj;
        if (!Objects.equals(this.studentId, other.studentId)) {
            return false;
        }
        if (!Objects.equals(this.studentName, other.studentName)) {
            return false;
        }
        return true;
    }
    
    /**
     * Compara por dias restantes até o vencimento.
     */
    public static Comparator<PaymentNearEndDTO> daysNearEndComparator = new Comparator<PaymentNearEndDTO>(){
        public int compare(PaymentNearEndDTO paymentOne, PaymentNearEndDTO paymentTwo){
            return paymentOne.getDaysToEnd() - paymentTwo.getDaysToEnd();      
        }
    };
}

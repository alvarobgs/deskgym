package br.com.abg.deskgym.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade de pagamento de funcionário.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "employee_payment")
public class EmployeePayment extends AbstractEntity implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 6431283973132309980L;

    /**
     * Constante para chave estrangeira.
     */
    private static transient final String FK = "fk_employee_payment_";

    /**
     * Valor pago.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "value", nullable = false, precision = 10, scale = 2)
    private BigDecimal value;
    
    /**
     * Data que o pagamento foi realizado.
     */
    @Getter
    @Setter
    @NotEmpty
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;
        
    /**
     * Obsoleto.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "outDated", nullable = false)
    private boolean outDated;
    
    /**
     * Funcionário.
     */
    @Getter
    @Setter
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = FK + "employee"), nullable = false)
    private Employee employee;
    
}

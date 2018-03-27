package br.com.abg.deskgym.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade de parcela.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "plot")
public class Plot extends AbstractEntity implements Serializable {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -4113369226664907575L;

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
}

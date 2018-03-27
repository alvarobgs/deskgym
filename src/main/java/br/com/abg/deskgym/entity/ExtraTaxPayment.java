package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.enums.PaymentMethod;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade de pagamento de taxa extra pelo aluno.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "extra_tax_payment")
public class ExtraTaxPayment extends AbstractEntity implements Serializable {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 4892523989333634257L;

    /**
     * Valor pago.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
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
     * Descontos.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "discount", nullable = false, precision = 10, scale = 2)
    private BigDecimal discount;
    
    /**
     * Método de pagamento.
     */
    @Getter
    @Setter
    @NotEmpty
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    /**
     * Descrição.
     */
    @Getter
    @Setter
    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;

    /**
     * Aluno.
     */
    @Getter
    @Setter
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "fk_registration_tax_student"), nullable = false)
    private Student student;
    
}

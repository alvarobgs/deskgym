package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.enums.PaymentMethod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
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
 * Entidade de pagamento de mensalidade.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "class_payment")
public class ClassPayment extends AbstractEntity implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 1127175917252404896L;

    /**
     * Constante para chave estrangeira.
     */
    private static transient final String FK = "fk_class_payment_";

    /**
     * Nome do plano pago.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
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
     * Início do plano.
     */
    @Getter
    @Setter
    @NotEmpty
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    
    /**
     * Validade do plano.
     */
    @Getter
    @Setter
    @NotEmpty
    @Temporal(TemporalType.DATE)
    @Column(name = "validity_date", nullable = false)
    private Date validityDate;
    
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
     * Parcelas.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "plots", nullable = false)
    private int plots;
    
    /**
     * Descontos.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "discount", nullable = false, precision = 10, scale = 2)
    private BigDecimal discount;
    
    /**
     * Obsoleto.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "outDated", nullable = false)
    private boolean outDated;
    
    /**
     * Aula experimental.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "experimental_class", nullable = false)
    private boolean experimentalClass;
    
    /**
     * Plano.
     */
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "plan_id", foreignKey = @ForeignKey(name = FK + "plan"))
    private Plan plan;
    
    /**
     * Aluno.
     */
    @Getter
    @Setter
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = FK + "student"), nullable = false)
    private Student student;
    
    /**
     * Construtor.
     */
    public ClassPayment() {
        this.outDated = false;
        this.experimentalClass = false;
    }

	/**
	 * Se foi paga com dinheiro.
	 */
	public boolean isCashPayment() {
		return this.paymentMethod == PaymentMethod.CASH;
	}

	/**
     * Comparador para data de validade da mensalidade.
     */
    public static Comparator<ClassPayment> lastValidPaymentComparator = new Comparator<ClassPayment>() {
        public int compare(ClassPayment p1, ClassPayment p2) {
            return p1.getValidityDate().compareTo(p2.getValidityDate());
        }
    };
}

package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.enums.MonetaryMovementType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade de ganhos e gastos.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "monetary_movement")
public class MonetaryMovement extends AbstractEntity implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = -5288903364391006712L;

	/**
     * Nome.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    /**
     * Valor.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "value", precision = 10, scale = 2, nullable = false)
    private BigDecimal value;
    
    /**
     * Descrição.
     */
    @Getter
    @Setter
    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    /**
     * Data em que o pagamento/recebimento foi registrado.
     */
    @Getter
    @Setter
    @NotEmpty
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;
    
    /**
     * Tipo de movimentação
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @NotEmpty
    @Column(name = "type", nullable = false)
    private MonetaryMovementType type;

    @Override
    public int hashCode() {
		int hash = 7;
		hash = 29 * hash + Objects.hashCode(this.name);
		hash = 29 * hash + Objects.hashCode(this.value);
		hash = 29 * hash + Objects.hashCode(this.date);
		hash = 29 * hash + Objects.hashCode(this.type);
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
		final MonetaryMovement other = (MonetaryMovement) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (!Objects.equals(this.value, other.value)) {
			return false;
		}
		if (!Objects.equals(this.date, other.date)) {
			return false;
		}
		if (this.type != other.type) {
			return false;
		}
		return true;
    }

	/**
	 * Verifica se é um ganho.
	 *
	 * @return
	 */
	public boolean isProfit() {
    	return this.type == MonetaryMovementType.PROFIT;
	}
}

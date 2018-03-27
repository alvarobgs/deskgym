package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.dto.PlanDTO;
import br.com.abg.deskgym.utils.Converter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade plano.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "plan", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "duration"}, name = "uq_plan"))
public class Plan extends AbstractEntity implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = -188761967057667148L;

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
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
    
    /**
     * Duração.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "duration", nullable = false)
    private int duration;

    /**
     * Construtor.
     */
    public Plan() {}
    
    /**
     * Construtor sobrescrito
     * @param planDTO 
     */
    public Plan(final PlanDTO planDTO) throws NumberFormatException {
        this.price = Converter.convertMoneyString(planDTO.getValue());
        this.name = planDTO.getName();
        this.duration = Integer.parseInt(planDTO.getDuration());
    }
    
	@Override
    public int hashCode() {
		int hash = 5;
		hash = 79 * hash + Objects.hashCode(this.name);
		hash = 79 * hash + this.duration;
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
		final Plan other = (Plan) obj;
		if (this.duration != other.duration) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return true;
    }
}

package br.com.abg.deskgym.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade cidade.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "city", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "state_id"}, name = "uq_city"))
public class City extends AbstractEntity implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = -6872344841330494447L;

    /**
     * Constante para chave estrangeira.
     */
    private static transient final String FK = "fk_city_";

    /**
     * Nome.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(min = 1, max = 50)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    
    /**
     * Estado.
     */
    @Getter
    @Setter
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false, foreignKey = @ForeignKey(name = FK + "state"))
    private State state;

    /**
     * Construtor.
     */
    public City() {
        this.state = new State();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.state);
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
        final City other = (City) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }
}

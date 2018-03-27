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
 * Entidade estado.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "state", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "country_id"}, name = "uq_state"))
public class State extends AbstractEntity implements Serializable {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 9009978681075085216L;

    /**
     * Unidade federativa.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(min = 1, max = 10)
    @Column(name = "uf", nullable = false, length = 10)
    private String uf;
    
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
     * País.
     */
    @Getter
    @Setter
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false, foreignKey = @ForeignKey(name = "fk_state_country"))
    private Country country;

    /**
     * Construtor.
     */
    public State() {
        this.country = new Country();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.uf);
        hash = 29 * hash + Objects.hashCode(this.name);
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
        final State other = (State) obj;
        if (!Objects.equals(this.uf, other.uf)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}

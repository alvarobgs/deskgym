package br.com.abg.deskgym.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade país
 * 
 * @author alvaro
 */
@Entity
@Table(name = "country", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "abbreviation"}, name = "uq_country"))
public class Country extends AbstractEntity implements Serializable {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -24587006829660661L;

    /**
     * Código telefônico.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "phone_code", length = 5, nullable = false)
    private int phoneCode;
    
    /**
     * Sigla.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(min = 1, max = 10)
    @Column(name = "abbreviation", length = 10, nullable = false)
    private String abbreviation;
    
    /**
     * Nome.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(min = 1, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.abbreviation);
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
        final Country other = (Country) obj;
        if (!Objects.equals(this.abbreviation, other.abbreviation)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}

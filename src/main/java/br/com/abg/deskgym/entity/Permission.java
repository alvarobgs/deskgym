package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.enums.Operation;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade permissão.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "permission")
public class Permission extends AbstractEntity implements Serializable {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 8005329160252427052L;

    /**
     * Entidade.
     */
    @Getter
    @Setter
    @Size(max = 100)
    @Column(name = "entity", length = 100)
    private String entity;
    
    /**
     * Local (inutilizado para a versão desktop).
     */
    @Getter
    @Setter
    @Size(max = 100)
    @Column(name = "path", length = 100)
    private String path;
    
    /**
     * Tipo de operação.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 50)
    @Column(name = "operation", nullable = false)
    @Enumerated(EnumType.STRING)
    private Operation operation;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.entity);
        hash = 17 * hash + Objects.hashCode(this.path);
        hash = 17 * hash + Objects.hashCode(this.operation);
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
        final Permission other = (Permission) obj;
        if (!Objects.equals(this.entity, other.entity)) {
            return false;
        }
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        if (this.operation != other.operation) {
            return false;
        }
        return true;
    }
}

package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.utils.Validator;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

/**
 *  Entidade abstrata
 * 
 * @author alvaro
 */
@MappedSuperclass
public abstract class AbstractEntity {
    
    @Id
    @GeneratedValue
    @Getter
    @Setter
    protected Long id;
    
    @Version
    @Getter
    @Setter
    protected long version;
    
    @Override
    public String toString() {
        return "[id=" + String.valueOf(this.id) + "]";
    }
    
    /**
     * Verifica se o objeto está sendo persistido ou atualizado.
     */
    public boolean isSaving() {
	return Validator.isEmpty(this.id);
    }
}

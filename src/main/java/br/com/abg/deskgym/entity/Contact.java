package br.com.abg.deskgym.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade contato.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "contact")
public class Contact extends AbstractEntity implements Serializable {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -6730894739196367342L;

    /**
     * Celular.
     */
    @Getter
    @Setter
    @Size(max = 12)
    @Column(name = "cellphone", length = 12)
    private String cellPhone;
    
    /**
     * Telefone fixo.
     */
    @Getter
    @Setter
    @Size(max = 12)
    @Column(name = "telephone", length = 12)
    private String telephone;
    
    /**
     * E-mail.
     */
    @Getter
    @Setter
    @Size(max = 100)
    @Column(name = "mail", length = 100)
    private String mail;
    
    /**
     * Construtor.
     */
    public Contact() {}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.cellPhone);
        hash = 73 * hash + Objects.hashCode(this.telephone);
        hash = 73 * hash + Objects.hashCode(this.mail);
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
        final Contact other = (Contact) obj;
        if (!Objects.equals(this.cellPhone, other.cellPhone)) {
            return false;
        }
        if (!Objects.equals(this.telephone, other.telephone)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        return true;
    }
}

package br.com.abg.deskgym.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade endereço.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "address")
public class Address extends AbstractEntity implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 6732264457915009267L;

    /**
     * Constante para chave estrangeira.
     */
    private static transient final String FK = "fk_address_";

    /**
     * Logradouro.
     */
    @Getter
    @Setter
    @Size(max = 100)
    @Column(name = "place", length = 100)
    private String place;
    
    /**
     * Número.
     */
    @Getter
    @Setter
    @Size(max = 20)
    @Column(name = "number", length = 20)
    private String number;
    
    /**
     * Complemento.
     */
    @Getter
    @Setter
    @Size(max = 100)
    @Column(name = "complement", length = 100)
    private String complement;
    
    /**
     * CEP.
     */
    @Getter
    @Setter
    @Size(max = 9)
    @Column(name = "zip_code", length = 9)
    private String zipCode;
    
    /**
     * Bairro.
     */
    @Getter
    @Setter
    @Size(max = 100)
    @Column(name = "neighborhood", length = 100)
    private String neighborhood;
    
    /**
     * Cidade.
     */
    @Getter
    @Setter
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false, foreignKey = @ForeignKey(name = FK + "city"))
    private City city;

    /**
     * Construtor
     */
    public Address() {
        this.city = new City();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.number);
        hash = 41 * hash + Objects.hashCode(this.zipCode);
        hash = 41 * hash + Objects.hashCode(this.city);
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
        final Address other = (Address) obj;
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        if (!Objects.equals(this.zipCode, other.zipCode)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        return true;
    }
}

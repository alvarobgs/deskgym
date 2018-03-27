package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.enums.Gender;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *  Entidade abstrata para pessoa
 * 
 * @author alvaro
 */
@MappedSuperclass
public abstract class Person extends AbstractEntity {
    
    /**
     * Constante para chave estrangeira.
     */
    private static transient final String FK = "fk_person_";
    
    /**
     * Nome.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    protected String name;
    
    /**
     * Sexo.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 25)
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 25)
    protected Gender gender;
    
    /**
     * RG.
     */
    @Getter
    @Setter
    @Size(max = 20)
    @Column(name = "rg", length = 20)
    protected String rg;
    
    /**
     * CPF.
     */
    @Getter
    @Setter
    @Size(max = 20)
    @Column(name = "cpf", length = 20)
    protected String cpf;
    
    /**
     * Data de nacimento.
     */
    @Getter
    @Setter
    @NotEmpty
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_day", nullable = false)
    protected Date birthday;

    /**
     * Data de cadastro.
     */
    @Getter
    @NotEmpty
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date", nullable = false)
    protected Date registrationDate;
    
    /**
     * Observação geral.
     */
    @Getter
    @Setter
    @Size(max = 500)
    @Column(name = "observation", length = 500)
    protected String observation;
    
    /**
     * Foto.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id", foreignKey = @ForeignKey(name = FK + "picture"))
    protected Picture picture;
    
    /**
     * Endereço.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = FK + "address"))
    protected Address address;
    
    /**
     * Contato.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", foreignKey = @ForeignKey(name = FK + "contact"))
    protected Contact contact;

    /**
     * Construtor.
     */
    public Person() {
        this.address = new Address();
        this.contact = new Contact();
        this.picture = new Picture();
	this.registrationDate = new Date();
    }
    
    @Override
    public int hashCode() {
	int hash = 5;
	hash = 47 * hash + Objects.hashCode(this.name);
	hash = 47 * hash + Objects.hashCode(this.birthday);
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
	final Person other = (Person) obj;
	if (!Objects.equals(this.name, other.name)) {
	    return false;
	}
	if (!Objects.equals(this.birthday, other.birthday)) {
	    return false;
	}
	return true;
    }
}

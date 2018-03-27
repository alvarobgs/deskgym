package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.enums.EmployeeStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade funcionario.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "employee", uniqueConstraints = @UniqueConstraint(name = "uq_employee", columnNames = { "name", "birth_day" }))
public class Employee extends Person implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = -423150485093532676L;

    /**
     * Constante para chave estrangeira.
     */
    private static transient final String FK = "fk_employee_";

	/**
     * Status do funcionário.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 50)
    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;
    
    /**
     * Data de admissão.
     */
    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "admission_date")
    private Date admissionDate;
    
    /**
     * Dia do pagamento do salário.
     */
    @Getter
    @Setter
    @Column(name = "payment_day")
    private int paymentDay;
    
    /**
     * Cargo.
     */
    @Getter
    @Setter
    @Size(max = 100)
    @Column(name = "employee_role", length = 100)
    private String employeeRole;
    
    /**
     * Cref.
     */
    @Getter
    @Setter
    @Size(max = 100)
    @Column(name = "cref", length = 100)
    private String cref;
    
    /**
     * Lista de pagamentos recebidos.
     */
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<EmployeePayment> payments;
    
    /**
     * Salário.
     */
    @Getter
    @Setter
    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal salary;
    
    /**
     * Usuário.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = FK + "user"))
    private User user;
    
    /**
     * Construtor.
     */
    public Employee() {
        this.user = new User();
	this.payments = new ArrayList<>();
    }
    
    /**
     * Está ativo.
     */
    public boolean isActive() {
	return this.status == EmployeeStatus.ACTIVE;
    }
    
    /**
     * Retorna o último pagamento.
     */
    public EmployeePayment getLastPayment() {
        return null;
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
	final Employee other = (Employee) obj;
	if (!Objects.equals(this.name, other.name)) {
	    return false;
	}
	if (!Objects.equals(this.birthday, other.birthday)) {
	    return false;
	}
	return true;
    }
}

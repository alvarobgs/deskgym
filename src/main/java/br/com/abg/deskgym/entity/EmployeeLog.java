package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.enums.LogType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade log de atividade de funcionário.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "employee_log")
public class EmployeeLog extends AbstractEntity implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = -5491795565133551568L;

    /**
     * Constante para chave estrangeira.
     */
    private static transient final String FK = "fk_employee_log_";

    /**
     * Tipo.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 50)
    @Column(name = "log_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LogType logType;
    
    /**
     * Descrição.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 500)
    @Column(name = "description", nullable = false)
    private String description;
    
    /**
     * Data.
     */
    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;
    
    /**
     * Funcionário.
     */
    @Getter
    @Setter
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = FK + "employee"), nullable = false)
    private Employee employee;

    /**
     * Construtor.
     */
    public EmployeeLog() {
	    this.date = new Date();
    }

    /**
     * Construtor passando parâmetros.
     */
    public EmployeeLog(final Employee employee, final LogType logType, final String description) {
    	this.employee = employee;
    	this.logType = logType;
    	this.description = description;
        this.date = new Date();
    }
}

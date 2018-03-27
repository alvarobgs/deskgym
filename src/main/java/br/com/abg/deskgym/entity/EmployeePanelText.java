package br.com.abg.deskgym.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade de texto do funcionário.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "employee_panel_text")
public class EmployeePanelText extends AbstractEntity implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 5856121399109098466L;

    /**
     * Constante para chave estrangeira.
     */
    private static transient final String FK = "fk_employee_panel_text_";

	/**
     * Texto.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 5000)
    @Column(name = "text", nullable = false, length = 5000)
    private String text;
    
    /**
     * Funcionário.
     */
    @Getter
    @Setter
    @NotEmpty
    @OneToOne
    @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = FK + "employee"), nullable = false, unique = true)
    private Employee employee;

	/**
	 * Construtor.
	 */
	public EmployeePanelText() {}

	/**
	 * Construtor.
	 */
	public EmployeePanelText(final Employee employee, final String text) {
		this.employee = employee;
		this.text = text;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (!(o instanceof EmployeePanelText))
			return false;

		final EmployeePanelText that = (EmployeePanelText) o;

		if (!text.equals(that.text))
			return false;
		return employee.equals(that.employee);
	}

	@Override
	public int hashCode() {
		int result = text.hashCode();
		result = 31 * result + employee.hashCode();
		return result;
	}
}

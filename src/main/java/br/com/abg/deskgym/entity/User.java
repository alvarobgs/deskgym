package br.com.abg.deskgym.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
 * Entidade usuário.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"user_name"}, name = "uq_user"))
public class User extends AbstractEntity implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 1822463683662429912L;

    /**
     * Constante para chave estrangeira.
     */
    private static transient final String FK = "fk_user_";

    /**
     * Nome de usuário.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(min = 5, max = 50)
    @Column(name = "user_name", nullable = false, unique = true, length = 50)
    private String userName;
    
    /**
     * Dia do vencimento preferido.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 255)
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    /**
     * Último login.
     */
    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login")
    private Date lastLogin;
    
    /**
     * Funcionário.
     */
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = FK + "employee"))
    private Employee employee;
    
    /**
     * Aluno
     */
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = FK + "student"))
    private Student student;
    
    /**
     * Permissões do usuário.
     */
    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name = "user_has_permission", joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "permission_id"),
	    foreignKey = @ForeignKey(name = "user_has_permission_user"), 
	    inverseForeignKey = @ForeignKey(name = "user_has_permission_permission"))
    private List<Permission> permissions;
}

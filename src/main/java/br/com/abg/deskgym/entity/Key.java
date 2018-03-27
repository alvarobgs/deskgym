package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.enums.Gender;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade chave.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "key", uniqueConstraints = @UniqueConstraint(name = "uq_key", columnNames = { "number", "gender" }))
public class Key extends AbstractEntity implements Serializable {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 2455250500999435344L;

    /**
     * Nome do aluno.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(min = 5, max = 50)
    @Column(name = "student_name", nullable = false, length = 50)
    private String studentName;
    
    /**
     * Número da chave.
     */
    @Getter
    @Setter
    @NotEmpty
    @Column(name = "number", nullable = false)
    private int number;
    
    /**
     * Sexo.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 50)
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    /**
     * Construtor.
     */
    public Key() {}
    
    /**
     * Construtor sobrescrito.
     * 
     * @param studentName
     * @param index
     * @param gender
     */
    public Key(final String studentName, final int index, final Gender gender) {
        this.studentName = studentName;
        this.number = index;
        this.gender = gender;
    }
}

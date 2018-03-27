package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.enums.StudentStatus;
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
 * Entidade para registrar a mudança de status do aluno.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "student_status_change")
public class StudentStatusChange extends AbstractEntity implements Serializable {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -4213428890509241657L;

    /**
     * Aluno.
     */
    @Getter
    @Setter
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "fk_student_status_student"), nullable = false)
    private Student student;
    
    /**
     * Status antigo.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 50)
    @Column(name = "old_status", length = 50)
    @Enumerated(EnumType.STRING)
    private StudentStatus oldStatus;
    
    /**
     * Status novo.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 50)
    @Column(name = "new_status", length = 50)
    @Enumerated(EnumType.STRING)
    private StudentStatus newStatus;
    
    /**
     * Data.
     */
    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;    

    /**
     * Construtor.
     */
    public StudentStatusChange() {
        this.student = new Student();
        this.date = new Date();
    }
    
    /**
     * Construtor sobrescrito.
     * 
     * @param student
     * @param oldStatus
     */
    public StudentStatusChange(final Student student, final StudentStatus oldStatus) {
        this.student = student;
        this.oldStatus = oldStatus;
        this.newStatus = student.getStatus();
        this.date = new Date();
    }
}

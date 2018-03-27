package br.com.abg.deskgym.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade de agendamento de aula experimental.
 * 
 * @author alvaro
 */
@Entity
@Table(name = "experimental_class_schedule")
public class ExperimentalClassSchedule extends AbstractEntity implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 6285709114885195991L;

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
     * Celular do aluno.
     */
    @Getter
    @Setter
    @Size(max = 12)
    @Column(name = "cellphone", length = 12)
    private String cellPhone;
    
    /**
     * Data do agendamento da aula.
     */
    @Getter
    @Setter
    @NotEmpty
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;

    @Override
    public int hashCode() {
		int hash = 5;
		hash = 59 * hash + Objects.hashCode(this.studentName);
		hash = 59 * hash + Objects.hashCode(this.date);
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
		final ExperimentalClassSchedule other = (ExperimentalClassSchedule) obj;
		if (!Objects.equals(this.studentName, other.studentName)) {
			return false;
		}
		if (!Objects.equals(this.date, other.date)) {
			return false;
		}
		return true;
    }
    
    /**
     * Comparador por horário de aula.
     */
    public static Comparator<ExperimentalClassSchedule> timeCompare = new Comparator<ExperimentalClassSchedule>(){
        public int compare(ExperimentalClassSchedule e1, ExperimentalClassSchedule e2){
            return e1.getDate().compareTo(e2.getDate());
        }
    };
}

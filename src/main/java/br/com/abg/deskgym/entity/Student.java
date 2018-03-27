package br.com.abg.deskgym.entity;

import br.com.abg.deskgym.enums.StudentStatus;
import br.com.abg.deskgym.utils.DateUtil;
import br.com.abg.deskgym.utils.Validator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.Hibernate;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade aluno
 * 
 * @author alvaro
 */
@Entity
@Table(name = "student", uniqueConstraints = @UniqueConstraint(name = "uq_student", columnNames = { "name", "birth_day" }))
public class Student extends Person implements Serializable {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = 2350062941878363712L;

    /**
     * Constante para chave estrangeira.
     */
    private static transient final String FK = "fk_student_";

	/**
     * Status do aluno.
     */
    @Getter
    @Setter
    @NotEmpty
    @Size(max = 50)
    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private StudentStatus status;
    
    /**
     * Observação médica.
     */
    @Getter
    @Setter
    @Size(max = 500)
    @Column(name = "medic_observation", length = 500)
    private String medicObservation;
    
    /**
     * Dia do vencimento preferido.
     */
    @Getter
    @Setter
    @Column(name = "payment_day")
    private int paymentDay;
    
     /**
     * Lista de mensalidades.
     */
    @Getter
    @Setter
    @NotEmpty
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    private List<ClassPayment> payments;
    
    /**
     * Usuário
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = FK + "user"))
    private User user;
    
    /**
     * Atestado médico.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medical_certificate_id", foreignKey = @ForeignKey(name = FK + "medical_certificate"))
    private MedicalCertificate medicalCertificate;

	/**
     * Construtor.
     */
    public Student() {
		this.payments = new ArrayList<>();
		this.user = new User();
		this.medicalCertificate = new MedicalCertificate();
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
		final Student other = (Student) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (!Objects.equals(this.birthday, other.birthday)) {
			return false;
		}
		return true;
    }
    
    /**
     * Se está com mensalidade atrasada.
     * 
     * @return verdadeiro se está atrasado ou no tempo de carência.
     */
    public boolean haveOutdatedPayment() {
	return this.status == StudentStatus.DELAYED || this.status == StudentStatus.PENDING;
    }
    
    /**
     * Retorna a última mensalidade válida.
     * 
     * @return
     */
    public ClassPayment getLastClassPayment() {
		if (!Validator.isEmpty(this.payments)) {
			int size = this.payments.size();
			this.payments.sort(ClassPayment.lastValidPaymentComparator);
			return this.payments.get(size - 1);
		}
		return null;
    }

	/**
	 * Verifica se o aluno é novo.
	 *
	 * @return
	 */
	public boolean isBrandNew() {
		if (Validator.isEmpty(this.payments)) {
			Hibernate.initialize(this.payments);
		}
    	return this.status == StudentStatus.INACTIVE && Validator.isEmpty(this.payments);
	}

	/**
	 * Verifica se o aluno está ativo.
	 *
	 * @return
	 */
	public boolean isActive() {
		return this.status == StudentStatus.ACTIVE;
	}

	/**
	 * Seta o novo status do aluno de acordo com a validade da última mensalidade.
	 *
	 * @param daysToGetDelayed
	 * @param daysToGetInactivated
	 */
	public void generateNewStudentStatus(final int daysToGetDelayed, final int daysToGetInactivated) {
		Hibernate.initialize(this.payments);
		final Date validity = this.getLastClassPayment().getValidityDate();
		final Date today = new Date();
		final Date deadlineInactivate = DateUtil.addDaysToDate(validity, daysToGetInactivated);
		final Date deadlineDelay = DateUtil.addDaysToDate(validity, daysToGetDelayed);

		if (daysToGetInactivated > 0 && deadlineInactivate.before(today)) {
			this.status = StudentStatus.INACTIVE;
		} else if (daysToGetDelayed > 0) {
			if (deadlineDelay.before(today)) {
				if (daysToGetInactivated > 0 && deadlineInactivate.after(today)) {
					this.status = StudentStatus.DELAYED;
				} else {
					this.status = StudentStatus.DELAYED;
				}
			} else {
				this.status = StudentStatus.PENDING;
			}
		}
		//REALIZAR DIRETO NO BANCO
		//MUDAR O STATUS DOS ALUNOS ATIVOS PARA INADIMPLENTES
		//        List<Aluno> alunos = alunoDado.buscaTodosAlunosPorStatus(2);
		//        if (!alunos.isEmpty()) {
		//            for(Aluno aluno : alunos) {
		//                boolean inadimplente = true;
		//                List<Mensalidade> mensalidadesAluno = mensDado.buscarPorAluno(aluno.getId());
		//                for(Mensalidade mensalidadeAluno : mensalidadesAluno) {
		//                    if(mensalidadeAluno.getObsoleto() == 0 || mensalidadeAluno.getDataFim().plusDays(diasTolerancia+1).isAfter(LocalDate.now())) {
		//                        inadimplente = false;
		//                        break;
		//                    }
		//                }
		//                if(inadimplente) {
		//                    alunoDado.atualizarStatusAluno(aluno.getId(),1); //SALVA COMO INADIMPLENTE NO BANCO
		//                }
		//            }
		//        }
		//BUSCAR DATA LIMITE NAS CFGS DO SISTEMA
		//REALIZAR DIRETO NO BANCO

		//        int diasInatividade = confDado.selecionarDiasInatividade();
		//
		//        //MUDAR O STATUS DOS ALUNOS INADIMPLENTES PARA INATIVOS
		//        List<Aluno> alunosInativos = alunoDado.buscaTodosAlunosPorStatus(1);
		//        List<String> alunosInativados = new ArrayList<>();
		//
		//        if (!alunosInativos.isEmpty() && diasInatividade > 0){
		//            for (Aluno aluno : alunosInativos){
		//                boolean inativar = true;
		//                mensalidades = mensDado.buscarPorAluno(aluno.getId());
		//                for(Mensalidade mensalidade : mensalidades) {
		//                    if(mensalidade.getDataFim().plusDays(diasInatividade+1).isAfter(LocalDate.now()) || mensalidade.getObsoleto() == 0) {
		//                        inativar = false;
		//                    }
		//                }
		//                if(inativar) {
		//                    alunoDado.atualizarStatusAluno(aluno.getId(),0);
		//                    alunosInativados.add(aluno.getNome());
		//                }
		//
		//            }
		//        }
	}
    
    /**
     * Compara por nome do aluno.
     */
    public static Comparator<Student> nameComparator = new Comparator<Student>(){
        public int compare(Student stOne, Student stTwo){
            return stOne.getName().compareTo(stTwo.getName());      
        }
    };
    
    /**
     * Compara por dia de aniversário.
     */
    public static Comparator<Student> birthDayComparator = new Comparator<Student>(){
        public int compare(Student stOne, Student stTwo){
            return stOne.getBirthday().compareTo(stTwo.getBirthday());
        }
    };
}

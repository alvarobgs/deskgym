package br.com.abg.deskgym.enums;

/**
 * Enum para tipo de log.
 * 
 * @author Alvaro
 */
public enum LogType {

	/**
	 * Cadastrar aluno
	 */
	SAVE_STUDENT("Cadastrar aluno"),

	/**
	 * Editar aluno
	 */
	EDIT_STUDENT("Editar aluno"),

	/**
	 * Remover aluno
	 */
    REMOVE_STUDENT("Remover aluno"),

	/**
	 * Inativar aluno
	 */
    INACTIVE_STUDENT("Inativar aluno"),

	/**
	 * Cadastrar funcionário
	 */
    SAVE_EMPLOYEE("Cadastrar funcionário"),

	/**
	 * Editar funcionário
	 */
    EDIT_EMPLOYEE("Editar funcionário"),

	/**
	 * Remover funcionário
	 */
    REMOVE_EMPLOYEE("Remover funcionário"),

	/**
	 * Ativar funcionário
	 */
    ACTIVE_EMPLOYEE("Ativar funcionário"),

	/**
	 * Desativar funcionário
	 */
    INACTIVE_EMPLOYEE("Desativar funcionário"),

	/**
	 * Cadastrar plano
	 */
    SAVE_PLAN("Cadastrar plano"),

	/**
	 * Editar plano
	 */
    EDIT_PLAN("Editar plano"),

	/**
	 * Remover plano
	 */
    REMOVE_PLAN("Remover plano"),

	/**
	 * Cadastrar mensalidade
	 */
    SAVE_CLASS_PAYMENT("Cadastrar mensalidade"),

	/**
	 * Editar mensalidade
	 */
	EDIT_CLASS_PAYMENT("Editar mensalidade"),

	/**
	 * Remover mensalidade
	 */
    REMOVE_CLASS_PAYMENT("Remover mensalidade"),

	/**
	 * Cadastrar taxa extra para aluno
	 */
    SAVE_EXTRA_TAX_PAYMENT("Cadastrar taxa extra para aluno"),

	/**
	 * Editar taxa extra para aluno
	 */
	EDIT_EXTRA_TAX_PAYMENT("Editar taxa extra para aluno"),

	/**
	 * Remover taxa extra para aluno
	 */
    REMOVE_EXTRA_TAX_PAYMENT("Remover taxa extra para aluno"),

	/**
	 * Cadastrar pagamento de funcionário
	 */
    SAVE_EMPLOYEE_PAYMENT("Cadastrar pagamento de funcionário"),

	/**
	 * Editar pagamento de funcionário.
	 */
	EDIT_EMPLOYEE_PAYMENT("Editar pagamento de funcionário"),

	/**
	 * Remover pagamento de funcionário
	 */
    REMOVE_EMPLOYEE_PAYMENT("Remover pagamento de funcionário"),

	/**
	 * Cadastrar gasto
	 */
    SAVE_SPENT("Cadastrar gasto"),

	/**
	 * Editar gasto
	 */
    EDIT_SPENT("Editar gasto"),

	/**
	 * Remover gasto
	 */
    REMOVE_SPENT("Remover gasto"),

	/**
	 * Cadastrar ganho
	 */
    SAVE_PROFIT("Cadastrar ganho"),

	/**
	 * Editar ganho
	 */
    EDIT_PROFIT("Editar ganho"),

	/**
	 * Remover ganho
	 */
    REMOVE_PROFIT("Remover ganho"),

	/**
	 * Cadastrar aula experimental
	 */
	SAVE_EXPERIMENTAL_CLASS_SCHEDULE("Cadastrar aula experimental"),

	/**
	 * Editar aula experimental
	 */
	EDIT_EXPERIMENTAL_CLASS_SCHEDULE("Editar aula experimental"),

	/**
	 * Remover aula experimental
	 */
	REMOVE_EXPERIMENTAL_CLASS_SCHEDULE("Remover aula experimental"),

	/**
	 * Gerar relatório geral
	 */
    GENERATE_GERAL_REPORT("Gerar relatório geral"),

	/**
	 * Gerar relatório de alunos
	 */
    GENERATE_STUDENT_REPORT("Gerar relatório de alunos");

    /**
     * Enum em string.
     */
    private final String log;

    /**
     * Construtor com o parâmetro.
     */
    private LogType(final String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return this.log;
    }

}

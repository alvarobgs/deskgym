package br.com.abg.deskgym.session;

/**
 * Constantes.
 * 
 * @author Alvaro
 */
public final class Constant {

    /**
     * Constante para o host.
     */
	public static final String HOST = "localhost";

	/**
	 * Constante para a porta.
	 */
	public static final String PORT = "3306";

	/**
	 * Constante para o username.
	 */
	public static final String USER = "root";

	/**
	 * Constante para o password.
	 */
	public static final String PASS = "";

	/**
	 * Constante para o nome do banco de dados.
	 */
	public static final String DATABASE = "deskgym";
    
	/**
     * Frame login.
     */
    public static final String LOGIN_FRAME = "LOGIN_FRAME";
        
	/**
     * Frame principal.
     */
    public static final String MAIN_FRAME = "MAIN_FRAME";
    
    /**
     * Frame para criação de novo aluno.
     */
    public static final String NEW_STUDENT_FRAME = "NEW_STUDENT_FRAME";
    
    /**
     * Frame para busca e edição de aluno.
     */
    public static final String EDIT_STUDENT_FRAME = "EDIT_STUDENT_FRAME";
    
    /**
     * Frame para pagamento de mensalidade de aluno.
     */
    public static final String CLASS_PAYMENT_FRAME = "CLASS_PAYMENT_FRAME";
    
    /**
     * Frame para criação de novo funcionário.
     */
    public static final String NEW_EMPLOYEE_FRAME = "NEW_EMPLOYEE_FRAME";
    
    /**
     * Frame para busca e edição de funcionário.
     */
    public static final String EDIT_EMPLOYEE_FRAME = "EDIT_EMPLOYEE_FRAME";
    
    /**
     * Frame para pagamento de salário de funcionário.
     */
    public static final String EMPLOYEE_PAYMENT_FRAME = "EMPLOYEE_PAYMENT_FRAME";
    
    /**
     * Frame para criação de novo produto na loja.
     */
    public static final String NEW_SHOPPING_PRODUCT_FRAME = "NEW_SHOPPING_PRODUCT_FRAME";
    
    /**
     * Frame para registro de venda na loja.
     */
    public static final String SHOPPING_SALES_FRAME = "SHOPPING_SALES_FRAME";
       
    /**
     * Frame para gerenciamento de estoque da loja.
     */
    public static final String SHOPPING_MANAGEMENT_FRAME = "SHOPPING_MANAGEMENT_FRAME";
    
    /**
     * Frame para gerenciamento de movimentação financeira.
     */
    public static final String MONETARY_MOVEMENT_FRAME = "MONETARY_MOVEMENT_FRAME";
    
    /**
     * Frame para gerenciamento de planos da academia.
     */
    public static final String PLAN_MANAGEMENT_FRAME = "PLAN_MANAGEMENT_FRAME";
    
    /**
     * Frame para balanço por período.
     */
    public static final String PERIOD_BALANCE_FRAME = "PERIOD_BALANCE_FRAME";
    
    /**
     * Frame para estatísticas de alunos.
     */
    public static final String STUDENTS_STATISTICTS_FRAME = "STUDENTS_STATISTICTS_FRAME";
    
     /**
     * Frame para balanço anual.
     */
    public static final String ANUAL_BALANCE_FRAME = "ANUAL_BALANCE_FRAME";
    
    /**
     * Frame para log do sistema.
     */
    public static final String SYSTEM_LOG_FRAME = "SYSTEM_LOG_FRAME";
    
    /**
     * Frame para configurações do sistema.
     */
    public static final String SYSTEM_CONFIGURATION_FRAME = "SYSTEM_CONFIGURATION_FRAME";

	/**
	 * Gera a string para backup do banco.
	 * @return
	 */
	public static String gerenateDatabaseBackupCommand(final String mysqlPath, final String backupPath) {
   		final StringBuilder command = new StringBuilder();

   		command.append(mysqlPath);
		command.append(" -h");
		command.append(HOST);
		command.append(" -port");
		command.append(PORT);
		command.append(" -u ");
		command.append(USER);
		command.append(" -p");
		command.append(PASS);
		command.append(" --add-drop-database -B ");
		command.append(DATABASE);
		command.append(" -r ");
		command.append(backupPath);

   		return command.toString();
   	}
    
}
